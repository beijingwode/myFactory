package com.wode.api.web.controller;

import java.io.IOException;
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

import org.nutz.lang.util.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wode.api.facade.LoginFacade;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.FileUtils;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.model.Comments;
import com.wode.factory.model.Currency;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.Invoice;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.SuborderitemLimitTicket;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.UploadService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.CommentsFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.AppCartItemVo;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.InvoiceService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.ProductQuestionnaireService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ProductTrialLimitItemService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.RefundorderService;
import com.wode.factory.user.service.ReturnorderService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.SuborderLimitTicketService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.service.UserLimitTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.OrderUtils;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.OrderVO;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.user.vo.SubOrderItemVo2;
import com.wode.factory.user.vo.SubOrderVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.search.WodeSearchManager;

/**
 * 订单
 *
 * @author
 */
@Controller
@RequestMapping("/order")
@SuppressWarnings("unchecked")
public class OrderController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ProductTrialLimitItemService productTrialLimitItemService;
	
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private OrdersFacade ordersFacade;
	
	@Autowired
	private SuborderService suborderService;

	@Autowired
	private SuborderitemService suborderitemService;

	@Autowired
	private ShippingAddressService shippingAddressService;

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
	@Autowired
	private LoginFacade loginFacade;
	
	@Autowired
	private ReturnorderService returnorderService;

	@Autowired
	private RefundorderService refundorderService;
    @Autowired
	private ShippingFacade shippingFacade;
    @Autowired
    private CommentsFacade commentsFacade;
    @Autowired
    private WodeSearchManager wsm;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private ProductQuestionnaireService productQuestionnaireService;
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;
	
	private static final int CART_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月

	static UploadService us = ServiceFactory.getUploadService(Constant.OUTSIDE_SERVICE_URL);
	
	@Autowired
	private GroupOrdersService groupOrdersService;
	
	@Autowired
	private GroupBuyService groupBuyService;
	
	@Autowired
	private UserLimitTicketService userLimitTicketService;
	
	@Autowired
    private com.wode.factory.user.service.ProductService userService_productService;
	
	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
	
	@Autowired
    private SuborderLimitTicketService suborderLimitTicketService;
	/**
	 * 跳转订单详情页面（线下发货）
	 * @param subOrderId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderDetailPageEx.user")
	public ModelAndView pageEx(String subOrderId,ModelAndView model,HttpServletRequest request){
		UserFactory user = loginUser;
		if(user.getRole() == 9) {
			model.addObject("subOrderId", subOrderId);
			model.addObject("uid", user.getId());
			model.setViewName("order_detailsEx");
			return model;
		}
		return null;
	}
	
	//保存发票信息
	@RequestMapping(value = {"/saveInvoice.user"})
	@ResponseBody
	public ActResult<String> saveInvoice(Long invoiceId, String title, Integer type, String ticket, HttpServletRequest request) {
		Invoice invoice = new Invoice();
		if (invoiceId > 0) {
			invoice.setId(invoiceId);
		}
		invoice.setUserId(loginUser.getId());
		invoice.setTitle(title);
		invoice.setType(type);
		invoiceService.updateInvoice(invoice);
		return ActResult.successSetMsg("发票保存成功");

	}

	//删除发票信息
	@RequestMapping(value = {"/deleteInvoice.user"})
	@ResponseBody
	public ActResult<String> deleteInvoice(Long invoiceId, String ticket, HttpServletRequest request) {
		invoiceService.deleteInvoice(loginUser.getId(), invoiceId);
		return ActResult.successSetMsg("发票删除成功");
	}

	//购物车确认订单并下单 
	@RequestMapping(value = {"/confirmCart.user"})
	@ResponseBody
	public ActResult<String> confirmCart(ModelMap model, HttpServletRequest request, String partNumbers,String productPageStock) {
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
		Boolean isSelfDelivery = true;
		String[] offlineCounts=null;	// 线下内购会商品库存量 商品id_库存
		if(StringUtils.isEmpty(productPageStock)) {
			isSelfDelivery=false;		// 不默认自提
		} else {
			offlineCounts = productPageStock.split(",");
			if(clist.size()!=offlineCounts.length) {//购买商品数跟传递页面库存商品一致
				isSelfDelivery=false;	// 不默认自提
			}
		}
		
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
			/**
			 * ***********************************统计当天购买商品个数**********************************************
			 */
			// (productPageStock--->商品ID+_+页面库存)多个以，分隔
			if(isSelfDelivery) {
				for (int j = 0; j < offlineCounts.length; j++) {
					String[] productIdStock = offlineCounts[i].split("_");
					Long productId = Long.parseLong(productIdStock[0]);
					int pageStock = Integer.parseInt(productIdStock[1]);//页面库存
					if (productId.equals(cartItem.getProductId())) {
						//已购买数量
						Integer cnt = suborderitemService.getCountByPageKeyAndProduct(productId,cartItem.getPageKey());
						if(cnt==null) cnt=0;
						if(pageStock < cnt+cartItem.getQuantity()) {
							// 不足线下发货时 不默认自提
							isSelfDelivery =false;
						}
						break;
					}
				}
				
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
		/**
		 * ***********************************提取要购买的商品信息**********************************************
		 **/

		// 加载常用地址
		List<ShippingAddress> shippingAddressList = shippingAddressService
				.findByUserId(loginUser.getId());
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
		model.addAttribute("exchangeCash", exchangeCash);
		model.addAttribute("exchangeTicket", exchangeTicket);
		model.addAttribute("isSelfDelivery", isSelfDelivery);//是否自提
		model.addAttribute("cart", appCartItem);
		model.addAttribute("freeMap",freeMap);
		model.addAttribute("shippingAddressList", shippingAddressList);
		model.addAttribute("invoiceList", invoiceList);
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
//		redisUtil.setData(RedisConstant.CART_REDIS + "_" + loginUser.getUserId(),JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
		return ActResult.success(model);
	}

	@RequestMapping(value = {"/confirmCartPage.user"})
    public ModelAndView confirmCartPage(HttpServletRequest request, ModelAndView model,String partNumbers,Long shippingAddressId,String backNum,String productIds) {
		
		model.addObject("partNumbers", partNumbers);
		model.addObject("orderType","cart");
		model.addObject("shippingAddressId",shippingAddressId);
		model.addObject("backNum",backNum);
		model.addObject("productIds",productIds);
		//model.addObject("btnName",StringUtils.isEmpty(btnName) ? "" : btnName);
		model.setViewName("order/order_confirm");
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
	public ActResult<Object> create(HttpServletRequest request,String fromType, Orders order,
			String useExchangeTicket, String useExchangeCash, String useExchangeSelf,BigDecimal useCash, 
			Long shippingAddressId,String sku_nums,String sku_freights,String message,String limitTickets) {

		try {
			if(useCash==null) useCash=BigDecimal.ZERO;
			if(useCash.compareTo(BigDecimal.ZERO)>0) {
				Currency cashCurrency = currencyService.findByName("balance");
				UserBalance userBalance = userBalanceService.findByUserAndType(loginUser.getId(), cashCurrency.getId());
				if(userBalance==null || useCash.compareTo(userBalance.getBalance())>0) {
					return ActResult.fail("现金券余额不足");
				}					
			}

			String limit4Pro =null;
			Map<String,List<SuborderitemLimitTicket>> mapSuborderitemLimitTicket =new HashMap<String,List<SuborderitemLimitTicket>>();
			Map<Long,UserLimitTicket> useTickts = new HashMap<Long,UserLimitTicket>();
			ActResult<Object> chk = this.dealLimitTickets(limitTickets, loginUser, mapSuborderitemLimitTicket, useTickts);
			if(chk.isSuccess()) {
				limit4Pro=chk.getData().toString();
			} else {
				return chk;
			}
			
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
			}
			
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
				
				//////////////////////////////////////////////////////////////////////////////////////////////
				
				Integer num = ci.getQuantity();
								
				//优惠券
				BigDecimal amount =ci.getRealPrice().multiply(NumberUtil.toBigDecimal(num));		
				amount = amount.subtract(ci.getBenefitAmount());
				
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
				
				//处理优惠券
				List<SuborderitemLimitTicket> slts = mapSuborderitemLimitTicket.get(ci.getPartNumber());
				if(slts!=null && slts.size()>0) {
					boolean isFist = true;
					for(SuborderitemLimitTicket slt :slts ) {

						//修改cartItem
						if(NumberUtil.isGreaterZero(slt.getBenefitTicket())) {
							ci.setCompanyTicket(ci.getCompanyTicket().subtract(slt.getBenefitTicket()));
						}
						if(NumberUtil.isGreaterZero(slt.getBenefitCash())) {//现金抵扣大于0
							if(ci.getBenefitAmount()==null) ci.setBenefitAmount(BigDecimal.ZERO);
							ci.setBenefitAmount(ci.getBenefitAmount().add(slt.getBenefitCash()));
							// 暂不细分每张优惠券抵扣几个,第一个现金抵扣记录数量
							if(isFist) {
								slt.setSkuNum(ci.getQuantity());
							}
						}
						
						// 合计每张券使用量
						UserLimitTicket ult = useTickts.get(slt.getUserLimitTicketId());
						ult.setCashBalance(ult.getCashBalance().add(slt.getBenefitCash()));
						ult.setTicketBalance(ult.getTicketBalance().add(slt.getBenefitTicket()));
					}
					if(isFist) {
						//没有现金抵扣 第一个现金抵扣记录数量
						slts.get(0).setSkuNum(ci.getQuantity());
					}
				}
				//计算全部的内购券
				totalCompanyTicket = totalCompanyTicket.add(ci.getCompanyTicket());
				//////////////////////////////////////////////////////////////////////////////////////////////
			}
			
			//判断用户是否足够支付内购券
			if(!checkUserTicket(loginUser.getId(), totalCompanyTicket)){
				return ActResult.fail("内购券不足");
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
					System.out.println("------------System Error uid="+ order.getUserId() +",loginUserId=" + loginUser.getId() + "--------------------------------");
				}
			}

			order.setCreateTime(new Date());
			OrderVO vo = new OrderVO();
			vo.setOrders(order);
			vo.setMapSuborderitemLimitTicket(mapSuborderitemLimitTicket);
			vo.setUserLimitTicketMap(useTickts);
			// 增加企业券使用 2015-6-12
			List<String> subIds = new ArrayList<String>();
			
			//String message=null;
			try{
				ActResult<Object> ar = ordersFacade.createOrder(vo, actCart.getData(),useCash,subIds,message);// 保存订单
				// 清空缓存
				if (!ar.isSuccess()) {
					return ar;
				}
				//记录内购券流水
				ActResult<Object> recordFlowResult = ordersService.recordFlow(order.getOrderId(), loginUser);
				if (recordFlowResult == null || !recordFlowResult.isSuccess()) {
					ordersFacade.cancelOrder(loginUser, order.getOrderId(), recordFlowResult.getMsg(),false);
					return ActResult.fail("流水记录失败，订单已取消，请联系客服");
				}
				// 有专享商品抵扣
				if(!StringUtils.isEmpty(limit4Pro)) {
					redisUtil.push(RedisConstant.SUBSCRIBE_CHANNEL_ORDER_CREATE, limit4Pro);
				}
				ShopPushUtil.pushMsg4order(redisUtil,subIds,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
				
				cart.clearBuied(cartItemList);
				redisUtil.setData(RedisConstant.CART_REDIS + "_" + loginUser.getId(), JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
				return ar;
			} catch (BenefitLessException e) {
				if("limit".equals(e.getTicketType())) {
					return ActResult.fail("优惠券余额不足，请刷新后重试");
				} else {
					return ActResult.fail("换领币余额不足，请刷新后重试");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("参数错误");
		}
	}

	/**
	 * 记录每个sku 使用的优惠券
	 * @param ci
	 * @param limitTicketList
	 * @param loginUser
	 * @param mapSuborderitemLimitTicket
	 */
	private ActResult<Object> dealLimitTickets(String limitTickets, UserFactory loginUser,
			Map<String, List<SuborderitemLimitTicket>> mapSuborderitemLimitTicket,
			Map<Long,UserLimitTicket> useTickts) {
		if(StringUtils.isEmpty(limitTickets)) return ActResult.success("");

		String sb = "limit4";
		
		if(!StringUtils.isEmpty(limitTickets)) { //limitTickets skuId_id_现金券_内购券,id_现金券_内购券,
			String[] limitTicketArr = limitTickets.trim().split(",");
			for (int i = 0; i < limitTicketArr.length; i++) {
				BigDecimal skuCash=BigDecimal.ZERO;
				BigDecimal skuTicket=BigDecimal.ZERO;
				String[] limitTicketInfo = limitTicketArr[i].split("_");
				if(limitTicketInfo.length==4) {
					Long skuId = NumberUtil.toLong(limitTicketInfo[0]);
					
					UserLimitTicket userLimitTicket = this.getULT(NumberUtil.toLong(limitTicketInfo[1]), useTickts);
					if(userLimitTicket!=null) {
						if(userLimitTicket.getStatus()==2 || userLimitTicket.getStatus()==3) {
							//userLimitTicket.setCashBalance(BigDecimal.ZERO);
							//userLimitTicket.setTicketBalance(BigDecimal.ZERO);
							return ActResult.fail("优惠券信息有误，请刷新重试");
						}
						skuCash = new BigDecimal(limitTicketInfo[2]);
						skuTicket = new BigDecimal(limitTicketInfo[3]);
						if(NumberUtil.isGreaterZero(skuCash)) {
							// 使用现金抵扣 内购券全免
							if(userLimitTicket.getCashBalance().compareTo(skuCash)<0) {
								return ActResult.fail("优惠券信息有误，请刷新重试");
							}
							userLimitTicket.setCashBalance(userLimitTicket.getCashBalance().subtract(skuCash));
						} else {
							if(userLimitTicket.getTicketBalance().compareTo(skuTicket)<0) {
								return ActResult.fail("优惠券信息有误，请刷新重试");
							}
							userLimitTicket.setTicketBalance(userLimitTicket.getTicketBalance().subtract(skuTicket));
						}

						Product p =  productService.findBySku(skuId);

						SuborderitemLimitTicket suborderitemLimitTicket = new SuborderitemLimitTicket();
						suborderitemLimitTicket.setUserId(loginUser.getId());
						suborderitemLimitTicket.setOrderType("0");
						suborderitemLimitTicket.setLimitKey(userLimitTicket.getLimitKey());
						suborderitemLimitTicket.setCreateTime(new Date());
						suborderitemLimitTicket.setUserLimitTicketId(userLimitTicket.getId());
						suborderitemLimitTicket.setTicketType(userLimitTicket.getTicketType());
						suborderitemLimitTicket.setStatus(1);
						suborderitemLimitTicket.setProductId(p.getId());
						if(userLimitTicket.getTicketType()==4 && p.getSaleKbn()!=null && p.getSaleKbn()==4) {
							// 专享券抵扣
							if(sb.contains(","+p.getId()+",") || sb.endsWith(","+p.getId())) {
							} else {
								sb += ","+p.getId();
							}
						}
						suborderitemLimitTicket.setSkuId(skuId);
						suborderitemLimitTicket.setSkuNum(0);			//只记录第一个，其余为空
						suborderitemLimitTicket.setBenefitCash(skuCash);
						//使用内购优惠金额
						suborderitemLimitTicket.setBenefitTicket(skuTicket);
						List<SuborderitemLimitTicket> list = mapSuborderitemLimitTicket.get(limitTicketInfo[0]);
						if(list == null) {
							list =new ArrayList<SuborderitemLimitTicket>();
							mapSuborderitemLimitTicket.put(limitTicketInfo[0], list);
						}
						list.add(suborderitemLimitTicket);
						
					}else {
						return ActResult.fail("优惠券信息有误，请刷新重试");
					}
				}else {
					return ActResult.fail("版本错误，请使用最新版APP或者微信公众号下单");
				}
			}
		}
		// 临时清空以备记录变化量
		for (UserLimitTicket ult : useTickts.values()) {
			ult.setCashBalance(BigDecimal.ZERO);
			ult.setTicketBalance(BigDecimal.ZERO);
		}
		if(sb.length()>"limit4".length()) {
			return  ActResult.success(sb);
		} else {
			return ActResult.success("");
		}
	}

	private UserLimitTicket getULT(Long ultId,Map<Long,UserLimitTicket> map) {
		if(!map.containsKey(ultId)) {
			map.put(ultId, userLimitTicketService.getById(ultId));
		}
		return map.get(ultId);
	}
	private ActResult<Cart> makeBuyCart(String fromType, String skuNums, String skuFreights, String useExchangeTicket,
			String useExchangeCash, String useExchangeSelf, UserFactory user, Cart cart,
			List<ProductSpecifications> outSkus) {
		// 检查购物车，防止下单重复操作
		if (cart.getAllItems().isEmpty()) {
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}

		// 创建结算购物车
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long, Integer> sku_nums = new HashMap<Long, Integer>();
		Map<Long, BigDecimal> sku_freights = new HashMap<Long, BigDecimal>();
		Map<Long, BigDecimal> sku_realPrices = new HashMap<Long, BigDecimal>();
		Map<Long, String> sku_images = new HashMap<Long, String>();
		Map<Long, String> sku_pageKeys = new HashMap<Long, String>();
		Map<Long, String> sku_froms = new HashMap<Long, String>();
		int errCnt = 0;

		// 处理sku数量参数
		if (!StringUtils.isEmpty(skuNums)) {
			if (skuNums.endsWith(",")) {
				skuNums = skuNums.substring(0, skuNums.length());
			}
			String[] arySkun = skuNums.split(",");
			for (String str : arySkun) {
				String[] strs = str.split("_");
				Long skuId = NumberUtil.toLong(strs[0]);
				sku_nums.put(skuId, NumberUtil.toInteger(strs[1]));
			}
		}
		if (skuFreights.endsWith(",")) {
			skuFreights = skuFreights.substring(0, skuFreights.length());
		}
		String[] arySkuf = skuFreights.split(",");
		for (String str : arySkuf) {
			String[] strs = str.split("_");
			Long skuId = NumberUtil.toLong(strs[0]);
			skuIds.add(skuId);
			sku_freights.put(skuId, NumberUtil.toBigDecimal(strs[1]));

			CartItem rds = cart.getItem(skuId);
			if (rds == null) {
				if (!sku_nums.containsKey(skuId)) {
					return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
				}

				errCnt++;
				ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuId, null);
				outSkus.add(sku);
				if (null == sku) {
					return ActResult.fail("商品不存在,或已下架,请返回购物车后再次购买");
				}
				Product product = this.userService_productService.getById(sku.getProductId());
				productSpecificationsService.resetPrice(sku, product, user, false, sku_nums.get(skuId));

				sku_realPrices.put(skuId, sku.getInternalPurchasePrice());
				sku_images.put(skuId, productSpecificationsImageService.findProductPicture(sku.getId(), product.getId())
						.get(0).getSource());
				sku_pageKeys.put(skuId, "");
				sku_froms.put(skuId, "");
			} else {
				sku_realPrices.put(skuId, rds.getRealPrice());
				sku_images.put(skuId, rds.getImagePath());
				sku_pageKeys.put(skuId, rds.getPageKey());
				sku_froms.put(skuId, rds.getFromType());
				if (!sku_nums.containsKey(skuId)) {
					sku_nums.put(skuId, rds.getQuantity());
				}
			}
		}

		if (errCnt == skuIds.size()) {
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}
		ActResult<Cart> actCart = ordersService.mergeBuyCart(fromType, user,skuIds, sku_nums,sku_realPrices, sku_freights,
				sku_images, sku_pageKeys, sku_froms, null,useExchangeTicket, useExchangeCash, useExchangeSelf, outSkus);
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
			ac = ordersFacade.cancel(user, subOrderId, closeReason,true);
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail(ac.getMsg());
		}
		return ac;
	}

	/**
	 * 获取订单中可返现的金额
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/getTrialReturn.user")
	@ResponseBody
	public ActResult<BigDecimal> getTrialReturn(HttpServletRequest request, String subOrderId) {
		return ActResult.success(this.suborderitemService.getTrialReturnByOrder(subOrderId));
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
		ActResult<Object> ac = suborderService.delete(user, subOrderId);
		return ac;
	}

	/**
	 * 催促发货
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/urgedDelivery.user")
	@ResponseBody
	public ActResult<Object> urgedDelivery(HttpServletRequest request, String subOrderId) {
		UserFactory user = loginUser;
		return suborderService.urgedDelivery(user, subOrderId);
	}

	/**
	 * 确认收货
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/confirmOrder.user")
	@ResponseBody
	public ActResult<Object> confirmOrder(HttpServletRequest request, String subOrderId) {
		boolean b = ordersFacade.updateOrderStatus4(loginUser, subOrderId);

		if (b) {
			return ActResult.success("确认收货成功");
		} else {
			return ActResult.fail("参数错误");
		}
	}

	/**
	 * 功能：退货退款/仅退款申请
	 *
	 * @param type        申请状态（0：退货退款；1：仅退款）
	 * @param returnOrder
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/applyReturn.user")
	@ResponseBody
	public ActResult<Object> applyReturn(Integer type, Returnorder returnOrder, Integer goodsStatus,
	                                     String imagePath1, String imagePath2, String imagePath3) throws IOException {
		if (type == null || (type != 0 && type != 1)) {
			return ActResult.fail("系统错误");
		}
		returnOrder.setUserId(loginUser.getId());
		returnOrder.setStatus(0);
		returnOrder.setCreateTime(new Date());
		returnOrder.setLastTime(TimeUtil.addDay(returnOrder.getCreateTime(), 7));

		// 上传后的图片地址
		List<String> imgPathList = new ArrayList<String>();
		// 图片1
		if (!StringUtils.isEmpty(imagePath1) && !("null").equals(imagePath1) && !("undefined").equals(imagePath1)) {
			imgPathList.add(imagePath1);
		}
		// 图片2
		if (!StringUtils.isEmpty(imagePath2) && !("null").equals(imagePath2) && !("undefined").equals(imagePath2)) {
			imgPathList.add(imagePath2);
		}
		// 图片3
		if (!StringUtils.isEmpty(imagePath3) && !("null").equals(imagePath3) && !("undefined").equals(imagePath3)) {
			imgPathList.add(imagePath3);
		}
		// 退货退款/仅退款申请
		Map<String, Object> result = returnorderService.applyReturn(type, returnOrder, goodsStatus, imgPathList);
		if (!(boolean) result.get("status")) {
			return ActResult.fail(result.get("msg").toString());
		}
		return ActResult.success("申请成功");
	}

	/**
	 * 用户上传退货退款/仅退款凭证图片
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadReturnEvidence.user")
	@ResponseBody
	public ActResult<String> uploadReturnEvidence(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> map = multipartRequest.getFileMap();
		if (map.size() > 0) {
			Long size = 0l;
			for (String key : map.keySet()) {
				size = map.get(key).getSize();
				if (size > 2097152) {
					return ActResult.fail("图片大小不能超过2M");
				}
			}
			for (String fname : map.keySet()) {
				// 获得文件：
				MultipartFile file = multipartRequest.getFile(fname);
				// 获得文件名：
//				String filename = file.getOriginalFilename();
				//String type = filename.substring(filename.lastIndexOf('.'));
				try {
					String picTYpe = FileUtils.getPicType(file.getInputStream());
					if (picTYpe == null) {
						CommonsMultipartFile cf = (CommonsMultipartFile) file;
						cf.getFileItem().delete();
						return ActResult.fail("图片格式不支持");
					}
					ActResult<List<String>> as = us.uploadPic(file.getInputStream(), file.getSize(), "."+picTYpe, "ReturnEvidence");
					if (!as.isSuccess()) {
						return ActResult.fail("图片上传失败");
					} else {
						String address =  "http://" + as.getData().get(0);
						return ActResult.success(address);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return ActResult.fail("图片上传失败");
				}
			}
		}
		return ActResult.fail("图片未修改");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/sendReturnOrder.user")
	@ResponseBody
	public ActResult<String> sendReturnOrder(HttpServletRequest request, HttpServletResponse response,Long returnOrderId,String expressType,String expressNo) {
		ActResult<String> ac = new ActResult<String>();
		Returnorder returnorder = returnorderService.getById(returnOrderId);
		if(returnorder!=null){
			if(!StringUtils.isEmpty(expressType) && !StringUtils.isEmpty(expressNo)){
				returnorder.setExpressType(expressType);
				returnorder.setExpressNo(expressNo);
				returnorder.setGoodsStatus(0);
				returnorder.setStatus(4);
				returnorderService.update(returnorder);
				ac.successSetMsg("退货成功");
			}else{
				ac.fail("缺少物流信息");
			}
		}else{
			ac.fail("查询不到此退货单");
		}
		return ac;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	 @RequestMapping("/updateReturnOrder.user")
	 @ResponseBody
	 public ActResult<Object> updateReturnOrder(Integer type, Returnorder returnOrder, Integer goodsStatus,Long refundOrderId,
             String imagePath1, String imagePath2, String imagePath3) throws IOException {
		 	if (type == null || (type != 0 && type != 1)) {
				return ActResult.fail("系统错误");
			}
			 returnOrder.setUserId(loginUser.getId());
			 returnOrder.setCreateTime(new Date());
			 returnOrder.setLastTime(TimeUtil.addDay(returnOrder.getCreateTime(), 7));
			// 上传后的图片地址
			List<String> imgPathList = new ArrayList<String>();
			// 图片1
			if (!StringUtils.isEmpty(imagePath1) && !("null").equals(imagePath1) && !("undefined").equals(imagePath1)) {
				imgPathList.add(imagePath1);
			}
			// 图片2
			if (!StringUtils.isEmpty(imagePath2) && !("null").equals(imagePath2) && !("undefined").equals(imagePath2)) {
				imgPathList.add(imagePath2);
			}
			// 图片3
			if (!StringUtils.isEmpty(imagePath3) && !("null").equals(imagePath3) && !("undefined").equals(imagePath3)) {
				imgPathList.add(imagePath3);
			}
		    // 退货退款/仅退款申请
		    Map<String, Object> result = returnorderService.updateReturn(type, returnOrder,refundOrderId, goodsStatus, imgPathList);
		    //TODO 应显示失败原因
		    if (!(boolean) result.get("status")) {
				return ActResult.fail(result.get("msg").toString());
			}
			return ActResult.success("修改售后申请成功");
	    }
	
	
	/**
	 * 功能：查询不同状态订单列表
	 *
	 * @param status   订单状态
	 *                 （
	 *                 0、未支付（待支付），
	 *                 1、已支付（待发货），
	 *                 2、已发货（待收货），
	 *                 3、退单申请中，退款申请中
	 *                 4、已收货（待评价），
	 *                 10、买家已评价，
	 *                 11、已退货完毕，
	 *                 -1、已取消，
	 *                 311、退款/售后
	 *                 ）
	 * @param pageSize
	 * @param page
	 * @author 刘聪
	 * @since 2015-06-19
	 */
	@RequestMapping("query.user")
	@ResponseBody
	public ActResult<PageInfo> query(Integer status, Integer page, Integer pageSize) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize < 1) {
			return ActResult.fail("每页显示数据不能为0");
		}
		// 查询不同状态订单列表
		return ActResult.success(suborderService.getSubordersList(loginUser.getId(), status, page, pageSize));
	}

	/**
	 * 订单详情
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/detail.user")
	@ResponseBody
	public ActResult<Map<String, Object>> orderDetail(HttpServletRequest request, String subOrderId) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(loginUser.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("order", subOrder);
		Refundorder refundorder = null;
		Returnorder returnorder = null;
		SuborderLimitTicket suborderLimitTicket =null;
		if (subOrder.getRefundOrderId() != null) {
			refundorder = refundorderService.getById(subOrder.getRefundOrderId());
			data.put("refund", refundorder);
		}
		if (subOrder.getReturnOrderId() != null) {
			returnorder = returnorderService.getById(subOrder.getReturnOrderId());
			data.put("return", returnorder);
		}
		if(subOrder.getLimitTicketCnt()!=null && subOrder.getLimitTicketCnt()>0) {
			suborderLimitTicket= suborderLimitTicketService.findBySuborderId(subOrder.getSubOrderId());
			data.put("suborderLimitTicket", suborderLimitTicket);
		}
		return ActResult.success(data);
	}
	
	/**
	 * 订单详情Ex（线下发货）
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/detailEx.user")
	@ResponseBody
	public ActResult<Map<String, Object>> orderDetailEx(HttpServletRequest request, String subOrderId) {
		SuborderQuery query = new SuborderQuery();
//		query.setUserId(loginUser.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		if(subOrder==null){
			return ActResult.fail("暂时无此订单");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("order", subOrder);
		Refundorder refundorder = null;
		Returnorder returnorder = null;
		if (subOrder.getRefundOrderId() != null) {
			refundorder = refundorderService.getById(subOrder.getRefundOrderId());
			data.put("refund", refundorder);
		}
		if (subOrder.getReturnOrderId() != null) {
			returnorder = returnorderService.getById(subOrder.getReturnOrderId());
			data.put("return", returnorder);
		}
		return ActResult.success(data);
	}
	/**
	 * 售后订单详情
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/afterSaleOrderDetail.user")
	@ResponseBody
	public ActResult<Map<String, Object>> afterSaleOrderDetail(HttpServletRequest request, String subOrderId) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(loginUser.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		if(subOrder==null){
			return ActResult.fail("暂时无此订单");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		Refundorder refundorder = null;
		Returnorder returnorder = null;
		if(subOrder.getRefundOrderId() == null && subOrder.getReturnOrderId() == null){
			return ActResult.fail("此订单不是售后订单");
		}
		if (subOrder.getRefundOrderId() != null) {
			refundorder = refundorderService.getRefundordersById(subOrder.getRefundOrderId());
			data.put("refund", refundorder);
		}
		if (subOrder.getReturnOrderId() != null) {
			returnorder = returnorderService.getReturnOrdersById(subOrder.getReturnOrderId());
			data.put("return", returnorder);
		}
		return ActResult.success(data);
	}

	/**
	 * @param subOrderId
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/comments/getSubOrderInfo")
	public ModelAndView getSubOrderInfo(String subOrderId,String uid, String ticket, ModelAndView model) {

		Long userId = null;
		//根据ticket获取用户的信息
		if (StringUtils.isEmpty(ticket)) {
			if (StringUtils.isEmpty(uid)) {
				
				return model;
			}else{
				userId = Long.valueOf(uid);
			}

		} else {
			 ActResult<UserFactory> actUser=loginFacade.hasLogin(ticket);
			if (actUser.isSuccess()) {
				userId = actUser.getData().getId();
			}
		}

		//根据subOrderId查询订单信息
		SubOrderItemVo2 vo = new SubOrderItemVo2();
        vo.setUserId(userId);
        vo.setSubOrderId(subOrderId);
        vo.setCommentFlag(0);
        com.github.pagehelper.PageInfo<SubOrderItemVo2> pList= suborderitemService.findForComment(vo,1,100);
        
		List<SubOrderItemVo2> listSubItem = pList.getList();
		List<Comments> listComments = new ArrayList<Comments>();
		
        List<Long> qids = new ArrayList<Long>();
        List<SubOrderItemVo2> qItems = new ArrayList<SubOrderItemVo2>();
        
		for (SubOrderItemVo2 subItem : listSubItem) {
        	if(subItem.getSaleKbn() == 5 && NumberUtil.isGreaterZero(subItem.getEmpPrice())) {
        		// 试用
        		Long qid = productQuestionnaireService.getQuestionnaireId(subItem.getProductId()); 
        		if(NumberUtil.isGreaterZero(qid)) {
        			qids.add(qid);
        			qItems.add(subItem);
        			continue;
        		}
        	}
			Comments co = new Comments();
			//subOrderId
			co.setSubOrderid(subItem.getSubOrderId());
			//图片
			co.setPic(subItem.getImage());
			//子单项
			co.setSubOrderItemId(subItem.getSubOrderItemId());
			co.setSuborderitem(subItem);
			listComments.add(co);
		}
		
        if (listComments.size() ==0){
    		model.addObject("comment", qItems.get(0));
    		model.addObject("questionnare", productQuestionnaireService.getExById(qids.get(0)));
    		model.setViewName("sy_vote");
        } else {
    		model.addObject("info", listComments);
    		model.setViewName("plbb");
        }
        
		model.addObject("userId", userId);//
		model.addObject("qcnt", qItems.size());//
		if (!listSubItem.isEmpty()) {
			model.addObject("subOrderId", listSubItem.get(0).getSubOrderId());
		}
		return model;
	}

	/**
	 * @param subOrderId
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/comments/questionnaire{qId}")
	public ModelAndView questionnaire(@PathVariable Long qId, ModelAndView model,String fromWay) {

		model.addObject("userId", loginUser.getId());
		model.addObject("q", productQuestionnaireService.getExById(qId));
		model.addObject("fromWay", fromWay);
		model.setViewName("sy_vote2");
		return model;
	}
	
	@RequestMapping(value = "/comments/saveComments", headers = {"content-type=application/json", "content-type=application/xml"})
	@ResponseBody
	public ActResult<Object> saveComments(@RequestBody List<Comments> comments,Long questionnareId,String answers, ModelAndView model, HttpServletRequest request) {
		ActResult<Object> act = new ActResult<Object>();
		if (comments.isEmpty()) {
			act.setSuccess(false);
			act.setMsg("参数错误");
			return act;
		} else {
			/*//subOrderId+subOrderItem+star1+star2+star3+text+userId都是后台传过来的
			List<Comments> listComm = JsonUtil.getList(comments.toString(), Comments.class);
			if (listComm.isEmpty()) {
				act.setSuccess(false);
				act.setMsg("参数错误");
				return act;
			}*/
			
			boolean isNormal = true;
			boolean isOrderComment = true;
		    if(NumberUtil.isGreaterZero(questionnareId)){
		    	isNormal = false;
		    } else {
		    	isNormal = true;
		    }
		    if(comments!=null && !comments.isEmpty()) {
		    	if(StringUtils.isEmpty(comments.get(0).getSubOrderid())) {
			    	isOrderComment=false;
		    	}
		    } else {
		    	isOrderComment=false;
		    }
		    
		    
		    if(isOrderComment) {
				for (Comments comm : comments) {
					//插入评论的内容
					comm.setUserId(comm.getUserId());
					comm.setCreatTime(new Date());
					//评级
					if(isNormal) {
						comm.setCommentDegree(comm.getStar1());
					}
					//根据subOrderItemId查询商品的sku信息，并获取skuID
					Suborderitem subOrderItem = this.suborderitemService.getById(comm.getSubOrderItemId());
	
					comm.setProductId(subOrderItem.getProductId());
					//供应商id  需要从product表中查询 supplyid
			        Suborder suborder = suborderService.getById(subOrderItem.getSubOrderId());
					comm.setSupplyid(suborder.getSupplierId());
	
					// 商品sku
					comm.setAttributeJson(subOrderItem.getItemValues());
					
					//添加评论信息
					boolean cmt = this.commentsFacade.save(comm,isNormal,questionnareId,answers); 
					if(cmt) {
				    	if(isNormal) {
				    		try {
				    			wsm.updateCommentNum(comm.getProductId());
							} catch (Exception e) {
								logger.info("商品评论数更新异常，可能是商品已经下架");
							}
				    	}
						commentsFacade.prizeForComment(comm, entParamCodeService.getAppFirstPrizeCode().get("002"), loginUser.getNickName());
					}
				}
		    } else {
			    questionnaireAnswerService.answerQuestion(answers, loginUser.getId(), questionnareId);
		    }

			act.setSuccess(true);
			act.setMsg("修改成功");
			return act;
		}

	}
	@RequestMapping(value = { "/comments/saveComments.tj" })
	@ResponseBody
	public ActResult<Object> tjSaveComments(Comments comm) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		//添加评论信息
		boolean cmt = this.commentsFacade.save(comm,true,null,""); 
		if(cmt) {
	    	try {
	    		wsm.updateCommentNum(comm.getProductId());
			} catch (Exception e) {
				logger.info("商品评论数更新异常，可能是商品已经下架");
			}
	    	UserFactory user = userService.getById(comm.getUserId());
			commentsFacade.prizeForComment(comm, entParamCodeService.getAppFirstPrizeCode().get("002"), user.getNickName());
			result.setSuccess(true);
			result.setMsg("系统评价成功");
		}else {
			result.setSuccess(false);
			result.setMsg("保存失败");
		}
		return result;
	}
	
	/**
	 * 延长收货
	 *
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/extendedReceipt.user")
	@ResponseBody
	public ActResult<Object> extendedReceipt(String subOrderId) {
		return returnorderService.extendedReceipt(subOrderId);
	}
	
	@RequestMapping(value = { "/newCalculateFreight.user" })
	@ResponseBody
	public ActResult<Map<String,Object>> newCalculateFreight(String selfDelivery,Long shippingAddressId, String sku_nums,HttpServletRequest request) {
		List<String> nums = new ArrayList<String>();
		List<String> skus = new ArrayList<String>();

		ActResult<Map<String,Object>> result = new ActResult<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		//supplier对应的运费金额
		Map<String,BigDecimal> supplierShippingMap = new HashMap<String,BigDecimal>();
		ShippingAddress addr = null;
		// 自提
		if(!"1".equals(selfDelivery)) {
			addr = shippingAddressService.getById(loginUser.getId(),shippingAddressId);
			if(StringUtils.isEmpty(addr)) {
				result.setSuccess(false);
				result.setMsg("请先指定收货地址");
				return result;
			}
		}
		

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
		
		//sku 对应的商品及数量合并
		for (int i=0;i<skus.size();i++) {
			Product p = productService.findBySku(Long.parseLong(skus.get(i)));
			ProductSpecificationsVo sku= specificationsService.findByIdCache(Long.parseLong(skus.get(i)),p.getId().toString());
			Integer num = NumberUtil.toInteger(nums.get(i));
			specificationsService.resetPrice(sku, p, loginUser, false,num);
			skuProduct.put(skus.get(i), p.getId());
			
			if(!productMap.containsKey(p.getId())) {
				productMap.put(p.getId(), p);
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
		List<Long> list2 = new ArrayList<Long>();
		boolean flag = true;
		for (Long pid : productMap.keySet()) {
			if(flag){
				if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+pid) != null) {
					list2.add(pid);
				}
			}
			BigDecimal chkLimitCntAndArea = shippingFacade.chkLimitCntAndArea(productMap.get(pid), numMap.get(pid),null != addr ? addr.getAid():null,null,loginUser.getId(),selfDelivery);
			if(chkLimitCntAndArea.compareTo(BigDecimal.ZERO) != 0) {
				flag = false;
			}
			freightMap.put(pid, chkLimitCntAndArea);
			List<Long> lc = supplierMap.get(productMap.get(pid).getSupplierId());
			if(lc == null) {
				lc = new ArrayList<Long>();
				supplierMap.put(productMap.get(pid).getSupplierId(), lc);
				supplierShippingMap.put(""+productMap.get(pid).getSupplierId(), BigDecimal.ZERO);
			}
			supplierIdList.add(productMap.get(pid).getSupplierId());
			lc.add(pid);
		}
		//处理购物车多个商品限购组
		if(flag) {
			if(list2.size() > 0) {
				Integer total = productTrialLimitItemService.getProductTrialLimitItemByProductId(list2);
				if(total > 0){
					freightMap.put(list2.get(0),new BigDecimal(9999));
				}
			}
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
		map.put("newData", supplierShippingMap);
		map.put("freeMap", shippingFacade.getFreeShippingMap(supplierIdList, "1".equals(selfDelivery) ? "1":addr.getAid()));
		result.setData(map);
		return result;
	}

	/**
	 * 微信订单
	 * @param uid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView page(Integer status,ModelAndView model,HttpServletRequest request){
		model.addObject("status", status);
		model.setViewName("order");
		return model;
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
	
	/**
	 * 订单详情
	 *
	 * @param request
	 * @param subOrderId
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/getOrderInfo.user")
	@ResponseBody
	public ActResult<Map<String, Object>> getOrderInfo(HttpServletRequest request, String subOrderId,String orderId,String type) throws IllegalAccessException, InvocationTargetException {
			
		if(StringUtils.isEmpty(orderId) && StringUtils.isEmpty(subOrderId)&&StringUtils.isEmpty(type)){
			return ActResult.fail("参数有误");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		if("1".equals(type) || "4".equals(type)){//type=1为团购订单
			GroupOrders groupOrders = groupOrdersService.getByOrderIdAndUserId(orderId,loginUser.getId());
			GroupBuyVo groupBuy = groupBuyService.getById(groupOrders.getGroupId());
			data.put("groupBuy", groupBuy);
			data.put("groupOrders", groupOrders);
		} else if("5".equals(type)) {
			ExchangeSuborder subOrder =null;

			if(!StringUtils.isEmpty(subOrderId)){
				subOrder = exchangeSuborderService.getById(subOrderId);
			}else{
				ExchangeSuborder q = new ExchangeSuborder();
				q.setOrderId(Long.valueOf(orderId));
				q.setUserId(loginUser.getId());
				subOrder = exchangeSuborderService.selectByModel(q).get(0);
			}
			exchangeSuborderService.selectItems4Set(subOrder);
			data.put("order", subOrder);
			// 自动添加换领清单
			/*List<UserExchangeFavorites> uef= userExchangeFavoritesService.autoAddFavorites(loginUser.getId(), subOrder);*/
			//计算换领总额
			List<UserExchangeTicket> userExchangeList = userExchangeTicketService.usingTicket(loginUser.getId());
			BigDecimal total = BigDecimal.ZERO;
			if(null != userExchangeList && userExchangeList.size() >0) {
				for (UserExchangeTicket userExchangeTicket : userExchangeList) {
					total = total.add(userExchangeTicket.getEmpAvgAmount().subtract(userExchangeTicket.getUsedAmount()));
				}
			}
			data.put("userExchange", total);
			data.put("addCnt", 0);
		}else{
			
			if(!StringUtils.isEmpty(subOrderId)){
				SuborderQuery query = new SuborderQuery();
				query.setUserId(loginUser.getId());
				query.setSubOrderId(subOrderId);
				SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
				//所有的子单项
				List<Suborderitem> suborderItemList = this.suborderitemService.findBySubOrderId(subOrderId);
				data.put("order", subOrder);
				data.put("saveAmount", getOrderOrSuborderSaveAmount(suborderItemList));
			}else{
				Orders orders = ordersService.findById(loginUser.getId(),Long.valueOf(orderId));
				List<Suborder> suborderList = suborderService.findByOrderId(Long.valueOf(orderId));
				List<Suborderitem> suborderItemList = this.suborderitemService.findByOrderId(orderId);
				
				orders.setItems(suborderList);
				data.put("order", orders);
				data.put("saveAmount", getOrderOrSuborderSaveAmount(suborderItemList));
			}
		}
		return ActResult.success(data);
	}

	private BigDecimal getOrderOrSuborderSaveAmount(List<Suborderitem> SuborderitemList) {
		BigDecimal saveAmount = BigDecimal.ZERO;
		if(SuborderitemList!=null && SuborderitemList.size()>0) {
			for (Suborderitem suborderitem : SuborderitemList) {
				//电商价减去当前内购价*数量
				BigDecimal amount = (suborderitem.getPrice().subtract(suborderitem.getInternalPurchasePrice())).multiply(NumberUtil.toBigDecimal(suborderitem.getNumber()));
				saveAmount = saveAmount.add(amount);
			}
		}
		return saveAmount;
	}
}
