package com.wode.factory.user.web.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.Product;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.model.UserFactory;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.QuestionnaireAnswerService;
import com.wode.factory.user.service.ShippingAddressService;
import com.wode.factory.user.service.UserExchangeFavoritesService;
import com.wode.factory.user.service.UserExchangeTicketService;
import com.wode.factory.user.util.CartUtil;
import com.wode.factory.user.util.OrderUtils;
import com.wode.factory.user.util.ShippingUtil;
import com.wode.factory.user.vo.ProductSpecificationsVo;

/**
 * 订单
 * 
 * @author zhengxiongwu
 *
 */
@Controller
@RequestMapping("/exchangeOrder")
@SuppressWarnings("unchecked")
public class ExchangeOrderController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(ExchangeOrderController.class);
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ExchangeOrdersFacade ordersFacade;

	@Qualifier("shippingAddressService")
	@Autowired
	private ShippingAddressService shippingAddressService;
	//factory_service
    @Autowired
    private ProductService productService;
    //user_service
    @Autowired
    private com.wode.factory.user.service.ProductService userService_productService;
    
    @Autowired
	private ShippingFacade shippingFacade;
    
    @Autowired
    ProductSpecificationsService specificationsService;
	@Qualifier("productSpecificationsService")
	@Autowired
	private ProductSpecificationsService productSpecificationsService;
    
	private static final int CART_REDIS_EXPTIME = 60 * 60 * 24 * 30 * 6;//redis 过期时间6个月
	@Autowired
	private UserExchangeTicketService userExchangeTicketService;
	@Autowired
	private QuestionnaireAnswerService questionnaireAnswerService;
	@Autowired
	private UserExchangeFavoritesService userExchangeFavoritesService;
	
	 //运费工具类
    @Autowired
    private ShippingUtil shippingUtil;
    //购物车工具类
    @Autowired
    private CartUtil cartUtil;
    //阶梯价格
    @Autowired
    private ProductLadderService productLadderService;
    
    @Autowired
	private ExchangeSuborderService exchangeSuborderService;
	
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
		Cart cart = JsonUtil.getObject(cartJson, Cart.class);
		//购物查为空。应该跳转到购物车页面
		if(cart.getAllItems().isEmpty()){
			return "redirect:/cart/list.html";
		}
		cart.setSelectProduct(list);
		
		int totalBuy = 0 ;
		
		List<CartItem> exchangeCI = new ArrayList<CartItem>();
		BigDecimal productPrice = BigDecimal.ZERO;//商品总价
		
		for(CartItem item : cart.getAllItems()){
			ProductSpecificationsVo sku = null;
			Product product = null;
			if(item.isBuyFlag()){
				Long skuId = Long.parseLong(item.getPartNumber());
				sku = productSpecificationsService.findByIdCache(skuId,item.getProductId().toString());
				
				if(sku!=null) {
					if(!skuId.equals(sku.getId())) {
						item.setPartNumber(sku.getId()+"");
					}
					//重新计算购物车中商品的价格
					product = productService.findById(item.getProductId(), false);
					boolean isLadder = productSpecificationsService.resetPrice(sku, product, user,false, item.getQuantity());
					
					if(item.getPrice().compareTo(sku.getPrice())!=0 ||item.getMaxCompanyTicket().compareTo(sku.getMaxFucoin())!=0
							||item.getRealPrice().compareTo(sku.getInternalPurchasePrice())!=0 ){//判断价格，购物车价格和数据库价格不想等，取数据库价格
						item.setPrice(sku.getPrice());
						item.setMaxCompanyTicket(sku.getMaxFucoin());
						item.setProductCode(sku.getProductCode());
					}
					item.setSaleKbn(product.getSaleKbn());
					item.setRealPrice(sku.getInternalPurchasePrice());
					item.setIsLadder(isLadder);

					totalBuy+=item.getQuantity();//购买数量
					
					item.setRealAmount(item.getRealPrice().multiply(NumberUtil.toBigDecimal(item.getQuantity())));

					if(item.getSaleKbn() == 2) {
						item.setRealAmount(item.getRealPrice().multiply(NumberUtil.toBigDecimal(item.getQuantity())));
						item.setBenefitAmount(BigDecimal.ZERO);
						item.setBenefitTicket(BigDecimal.ZERO);
						item.setBenefitSelf(BigDecimal.ZERO);
						exchangeCI.add(item);
					}
					productPrice=productPrice.add(item.getRealAmount());
	                //查询企业限购
	        		if(productService.checkProductLimit(product, user)){
	        			model.addAttribute("errormsg",product.getName() + "只允许企业级用户购买");
	        		}
				} else {
					item.setBuyFlag(false);
				}
			}
		}
		
		Map<Long, List<CartItem>> supplierCartList = cart.groupBySupplier();
		model.addAttribute("cart", cartUtil.setCartItemShopId(supplierCartList));
		model.addAttribute("freeMap", shippingUtil.getFreeString(supplierCartList, user != null ? user.getId() : null));
		model.addAttribute("totalNum", totalBuy);
		model.addAttribute("shippingAddressList", shippingAddressList);
		//代收点信息
		setExpressCollectingPoint(shippingAddressList, model);

		/**
		 * ***********************************分配换领币**********************************************
		 **/
		BigDecimal userExchage = BigDecimal.ZERO;
		BigDecimal exchangeCanUse = BigDecimal.ZERO;
		
		if(!exchangeCI.isEmpty()) {
			// 获取员工换领币
			List<UserExchangeTicket> ets = getExchangeTicket(user.getId());
			
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
		BigDecimal sum = exchangeSuborderService.getOrderAmount(user.getId());
		BigDecimal newsum = sum.add(productPrice);
		List<HashMap<String, Object>> searchs =userExchangeFavoritesService.getMaySelectable(user.getId(), sum, newsum);
		model.addAttribute("searchs", searchs);
		model.addAttribute("userExchage", userExchage);
		model.addAttribute("exchangeCanUse", exchangeCanUse);
		model.addAttribute("btnName", "match");
		model.addAttribute("user", user);
		redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
		return "exchange/info";
	}

	private List<UserExchangeTicket> getExchangeTicket(Long userId) {
		return userExchangeTicketService.usingTicket(userId);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/create" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public ActResult<Object> create(HttpServletRequest request, HttpServletResponse response, ExchangeOrders order,
			BigDecimal useCompanyTicket, String useExchangeTicket, String useExchangeCash,
			String useExchangeSelf, BigDecimal useCash, String message,String freeSwap) {
		
		try {
			if(useCompanyTicket == null) useCompanyTicket=BigDecimal.ZERO;

			UserFactory user = getUser(request,response);
			String cartJson = redisUtil.getData(RedisConstant.CART_REDIS + "_"+ user.getId());
			Cart cart = JsonUtil.getObject(cartJson, Cart.class);
			List<CartItem> cartItemList = cart.getAllItems();
			if(cartItemList.isEmpty()){
				return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
			}
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
				order.setAid(addr.getAid());
			}
			//useExchangeTicket  商品SKUID+_+使用换领币数量
			if(useExchangeTicket == null) useExchangeTicket="";
			if(useExchangeCash == null) useExchangeCash="";
			if(useExchangeSelf == null) useExchangeSelf="";
			
			String[] productUseTick = useExchangeTicket.split(",");
			String[] productExchangeCash = useExchangeCash.split(",");
			String[] productExchangeSelf = useExchangeSelf.split(",");
						
			if(useCash==null) useCash=BigDecimal.ZERO;
			
			//展示时运费
			String[] skus = request.getParameterValues("skus[]");
			String[] freights = request.getParameterValues("freights[]");
			Map<String,BigDecimal> sku_freights = new HashMap<String,BigDecimal>();
			for (int i=0;i<skus.length;i++) {
				sku_freights.put(skus[i], NumberUtil.toBigDecimal(freights[i]));
			}
			
			// 更新购物车中选中商品的企业券使用量
			List<CartItem> lstForCheckFreights = new ArrayList<CartItem>();
			Map<String,Long> skuProduct = new HashMap<String,Long>();
			Map<Long,Product> productMap = new HashMap<Long,Product>();
			Map<Long,Integer> numMap = new HashMap<Long,Integer>();
			Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
			for (CartItem ci : cartItemList) {
				if (ci.isBuyFlag()) {
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
					
					// 直接从数据库查询sku信息
					ProductSpecificationsVo sku = this.specificationsService.findByIdCache(Long.valueOf(ci.getPartNumber()),ci.getProductId()+"");
                    if(null == sku){
                    	return ActResult.fail("商品不存在,或已下架,请返回购物车后再次购买");
                    }
					productSpecificationsService.resetPrice(sku, product, user,false,ci.getQuantity());
					
					//比对内购价
					if(sku.getInternalPurchasePrice().compareTo(ci.getRealPrice()) != 0){
						return ActResult.fail("商家修改了:"+ci.getProductName()+"商品的内购价格,请重新下单");
					}
					Integer num = ci.getQuantity();					
					//内购券
					ci.setCompanyTicket(BigDecimal.ZERO);
					
					//优惠券
					BigDecimal amount =ci.getRealPrice().multiply(NumberUtil.toBigDecimal(num));
					ci.setBenefitAmount(BigDecimal.ZERO);
					for (int i = 0; i < productUseTick.length; i++) {
						String[] strs = productUseTick[i].split("_");
						if (ci.getPartNumber().equals(strs[0])) {
							BigDecimal exchangeTicket =new BigDecimal(strs[1]);
							String[] cashs = productExchangeCash[i].split("_");
							String[] selfs = productExchangeSelf[i].split("_");
							BigDecimal exchangeCash =new BigDecimal(cashs[1]);
							BigDecimal exchangeSelf =new BigDecimal(selfs[1]);
							if (NumberUtil.isGreaterZero(exchangeTicket)) {
								ci.setBenefitAmount(exchangeCash);
								ci.setBenefitTicket(exchangeTicket);
								ci.setBenefitSelf(exchangeSelf);								
							} else {
								ci.setBenefitAmount(BigDecimal.ZERO);
								ci.setBenefitTicket(BigDecimal.ZERO);
								ci.setBenefitSelf(BigDecimal.ZERO);
							}
							break;
						}
					}
					
					// 暂不考虑换领券抵扣后包邮
					//amount = amount.subtract(ci.getBenefitAmount()); 
					
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
					ci.setFreight(sku_freights.get(ci.getPartNumber()));
					lstForCheckFreights.add(ci);
				}
			}

			if(skuProduct.isEmpty()){
				return ActResult.fail("订单已提交,不能重复提交订单,您可以在我的订单中查看");
			}
			
			//check 运费
			ActResult chkfr = checkFreight(order.getSelfDelivery(),addr==null?null:addr.getAid(), lstForCheckFreights, skuProduct, productMap, numMap,amountMap,user.getId());
			if(!chkfr.isSuccess()) {
				return chkfr;
			}
			
			order.setUserId(user.getId());
			order.setCreateTime(new Date());
			order.setUpdateBy(user.getUserName());
			order.setUpdateTime(new Date());
			
			//增加企业券使用 2015-6-12
			List<String> subIds = new ArrayList<String>();
			try{
				ActResult<Object> ar = ordersFacade.createOrder(order, cart,useCash,subIds,message,freeSwap);// 保存订单
				//清空缓存
				if(!ar.isSuccess()){
					return ar;
				}
				//记录内购券流水
//				ActResult recordFlowResult = ordersService.recordFlow(order.getOrderId(), user);
//				if(!recordFlowResult.isSuccess()){
//					ordersFacade.cancelOrder(user, order.getOrderId(), recordFlowResult.getMsg());
//					return ActResult.fail("流水记录失败，请联系客服");
//				}
//				for (String string : subIds) {
//					ShopPushUtil.pushMsg4order(redisUtil,string,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
//				}
				cart.clearBuied();
				redisUtil.setData(RedisConstant.CART_REDIS+"_"+user.getId(), JsonUtil.toJsonString(cart), CART_REDIS_EXPTIME);
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
			return ActResult.success("/member/paySuccess?payType=5&orderId="+order.getOrderId()); 
		return ActResult.success("/payment/pay?payType=5&orderId="+order.getOrderId()); 
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
		
	/**
	 * 获取可能可能加入的匹配商品
	 */
	@RequestMapping(value = { "/getMaySelectable" })
	@ResponseBody
	public ActResult<Object> getMaySelectable(BigDecimal productPrice,HttpServletRequest request,HttpServletResponse response) throws NumberFormatException, IllegalAccessException, InvocationTargetException {
		ActResult<Object> result = new ActResult<Object>(); 
		// 计算区间
		UserFactory user = getUser(request,response);
		BigDecimal sum = exchangeSuborderService.getOrderAmount(user.getId());
		BigDecimal newsum = sum.add(productPrice);
		List<HashMap<String, Object>> searchs =userExchangeFavoritesService.getMaySelectable(user.getId(), sum, newsum);
		return result.success(searchs);
	}
}
