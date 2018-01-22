package com.wode.factory.user.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.exception.SupplierShardeLessException;
import com.wode.factory.model.Currency;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.Invoice;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.UserBalance;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.InvoiceService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ProductTrialLimitItemService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.SuborderstatuslogService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.util.CartUtil;
import com.wode.factory.user.util.OrderUtils;
import com.wode.factory.user.util.ShippingUtil;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.OrderVO;
import com.wode.factory.user.vo.ProductSpecificationsVo;

import cn.org.rapid_framework.web.util.CookieUtils;

/**
 * 订单
 * 
 * @author zhengxiongwu
 *
 */
@Controller
@RequestMapping("/order")
@SuppressWarnings("unchecked")
public class OrderController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private ProductTrialLimitItemService productTrialLimitItemService;
	
	@Autowired
	private SuborderitemService suborderitemService;

	@Qualifier("ordersService") 
	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrdersFacade ordersFacade;
	
	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;

	@Qualifier("suborderstatuslogService")
	@Autowired
	private SuborderstatuslogService suborderstatuslogService;

	@Qualifier("shippingAddressService")
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@Qualifier("invoiceService")
	@Autowired
	private InvoiceService invoiceService;
	//factory_service
    @Autowired
    private ProductService productService;
    //user_service
    @Autowired
    private com.wode.factory.user.service.ProductService userService_productService;


	@Autowired
	private ProductSpecificationsImageService productSpecificationsImageService;
    @Autowired
	private CurrencyService currencyService;
    @Autowired
	private ShippingFacade shippingFacade;
    
    @Autowired
    ProductSpecificationsService specificationsService;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
    
    @Autowired
	private UserBalanceService userBalanceService;
	private static final int CART_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;
	
	 //运费工具类
    @Autowired
    private ShippingUtil shippingUtil;
    //购物车工具类
    @Autowired
    private CartUtil cartUtil;
	
	
	@RequestMapping(value = { "/saveShippingAddress" })
	@ResponseBody
	public ActResult<ShippingAddress> saveShippingAddress(Long shippingAddressId,String aid,String address,String name,String provinceName,String cityName,String areaName,String phone,HttpServletRequest request, HttpServletResponse response) {
		ActResult<ShippingAddress> ret = new ActResult<ShippingAddress>();
		try{
			ret.setSuccess(false);
			UserFactory user = getUser(request,response);
			ShippingAddress shippingAddress = new ShippingAddress();
			if(shippingAddressId != null){
				shippingAddress.setId(shippingAddressId);
			}
			shippingAddress.setAid(aid);
			shippingAddress.setName(name);
			shippingAddress.setPhone(phone);
			shippingAddress.setAddress(address);
			shippingAddress.setOrderNo(1);
			shippingAddress.setUserId(user.getId());
			shippingAddress.setProvinceName(provinceName);
			shippingAddress.setCityName(cityName);
			shippingAddress.setAreaName(areaName);
			shippingAddressService.saveOrupdateAddress(shippingAddress);
			ret.setSuccess(true);
			ret.setData(shippingAddress);
		}catch(Exception e){
			e.printStackTrace();
			ret.setSuccess(false);
			ret.setMsg("服务器异常");
		}
		return ret;
	}
	
	//删除常用地址
	@RequestMapping(value = { "/deleteShippingAddress" })
	@ResponseBody
	public ActResult<ShippingAddress> deleteShippingAddress(Long shippingAddressId,HttpServletRequest request, HttpServletResponse response) {
		ActResult<ShippingAddress> ret = new ActResult<ShippingAddress>();
		try{
			ret.setSuccess(false);
			UserFactory user = getUser(request,response);
			shippingAddressService.deleteAddress(user.getId(), shippingAddressId);
			ret.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			ret.setSuccess(false);
			ret.setMsg("服务器异常");
		}
		return ret;
	}
	
	//保存发票信息
	@RequestMapping(value = { "/saveInvoice" })
	@ResponseBody
	public ActResult<Invoice> saveInvoice(Invoice invoice,HttpServletRequest request, HttpServletResponse response) {
		ActResult<Invoice> ret = new ActResult<Invoice>();
		try{
			ret.setSuccess(false);
			UserFactory user = getUser(request,response);
//			Invoice invoice = new Invoice();
//			if(invoi.getId() > 0){
//				invoice.setId(invoi.getId());
//			}
			invoice.setUserId(user.getId());
//			invoice.setTitle(invoi.getTitle());
//			invoice.setType(invoi.getType());
			invoiceService.updateInvoice(invoice);
			ret.setSuccess(true);
			ret.setData(invoice);
		}catch(Exception e){
			e.printStackTrace();
			ret.setSuccess(false);
			ret.setMsg("服务器异常");
		}
		return ret;
	}
	
	//删除发票信息
	@RequestMapping(value = { "/deleteInvoice" })
	@ResponseBody
	public ActResult<Invoice> deleteInvoice(Long invoiceId,HttpServletRequest request, HttpServletResponse response) {
		ActResult<Invoice> ret = new ActResult<Invoice>();
		try{
			ret.setSuccess(false);
			UserFactory user = getUser(request,response);
			invoiceService.deleteInvoice(user.getId(), invoiceId);
			ret.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			ret.setSuccess(false);
			ret.setMsg("服务器异常");
		}
		return ret;
	}
	
	@RequestMapping(value = { "/info" })
	public String info(ModelMap model, HttpServletRequest request,String partNumbers, HttpServletResponse response) {
		UserFactory user = getUser(request,response);
		String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_"
				+ user.getId());
		if (null == cartJson || cartJson.trim().equals("") || null == partNumbers || partNumbers.trim().equals("")) {
			return "redirect:/index.html";
		}
		//初始化购物车选中商品
		String [] selectProduct = partNumbers.substring(0, partNumbers.length()-1).split(",");
		Set<Long> list = new HashSet<Long>();
		for(String str : selectProduct){
			String [] strs = str.split("_");
			long skuId = Long.parseLong(strs[1]);
			list.add(skuId);
		}
		
		//加载常用地址
		List<ShippingAddress> shippingAddressList =shippingAddressService.findByUserId(user.getId());
		//加载发票信息
		List<Invoice> invoiceList =invoiceService.findByUserId(user.getId());
		Cart cart = JsonUtil.getObject(cartJson, Cart.class);
		//购物查为空。应该跳转到购物车页面
		if(cart.getAllItems().isEmpty()){
			return "redirect:/cart/list.html";
		}
		cart.setSelectProduct(list);
		BigDecimal canUseTicket = BigDecimal.ZERO;//购物车购买商品最高可使用的内购券数量
		Currency ticketCurrency = currencyService.findByName("companyTicket");
		UserBalance ticketBalance = userBalanceService.findByUserAndType(user.getId(), ticketCurrency.getId());
		BigDecimal ticket = BigDecimal.ZERO;
		int totalBuy = 0 ;

		//总内购券
		BigDecimal totalCompanyTicket = BigDecimal.ZERO;
		
		List<Long> list2 = new ArrayList<Long>();
		boolean flag = true;
		for(CartItem item : cart.getAllItems()){
			ProductSpecificationsVo sku =null;
			if(item.isBuyFlag()){
				Long skuId = Long.parseLong(item.getPartNumber());
				 sku= productSpecificationsService.findByIdCache(skuId,
						item.getProductId().toString());

				if (sku != null) {
					if (!skuId.equals(sku.getId())) {
						item.setPartNumber(sku.getId() + "");
					}
					// 重新计算购物车中商品的价格
					boolean isLadder= productSpecificationsService.resetPrice(sku, productService.findById(sku.getProductId(), false),
							user, false, item.getQuantity());
					if (item.getPrice().compareTo(sku.getPrice()) != 0
							|| item.getMaxCompanyTicket().compareTo(sku.getMaxFucoin()) != 0
							|| item.getRealPrice().compareTo(sku.getInternalPurchasePrice()) != 0) {// 判断价格，购物车价格和数据库价格不想等，取数据库价格
						item.setPrice(sku.getPrice());
						item.setMaxCompanyTicket(sku.getMaxFucoin());
						item.setProductCode(sku.getProductCode());
						item.setRealPrice(sku.getInternalPurchasePrice());
					}
					item.setIsLadder(isLadder);
				} else {
					item.setBuyFlag(false);
				}
			}
			if(item.isBuyFlag()){
				totalBuy+=item.getQuantity();//购买数量
//				canUseTicket +=item.getMaxCompanyTicket()==null?0:item.getMaxCompanyTicket()*item.getQuantity();
				canUseTicket=canUseTicket.add(item.getMaxCompanyTicket()==null?BigDecimal.ZERO:item.getMaxCompanyTicket().multiply(BigDecimal.valueOf(item.getQuantity())));
				if(!StringUtils.isEmpty(ticketBalance)){
//					ticket = ticketBalance.getBalance().intValue();
					ticket = ticketBalance.getBalance();
					if(ticket.compareTo(canUseTicket)==1){//ticket>canUseTicket
						ticket = canUseTicket;
					}
//					ticketExchange = ticket * ticketCurrency.getPercentage();
					//ticketExchange = ticket.multiply(BigDecimal.valueOf(ticketCurrency.getPercentage()));
				}

				Product product = productService.findById(item.getProductId(), false);
				item.setSaleKbn(product.getSaleKbn());
				//item.setImagePath(productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
				item.setRealAmount(item.getRealPrice().multiply(NumberUtil.toBigDecimal(item.getQuantity())));

				/*if(item.getSaleKbn() == 2) {
					item.setRealAmount(item.getPrice().subtract(item.getMaxCompanyTicket()).multiply(NumberUtil.toBigDecimal(item.getQuantity())));
					item.setBenefitAmount(BigDecimal.ZERO);
					item.setBenefitTicket(BigDecimal.ZERO);
					item.setBenefitSelf(BigDecimal.ZERO);
					exchangeCI.add(item);
				}*/
				//计算全部的内购券
                totalCompanyTicket = totalCompanyTicket.add(sku.getMaxFucoin().multiply(BigDecimal.valueOf(item.getQuantity())));
                //查询企业限购
                if(flag) {
	        		if(productService.checkProductLimit(product, user)){
	        			flag = false;
	        			model.addAttribute("errormsg",product.getName() + "只允许企业级用户购买");
	        		}
        		}
        		// 查看限购组限购
        		if(flag) {
        			if(redisUtil.getData(RedisConstant.GROUP_LIMIT_PRODUCT_PRE+product.getId()) != null) {
        				list2.add(item.getProductId());
        				Map<String,Object> map = new  HashMap<String,Object>();
        				map.put("productId", product.getId());
        				map.put("userId", user.getId());
        				Integer totalCount = suborderitemService.getSuborderitemListByProductId(map);
        				if(totalCount > 0){
        					flag = false;
        					model.addAttribute("errormsg",product.getName() + "存在限购");
        				}
        			}
        		}
			}
		}
		//购物车结算处理用户在同一个限购组购买多个商品
		if(flag) {
			if(list2.size() > 0){
				Integer total = productTrialLimitItemService.getProductTrialLimitItemByProductId(list2);
				if(total > 0){
					model.addAttribute("errormsg","此订单存在限购商品");
				}
			}
		}
		
		//判断用户是否足够支付内购券
		if(!checkUserTicket(user.getId(), totalCompanyTicket)){
			model.addAttribute("errormsg", "可用内购券不足");
		}
		
		redisUtil.setData(RedisConstant.CART_REDIS + "_" + user.getId(), JsonUtil.toJsonString(cart), CartController.CAR_REDIS_EXPTIME);
		
		//用户可使用的货币
