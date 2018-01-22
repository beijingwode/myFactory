package com.wode.api.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.model.Currency;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.Invoice;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.GroupBuyUserService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.GroupOrdersFacade;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.AppCartItemVo;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.GroupSuborderItemService;
import com.wode.factory.user.service.GroupSuborderService;
import com.wode.factory.user.service.InvoiceService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserContactsService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.EasemobIMUtils;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.GroupOrderVO;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.GroupBuyUserVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.factory.vo.GroupOrdersVo;

/**
 * 订单
 *
 * @author
 */
@Controller
@RequestMapping("/groupOrder")
@SuppressWarnings("unchecked")
public class GroupOrderController extends BaseController {
//	private static Logger logger = LoggerFactory.getLogger(GroupOrderController.class);
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private OrdersService ordersService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ProductService productService;

	@Autowired
	ProductSpecificationsService specificationsService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private UserBalanceService userBalanceService;

	@Autowired
	private UserService userService;
	//团购订单表
	@Autowired
	private GroupSuborderService groupSuborderService;
    @Autowired
	private ShippingFacade shippingFacade;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
    @Autowired
    private com.wode.factory.user.service.ProductService userService_productService;
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	@Autowired
	private UserImGroupService userImGroupService;
	
	private static final int CART_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月

	static UploadService us = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);

	@Autowired
	private GroupOrdersFacade groupOrdersFacade;
	
	@Autowired
	private GroupBuyService groupBuyService;
	@Autowired
	private GroupBuyUserService groupBuyUserService;
	
	@Autowired
	private GroupSuborderItemService groupSuborderItemService;
	
	@Autowired
	private GroupOrdersService groupOrdersService;
	
	@Autowired
	private UserContactsService userContactsService;
	
	//购物车确认订单并下单 
	@RequestMapping(value = {"/confirmCart.user"})
	@ResponseBody
	public ActResult<String> confirmCart(ModelMap model, HttpServletRequest request, String partNumbers) {
		Long shopId =0l;
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
		//总内购券
		BigDecimal totalCompanyTicket = BigDecimal.ZERO;
		BigDecimal totalUseTicket = BigDecimal.ZERO;
		String productIds = "";
		for (int i = clist.size()-1; i >=0; i--) {
			CartItem cartItem = clist.get(i);
			productIds += cartItem.getProductId().toString()+",";
			if(cartItem.getSaleKbn()!=null) {
				if(cartItem.getSaleKbn()!=1 && cartItem.getSaleKbn()!=0) {//不是普通或者特省商品或专享
					return ActResult.fail("商品："+cartItem.getProductName()+"不能参加一起购");
				}
			}
			Long skuId = Long.parseLong(cartItem.getPartNumber());
			ProductSpecificationsVo sku = productSpecificationsService.findByIdCache(skuId,cartItem.getProductId().toString());
			if(sku != null) {
				if(!skuId.equals(sku.getId())) {
					cartItem.setPartNumber(sku.getId()+"");
				}
				
				boolean isLadder =  productSpecificationsService.resetPrice(sku, productService.findById(sku.getProductId(), false), loginUser,false,cartItem.getQuantity());
				if(cartItem.getPrice().compareTo(sku.getPrice())!=0 ||cartItem.getMaxCompanyTicket().compareTo(sku.getMaxFucoin())!=0
						||cartItem.getRealPrice().compareTo(sku.getInternalPurchasePrice())!=0){//判断价格，购物车价格和数据库价格不想等，取数据库价格
					cartItem.setPrice(sku.getPrice());
					cartItem.setMaxCompanyTicket(sku.getMaxFucoin());
					cartItem.setProductCode(sku.getProductCode());
					cartItem.setRealPrice(sku.getInternalPurchasePrice());
				}
				
				//判断是否试用了员工专享价如果有，阶梯价不起作用
				cartItem.setIsLadder(isLadder);
				
				totalCompanyTicket = totalCompanyTicket.add(cartItem.getMaxCompanyTicket());
				totalUseTicket = totalUseTicket.add(cartItem.getCompanyTicket());
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
					shopId = p.getShopId();
				}
			}
			Iterator<CartItem> cartItemIterator = entry.getValue().iterator();
			while (cartItemIterator.hasNext()) {
				CartItem ci = cartItemIterator.next();
				if (!ci.isBuyFlag()) {
					cartItemIterator.remove();
				} else {
					if(ci.getSaleKbn() == 2) {
						ci.setRealAmount(ci.getPrice().subtract(ci.getMaxCompanyTicket()).multiply(NumberUtil.toBigDecimal(ci.getQuantity())));
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
		//返回已有团数量 
		Integer groupBuyNum = groupBuyUserService.getApplyByShop(shopId,loginUser.getId(),productIds);
		if (loginUser.getEmployeeType() != 1 && groupBuyNum==0) {
			return ActResult.fail("非员工账户,且未受到邀请。");
		}
		/**
		 * ***********************************提取要购买的商品信息**********************************************
		 **/

		// 加载常用地址
		// 加载发票信息
		List<Invoice> invoiceList = invoiceService.findByUserId(loginUser.getId());
		//查询个人所有财富信息
		List<Currency> currencyList = currencyService.findAll();
		for (Currency c : currencyList) {
			UserBalance userBalance = userBalanceService.findByUserAndType(loginUser.getId(), c.getId());
			if (userBalance != null)
				model.addAttribute(c.getName(), userBalance.getBalance());
			else
				model.addAttribute(c.getName(), 0);
		}

		/**
		 * ***********************************分配换领币**********************************************
		 **/
		BigDecimal exchangeCash = BigDecimal.ZERO;
		BigDecimal exchangeTicket = BigDecimal.ZERO;
		/*if(!exchangeCI.isEmpty()) {
			// 获取员工换领币
			List<UserExchangeTicket> ets = getExchangeTicket(loginUser.getId());
			BigDecimal share = BigDecimal.ZERO;
			if(!ets.isEmpty()) {
				share = userExchangeTicketService.getShareAmout(loginUser.getId());
				if(share == null) share=BigDecimal.ZERO;
			}

			// 优先处理资格换领
			for (CartItem ci : exchangeCI) {
				for (UserExchangeTicket et : ets) {
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
							BigDecimal ticket = BigDecimal.ZERO;
							// 记录券使用量
							if(needPay.compareTo(left)>0) {
								ticket = left;
							} else {
								ticket = needPay;
							}
							// 记录商品使用的换领币
							ci.setBenefitTicket(ci.getBenefitTicket().add(ticket));
							et.setUsedAmount(et.getUsedAmount().add(ticket));

							// 记录抵扣金额
							if(ticket.compareTo(share)>0) {
								ci.setBenefitAmount(ci.getBenefitAmount().add(share));
								share = BigDecimal.ZERO;
							} else {
								ci.setBenefitAmount(ci.getBenefitAmount().add(ticket));
								share = share.subtract(ticket);
							}
							needPay = needPay.subtract(ticket);
						}
						
						if(!NumberUtil.isGreaterZero(needPay)) {
							// 全部抵扣 
							break;
						}
					}
				}
				
				exchangeCash = exchangeCash.add(ci.getBenefitAmount());
				exchangeTicket = exchangeTicket.add(ci.getBenefitTicket());
			}
		}*/
		
		model.addAttribute("groupBuyNum", groupBuyNum);

		model.addAttribute("exchangeCash", exchangeCash);
		model.addAttribute("exchangeTicket", exchangeTicket);
		
		model.addAttribute("cart", appCartItem);
		model.addAttribute("freeMap",freeMap);
		model.addAttribute("invoiceList", invoiceList);
//		redisUtil.setData(RedisConstant.CART_REDIS + "_" + loginUser.getUserId(),JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
		return ActResult.success(model);
	}

	@RequestMapping(value = {"/confirmCartPage.user"})
    public ModelAndView confirmCartPage(HttpServletRequest request, ModelAndView model,String partNumbers,Long shippingAddressId,String backNum,Long groupId) {
		
		model.addObject("partNumbers", partNumbers);
		model.addObject("orderType","cart");
		model.addObject("shippingAddressId",shippingAddressId);
		model.addObject("backNum",backNum);
		model.addObject("groupId",groupId);
		model.setViewName("groupOrder_confirm");
		return model;
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
	public ActResult<Object> create(HttpServletRequest request, String fromType, GroupOrders groupOrders,
			String useExchangeTicket, String useExchangeCash, String useExchangeSelf, BigDecimal useCash, Long shippingAddressId, 
			String sku_nums,String sku_freights,String message,String groupId) {

		try {
			if(useCash==null) useCash=BigDecimal.ZERO;
			if(useCash.compareTo(BigDecimal.ZERO)>0) {
				Currency cashCurrency = currencyService.findByName("balance");
				UserBalance userBalance = userBalanceService.findByUserAndType(loginUser.getId(), cashCurrency.getId());
				if(userBalance==null || useCash.compareTo(userBalance.getBalance())>0) {
					return ActResult.fail("现金券余额不足");
				}					
			}
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_" + loginUser.getId());
			Cart cart = JsonUtil.getObject(cartJson, Cart.class);
			if(StringUtils.isEmpty(fromType)) {
				fromType="app";
			}
			List<ProductSpecifications> outSkus = new ArrayList<ProductSpecifications>();
			ActResult<Cart> actCart = this.makeBuyCart(fromType, sku_nums, sku_freights, useExchangeTicket,
					useExchangeCash, useExchangeSelf, loginUser, cart,groupId, outSkus);

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
			ShippingAddress addr = new ShippingAddress();
			if(StringUtils.isEmpty(groupId)) {
				return ActResult.fail("无对应团");
			}
			GroupBuyVo groupBuyVo = groupBuyService.getGroupBuyById(groupId);
			if(null == groupBuyVo) {
				return ActResult.fail("无对应团");
			} else {
				if(groupBuyVo.getStatus()!=1 || groupBuyVo.getOrderStatus()!=0) {
					return ActResult.fail("很遗憾，拼团已结束，不能再下单了");
				}
			}
			addr.setAid(groupBuyVo.getAid());
			groupOrders.setUserId(loginUser.getId());
			groupOrders.setGroupId(groupBuyVo.getId());
			groupOrders.setMobile(loginUser.getPhone());
			groupOrders.setName(loginUser.getNickName());
			groupOrders.setAddress(groupBuyVo.getAddress());
			
			// 更新购物车中选中商品的企业券使用量
			List<CartItem> lstForCheckFreights = new ArrayList<CartItem>();
			Map<String,Long> skuProduct = new HashMap<String,Long>();
			Map<Long,Product> productMap = new HashMap<Long,Product>();
			Map<Long,Integer> numMap = new HashMap<Long,Integer>();
			Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
			//总内购券
			BigDecimal totalCompanyTicket = BigDecimal.ZERO;
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
				if(ci.getCompanyTicket() == null) ci.setCompanyTicket(BigDecimal.ZERO);
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
				
				lstForCheckFreights.add(ci);
				//////////////////////////////////////////////////////////////////////////////////////////////
				
				//计算全部的内购券
				totalCompanyTicket = totalCompanyTicket.add(ci.getCompanyTicket());			
			}
			
			//判断用户是否足够支付内购券
			if(!checkUserTicket(loginUser.getId(), totalCompanyTicket)){
				return ActResult.fail("内购券不足");
			}

			//check 运费
			ActResult<Object> chkfr = checkFreight(groupOrders.getSelfDelivery(),addr==null?null:addr.getAid(), lstForCheckFreights, skuProduct, productMap, numMap,amountMap,loginUser.getId());
			if(!chkfr.isSuccess()) {
				return chkfr;
			}
			

			if(StringUtils.isEmpty(groupOrders.getUserId())) {
				groupOrders.setUserId(loginUser.getId());
			} else {
				if(!groupOrders.getUserId().equals(loginUser.getId())) {
					System.out.println("------------System Error Group Buy uid="+ groupOrders.getUserId() +",loginUserId=" + loginUser.getId() + "--------------------------------");
				}
			}
			
			groupOrders.setCreateTime(new Date());

			GroupOrderVO vo = new GroupOrderVO();
			vo.setGroupOrders(groupOrders);
			List<String> subIds = new ArrayList<String>();
			
			//String message=null;
			try{
				ActResult<Object> ar = groupOrdersFacade.createOrder(vo, actCart.getData(),useCash,subIds,message);// 保存订单
				// 清空缓存
				if (!ar.isSuccess()) {
					return ar;
				}
				//记录内购券流水
				ActResult<Object> recordFlowResult = ordersService.recordFlow(groupOrders.getOrderId(), loginUser);
				if (recordFlowResult == null || !recordFlowResult.isSuccess()) {
					groupOrdersFacade.cancelOrder(loginUser, groupOrders.getOrderId(), recordFlowResult.getMsg(),false);
					return ActResult.fail("流水记录失败，订单已取消，请联系客服");
				}
				
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
			UserFactory user,Cart cart,String groupId,List<ProductSpecifications> outSkus) {
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
		Map<Long,Integer> sku_groups = new HashMap<Long,Integer>();
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
			Long productId=null;
			ProductSpecificationsVo sku =null;
			skuIds.add(skuId);
			sku_freights.put(skuId, NumberUtil.toBigDecimal(strs[1]));
			
			CartItem rds = cart.getItem(skuId);
			if(rds == null) {
				if(!sku_nums.containsKey(skuId)) {
					return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
				}

				errCnt++;
				sku = this.specificationsService.findByIdCache(skuId,null);
				outSkus.add(sku);
	            if(null == sku){
	            	return ActResult.fail("商品不存在,或已下架,请返回购物车后再次购买");
	            }
	            productId = sku.getProductId();
				
				sku_images.put(skuId, productSpecificationsImageService.findProductPicture(sku.getId(), productId).get(0).getSource());
				sku_pageKeys.put(skuId, "");
				sku_froms.put(skuId, "");
			} else {
				productId = rds.getProductId();
				sku_images.put(skuId, rds.getImagePath());
				sku_pageKeys.put(skuId, rds.getPageKey());
				sku_froms.put(skuId, rds.getFromType());
				if(!sku_nums.containsKey(skuId)) {
					sku_nums.put(skuId, rds.getQuantity());
				}
				sku = this.specificationsService.findByIdCache(skuId,productId+"");
			}
			Integer groupProductSum = groupSuborderItemService.getSuborderItemSum(groupId,productId,""+skuId);
			sku_groups.put(skuId, groupProductSum);
			Product product = this.userService_productService.getById(productId);
			productSpecificationsService.resetPrice(sku, product, user,false,sku_nums.get(skuId)+groupProductSum);
			sku_realPrices.put(skuId, sku.getInternalPurchasePrice());
		}

		if(errCnt== skuIds.size()){
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}
		ActResult<Cart> actCart = ordersService.mergeBuyCart(fromType, user,skuIds, sku_nums,sku_realPrices, sku_freights,
				sku_images, sku_pageKeys, sku_froms, sku_groups,useExchangeTicket, useExchangeCash, useExchangeSelf, outSkus);
		return actCart;
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
			//放开数量的限制
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
	 * 团购计算运费
	 * @param selfDelivery
	 * @param groupId
	 * @param useCompanyTicket
	 * @param sku_nums
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/newCalculateFreight.user" })
	@ResponseBody
	public ActResult<Map<String,Object>> newCalculateFreight(String selfDelivery,String groupId, String sku_nums,HttpServletRequest request) {
		List<String> nums = new ArrayList<String>();
		List<String> skus = new ArrayList<String>();

		ActResult<Map<String,Object>> result = new ActResult<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		//supplier对应的运费金额
		Map<String,BigDecimal> supplierShippingMap = new HashMap<String,BigDecimal>();
		ShippingAddress addr = new ShippingAddress();
		GroupBuyVo groupBuyVo = groupBuyService.getGroupBuyById(groupId);
		if(null == groupBuyVo) {
				result.setSuccess(false);
				result.setMsg("无对应团");
				return result;
		}
		GroupBuyUserVo groupBuyUserVo = groupBuyUserService.getByUserIdAndGroupId(loginUser.getId(), groupId);
		if(groupBuyUserVo!=null && groupBuyUserVo.getStatus()==0) {
			//修改加入成员的团内状态
			groupBuyUserVo.setStatus(1);
			groupBuyUserService.update(groupBuyUserVo);
			//将成员直接加入到群聊中
			String imgroupId = userContactsService.createGroupBuyGroup(loginUser.getId(), "" + groupId);
			if(!StringUtils.isEmpty(imgroupId)) {
				UserImGroup im= userImGroupService.getById(NumberUtil.toLong(imgroupId));
				if(im!=null) {
					try {
						List<UserImGroup> groups = new ArrayList<UserImGroup>();
						groups.add(im);
						String name =loginUser.getNickName() == null ? loginUser.getUserName() : loginUser.getNickName();
						String msg="@"+name+"欢迎加入！"+"\n"+"团越多，省越多。"+"\n"+"拼团完成后，由团长统一收货。"+"\n"+"点击选购商品";
						if(groups!=null && !groups.isEmpty()) {
							EasemobIMUtils.shoppingGroupMessage(msg, groups, loginUser.getId(), null, name,"add");
						}
					} catch (Exception e) {
					}
				}
			}
		}
		addr.setAid(groupBuyVo.getAid());

		String[] arySkuf = sku_nums.substring(0, sku_nums.length()).split(",");
		for (String str : arySkuf) {
			String[] strs = str.split("_");
			skus.add(strs[0]);
			nums.add(strs[1]);
		}
		
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		Map<Long,Integer> numMap = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
		Map<String,Long> skuProduct = new HashMap<String,Long>();
		Map<String,Object> productPrice = new HashMap<String,Object>();
		
		//sku 对应的商品及数量合并
		for (int i=0;i<skus.size();i++) {
			Product p = productService.findBySku(Long.parseLong(skus.get(i)));
			ProductSpecificationsVo sku= specificationsService.findByIdCache(Long.parseLong(skus.get(i)),p.getId().toString());
			Integer num = NumberUtil.toInteger(nums.get(i));
			//获取已购买的商品规格的数量
			Integer groupProductNum = groupSuborderItemService.getSuborderItemSum(groupId,p.getId(),skus.get(i));
			//计算阶梯价
			specificationsService.resetPrice(sku, p, loginUser, false,num+groupProductNum);
			skuProduct.put(skus.get(i), p.getId());
			
			if(!productMap.containsKey(p.getId())) {
				productMap.put(p.getId(), p);
			}
			if(!productPrice.containsKey(sku.getId()+"")){
				/*ProductSpecificationsVo newSku = new ProductSpecificationsVo();
				newSku.setPrice(sku.getPrice());
				newSku.setMarketPrice(sku.getMarketPrice());
				newSku.setMaxFucoin(sku.getMaxFucoin());
				newSku.setInternalPurchasePrice(sku.getInternalPurchasePrice());*/
				productPrice.put(""+sku.getId(), sku);
			}
			BigDecimal amount = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(num));
			
			if(numMap.containsKey(p.getId())) {
				num= num+numMap.get(p.getId());
			}
			numMap.put(p.getId(), num);
			
			//保存金额
			if(amountMap.containsKey(p.getId())) {
				amount = amountMap.get(p.getId()).add(amount);
			}
			amountMap.put(p.getId(), amount);
		}		

		//根据商品、数量、收货地址计算运费
		Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();
		Map<Long,List<Long>> supplierMap = new HashMap<Long,List<Long>>();
		//设置商家supplierId 的list
		HashSet<Long> supplierIdList  =   new  HashSet<Long>();
		for (Long pid : productMap.keySet()) {
			//放开数量的限制
			freightMap.put(pid, shippingFacade.chkLimitCntAndArea(productMap.get(pid), numMap.get(pid),null != addr ? addr.getAid():null,null,loginUser.getId(),selfDelivery));
			List<Long> lc = supplierMap.get(productMap.get(pid).getSupplierId());
			if(lc == null) {
				lc = new ArrayList<Long>();
				supplierMap.put(productMap.get(pid).getSupplierId(), lc);
				supplierShippingMap.put(""+productMap.get(pid).getSupplierId(), BigDecimal.ZERO);
			}
			supplierIdList.add(productMap.get(pid).getSupplierId());
			lc.add(pid);
		}

		//计算商家免邮// 自提
		if(!"1".equals(selfDelivery)) {
			//重新计算商品的运费已商家为单位
			for (Long sid : supplierMap.keySet()) {
				shippingFacade.calculateSupplierShippingFee(selfDelivery, addr == null ? null : addr.getAid(), loginUser,
						sid, supplierMap.get(sid), productMap, numMap, amountMap, freightMap, null);
			}
			for (Long sid : supplierMap.keySet()) {
				List<Long> lc = supplierMap.get(sid);
				// 计算总运费已供应商为单位
				BigDecimal supplierIdTotalShipping = new BigDecimal(0);
				for (Long pid : lc) {
					if (freightMap.get(pid) != null) {
						supplierIdTotalShipping = supplierIdTotalShipping.add(freightMap.get(pid));
					}
				}
				supplierShippingMap.put("" + sid, supplierIdTotalShipping);
			}
		}
		
		//每个sku运费返回，以商品为单位，首次出现则包含全部运费，再次出现运费为0
		List<Double> rtn = new ArrayList<Double>();
		for (int i=0;i<skus.size();i++) {
			Long key=skuProduct.get(skus.get(i));
			if(freightMap.containsKey(key)) {
				rtn.add(freightMap.get(key).doubleValue());
				freightMap.remove(key);
			} else {
				rtn.add(0D);
			}
		}
		
		result.setSuccess(true);
		map.put("oldData", rtn);
		map.put("productPrice", productPrice);
		map.put("newData", supplierShippingMap);
		map.put("freeMap", shippingFacade.getFreeShippingMap(supplierIdList, "1".equals(selfDelivery) ? "1":addr.getAid()));
		result.setData(map);
		return result;
	}
	
	/**
	 * 比较用户本身内购券是否足够支持购买商品 true 可以使用 false不足
	 * @param userId
	 * @param productTicket
	 * @return
	 */
	private boolean checkUserTicket(Long userId,BigDecimal productTicket){
		Currency ticketCurrency = currencyService.findByName("companyTicket");
		UserBalance ticketBalance = userBalanceService.findByUserAndType(userId, ticketCurrency.getId());
		if(ticketBalance.getBalance().compareTo(productTicket) >= 0){
			return true;
		}
		return false;
	}
	
	
	@RequestMapping(value = { "/openGroup.user" })
	@ResponseBody
	public ActResult<Object> openGroup(String groupId) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 

		List<String> subIds = new ArrayList<String>();			
		if(groupOrdersFacade.mergerOrder(Long.parseLong(groupId),subIds)) {
			ShopPushUtil.pushMsg4order(redisUtil,subIds,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
			result.setSuccess(true);
			pushEasemobMessage(groupId,"mergerOrder");
		}
		return result;
	}

	@RequestMapping(value = { "/tjOpenGroup.tj" })
	@ResponseBody
	public ActResult<Object> tjOpenGroup(String groupId) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		List<String> subIds = new ArrayList<String>();			
		if(groupOrdersFacade.mergerOrder(Long.parseLong(groupId),subIds)) {
			ShopPushUtil.pushMsg4order(redisUtil,subIds,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
			 result.setSuccess(true);
			 pushEasemobMessage(groupId,"mergerOrder");
		}
		return result;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = { "/tjRefundShipp.tj" })
	@ResponseBody
	public ActResult<Object> tjRefundShipp(String groupId) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		GroupBuyVo groupBuyVo = groupBuyService.getById(Long.parseLong(groupId));
		if(groupBuyVo!=null) {
			List<GroupOrdersVo>  list = groupOrdersService.getByGroupID(Long.parseLong(groupId),null);
			for (GroupOrdersVo groupOrders : list) {
				//要退的运费
				BigDecimal refundShippingAll = groupOrders.getTotalShipping().subtract(groupOrders.getNowShipping());
				//每一个人所有支付的suborder
				List<GroupSuborder> suborderList = groupSuborderService.findByOrderId(groupOrders.getOrderId());
				//退款金额 （运费+退的商品金额）
				BigDecimal refundAmount = BigDecimal.ZERO;
				for (GroupSuborder groupSuborder : suborderList) {
					// 退款金额最多不超过支付运费
					BigDecimal refundShipping = BigDecimal.ZERO;
					if(refundShippingAll.compareTo(groupSuborder.getTotalShipping())>0) {
						refundShipping=groupSuborder.getTotalShipping();
						refundShippingAll = refundShippingAll.subtract(groupSuborder.getTotalShipping());
					} else {
						refundShipping=refundShippingAll;
						refundShippingAll=BigDecimal.ZERO;				
					}
					refundAmount = groupSuborder.getRealPrice().subtract(groupSuborder.getAfterOpenRealPrice());							
					refundAmount = refundAmount.add(refundShipping);
				}
				//节省金额
				groupOrders.setGroupSaveAmonut(refundAmount);
			}
			if(groupOrdersFacade.refundShipp(Long.parseLong(groupId))) {
				 result.setSuccess(true);
				 pushWxRefundMessage(list,groupBuyVo);
			}
			return result;
		}else {
			return result.fail("");
		}
	}
    private void pushWxRefundMessage(List<GroupOrdersVo> list, GroupBuyVo groupBuy) {
    	// 推送消息
        Map<String,Object> paramPush=new HashMap<String,Object>();
        if(list!=null && list.size()>0) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			for (GroupOrdersVo groupOrdersVo : list) {
				paramPush.put("first", "恭喜您拼团成功！节省的金额已为您退回。商家将尽快发货。");
        		paramPush.put("remark", "下载APP，关注物流动态。\r\n" + "点击下载>>");
        		paramPush.put("groupName", groupBuy.getGroupName());
        		paramPush.put("time", formatter.format(groupBuy.getCreateTime()));
    			paramPush.put("total", groupOrdersVo.getRealPrice());
    			paramPush.put("refund", groupOrdersVo.getRealPrice());
    	        paramPush.put("type", "group_buy_close");
    			paramPush.put("fee", "0");
    			paramPush.put("userId", groupOrdersVo.getUserId());
				HttpClientUtil.sendHttpRequest("post", "http://"+Constant.SYSTEM_DOMAIN+"/wx/templateMsgSend", paramPush);
			}
        }
	}

	//超时取消订单
    @RequestMapping("/autoCancelOrder.tj")
    @ResponseBody
    public ActResult<String> autoCancelOrder(HttpServletRequest request,Long userId, String subOrderId,String closeReason) {
        try {
        	UserFactory user = userService.getById(userId);
        	
        	if(user==null){
        		return ActResult.fail("");
        	}
			return groupOrdersFacade.cancel(user, groupSuborderService.getById(subOrderId) ,closeReason,true);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("");
		}
    }
    
    /**
     * 放弃拼团
     * @param request
     * @param userId
     * @param subOrderId
     * @param closeReason
     * @return
     */
    @SuppressWarnings("static-access")
	@RequestMapping("/giveUpOrderTj.tj")
    @ResponseBody
	public ActResult<String> giveUpOrderTj(HttpServletRequest request, Long groupId, String closeReason) {
		ActResult<String> art = null;
		try {
			GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
			if(groupBuyVo!=null) {
				art = groupOrdersFacade.cancelAfterOpen(groupId, closeReason, -2);
				List<GroupOrdersVo> list = groupOrdersService.getByGroupID(groupId, null);
				if (art.isSuccess()) {
					pushEasemobMessage(groupId.toString(), "giveUpOrder");
					pushWxCancelMessage(list, groupBuyVo);
				}
			}
			return art;
		} catch (Exception e) {
			e.printStackTrace();
			return art.fail("");
		}
	}
    @RequestMapping("/giveUpOrder.user")
    @ResponseBody
    public ActResult<String> giveUpOrder(HttpServletRequest request, Long groupId,String closeReason) {
        try {
        	if(groupId==null) return ActResult.fail("参数错误");
        	GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
        	if(groupBuyVo==null) {
        		return ActResult.fail("无法获取该团信息");
        	}else {
        		if(!groupBuyVo.getUserId().equals(loginUser.getId())) {
        			return ActResult.fail("不是团长无法操作");
        		}
        		if(StringUtils.isEmpty(closeReason)) {
            		closeReason ="团长取消";
            	}
        		if(-1==groupBuyVo.getStatus()) {
        			return ActResult.fail("已放弃");
        		}
        		List<GroupOrdersVo> list = groupOrdersService.getByGroupID(groupId, null);
        		ActResult<String> result = groupOrdersFacade.cancelAfterOpen(groupId, closeReason,-1);
        		if(result.isSuccess()) {
        			pushEasemobMessage(groupId.toString(),"giveUpOrder");
        			pushWxCancelMessage(list,groupBuyVo);
        		}
        		return result;
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("");
		}
    }
    
	private void pushWxCancelMessage(List<GroupOrdersVo> list,GroupBuyVo groupBuy) {
		// 推送消息
        Map<String,Object> paramPush=new HashMap<String,Object>();
        if(list!=null && list.size()>0) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	for (GroupOrdersVo groupOrdersVo : list) {
        		paramPush.put("first", "团长刚刚放弃了拼团。您的团内订单已退款。");
        		paramPush.put("remark", "更多特惠福利等你来淘。\r\n" + "点击下载APP>>");
        		paramPush.put("groupName", groupBuy.getGroupName());
        		paramPush.put("time", formatter.format(groupBuy.getCreateTime()));
    			paramPush.put("total", groupOrdersVo.getRealPrice());
    			paramPush.put("refund", groupOrdersVo.getRealPrice());
    	        paramPush.put("type", "group_buy_close");
    			paramPush.put("fee", "0");
    			paramPush.put("userId", groupOrdersVo.getUserId());
    			HttpClientUtil.sendHttpRequest("post", "http://"+Constant.SYSTEM_DOMAIN+"/wx/templateMsgSend", paramPush);
    		}
        }
	}

	@RequestMapping(value = { "/postageStrategy" })
	@ResponseBody
	public ActResult<Object> postageStrategy(Long groupId){
		if(groupId==null)return ActResult.fail("参数错误");
		Map<String, Object> msg = groupOrdersFacade.postageStrategy(groupId);
		
		return ActResult.success(msg);
	}
	
	@RequestMapping(value = { "/checkGroupOrder.user" })
	@ResponseBody
	public ActResult<Object> checkGroupOrder(Long groupId){
		if(groupId==null)return ActResult.fail("参数错误");
		GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
		if(groupBuyVo.getStatus()!=-1 && groupBuyVo.getStatus()!=-2 && groupBuyVo.getOrderStatus()==0) {
			ActResult<Object> act= groupOrdersFacade.checkGroupOrder(groupId);
			return act;
		}else {
			return ActResult.fail("已操作");
		}
	}
	/**
	 * 环信一起购推送消息
	 * @param groupId
	 * @param state
	 */
	private void pushEasemobMessage(String groupId,String state) {
		GroupBuyVo groupBuyVo = groupBuyService.getById(Long.parseLong(groupId));
		if(groupBuyVo!=null) {
			BigDecimal allTotalPrice = groupBuyVo.getAllTotalPrice();//商品总价
			BigDecimal allTotalShipping = groupBuyVo.getAllTotalShipping();//支付总运费
			BigDecimal totalPrice = groupBuyVo.getTotalPrice();//实际商品总价
			BigDecimal totalShipping = groupBuyVo.getTotalShipping();//实际运费
			if(allTotalPrice==null) {
				allTotalPrice = BigDecimal.ZERO;
			}
			if(allTotalShipping==null) {
				allTotalShipping = BigDecimal.ZERO;
			}
			if(totalPrice==null) {
				totalPrice = BigDecimal.ZERO;
			}
			if(totalShipping==null) {
				totalShipping = BigDecimal.ZERO;
			}
			//总共节省金额
			BigDecimal totalSaveAmount = allTotalPrice.add(allTotalShipping).subtract(totalPrice.add(totalShipping));
			UserImGroup im= userImGroupService.getById(NumberUtil.toLong(groupBuyVo.getIm_groupId()));
			if(im!=null) {
				List<UserImGroup> groups = new ArrayList<UserImGroup>();
				groups.add(im);
				String msg ="";
				if("giveUpOrder".equals(state)) {
					msg="@所有人 团长刚刚放弃了拼团。\r\n" + "团内订单已取消，将尽快退款。\r\n" + "要不再团点别的试试？(*^__^*) ";
				}else if("mergerOrder".equals(state)) {
					msg="@所有人 恭喜各位拼团成功！\r\n" + "购物团节省了总计"+totalSaveAmount+"元~\r\n" + "节省的金额将为您退回；商家将尽快发货~";
				}
				EasemobIMUtils.shoppingGroupMessage(msg, groups, null, null, null,state);
			}
		}
	}
}
