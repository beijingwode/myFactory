package com.wode.api.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.api.facade.LoginFacade;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.UserExchangeFavorites;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.AppCartItemVo;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.query.ExchangeSuborderQuery;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.UserExchangeFavoritesService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.OrderUtils;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.ExchangeSubOrderVo;
import com.wode.factory.user.vo.ProductSpecificationsVo;

/**
 * 订单
 *
 * @author
 */
@Controller
@RequestMapping("/exchangeOrder")
@SuppressWarnings("unchecked")
public class ExchangeOrderController extends BaseController {
//	private static Logger logger = LoggerFactory.getLogger(ExchangeOrderController.class);
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ExchangeOrdersFacade ordersFacade;
	@Autowired
	private Dao dao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SuborderService suborderService;
	@Qualifier("ordersService") 
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	@Autowired
	private UserExchangeFavoritesService userExchangeFavoritesService;
	@Autowired
	private ShippingAddressService shippingAddressService;
	@Autowired
	private LoginFacade loginFacade;
    @Autowired
    private com.wode.factory.user.service.ProductService userService_productService;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;

	@Autowired
	private ProductService productService;

	@Autowired
	ProductSpecificationsService specificationsService;

	@Autowired
	private UserService userService;

    @Autowired
	private ShippingFacade shippingFacade;

	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	
	private static final int CART_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月