//		Currency poinCurrency = currencyService.findByName("point");
//		UserBalance poinBalance = userBalanceService.findByUserAndType(user.getId(), poinCurrency.getId());

		double pointExchange = 0;
//		if(!StringUtils.isEmpty(poinBalance)){
//			point = poinBalance.getBalance().intValue();
//			pointExchange = point * poinCurrency.getPercentage();
//		}

		//用户现金券余额
		Currency cashCurrency = currencyService.findByName("balance");
		UserBalance cashBalance = userBalanceService.findByUserAndType(user.getId(), cashCurrency.getId());
		BigDecimal cash = BigDecimal.ZERO;
		BigDecimal cashExchange = BigDecimal.ZERO;
		if(!StringUtils.isEmpty(cashBalance)){
			cash = cashBalance.getBalance();
			cashExchange = cash.multiply(new BigDecimal(cashCurrency.getPercentage()));
		}
		
		pointExchange = 0;
		Map<Long, List<CartItem>> supplierCartList = cart.groupBySupplier();
		model.addAttribute("cart", cartUtil.setCartItemShopId(supplierCartList));
		model.addAttribute("freeMap", shippingUtil.getFreeString(supplierCartList, user != null ? user.getId() : null));
		model.addAttribute("totalNum", totalBuy);
		model.addAttribute("totalFreight", cart.calculateTotalFreight());
		model.addAttribute("shippingAddressList", shippingAddressList);
		//代收点信息
		setExpressCollectingPoint(shippingAddressList, model);
		
		model.addAttribute("invoiceList", invoiceList);
		model.addAttribute("pointExchange", pointExchange);
		//用户账户余额中所有的优惠券
		model.addAttribute("totalTicket", ticketBalance.getBalance().multiply(new BigDecimal(ticketCurrency.getPercentage())));
		model.addAttribute("ticket", canUseTicket);
		model.addAttribute("ticketExchange", canUseTicket);
		model.addAttribute("cash", cash);
		model.addAttribute("cashExchange", cashExchange);

		/**
		 * ***********************************分配换领币**********************************************
		 **/
		BigDecimal exchangeCash = BigDecimal.ZERO;
		BigDecimal exchangeCanUse = BigDecimal.ZERO;
		/*if(!exchangeCI.isEmpty()) {
			// 获取员工换领币
			List<UserExchangeTicket> ets = new ArrayList<UserExchangeTicket>(); //getExchangeTicket(user.getId());
			BigDecimal share = BigDecimal.ZERO;
			if(!ets.isEmpty()) {
				share = userExchangeTicketService.getShareAmout(user.getId());
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

							// 记录抵扣金额
							if(bticket.compareTo(share)>0) {
								ci.setBenefitAmount(ci.getBenefitAmount().add(share));
								share = BigDecimal.ZERO;
							} else {
								ci.setBenefitAmount(ci.getBenefitAmount().add(bticket));
								share = share.subtract(bticket);
							}
							needPay = needPay.subtract(bticket);
						}
						
						if(!NumberUtil.isGreaterZero(needPay)) {
							// 全部抵扣 
							break;
						}
					}
				}
				
				exchangeCash = exchangeCash.add(ci.getBenefitAmount());
				exchangeCanUse = exchangeCanUse.add(ci.getBenefitTicket());
			}
		}*/

		model.addAttribute("exchangeCash", exchangeCash);
		model.addAttribute("exchangeCanUse", exchangeCanUse);
		
		model.addAttribute("user", user);
		redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
		return "order/info";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/create" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public ActResult<Object> create(HttpServletRequest request, HttpServletResponse response, Orders order,BigDecimal useCompanyTicket,
			String useExchangeTicket, String useExchangeCash,
			String useExchangeSelf, BigDecimal useCash, String message) {
		
		try {
			if(useCompanyTicket == null) useCompanyTicket=BigDecimal.ZERO;
			if(useCompanyTicket.compareTo(new BigDecimal(0))==-1){//useCompanyTicket<0
				return ActResult.fail("内购券使用不正确");
			}
			UserFactory user = getUser(request,response);

			ShippingAddress addr = null;
			// 自提
			if("1".equals(order.getSelfDelivery())) {
				//设置自提手机号姓名
				OrderUtils.setMobileAndAddress(order, user);
			} else {
				order.setSelfDelivery("0");
				
				String shippingAddressId = request.getParameter("addressRadio");
				if(StringUtils.isEmpty(shippingAddressId)) {
					return ActResult.fail("请指定收货地址");				
				}
				addr = shippingAddressService.getById(user.getId(), Long.parseLong(shippingAddressId));
				if(StringUtils.isEmpty(addr)) {
					return ActResult.fail("请指定收货地址");				
				}
			}
			if(useCash==null) useCash=BigDecimal.ZERO;
			if(useCash.compareTo(BigDecimal.ZERO)>0) {
				Currency cashCurrency = currencyService.findByName("balance");
				UserBalance userBalance = userBalanceService.findByUserAndType(user.getId(), cashCurrency.getId());
				if(userBalance==null || useCash.compareTo(userBalance.getBalance())>0) {
					return ActResult.fail("现金券余额不足");
				}
			}

			List<ProductSpecifications> outSkus = new ArrayList<ProductSpecifications>();
			
			//展示时运费
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_"+ user.getId());
			Cart cart = JsonUtil.getObject(cartJson, Cart.class);
			String[] skus = request.getParameterValues("skus[]");
			String[] nums = request.getParameterValues("nums[]");
			String[] freights = request.getParameterValues("freights[]");
			ActResult<Cart> actCart = this.makeBuyCart(skus, nums, freights, useExchangeTicket, useExchangeCash, useExchangeSelf, user,cart, outSkus);
			
			if(!actCart.isSuccess()) {
				return ActResult.fail(actCart.getMsg());
			}
			List<CartItem> cartItemList = actCart.getData().getAllItems();
			// 更新购物车中选中商品的企业券使用量
			List<CartItem> lstForCheckFreights = new ArrayList<CartItem>();
			Map<String,Long> skuProduct = new HashMap<String,Long>();
			Map<Long,Product> productMap = new HashMap<Long,Product>();
			Map<Long,Integer> numMap = new HashMap<Long,Integer>();
			Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
			//总应该使用的内购券
			BigDecimal totalUseTicket = BigDecimal.ZERO;
			for (CartItem ci : cartItemList) {
				//直接从数据库查询商品信息
				Product product = this.userService_productService.getById(ci.getProductId());
				//////////////////////////////////////////////////////////////////////////////////////////////
				//试用商品检查问卷
				if(product.getSaleKbn()!=null && product.getSaleKbn()==5) {
					if(!NumberUtil.isGreaterZero(product.getEmpPrice())) {
						//评价后购买
						if(product.getQuestionnaireId() != -1 &&!this.questionnaireAnswerService.hasAnswer(product.getQuestionnaireId(), user.getId())){
							ActResult<Object> rtn = ActResult.fail(ci.getProductName()+",需要先回答商家问卷才能下单");
							rtn.setData(product.getQuestionnaireId());
							return rtn;
						}
					}	
				}
				//////////////////////////////////////////////////////////////////////////////////////////////
				
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
				
				if(ci.getCompanyTicket() == null) ci.setCompanyTicket(BigDecimal.ZERO);
				
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
				
				//计算全部的内购券
                totalUseTicket = totalUseTicket.add(ci.getCompanyTicket());
			
			}

			//判断用户是否足够支付内购券
			if(!checkUserTicket(user.getId(), totalUseTicket)){
				return ActResult.fail("内购券不足");
			}
			
			//check 运费
			ActResult chkfr = checkFreight(order.getSelfDelivery(), addr == null ? null : addr.getAid(),
					lstForCheckFreights, skuProduct, productMap, numMap, amountMap, user.getId());
			if (!chkfr.isSuccess()) {
				return chkfr;
			}
			
			order.setUserId(user.getId());
			order.setCreateTime(new Date());
			order.setUpdateBy(user.getUserName());
			order.setUpdateTime(new Date());
			OrderVO vo = new OrderVO();
			vo.setOrders(order);
			//增加企业券使用 2015-6-12
			List<String> subIds = new ArrayList<String>();
			try{
				ActResult<Object> ar = ordersFacade.createOrder(vo, actCart.getData(),useCash,subIds,message);// 保存订单
				//清空缓存
				if(!ar.isSuccess()){
					return ar;
				}
				//处理ace用户消费
				if(Long.valueOf("201712221700825").equals(user.getSupplierId())) {
					Cookie[] cookies = request.getCookies();
					Map<String, Cookie> map = CookieUtils.toMap(cookies);
					Cookie cookie = map.get("cookie_aceTicket");
					user.setEmail(cookie.getValue());
				}
				//记录内购券流水
				ActResult recordFlowResult = ordersService.recordFlow(order.getOrderId(), user);
				if(!recordFlowResult.isSuccess()){
					ordersFacade.cancelOrder(user, order.getOrderId(), recordFlowResult.getMsg(),false);
					return ActResult.fail("流水记录失败，请联系客服");
				}
				ShopPushUtil.pushMsg4order(redisUtil,subIds,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
				cart.clearBuied(cartItemList);
				redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), 
						JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
			} catch (BenefitLessException e1) {
				return ActResult.fail("换领币余额不足，请刷新后重试"); 
			} catch (SupplierShardeLessException e1) {
				return ActResult.fail("企业共享券不足，请刷新后重试"); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ActResult.fail("系统错误，请联系客服"); 
		}
		
		if (order.getStatus() == 1)
			return ActResult.success("/member/paySuccess?orderId="+order.getOrderId()); 
		return ActResult.success("/payment/pay?orderId="+order.getOrderId()); 
	}

	private ActResult<Cart> makeBuyCart(String[] skus, String[] nums,String[] freights, 
			String useExchangeTicket, String useExchangeCash,String useExchangeSelf,
			UserFactory user,Cart cart,List<ProductSpecifications> outSkus) {
		// 检查购物车，防止下单重复操作
		if(cart.getAllItems().isEmpty()){
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}
					
		// 创建结算购物车
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long,Integer> sku_nuns = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> sku_freights = new HashMap<Long,BigDecimal>();
		Map<Long,BigDecimal> sku_realPrices = new HashMap<Long,BigDecimal>();
		Map<Long,String> sku_images = new HashMap<Long,String>();
		Map<Long,String> sku_pageKeys = new HashMap<Long,String>();
		Map<Long,String> sku_froms = new HashMap<Long,String>();
		int errCnt=0;
		for (int i=0;i<skus.length;i++) {
			Long skuId=NumberUtil.toLong(skus[i]);
			skuIds.add(skuId);
			sku_nuns.put(skuId, NumberUtil.toInteger(nums[i]));
			sku_freights.put(skuId, NumberUtil.toBigDecimal(freights[i]));
			
			CartItem rds = cart.getItem(skuId);
			if(rds == null) {
				errCnt++;

				ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuId,null);
				outSkus.add(sku);
	            if(null == sku){
	            	return ActResult.fail("商品不存在,或已下架,请返回购物车后再次购买");
	            }
				Product product = this.userService_productService.getById(sku.getProductId());
				productSpecificationsService.resetPrice(sku, product, user,false,sku_nuns.get(skuId));
				
				sku_realPrices.put(skuId, sku.getInternalPurchasePrice());
				sku_images.put(skuId, productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
				sku_pageKeys.put(skuId, "");
				sku_froms.put(skuId, "");
			} else {
				sku_realPrices.put(skuId, rds.getRealPrice());
				sku_images.put(skuId, rds.getImagePath());
				sku_pageKeys.put(skuId, rds.getPageKey());
				sku_froms.put(skuId, rds.getFromType());
			}
		}

		if(errCnt== skus.length){
			return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
		}
		ActResult<Cart> actCart = ordersService.mergeBuyCart("pc", user,skuIds, sku_nuns,sku_realPrices, sku_freights,
				sku_images, sku_pageKeys, sku_froms,null, useExchangeTicket, useExchangeCash, useExchangeSelf, outSkus);
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

		//不自提才进行运算
		if(!"1".equals(selfDelivery)){
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
		}
		
		
		////////////////////////////////////////////////////////////////////////////////
		return ActResult.success(null);
	}
	
	private void setExpressCollectingPoint(List<ShippingAddress> shippingAddressList,ModelMap model){
		if(!shippingAddressList.isEmpty()){
			ShippingAddress sa = shippingAddressList.get(0);
			String address = sa.getProvinceName()+sa.getCityName()+sa.getAreaName()+sa.getAddress();
//			ActResult<Object> act = this.expressCollectingPointsService.collectingPoints(null, null,address, true);
//			if(act.isSuccess()){
//				Map<String,Object> map = (Map<String,Object>)act.getData();
//				
//				/*model.addAttribute("lng",map.get("lng"));
//				model.addAttribute("lat",map.get("lat"));*/
//				model.addAttribute("collectingPoint_address",map.get("address"));//代收点地址
////				model.addAttribute("address1",map.get("phone")+"  ("+sa.getName()+" 收)"+sa.getPhone());
//				model.addAttribute("collectingPoint_phone", map.get("phone"));//代收点手机号
////				model.addAttribute("id", map.get("id"));
//			}
			model.addAttribute("shippingName", sa.getName());//收货人的名称
			model.addAttribute("shippingPhone", sa.getPhone());//收货人的电话号码
			model.addAttribute("shippingAddress", address);//收货人地址
		}
	}
	
	@RequestMapping(value = { "/calculateTotalFreight" })
	@ResponseBody
	public ActResult<Map<String,Object>> calculateTotalFreight(String selfDelivery,Long shippingAddressId, HttpServletRequest request,HttpServletResponse response) {
		String[] nums = request.getParameterValues("nums[]");
		String[] skus = request.getParameterValues("skus[]");

		ActResult<Map<String,Object>> result = new ActResult<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();

		UserFactory user =getUser(request,response);
		// 自提
		ShippingAddress addr = null;
		if("1".equals(selfDelivery)) {
		} else {
			if(StringUtils.isEmpty(shippingAddressId)) {
				result.setSuccess(false);
				result.setMsg("请先指定收货地址");
				return result;
			}
			addr = shippingAddressService.getById(user==null?null:user.getId(),shippingAddressId);
			if(StringUtils.isEmpty(addr)) {
				result.setSuccess(false);
				result.setMsg("请先指定收货地址");
				return result;
			}
		}
		
		
		if(skus == null || skus.length==0) {
			result.setSuccess(false);
			result.setMsg("商品信息错误,请返回购车重新下单");
			return result;
		}
		if(nums == null || nums.length==0) {
			result.setSuccess(false);
			result.setMsg("数量信息错误");
			return result;
		}
		if(skus.length != nums.length) {
			result.setSuccess(false);
			result.setMsg("商品，数量信息错误");
			return result;
		}
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		Map<Long,Integer> numMap = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
		Map<String,Long> skuProduct = new HashMap<String,Long>();
		
		//sku 对应的商品及数量合并
		for (int i=0;i<skus.length;i++) {
			ProductSpecificationsVo sku= specificationsService.findByIdCache(Long.parseLong(skus[i]),null);
			Product p = productService.findById(sku.getProductId(), false);
			Integer num = NumberUtil.toInteger(nums[i]);
			specificationsService.resetPrice(sku, p, user, false,num);
			skuProduct.put(skus[i], p.getId());
			
			if(!productMap.containsKey(p.getId())) {
				productMap.put(p.getId(), p);
			}
			
			BigDecimal amount = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(num));
			
			//保存数量
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
		
		logger.debug("tongyiceshi orderController amountMap is {}" ,amountMap.toString());
		
		// 根据商品，判断数量及收货地址是否超出限购 并设置初始运费0
		Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();
		Map<Long,List<Long>> supplierMap = new HashMap<Long,List<Long>>();
		//设置商家supplierId 的list
		HashSet<Long> supplierIdList  =   new  HashSet<Long>();
		for (Long pid : productMap.keySet()) {
			freightMap.put(pid, shippingFacade.chkLimitCntAndArea(productMap.get(pid), numMap.get(pid),null != addr ?addr.getAid():null,null,user==null?null:user.getId(),selfDelivery));
			List<Long> lc = supplierMap.get(productMap.get(pid).getSupplierId());
			if(lc == null) {
				lc = new ArrayList<Long>();
				supplierMap.put(productMap.get(pid).getSupplierId(), lc);
			}
			supplierIdList.add(productMap.get(pid).getSupplierId());
			lc.add(pid);
		}
		logger.debug("tongyiceshi orderController supplierIdList is {}" ,supplierIdList.toString());
		
		logger.debug("tongyiceshi orderController freightMap is {}" ,freightMap.toString());
		
		////////////////////////////////////////////////////////////////////////////////


		if("1".equals(selfDelivery)) {
			
		} else {
			//重新计算商品的运费已商家为单位
			for (Long sid : supplierMap.keySet()) {
				//增加新的商品列表 去掉包邮的
				shippingFacade.calculateSupplierShippingFee(selfDelivery, addr == null ? null : addr.getAid(), user,
						sid, supplierMap.get(sid), productMap, numMap, amountMap, freightMap, null);
			}
			logger.debug("tongyiceshi orderController freightMap1 is {}" ,freightMap.toString());
		}
		
		//每个sku运费返回，以商品为单位，首次出现则包含全部运费，再次出现运费为0
		List<Double> rtn = new ArrayList<Double>();
		for (int i=0;i<skus.length;i++) {
			Long key=skuProduct.get(skus[i]);
			if(freightMap.containsKey(key)) {
				rtn.add(freightMap.get(key).doubleValue());
				freightMap.remove(key);
			} else {
				rtn.add(0D);
			}
		}
		
		result.setSuccess(true);
		map.put("oldData", rtn);
		map.put("freeMap", shippingUtil.getFreeShippingMap(supplierIdList, "1".equals(selfDelivery) ? "1":addr.getAid()));
		result.setData(map);
		return result;
	}

	/**
	 * 查询订单状态
	 * @param subOrderId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/orderStatus" })
	@ResponseBody
	public ActResult<Object> orderStatus(String subOrderId,String orderId,String payType,HttpServletRequest request,HttpServletResponse response){
		UserFactory user = getUser(request,response);
		if("5".equals(payType)) {
			if(StringUtils.isEmpty(orderId)){
				return ActResult.success(this.exchangeSuborderService.getById(subOrderId).getStatus());
			}else{
				ExchangeSuborder sq = new ExchangeSuborder();
				sq.setUserId(user.getId());
				sq.setOrderId(NumberUtil.toLong(orderId));
				
				List<ExchangeSuborder> suborders = this.exchangeSuborderService.selectByModel(sq);
				for (ExchangeSuborder suborder : suborders) {
					if (suborder.getStatus()!=0) {
						return ActResult.success(suborder.getStatus());
					}
				}
				return ActResult.success(0);
			}
		
		} else {
			if(StringUtils.isEmpty(orderId)){
				SuborderQuery sq = new SuborderQuery();
				sq.setUserId(user.getId());
				sq.setSubOrderId(subOrderId);
				return ActResult.success(this.suborderService.querySubOrder(sq).getStatus());
			}else{
				List<Suborder> suborders = this.suborderService.findByOrderId(Long.valueOf(orderId));
				for (Suborder suborder : suborders) {
					if (suborder.getStatus()!=0) {
						return ActResult.success(suborder.getStatus());
					}
				}
				return ActResult.success(0);
			}
		}
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
}