	static UploadService us = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);


	@RequestMapping(value = {"/confirmCartPage.user"})
    public ModelAndView confirmCartPage(HttpServletRequest request, ModelAndView model,String partNumbers,Long shippingAddressId,String backNum,String productIds) {
		
		model.addObject("partNumbers", partNumbers);
		model.addObject("orderType","cart");
		model.addObject("shippingAddressId",shippingAddressId);
		model.addObject("backNum",backNum);
		model.addObject("productIds",productIds);
		model.setViewName("exchangeOrder/order_confirm");
		return model;
	}

	//购物车确认订单并下单 
	@RequestMapping(value = {"/confirmCart.user"})
	@ResponseBody
	public ActResult<String> confirmCart(ModelMap model, HttpServletRequest request, String partNumbers) {
		/**
		 * ***********************************提取要购买的商品信息**********************************************
		 * */
		//从缓存中提取购物车商品信息
		String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_"
				+ loginUser.getId());
		if (null == cartJson || cartJson.trim().equals("")
				|| null == partNumbers || partNumbers.trim().equals("")) {
			return ActResult.fail("购买商品有误");
		}
		// 初始化购物车选中商品  (partNumbers--->商家ID+_+商品SKUID，多个商品以，分割)
		String[] selectProduct = partNumbers.substring(0, partNumbers.length()).split(",");
		Set<Long> skuIdSet = new HashSet<Long>();// 选中的商品  商品skuId集合
		for (String str : selectProduct) {
			String[] strs = str.split("_");
			long skuId = Long.parseLong(strs[1]);
			skuIdSet.add(skuId);
		}
		Cart cart = JsonUtil.getObject(cartJson, Cart.class);
		//获取购买的商品,并修改勾选商品的状态
		List<CartItem> clist = cart.setSelectProduct(skuIdSet);
		for (int i = clist.size()-1; i >=0; i--) {
			CartItem cartItem = clist.get(i);
			Long skuId = Long.parseLong(cartItem.getPartNumber());
			ProductSpecificationsVo sku = productSpecificationsService.findByIdCache(skuId,cartItem.getProductId().toString());
			if(sku != null) {
				if(!skuId.equals(sku.getId())) {
					cartItem.setPartNumber(sku.getId()+"");
				}
				
				boolean isLadder =  productSpecificationsService.resetPrice(sku, productService.findById(sku.getProductId(), false), loginUser,false,cartItem.getQuantity());
				if(cartItem.getPrice().compareTo(sku.getPrice())!=0 ||cartItem.getMaxCompanyTicket().compareTo(sku.getMaxFucoin())!=0
						 ||cartItem.getRealPrice().compareTo(sku.getInternalPurchasePrice())!=0 ){//判断价格，购物车价格和数据库价格不想等，取数据库价格
					cartItem.setPrice(sku.getPrice());
					cartItem.setMaxCompanyTicket(sku.getMaxFucoin());
					cartItem.setProductCode(sku.getProductCode());
					cartItem.setRealPrice(sku.getInternalPurchasePrice());
				}
				
				//判断是否试用了员工专享价如果有，阶梯价不起作用
				cartItem.setIsLadder(isLadder);
			} else {
				clist.remove(i);
			}
		}
		//将购物车中的商品修改状态后,放入缓存中
		redisUtil.setData(RedisConstant.CART_REDIS + "_" + loginUser.getId(), JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
		cart.setItems(clist);
		//获取根据商家id分组的数据
		Map<Long, List<CartItem>> map = cart.groupBySupplier();
		Iterator<Entry<Long, List<CartItem>>> it = map.entrySet().iterator();

		List<CartItem> exchangeCI = new ArrayList<CartItem>();
		List<AppCartItemVo> appCartItem = new ArrayList<AppCartItemVo>();
		//定义商家返回的包邮信息
		Map<String, Object> freeMap = new HashMap<String, Object>();
		while (it.hasNext()) {
			Map.Entry<Long, List<CartItem>> entry = it.next();
			AppCartItemVo av = new AppCartItemVo();
			av.setSupplierId(entry.getKey());
			av.setSupplierName(entry.getValue().get(0).getSupplierName());
			freeMap.put(""+entry.getKey(), shippingFacade.getFreeString(entry.getKey(), loginUser.getId()));
			String proJson = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+entry.getValue().get(0).getProductId(), RedisConstant.PRODUCT_REDIS_INFO);
			if(!StringUtils.isEmpty(proJson)) {
				Product p  = JsonUtil.getObject(proJson, Product.class);
				if(p!=null) {
					av.setShopId(p.getShopId());
				}
			}
			Iterator<CartItem> cartItemIterator = entry.getValue().iterator();
			while (cartItemIterator.hasNext()) {
				CartItem ci = cartItemIterator.next();
				if (!ci.isBuyFlag()) {
					cartItemIterator.remove();
				} else {
					if(ci.getSaleKbn() == 2) {
						ci.setRealAmount(ci.getRealPrice().multiply(NumberUtil.toBigDecimal(ci.getQuantity())));
						ci.setBenefitAmount(BigDecimal.ZERO);
						ci.setBenefitTicket(BigDecimal.ZERO);
						ci.setBenefitSelf(BigDecimal.ZERO);
						exchangeCI.add(ci);
					}
				}
			}
			av.setCartItemList(entry.getValue());
			appCartItem.add(av);
		}
		
		if (appCartItem.size() < 1) {
			return ActResult.fail("购买商品有误");
		}
		/**
		 * ***********************************提取要购买的商品信息**********************************************
		 **/

		// 加载常用地址
		List<ShippingAddress> shippingAddressList = shippingAddressService.findByUserId(loginUser.getId());

		/**
		 * ***********************************分配换领币**********************************************
		 **/
		BigDecimal userExchage = BigDecimal.ZERO;
		BigDecimal exchangeCanUse = BigDecimal.ZERO;
		if(!exchangeCI.isEmpty()) {
			// 获取员工换领币
			List<UserExchangeTicket> ets = getExchangeTicket(loginUser.getId());
			
			// 优先处理资格换领
			for (UserExchangeTicket et : ets) {
				userExchage = userExchage.add(this.getLeft(et));
				for (CartItem ci : exchangeCI) {
					if(et.getProductId().equals(ci.getProductId())) {
						BigDecimal left = this.getLeft(et);
						if(NumberUtil.isGreaterZero(left)) {
							if(ci.getRealAmount().compareTo(left)>0) {
								// 记录商品使用的换领币
								ci.setBenefitTicket(left);
								ci.setBenefitAmount(left);
								ci.setBenefitSelf(left);
								
								et.setUsedAmount(et.getUsedAmount().add(left));
							} else {
								// 记录商品使用的换领币
								ci.setBenefitTicket(ci.getRealAmount());
								ci.setBenefitAmount(ci.getRealAmount());
								ci.setBenefitSelf(ci.getRealAmount());
								
								et.setUsedAmount(et.getUsedAmount().add(ci.getRealAmount()));
							}
						}
					}
				}
			}
			
			// 处理剩余商品
			for (CartItem ci : exchangeCI) {
				BigDecimal needPay = this.getNeedPay(ci);
				if(NumberUtil.isGreaterZero(needPay)) {
					// 需要使用共享金额
					for (UserExchangeTicket et : ets) {
						BigDecimal left = this.getLeft(et);
						// 判断换领币是否有剩余
						if(NumberUtil.isGreaterZero(left)) {
							
							BigDecimal bticket = BigDecimal.ZERO;
							// 记录券使用量
							if(needPay.compareTo(left)>0) {
								bticket = left;
							} else {
								bticket = needPay;
							}
							// 记录商品使用的换领币
							ci.setBenefitTicket(ci.getBenefitTicket().add(bticket));
							et.setUsedAmount(et.getUsedAmount().add(bticket));
							ci.setBenefitAmount(ci.getBenefitAmount().add(bticket));
							needPay = needPay.subtract(bticket);
						}
						
						if(!NumberUtil.isGreaterZero(needPay)) {
							// 全部抵扣 
							break;
						}
					}
				}

				exchangeCanUse = exchangeCanUse.add(ci.getBenefitTicket());
			}
		}
		if(loginUser.getEmployeeType()==1) {//员工账户
			EnterpriseUser eu= userService.getEmpById(loginUser.getId());
			if(eu!=null) {
				if(StringUtils.isEmpty(eu.getSectionName())) {
					loginUser.setSectionName("111");
				}else {
					loginUser.setSectionName(eu.getSectionName());
				}
				if(StringUtils.isEmpty(eu.getName())) {
					loginUser.setRealName("test");
				}else {
					loginUser.setRealName(eu.getName());
				}
			}
		}
		model.addAttribute("userExchage", userExchage);
		model.addAttribute("exchangeCanUse", exchangeCanUse);
		
		model.addAttribute("cart", appCartItem);
		model.addAttribute("freeMap",freeMap);
		model.addAttribute("shippingAddressList", shippingAddressList);
		return ActResult.success(model);
	}

	/**
	 * 创建订单（增加企业券使用）
	 *
	 * @time 2015-6-23
	 * @author 谷子夜
	 * 增加企业券使用
	 * update 2015-10-8
	 */
	@RequestMapping(value = {"/create.user"})
	@ResponseBody
	public ActResult<Object> create(HttpServletRequest request, String fromType,ExchangeOrders order,
			String useExchangeTicket, String useExchangeCash, String useExchangeSelf, BigDecimal useCash, 
			Long shippingAddressId,	String sku_nums,String sku_freights,String message,String freeSwap) {

		try {
			if(useCash==null) useCash=BigDecimal.ZERO;
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + loginUser.getId());
			Cart cart = JsonUtil.getObject(cartJson, Cart.class);
			if(StringUtils.isEmpty(fromType)) {
				fromType="app";
			}
			List<ProductSpecifications> outSkus = new ArrayList<ProductSpecifications>();
			ActResult<Cart> actCart = this.makeBuyCart(fromType, sku_nums, sku_freights, useExchangeTicket,
					useExchangeCash, useExchangeSelf, loginUser, cart, outSkus);

			if(!actCart.isSuccess()) {
				ActResult<Object> rtn = ActResult.fail(actCart.getMsg());
				if(actCart.getData()!=null && !StringUtils.isEmpty(actCart.getData().getErrKey())) {
					rtn.setData(actCart.getData().getErrKey());
				}
				return rtn;
			}
			//获取购物车全部商品信息
			List<CartItem> cartItemList = actCart.getData().getAllItems();
			
			//页面保存运费信息
			ShippingAddress addr = null;
			// 自提
			if("1".equals(order.getSelfDelivery())) {
				OrderUtils.setMobileAndAddress(order, loginUser);
			} else {
				order.setSelfDelivery("0");
				addr = shippingAddressService.getById(loginUser.getId(),shippingAddressId);
				if(StringUtils.isEmpty(addr)) {
					return ActResult.fail("请指定收货地址");
				}
				order.setAid(addr.getAid());				
				order.setName(addr.getName());
				order.setPhone(addr.getPhone());
				order.setMobile(addr.getPhone());
				order.setAddress(addr.getProvinceName()+" "+addr.getCityName()+" "+addr.getAreaName()+" "+addr.getAddress());
			}
			
			// 更新购物车中选中商品的企业券使用量
			List<CartItem> lstForCheckFreights = new ArrayList<CartItem>();
			Map<String,Long> skuProduct = new HashMap<String,Long>();
			Map<Long,Product> productMap = new HashMap<Long,Product>();
			Map<Long,Integer> numMap = new HashMap<Long,Integer>();
			Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();

			for (CartItem ci : cartItemList) {				
				//直接从数据库查询商品信息
				Product product = productService.findById(ci.getProductId(), false);

				//////////////////////////////////////////////////////////////////////////////////////////////
				//运费相关设置
				//sku 对应的商品及数量合并
				skuProduct.put(ci.getPartNumber(), ci.getProductId());
				if(!productMap.containsKey(ci.getProductId())) {
					productMap.put(ci.getProductId(), product);
				}
				
				Integer num = ci.getQuantity();
				// 换领商品不计内购券
				ci.setMaxCompanyTicket(BigDecimal.ZERO);
				ci.setCompanyTicket(BigDecimal.ZERO);
				BigDecimal amount =ci.getRealPrice().multiply(NumberUtil.toBigDecimal(num));
				
				if(numMap.containsKey(product.getId())) {
					num= num+numMap.get(product.getId());
				}
				numMap.put(product.getId(), num);

				//保存金额
				if(amountMap.containsKey(product.getId())) {
					amount = amountMap.get(product.getId()).add(amount);
				}
				amountMap.put(product.getId(), amount);
				
				//设置运费
				lstForCheckFreights.add(ci);
				//////////////////////////////////////////////////////////////////////////////////////////////
			}
			
			//check 运费
			ActResult<Object> chkfr = checkFreight(order.getSelfDelivery(), addr == null ? null : addr.getAid(),
					lstForCheckFreights, skuProduct, productMap, numMap, amountMap, loginUser.getId());
			if(!chkfr.isSuccess()) {
				return chkfr;
			}
			
			if(StringUtils.isEmpty(order.getUserId())) {
				order.setUserId(loginUser.getId());
			} else {
				if(!order.getUserId().equals(loginUser.getId())) {
					System.out.println("------------System Error Exchange uid="+ order.getUserId() +",loginUserId=" + loginUser.getId() + "--------------------------------");
				}
			}
			order.setCreateTime(new Date());
			
			List<String> subIds = new ArrayList<String>();
			//String message=null;
			try{
				ActResult<Object> ar = ordersFacade.createOrder(order, actCart.getData(),useCash,subIds,message,freeSwap);// 保存订单
				// 清空缓存
				if (!ar.isSuccess()) {
					return ar;
				}
//				//记录内购券流水
//				ActResult<Object> recordFlowResult = ordersService.recordFlow(order.getOrderId(), loginUser);
//				if (recordFlowResult == null || !recordFlowResult.isSuccess()) {
//					ordersFacade.cancelOrder(loginUser, order.getOrderId(), recordFlowResult.getMsg());
//					return ActResult.fail("流水记录失败，订单已取消，请联系客服");
//				}
//				
//				for (String string : subIds) {
//					ShopPushUtil.pushMsg4order(redisUtil,string,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
//				}
				cart.clearBuied(cartItemList);
				redisUtil.setData(RedisConstant.CART_REDIS + "_" + loginUser.getId(), JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
				return ar;
			} catch (BenefitLessException e) {
				return ActResult.fail("换领币余额不足，请刷新后重试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("参数错误");
		}
	}

	private ActResult<Cart> makeBuyCart(String fromType,String skuNums,String skuFreights, 
			String useExchangeTicket, String useExchangeCash,String useExchangeSelf,
			UserFactory user,Cart cart,List<ProductSpecifications> outSkus) {
		// 检查购物车，防止下单重复操作
		if(cart.getAllItems().isEmpty()){
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}
					
		// 创建结算购物车
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long,Integer> sku_nums = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> sku_freights = new HashMap<Long,BigDecimal>();
		Map<Long,BigDecimal> sku_realPrices = new HashMap<Long,BigDecimal>();
		Map<Long,String> sku_images = new HashMap<Long,String>();
		Map<Long,String> sku_pageKeys = new HashMap<Long,String>();
		Map<Long,String> sku_froms = new HashMap<Long,String>();
		int errCnt=0;

		// 处理sku数量参数
		if(!StringUtils.isEmpty(skuNums)) {
			if(skuNums.endsWith(",")) {
				skuNums=skuNums.substring(0, skuNums.length());
			}
			String[] arySkun = skuNums.split(",");
			for (String str : arySkun) {
				String[] strs = str.split("_");
				Long skuId=NumberUtil.toLong(strs[0]);
				sku_nums.put(skuId, NumberUtil.toInteger(strs[1]));
			}			
		}
		if(skuFreights.endsWith(",")) {
			skuFreights=skuFreights.substring(0, skuFreights.length());
		}
		String[] arySkuf = skuFreights.split(",");
		for (String str : arySkuf) {
			String[] strs = str.split("_");
			Long skuId=NumberUtil.toLong(strs[0]);
			skuIds.add(skuId);
			sku_freights.put(skuId, NumberUtil.toBigDecimal(strs[1]));
			
			CartItem rds = cart.getItem(skuId);
			if(rds == null) {
				if(!sku_nums.containsKey(skuId)) {
					return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
				}

				errCnt++;
				ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuId,null);
				outSkus.add(sku);
	            if(null == sku){
	            	return ActResult.fail("商品不存在,或已下架,请返回购物车后再次购买");
	            }
				Product product = this.userService_productService.getById(sku.getProductId());
				productSpecificationsService.resetPrice(sku, product, user,false,sku_nums.get(skuId));
				
				sku_realPrices.put(skuId, sku.getInternalPurchasePrice());
				sku_images.put(skuId, productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
				sku_pageKeys.put(skuId, "");
				sku_froms.put(skuId, "");
			} else {
				sku_realPrices.put(skuId, rds.getRealPrice());
				sku_images.put(skuId, rds.getImagePath());
				sku_pageKeys.put(skuId, rds.getPageKey());
				sku_froms.put(skuId, rds.getFromType());
				if(!sku_nums.containsKey(skuId)) {
					sku_nums.put(skuId, rds.getQuantity());
				}
			}			
		}

		if(errCnt== skuIds.size()){
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}
		ActResult<Cart> actCart = ordersService.mergeBuyCart(fromType, user,skuIds, sku_nums,sku_realPrices, sku_freights,
				sku_images, sku_pageKeys, sku_froms,null, useExchangeTicket, useExchangeCash, useExchangeSelf, outSkus);
		return actCart;
	}
	
	@RequestMapping("query.user")
	@ResponseBody
	public ActResult<PageInfo> query(Integer status, Integer exchangeStatus, Integer page, Integer pageSize) {
		
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize==null|| pageSize < 1) {
			pageSize=5;
		}
		ExchangeSuborderQuery query = new ExchangeSuborderQuery();
		query.setUserId(loginUser.getId());
		query.setStatus(status);
		query.setPageNumber(page);
		query.setPageSize(pageSize);
		query.setStatus(status);
		query.setExchangeStatus(exchangeStatus);
		// 查询不同状态订单列表
		return ActResult.success(exchangeSuborderService.findPage(query));
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getBatchInfo.user")
	@ResponseBody
	public ActResult<PageInfo> getBatchInfo(Long batchId,ModelMap model) {
		ExchangeSuborder q = new ExchangeSuborder();
		q.setBatchId(batchId);
		q.setUserId(loginUser.getId());
		List<ExchangeSuborder> exSubOrders = exchangeSuborderService.selectByModel(q);
		for (ExchangeSuborder exchangeSuborder : exSubOrders) {
			Supplier s = supplierDao.getById(exchangeSuborder.getSupplierId());
			if(s!=null) {
				exchangeSuborder.setSupplierName(s.getComName());
			} else {
				exchangeSuborder.setSupplierName("");
			}
			exchangeSuborderService.selectItems4Set(exchangeSuborder);
		}
		com.github.pagehelper.PageInfo pageOrder = suborderService.getOrderByUserId(loginUser.getId(), batchId, null, 1,100);
		
		model.addAttribute("exSubOrders", exSubOrders);
		model.addAttribute("subOrders", pageOrder.getList());
		// 查询不同状态订单列表
		return ActResult.success(model);
	}
	

	/**
	 * 取消订单
	 * @param request
	 * @param subOrderId   子单id
	 * @param closeReason  取消原因
	 * @return
	 */
	@RequestMapping("/cancelOrder.user")
	@ResponseBody
	public ActResult<String> cancelOrder(HttpServletRequest request, String subOrderId, String closeReason) {
		UserFactory user = loginUser;
		ActResult<String> ac = new ActResult<String>();
		try {
			ac = ordersFacade.cancel(user, subOrderId, closeReason,-1,true);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail(ac.getMsg());
		}
		return ac;
	}	
	
	/**
	 * 删除订单
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/deleteOrder.user")
	@ResponseBody
	public ActResult<Object> deleteOrder(HttpServletRequest request, String subOrderId) {
		UserFactory user = loginUser;
		ActResult<Object> ac = exchangeSuborderService.delete(user,subOrderId);
		//ActResult<Object> ac = suborderService.delete(user, subOrderId);
		return ac;
	}
	
	/**
	 * 可选换领商品列表，根据余额查询换领商品，并且排除已加入调剂清单商品
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getSelectable.user")
	@ResponseBody
	public ActResult<QueryResult> getSelectable(Integer page, Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize ==null || pageSize < 1) {
			pageSize=5;
		}
		
		// 查询购物团列表
		return ActResult.success(userExchangeFavoritesService.getSelectable(page, pageSize, loginUser.getId()));
	}

	/**
	 * 调剂清单列表
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("myFavorites.user")
	@ResponseBody
	public ActResult<QueryResult> myFavorites(Integer page, Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize ==null || pageSize < 1) {
			pageSize=5;
		}
		
		UserExchangeFavorites q = new UserExchangeFavorites();
		q.setUserId(loginUser.getId());
		List<UserExchangeFavorites> result= userExchangeFavoritesService.selectByModel(q);
		
		// 查询购物团列表
		Pager pager = dao.createPager(page, pageSize);
		pager.setRecordCount(result.size());
		int toIndex=page*pageSize;
		if(toIndex>result.size()) {
			toIndex=result.size();
		}
		int fromIndex = (page-1)*pageSize;
		while(fromIndex >= toIndex && fromIndex>0) {
			page--;
			fromIndex = (page-1)*pageSize;
		}
		return ActResult.success(new QueryResult(result.subList(fromIndex, toIndex), pager));
	}
	/**
	 * 微信心愿订单
	 * @return
	 */
	@RequestMapping("towishPage.user")
	public ModelAndView towishPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView result = new ModelAndView();
		result.setViewName("exchangeOrder/wish_list");
		return result;
	}
	/**
	 * 调剂清单列表
	 * @param id 调剂清单id
	 * @return
	 */
	@RequestMapping("delFavorite.user")
	@ResponseBody
	public ActResult<String> delFavorite(Long id) {
		userExchangeFavoritesService.removeById(id);
		return ActResult.success("");
	}

	/**
	 * 调剂清单列表
	 * @param id 调剂清单id
	 * @return
	 */
	@RequestMapping("addFavorite.user")
	@ResponseBody
	public ActResult<String> addFavorite(Long productId) {
		return userExchangeFavoritesService.addFavorites(loginUser.getId(), productId);
	}
	
	/**
	 * 获取 换领总数及余额
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getExchageTicket.user")
	@ResponseBody
	public ActResult<Map<String, Object>> getExchageTicket(HttpServletRequest request, HttpServletResponse response) {
		
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal balance = BigDecimal.ZERO;
		List<UserExchangeTicket> ets = getExchangeTicket(loginUser.getId());
		for (UserExchangeTicket userExchangeTicket : ets) {
			total=total.add(userExchangeTicket.getEmpAvgAmount());
			balance=balance.add(userExchangeTicket.getUsedAmount());	// 暂时记录已使用
		}
		Map<String,Object> rtnDate = new HashMap<String,Object>(); 
		rtnDate.put("total", total);
		rtnDate.put("balance", total.subtract(balance));
		
		// 查询购物团列表
		return ActResult.success(rtnDate);
	}
	
	/**
	 * 微信订单
	 * @param uid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/batchPage.user")
	public ModelAndView page(Long batchId,ModelAndView model,HttpServletRequest request){
		model.addObject("batchId", batchId);
		model.setViewName("exchange_adjust_details");
		return model;
	}
	
	/**
	 * 我的换领
	 * @param uid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/myhl.user")
	public ModelAndView myhl(ModelAndView model,HttpServletRequest request){
		model.setViewName("exchangeOrder/my_hl");
		return model;
	}
	
	/**
	 * 跳转到匹配中的订单详情
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/exchangeOrderDetailPageEx.user")
	public ModelAndView exchangeOrderDetailPageEx(ModelAndView model, String subOrderId,HttpServletRequest request){
		UserFactory user = loginUser;
		if(user.getRole() == 9) {
			model.addObject("uid", user.getId());
			model.addObject("subOrderId", subOrderId);
			model.setViewName("exchangeOrder/exchangeOrder_detailsEx");
			return model;
		}
		return null;
	}
	
	
	/**
	 * 查询匹配中的订单详情（线下发货）
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/exchangeOrderDetail.user")
	@ResponseBody
	public ActResult<Map<String, Object>> exchangeOrderDetail(HttpServletRequest request, String subOrderId){
		SuborderQuery query = new SuborderQuery();
//		query.setUserId(loginUser.getId());
		query.setSubOrderId(subOrderId);
		ExchangeSubOrderVo subOrder = exchangeSuborderService.findExchangeOrderDetailById(query);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("order", subOrder);

		return ActResult.success(data);
	}
	
    //超时取消订单
    @RequestMapping("/autoCancelOrder.tj")
    @ResponseBody
    public ActResult<String> autoCancelOrder(HttpServletRequest request,Long userId, String subOrderId,String closeReason,Integer exchangeStatus) {
        try {
        	UserFactory user = userService.getById(userId);
        	
        	if(user==null){
        		return ActResult.fail("");
        	}
        	if(exchangeStatus==null) exchangeStatus=-1;
        	ActResult<String> rtn= ordersFacade.cancel(user, subOrderId ,closeReason,exchangeStatus,true);
        	if(exchangeStatus == 3) {
        		// 调剂换领失败
        		if(rtn.isSuccess()) {
        			loginFacade.doPushNotify(userId, null, "换领失败", "很遗憾，您的商品没有领取成功。您的换领币及现金已退回。再来领别的试试吧~试试设置调剂商品，匹配成功的几率会增大哦~");
        			
        			Map<String,Object> param = new HashMap<String,Object>();
        			param.put("first", "很遗憾，您的商品没有领取成功。");
        			param.put("remark", "您的换领币及现金已退回。再来领别的试试吧~\n试试设置调剂商品，匹配成功的几率会增大哦~");
        			param.put("subOrderId", subOrderId);
        			param.put("exchangeStatus", "匹配失败");
        			loginFacade.doPushWxTemplateMsg(userId, WxOpenService.TEMPLATE_TYPE_RESERVATIONL_FAILURE, param);
        		}
        	}
        	return rtn;
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("");
		}
    }

	@RequestMapping(value = { "/submitOrder.tj" })
	@ResponseBody
	public ActResult<Object> submitOrder(Long userId,String subOrderId,String updateBy) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		if(ordersFacade.submitOrder(subOrderId,updateBy)) {
			result.setSuccess(true);
			List<String> ls =new ArrayList<String>();
			ls.add(subOrderId);
			ShopPushUtil.pushMsg4order(redisUtil,ls,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
			 
 			loginFacade.doPushNotify(userId, null, "换领匹配成功", "恭喜您！您的商品已匹配成功。商家将尽快发货，请您耐心等待。");
			
 			Map<String,Object> param = new HashMap<String,Object>();
 			param.put("first", "恭喜您！您的商品已匹配成功。");
 			param.put("remark", "商家将尽快发货，请您耐心等待。");
 			param.put("subOrderId", subOrderId);
 			param.put("exchangeStatus", "匹配成功");
 			loginFacade.doPushWxTemplateMsg(userId, WxOpenService.TEMPLATE_TYPE_RESERVATIONL_SUCCESS, param);
		}
		return result;
	}

	@RequestMapping(value = { "/mergeOrder.tj" })
	@ResponseBody
	public ActResult<Object> mergeOrder(Long userId,Long orderId,String subOrderIds,String sku_nums,String updateBy) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		List<String> subIds = new ArrayList<String>();
		if(ordersFacade.mergeOrder(userId, orderId, subOrderIds, sku_nums, updateBy, subIds)) {
			ShopPushUtil.pushMsg4order(redisUtil,subIds,ShopPushUtil.PUSH_TYPE_ORDER_PAY);

 			loginFacade.doPushNotify(userId, null, "换领调剂成功", "恭喜您！虽然您的商品没有匹配成功，但是我们根据您的调剂清单为您调剂了商品。结余的换领币及现金已退回。商家将尽快发货，请您耐心等待。");
			
 			Map<String,Object> param = new HashMap<String,Object>();
 			param.put("first", "恭喜您！虽然您的商品没有匹配成功，但是我们根据您的调剂清单为您调剂了商品。");
 			param.put("remark", "结余的换领币及现金已退回。商家将尽快发货，请您耐心等待。");
 			param.put("subOrderId", subIds.get(0));
 			param.put("exchangeStatus", "调剂成功");
 			loginFacade.doPushWxTemplateMsg(userId, WxOpenService.TEMPLATE_TYPE_RESERVATIONL_SUCCESS, param);
			
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 获取可用换领币
	 * @param t
	 * @return
	 */
	private BigDecimal getLeft(UserExchangeTicket t) {
		return t.getEmpAvgAmount().subtract(t.getUsedAmount());
	}

	/**
	 * 获取可用换领币
	 * @param t
	 * @return
	 */
	private BigDecimal getNeedPay(CartItem ci) {
		return ci.getRealAmount().subtract(ci.getBenefitAmount());
	}
	
	private List<UserExchangeTicket> getExchangeTicket(Long userId) {
		return userExchangeTicketService.usingTicket(userId);
	}	

	/**
	 * 检查运费是否发生变化
	 * @param areaCode
	 * @param lstForCheckFreights
	 * @param skuProduct
	 * @param productMap
	 * @param numMap
	 * @return
	 */
	private ActResult<Object> checkFreight(String selfDelivery,String areaCode, List<CartItem> lstForCheckFreights, Map<String, Long> skuProduct,
			Map<Long, Product> productMap, Map<Long, Integer> numMap,Map<Long,BigDecimal> amountMap,Long userId) {
		////////////////////////////////////////////////////////////////////////////////
		//check 运费
		//根据商品、数量、收货地址计算运费
		Map<Long,List<Long>> supplierMap = new HashMap<Long,List<Long>>();
		Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();
		for (Long pid : productMap.keySet()) {
			BigDecimal freight = shippingFacade.chkLimitCntAndArea(productMap.get(pid), numMap.get(pid), areaCode, null,userId,selfDelivery);
			if(ShippingFacade.productLimitCnt.compareTo(freight)==0) {
				return ActResult.fail("已超过商品:"+productMap.get(pid).getName()+"的限购数量,不能购买该商品了。或者减少购买数量");
			} else if(ShippingFacade.productAreaLimitCnt.compareTo(freight)==0){
				return ActResult.fail("商品:"+productMap.get(pid).getName()+"不能够配送到指定收货地址,不能购买该商品了，或者选择其他收货地址");
			} 
			freightMap.put(pid, freight);
			
			List<Long> lc = supplierMap.get(productMap.get(pid).getSupplierId());
			if(lc == null) {
				lc = new ArrayList<Long>();
				supplierMap.put(productMap.get(pid).getSupplierId(), lc);
			}
			
			lc.add(pid);
		}
		
		//重新计算商品的运费已商家为单位
		for (Long sid : supplierMap.keySet()) {

			//新版运费计算
			shippingFacade.calculateSupplierShippingFee(selfDelivery, areaCode, userService.getById(userId),
					sid, supplierMap.get(sid), productMap, numMap, amountMap, freightMap, null);
		    
		}
		
		for(CartItem ci:lstForCheckFreights) {
			Long key=skuProduct.get(ci.getPartNumber());
			if(freightMap.containsKey(key)) {
				if(ci.getFreight().compareTo(freightMap.get(key))!=0){
					return ActResult.fail("商家修改了:"+ci.getProductName()+"商品的运费,请重新下单");
				}
				freightMap.remove(key);
			} else {
				if(ci.getFreight().compareTo(BigDecimal.ZERO)!=0){
					return ActResult.fail("商家修改了:"+ci.getProductName()+"商品的运费,请重新下单");
				}
			}
		}
		////////////////////////////////////////////////////////////////////////////////
		
		return ActResult.success(null);
	}
	/**
	 * 获取可能可能加入的匹配商品
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = { "/getMaySelectable.user" })
	@ResponseBody
	public ActResult<Object> getMaySelectable(BigDecimal productPrice) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		// 计算区间
		BigDecimal sum = exchangeSuborderService.getOrderAmount(loginUser.getId());
		BigDecimal newsum = sum.add(productPrice);
		List<HashMap<String, Object>> searchs =userExchangeFavoritesService.getMaySelectable(loginUser.getId(), sum, newsum);
		return result.success(searchs);
	}
	/**
	 * 获取可换领商品
	 * @param orderId
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping(value = { "/queryProduct.user" })
	@ResponseBody
	public ActResult<Object> queryProduct(Long orderId,String subOrderId){
		ExchangeSuborder subOrder =null;
		if(!StringUtils.isEmpty(subOrderId)){
			subOrder = exchangeSuborderService.getById(subOrderId);
		}else{
			ExchangeSuborder q = new ExchangeSuborder();
			q.setOrderId(Long.valueOf(orderId));
			q.setUserId(loginUser.getId());
			subOrder = exchangeSuborderService.selectByModel(q).get(0);
		}
		if("1".equals(subOrder.getFreeSwap())) {
			List<UserExchangeFavorites> autoAddFavorites = userExchangeFavoritesService.autoAddFavorites(loginUser.getId(), subOrder);
			return ActResult.success(autoAddFavorites);
		}else {
			return ActResult.success("0");
		}
	}
	/**
	 * 添加可购商品
	 * @param Products
	 * @return
	 */
	@RequestMapping(value = { "/addFavorites.user" })
	@ResponseBody
	public ActResult<Object> addFavorites(String Products){
		if(StringUtils.isEmpty(Products)) {
			return ActResult.fail("");
		}
		String[] productIdArr = Products.split(",");
		for (String productId : productIdArr) {
			userExchangeFavoritesService.addFavorites(loginUser.getId(), Long.valueOf(productId));
		}
		return ActResult.success("");
	}
}