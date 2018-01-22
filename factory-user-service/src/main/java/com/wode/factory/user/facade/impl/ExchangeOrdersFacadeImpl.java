package com.wode.factory.user.facade.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.db.DBUtils;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.common.util.TimeUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.exception.BenefitLessException;
import com.wode.factory.exception.SupplierShardeLessException;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ProductSpecificationsImageService;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.PromotionDao;
import com.wode.factory.user.dao.PromotionProductDao;
import com.wode.factory.user.dao.SupplierCategoryDao;
import com.wode.factory.user.dao.UserBalanceDao;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.facade.ShippingFacade;
import com.wode.factory.user.facade.UserExchangeTicketFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.model.UseExchangeTicketVo;
import com.wode.factory.user.service.ExchangeOrdersService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.ExchangeSuborderitemService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.InvoiceService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.search.WodeSearchManager;

@Service("exchangeOrdersFacade")
public class ExchangeOrdersFacadeImpl implements  ExchangeOrdersFacade {
	private static Logger logger = LoggerFactory.getLogger(ExchangeOrdersFacadeImpl.class);
	@Autowired
	private ExchangeOrdersService ordersService;
	@Autowired
	private ExchangeSuborderService suborderService;

	@Autowired
	private ShippingFacade shippingFacade;
	@Autowired
	private OrdersFacade ordersFacade;

	@Qualifier("invoiceService")
	@Autowired
	private InvoiceService invoiceService;
	//factory_service
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;    
	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("supplierCategoryDao")
	private SupplierCategoryDao supplierCategoryDao;
	@Autowired
	private ExchangeSuborderitemService suborderitemService;
	@Autowired
	@Qualifier("userBalanceDao")
	private UserBalanceDao userBalanceDao;
	@Autowired
	UserBalanceService userBalanceService;

	@Autowired
	@Qualifier("promotionDao")
	private PromotionDao promotionDao;
	@Autowired
	@Qualifier("promotionProductDao")
	private PromotionProductDao promotionProductDao;
	@Autowired
	ProductSpecificationsService specificationsService;
	@Autowired
	private ProductSpecificationsImageService  productSpecificationsImageService;
	@Autowired
	private WodeSearchManager wsm;
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserExchangeTicketFacade userTicketFacade;
    @Autowired
    DBUtils dbUtils;
	private String qiyeApiUrl = Constant.QIYE_API_URL;
	
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;

	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	
	@Override
	@Transactional
	public ActResult<Object> createOrder(ExchangeOrders order, Cart cart,BigDecimal useCash,List<String> subIds,String message,String freeSwap) throws BenefitLessException, SupplierShardeLessException {
		//校验订单信息
		ActResult<Object> ar = ordersFacade.checkOrder(cart,userService.getById(order.getUserId()));
		if (!ar.isSuccess()) {
			return ar;
		}
		ActResult<Object> result = ActResult.success(null);
		BigDecimal totalProduc = BigDecimal.ZERO;// 商品总价，不含运费
		BigDecimal totalShipping = BigDecimal.ZERO; //cart.calculateTotalFreight();// 总运费
		BigDecimal totalAdjustment = BigDecimal.ZERO;// 总折扣金额
		BigDecimal realPrice = BigDecimal.ZERO;// 实际支付金额 = 商品总价 + 总运费 - 总折扣金额
		int status = 0;// 订单状态 具体规则未定(0：待付款，1：支付成功)
		// TODO 根据活动规则、优惠券、使用余额等计算总折扣
		//realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
		//商品价格
		//order.setTotalProduct(totalProduc);
		//商品运费
		order.setTotalShipping(totalShipping);
		//总折扣
		order.setTotalAdjustment(totalAdjustment);
		
		order.setRealPrice(realPrice);

		ordersService.save(order);// 保存订单
		long orderId = order.getOrderId();// 母单号
		Map<Long, List<CartItem>> map = cart.groupBySupplier();

		ExchangeSuborder subOrder = null;
		CartItem cartItem = null;
		int num = 0;
		Iterator<Map.Entry<Long, List<CartItem>>> it = map.entrySet().iterator();
		BigDecimal useCashLast = useCash==null?BigDecimal.ZERO:useCash;
		
		Map<String,String> supplierNote = new HashMap<String,String>();//对应商家留言集合
		if (!StringUtils.isEmpty(message)) {//pc端
			String[] noto = message.split(",");
			for (String string : noto) {
				String[] ary=string.split("_");
				if(ary.length>1 && !StringUtils.isEmpty(ary[1])) {
					supplierNote.put(ary[0], ary[1]);
				}
			}
		}
		List<ExchangeSuborderitem> cutItems = new ArrayList<ExchangeSuborderitem>();
		Map<Long,UseExchangeTicketVo> mapExchange = new HashMap<Long,UseExchangeTicketVo>();
		List<ExchangeSuborder> suborderList = new ArrayList<ExchangeSuborder>();
		while (it.hasNext()) {
			Map.Entry<Long, List<CartItem>> entry = it.next();
			// 保存子订单
			BigDecimal subTotalCash = BigDecimal.ZERO;// 总折扣金额
			BigDecimal subTotalAdjustment = BigDecimal.ZERO;// 总折扣金额
			List<CartItem> cartItems = entry.getValue();// 当前供应商的订单
			boolean flag = false;// 判断是否购买该供应商的商品
			for (CartItem item : cartItems) {
				if (item.isBuyFlag()) {
					flag = true;
					break;
				}
			}
			if (!flag) {// 未购买该供应商商品，跳出循环
				continue;
			}
			num++;
			subOrder = new ExchangeSuborder();
			subOrder.setUserId(order.getUserId());
			subOrder.setSubOrderId(orderId + "-" + num);
			subOrder.setOrderId(orderId);
			subOrder.setSupplierId(entry.getKey());
			subOrder.setFreeSwap(freeSwap);
			if (supplierNote.containsKey(subOrder.getSupplierId()+"")) {//pc端
				subOrder.setNoto(supplierNote.get(subOrder.getSupplierId()+""));
			}else{//app端
				subOrder.setNoto(order.getNote());
			}
			BigDecimal subTotalPrice = BigDecimal.ZERO;// 商品总价，不含折扣
			BigDecimal subTotalProduc = BigDecimal.ZERO;// 商品总价，不含运费
			BigDecimal subTotalShipping = cart.calculateSupplierFreight(entry.getKey());// 总运费
			BigDecimal subRealPrice = BigDecimal.ZERO;// 实际支付金额 = 商品总价 + 总运费 - 总折扣金额
			String productName = "";
			for (int j = 0; j < cartItems.size(); j++) {
				cartItem = cartItems.get(j);
				if (cartItem.isBuyFlag()) {
					subTotalProduc = subTotalProduc.add(cartItem.getRealPrice().multiply(new BigDecimal(cartItem.getQuantity())));
					Product product = productService.findById(cartItem.getProductId(),false);
					productName += product.getFullName()+",";
				}
			}
			//生成subOrder(不包含折扣)
			totalProduc = totalProduc.add(subTotalProduc);
			subTotalPrice = subTotalProduc.add(subTotalShipping);
			subOrder.setTotalProduct(subTotalPrice);
			subOrder.setTotalShipping(subTotalShipping);
			//订单运费加算
			totalShipping = totalShipping.add(subTotalShipping);
			subOrder.setRealPrice(subRealPrice);
			subOrder.setStatus(order.getStatus());// 状态
			subOrder.setExchangeStatus(0);
			
			Date date = new Date();
			subOrder.setCreateTime(date);// 创建日期
			// 延长15天
			subOrder.setLasttakeTime(TimeUtil.addDay(date, 15));// 最后确认收货时间
			subOrder.setInvoiceStatus(0);//默认不申请发票
			subOrder.setUpdateTime(date);
			productName = productName.substring(0, productName.length()-1);
			if(productName.length()>64) {//防止超出长度
				productName = productName.substring(0, 64);
			}
			subOrder.setProductName(productName);
			ExchangeSuborder so = suborderService.save(subOrder);
			suborderList.add(so);
			// 保存子订单项
			String subOrderId = subOrder.getSubOrderId();
			ExchangeSuborderitem subOrderItem = null;
			BigDecimal benefitUsage = BigDecimal.ZERO;// 优惠券
			for (int j = 0; j < cartItems.size(); j++) {
				cartItem = cartItems.get(j);
				if (cartItem.isBuyFlag()) {
					subOrderItem = new ExchangeSuborderitem();
					Product product = productService.findById(cartItem.getProductId(),false);
					if(StringUtils.isEmpty(subOrder.getShopId())) {
						subOrder.setShopId(product.getShopId());
					}
					SupplierCategory sc = supplierCategoryDao.getBySupplierAndCategory(product.getSupplierId() , product.getCategoryId(), product.getShopId());
					if (sc != null) {
						subOrderItem.setCommissionRatio(sc.getCommissionRatio() == null ? 0f : sc.getCommissionRatio());
					}
					subOrderItem.setSubOrderId(subOrderId);
					subOrderItem.setPartNumber(cartItem.getPartNumber());// 商品SKU
					subOrderItem.setPrice(cartItem.getPrice());// 价格
					subOrderItem.setNumber(cartItem.getQuantity());// 数量
					subOrderItem.setCreateTime(new Date());// 创建日期
					subOrderItem.setSkuId(Long.parseLong(cartItem.getPartNumber()));
					subOrderItem.setProductId(cartItem.getProductId());
					subOrderItem.setPromotionId(cartItem.getPromotionId());
					subOrderItem.setPromotionProductId(cartItem.getPromotionProductId());
					subOrderItem.setOrderId(order.getOrderId());
					subOrderItem.setShipping(cartItem.getFreight());
					subOrderItem.setSubOrderId(subOrderId);
					subOrderItem.setCommentFlag(0);
					subOrderItem.setSaleKbn(cartItem.getSaleKbn());
					//商品名称
					subOrderItem.setProductName(product.getFullName());
					//员工特价
					subOrderItem.setEmpPrice(product.getEmpPrice());
					//品类id
					subOrderItem.setCategoryId(product.getCategoryId());
					//品类名称
					subOrderItem.setCategoryName(productCategoryService.findById(product.getCategoryId()).getName());
					//商品条形码
					subOrderItem.setProductCode(cartItem.getProductCode());
					//商品规格
					subOrderItem.setItemValues(cartItem.getItemValues());
					//图片路径
					subOrderItem.setImage(cartItem.getImagePath());
					//增加内购价
					subOrderItem.setInternalPurchasePrice(cartItem.getRealPrice());
					//来自页面
					subOrderItem.setFromWay(cartItem.getPageKey());
					//来自支付方式
					subOrderItem.setFromType(cartItem.getFromType());
					subOrderItem = suborderitemService.save(subOrderItem);
					subOrderItem.setCompanyTicket(BigDecimal.ZERO);
										
					//计算换领币使用量 
					if(!NumberUtil.isGreaterZero(cartItem.getBenefitTicket())) {
						subOrderItem.setBenefitType(null);
						subOrderItem.setBenefitAmount(BigDecimal.ZERO);
						subOrderItem.setBenefitTicket(BigDecimal.ZERO);
						subOrderItem.setBenefitSelf(BigDecimal.ZERO);
					} else {
						subOrderItem.setBenefitType(3);
						if(cartItem.getBenefitAmount()==null) {
							cartItem.setBenefitAmount(BigDecimal.ZERO);
						}
						// 临时记录换领币使用状况（商品单位）
						UseExchangeTicketVo uext = null;
						if(mapExchange.containsKey(cartItem.getProductId())) {
							uext = mapExchange.get(cartItem.getProductId());
						} else {
							uext= new UseExchangeTicketVo();
							uext.setProductId(cartItem.getProductId());
							uext.setCash(BigDecimal.ZERO);
							uext.setTicket(BigDecimal.ZERO);
							uext.setSelf(BigDecimal.ZERO);
							uext.setNote("购买商品："+cartItem.getProductName()+",订单ID:"+subOrderItem.getSubOrderId());
							uext.setSubOrderId(subOrderItem.getSubOrderId());
							mapExchange.put(uext.getProductId(), uext);
						}
						uext.setCash(uext.getCash().add(cartItem.getBenefitAmount()));
						uext.setTicket(uext.getTicket().add(cartItem.getBenefitTicket()));
						uext.setSelf(uext.getSelf().add(cartItem.getBenefitSelf()));
						
						subOrderItem.setBenefitTicket(cartItem.getBenefitTicket());
						subOrderItem.setBenefitAmount(cartItem.getBenefitAmount());
						subOrderItem.setBenefitSelf(cartItem.getBenefitSelf());
						
						//优惠券累计
						benefitUsage=benefitUsage.add(cartItem.getBenefitAmount());
					}
					//这里换成内购价
					BigDecimal realPay = cartItem.getRealPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));//应付款
					//应付款=货款-内购券-优惠券
					//应付款=内购价-优惠券
					realPay = realPay.subtract(cartItem.getBenefitAmount()==null?BigDecimal.ZERO:cartItem.getBenefitAmount());//BigDecimal.valueOf(cartItem.getCompanyTicket() * freeTicket)
					subOrderItem.setRealPay(realPay);//不记运费
					suborderitemService.update(subOrderItem);
					
					//库存相关操作  不做库存处理
				}
			}
			// 用户内购券使用量计算折价总数
			so.setCompanyTicket(BigDecimal.ZERO);
			subTotalAdjustment=so.getCompanyTicket();
			//优惠券累计
			if (benefitUsage.compareTo(BigDecimal.ZERO)>0) {// > 0
				so.setBenefitType(3);
				so.setBenefitAmount(benefitUsage);
			} else {
				so.setBenefitType(null);
				so.setBenefitAmount(BigDecimal.ZERO);
			}
			subTotalAdjustment =subTotalAdjustment.add(benefitUsage); //BigDecimal.valueOf(fljUsage * freeTicket);
			
			//实付金额及优惠
			subRealPrice = subTotalPrice.subtract(subTotalAdjustment);
			so.setRealPrice(subRealPrice);
			so.setTotalAdjustment(subTotalAdjustment);
			
			//现金券抵扣
			if (subTotalCash.compareTo(BigDecimal.ZERO)>0) {// > 0
				so.setCashPay(subTotalCash);
			} else {
				so.setCashPay(BigDecimal.ZERO);
			}
			
			if(so.getRealPrice().compareTo(so.getCashPay())==0) {
				so.setStatus(1);// 状态
				subOrder.setExchangeStatus(1);
				so.setPayTime(date);
				subOrder.setStatus(1);// 状态
				subOrder.setPayTime(date);
				subIds.add(subOrder.getSubOrderId());
			}
			so.setLimitTicketCnt(0);
			suborderService.update(so);
			
			// 用户计算优惠总价格
			totalAdjustment=totalAdjustment.add(subTotalAdjustment);
		}
		
		// 换领币消费处理
		if(mapExchange.size()>0) {
			int deal = userTicketFacade.orderUserTicket(order.getUserId(), mapExchange, order.getName(), order.getOrderId());
			if(deal==-1) {
				// 回退库存
				for (ExchangeSuborderitem item : cutItems) {
					inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());
				}
				throw new BenefitLessException(order.getName(),order.getName()+"");
			} else if(deal==-2) {
				// 回退库存
				for (ExchangeSuborderitem item : cutItems) {
					inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());
				}
				throw new SupplierShardeLessException("换领币");
			}
		}

		realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
		order.setTotalProduct(totalProduc);
		order.setTotalShipping(totalShipping);
		order.setTotalAdjustment(totalAdjustment);
		order.setRealPrice(realPrice);
		order.setItems(suborderList);
		//根据支付方式、还需支付金额判断订单状态
		if (realPrice.compareTo(BigDecimal.ZERO) == 0 || realPrice.compareTo(useCash)==0) {
			status=1;
		}
		order.setStatus(status);
		ordersService.update(order);
		result.setData(order);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ActResult<String> cancel(UserFactory user, String subOrderId, String closeReason,Integer exchangeStatus,boolean flowResult) {
		ExchangeSuborder subOrder = this.suborderService.getById(subOrderId);
		if(!subOrder.getUserId().equals(user.getId())) {
			subOrder=null;
		}
		//判断是否为团购订单
		if (null == subOrder) {
			return ActResult.fail("订单不存在");
		}
		// -1 订单已关闭
		if(subOrder.getStatus()==-1){
			return ActResult.fail("订单已取消");
		}
		//未支付 0            已支付1
		if (subOrder.getStatus() != 0&&subOrder.getStatus() != 1) {
			return ActResult.fail("订单不能取消");
		}
		//未支付 0            已支付1
		if (subOrder.getStockUp() !=null && subOrder.getStockUp()==1) {
			return ActResult.fail("商家已经开始备货了，如要取消订单需要先联系商家。");
		}
		
		this.dealReturn(user, subOrder, subOrder.getRealPrice(), subOrder.getTotalAdjustment(),flowResult);

		//修改订单状态信息
		subOrder.setStatus(-1);
		subOrder.setExchangeStatus(exchangeStatus);
		subOrder.setCloseReason(closeReason);
		subOrder.setCancelTime(new Date());
		suborderService.update(subOrder);
		
		return ActResult.success("订单取消成功");
	}
	
	/**
	 * 支付成功的处理
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ActResult paySuccess(Long userId, String subOrderId,String way,String trade_no,BigDecimal totalFee) throws Exception {
		Date now = new Date();
		ExchangeSuborder subOrder = this.suborderService.getById(subOrderId);
		if (subOrder.getStatus() != 0 || !subOrder.getUserId().equals(userId)) {
			return ActResult.success(null);
		}
		ExchangeSuborderitem queryi = new ExchangeSuborderitem();
		queryi.setSubOrderId(subOrderId);
		List<ExchangeSuborderitem> items = suborderitemService.selectByModel(queryi);
		UserFactory user = userService.getById(userId);
		for (ExchangeSuborderitem item : items) {
			Product product = productService.findById(item.getProductId(),false);
			//判断当前商品库存(活动商品和正常商品,只有付款减库存的时候存在这样的)
			if(product.getStockLockType()==2){
				int balanceNum = 0;
				int buyNum = 0;
				if(!StringUtils.isEmpty(item.getPromotionProductId())){
					String key = String.valueOf(item.getPromotionId() + "_" + item.getPromotionProductId());
					try {
						Map<String, String> promotionMap = redisUtil.getMap(key);
						Integer locked = Integer.valueOf(promotionMap.get("locked"));
						Integer stock = Integer.valueOf(promotionMap.get("stock"));
						balanceNum = stock;
						buyNum = locked + item.getNumber();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					Integer stock = inventoryService.getInventoryFromRedis(Long.valueOf(item.getPartNumber()));
					balanceNum = stock == null ? 0 : stock;
					buyNum = item.getNumber();
				}
				if(buyNum>balanceNum){
					//库存不足，取消订单
					cancel(user, subOrderId, "库存不足，系统自动取消订单",-1,true);
					return ActResult.fail("商品：“"+product.getName()+"”库存不足， 子单：“"+subOrderId+"”已自动取消，付款已返还至账户余额");
				}
			}
			//更新销量
			product.setSellNum(product.getSellNum()==null?0:product.getSellNum() + item.getNumber());
			if (null != product.getStockLockType() && product.getStockLockType() == 2) {//减库存
				int stockNum = product.getAllnum() - item.getNumber();
				if (stockNum < 0) {
					stockNum = 0;
				}
				product.setAllnum(stockNum);
				inventoryService.cut(Long.valueOf(item.getPartNumber()), item.getNumber());
				//更新缓存信息
				redisUtil.setMapData(RedisConstant.PRODUCT_PRE+product.getId(), RedisConstant.PRODUCT_REDIS_INFO, JsonUtil.toJsonString(product));
			}
			productDao.update(product);
			try {
				wsm.updateSaleNum(product.getId(), item.getNumber());
			} catch (Exception e) {
				logger.info("索引库无此商品信息");
			}
		}
		subOrder.setPayTime(now);
		subOrder.setStatus(1);
		subOrder.setExchangeStatus(1);
		if(!StringUtils.isEmpty(trade_no)) {
			subOrder.setThirdNo(trade_no);
		}
		if(!StringUtils.isEmpty(totalFee)) {
			subOrder.setThirdPay(totalFee);
		}
		if(!StringUtils.isEmpty(way)) {
			subOrder.setThirdType(way);
		}
		suborderService.update(subOrder);
		//更新母单状态
		ExchangeSuborder query = new ExchangeSuborder();
		query.setOrderId(subOrder.getOrderId());
		List<ExchangeSuborder> list = this.suborderService.selectByModel(query);
		Boolean check = true;
		for(ExchangeSuborder sub : list){
			if(sub.getStatus()==0){
				check = false;
				break;
			}
		}
		ExchangeOrders order = ordersService.getById(subOrder.getOrderId());
		if(check)
			order.setStatus(2);//全部支付
		else
			order.setStatus(1);//部分支付
		order.setUpdateTime(now);
		order.setUpdateBy("System");
		ordersService.update(order);
		return ActResult.success(null);
	}
	
	//直接统一取消主订单
	@Override
	public ActResult<Object> cancelOrder(UserFactory user,Long orderId , String closeReason,boolean flowResult) {
		ExchangeSuborder query = new ExchangeSuborder();
		query.setOrderId(orderId);
		List<ExchangeSuborder> list = this.suborderService.selectByModel(query);
		ActResult ac =  new ActResult();
		ExchangeOrders order = ordersService.getById(orderId);
		if(order.getStatus()>0){
			return ActResult.fail("订单状态不正确");
		}
		for(ExchangeSuborder subOrder :list){
			ac = cancel(user, subOrder.getSubOrderId(), closeReason,-1,flowResult);
			if(!ac.isSuccess()){
				break;
			}
		}
		order.setStatus(-1);
		ordersService.update(order);
		return ac;
	}

	/**
	 * 支付成功且异步回调成功后更新记录以及订单状态(status=2:回调成功)
	 * @param orderId
	 * @param subOrderId
	 */
	@Override
	public void updateOrderToPay(Payment payment) {
		Date date = new Date();		

		//订单支付

		//更改母单及子弹状态
		if(!StringUtils.isEmpty(payment.getOrderId())){
			ExchangeOrders order = ordersService.getById(payment.getOrderId());
			if(order!=null) {
				ExchangeSuborder query = new ExchangeSuborder();
				query.setOrderId(payment.getOrderId());
				List<ExchangeSuborder> list = this.suborderService.selectByModel(query);
				for(ExchangeSuborder subOrder : list){
					try {
						this.paySuccess(order.getUserId(), subOrder.getSubOrderId(),payment.getWay(),payment.getTradeNo(),payment.getTotalFee());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} else if(!StringUtils.isEmpty(payment.getSubOrderId())){
			//更新子单
			ExchangeSuborder subOrder = this.suborderService.getById(payment.getSubOrderId());
			if(StringUtils.isEmpty(subOrder)){
				return;
			}
			ExchangeOrders order = ordersService.getById(subOrder.getOrderId());
			if(StringUtils.isEmpty(order)){
				return;
			}
			try {
				this.paySuccess(order.getUserId(), subOrder.getSubOrderId(),payment.getWay(),payment.getTradeNo(),payment.getTotalFee());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
				
		payment.setStatus(2);
		payment.setUpdateTime(date);
		paymentService.update(payment);
	}
	

	/**
	 * 合并订单并且支付
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Override
	@Transactional
	public boolean submitOrder(String subOrderId,String updateBy) throws IllegalAccessException, InvocationTargetException {
		//拼接原订单 原订单只会有一个合单，一个子单
		Orders orders = null;
		Suborder suborder = null;
		//订单商品行会很多，需要将相同的skuid订单合并
		List<Suborderitem> suborderitems = new ArrayList<Suborderitem>();
		//通过团购id查询所有一个团中的订单
		ExchangeSuborder eso = suborderService.getById(subOrderId);
		ExchangeOrders eos= ordersService.getById(eso.getOrderId());
		
		if(eso!=null &&  null !=eos ) {
			
			orders = new Orders();
			BeanUtils.copyProperties(orders, eos);
			orders.setStatus(1);//订单状态变成已支付
			orders.setSelfDelivery(orders.getSelfDelivery()==null?"0":orders.getSelfDelivery());

			//子订单
			suborder = new Suborder();
			//复制团长的订单信息
			BeanUtils.copyProperties(suborder, eso);
			suborder.setOrderType("5");			//标记团订单

			//查询所有订单商品行
			ExchangeSuborderitem query = new ExchangeSuborderitem();
			query.setSubOrderId(eso.getSubOrderId());
			List<ExchangeSuborderitem> suborderitemList = suborderitemService.selectByModel(query);

			for (ExchangeSuborderitem item : suborderitemList) {
				Suborderitem suborderitem = new Suborderitem();
				BeanUtils.copyProperties(suborderitem, item);
				suborderitem.setSubOrderId(suborder.getSubOrderId());
				suborderitem.setOrderId(orders.getOrderId());
				ordersFacade.listAdd(suborderitems, suborderitem);
				
				//库存相关操作
				inventoryService.cut(suborderitem.getSkuId(), suborderitem.getNumber());
			}
			//将数据插入数据库
			ordersFacade.dealOrders(orders, suborder, suborderitems);
			
			//将团订单改为2 已开团
			eso.setExchangeStatus(2);
			eso.setRelationId(suborder.getSubOrderId());
			eso.setUpdateBy(updateBy);
			eso.setUpdateTime(new Date());
			suborderService.update(eso);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean mergeOrder(Long userId, Long orderId, String subOrderIds, String sku_nums, String updateBy,List<String> subIds)
			throws IllegalAccessException, InvocationTargetException {
		ActResult<Cart> act = this.creatCart(sku_nums);		// 根据skuId 及 数量 创建临时购物车，不含运费、内购券及换领币
		if(!act.isSuccess()) return false;
		//拼接原订单 原订单只会有一个合单，一个子单
		Orders orders = null;
		//通过团购id查询所有一个团中的订单
		//ExchangeSuborder eso = suborderService.getById(subOrderId);
		ExchangeOrders eos= ordersService.getById(orderId);

		Date now = new Date();
		//BigDecimal exTotalProduct = BigDecimal.ZERO;
		BigDecimal exTotalShipping = BigDecimal.ZERO;
		BigDecimal exTotalAdjustment = BigDecimal.ZERO;
		BigDecimal exRealPrice = BigDecimal.ZERO;
		
		List<ExchangeSuborder> esos = new ArrayList<ExchangeSuborder>();
		String[] ary = subOrderIds.split(",");
		for (String string : ary) {
			ExchangeSuborder eso = suborderService.getById(string);
			//exTotalProduct=exTotalProduct.add(eso.getTotalProduct());
			exTotalShipping=exTotalShipping.add(eso.getTotalShipping());
			exTotalAdjustment=exTotalAdjustment.add(eso.getTotalAdjustment());
			exRealPrice=exRealPrice.add(eso.getRealPrice());
			esos.add(eso);
		}
		Cart cart = act.getData();
		if(null !=eos ) {
			UserFactory user = userService.getById(userId);

			BigDecimal totalProduc = BigDecimal.ZERO;// 商品总价，不含运费
			BigDecimal totalShipping = BigDecimal.ZERO; //cart.calculateTotalFreight();// 总运费
			BigDecimal totalAdjustment = BigDecimal.ZERO;// 总折扣金额
			BigDecimal realPrice = BigDecimal.ZERO;// 实际支付金额 = 商品总价 + 总运费 - 总折扣金额
			BigDecimal useCash = BigDecimal.ZERO;
			int status = 0;// 订单状态 具体规则未定(0：待付款，1：支付成功)
			
			orders = new Orders();
			BeanUtils.copyProperties(orders, eos);
			orders.setStatus(1);//订单状态变成已支付

			Map<Long, List<CartItem>> map = cart.groupBySupplier();
			CartItem cartItem = null;
			int num = 0;
			Suborder subOrder = null;
			Iterator<Map.Entry<Long, List<CartItem>>> it = map.entrySet().iterator();
			
			List<Suborder> suborderList = new ArrayList<Suborder>();
			while (it.hasNext()) {
				Map.Entry<Long, List<CartItem>> entry = it.next();
				// 保存子订单
				BigDecimal subTotalCash = BigDecimal.ZERO;// 总折扣金额
				BigDecimal subTotalAdjustment = BigDecimal.ZERO;// 总折扣金额
				List<CartItem> cartItems = entry.getValue();// 当前供应商的订单

				num++;
				subOrder = new Suborder();
				subOrder.setSubOrderId(orderId + "-" + num);
				subOrder.setOrderId(orderId);
				subOrder.setSupplierId(entry.getKey());
				BigDecimal subTotalPrice = BigDecimal.ZERO;// 商品总价，不含折扣
				BigDecimal subTotalProduc = BigDecimal.ZERO;// 商品总价，不含运费
				BigDecimal subTotalShipping = BigDecimal.ZERO;
				// 原则上不应该有自提订单
				if(!"1".equals(orders.getSelfDelivery())) {
					subTotalShipping=this.calculateShipping(eos.getAid(),user, cartItems);// 总运费
				}
				BigDecimal subRealPrice = BigDecimal.ZERO;// 实际支付金额 = 商品总价 + 总运费 - 总折扣金额

				// 运费处理原则上 运费不应超过 总运费（保证 平台不多掏运费）,结算必要时调整
				if(!NumberUtil.isGreaterZero(exTotalShipping)) {
					if(subTotalShipping.compareTo(exTotalShipping)>0) {
						subTotalShipping=exTotalShipping;
						exTotalShipping=BigDecimal.ZERO;
					} else {
						exTotalShipping=exTotalShipping.subtract(subTotalShipping);
					}
				} else {
					subTotalShipping=BigDecimal.ZERO;
				}
				
				for (int j = 0; j < cartItems.size(); j++) {
					cartItem = cartItems.get(j);
					if (cartItem.isBuyFlag()) {
						subTotalProduc = subTotalProduc.add(cartItem.getRealPrice().multiply(new BigDecimal(cartItem.getQuantity())));
					}
				}
				//生成subOrder(不包含折扣)
				totalProduc = totalProduc.add(subTotalProduc);
				subTotalPrice = subTotalProduc.add(subTotalShipping);
				subOrder.setTotalProduct(subTotalPrice);
				subOrder.setTotalShipping(subTotalShipping);
				//订单运费加算
				totalShipping = totalShipping.add(subTotalShipping);
				subOrder.setRealPrice(subRealPrice);
				subOrder.setStatus(1);// 状态
				
				subOrder.setCreateTime(now);// 创建日期
				// 延长15天
				subOrder.setLasttakeTime(TimeUtil.addDay(now, 15));// 最后确认收货时间
				subOrder.setInvoiceStatus(0);//默认不申请发票
				//ExchangeSuborder so = suborderService.save(subOrder);
				suborderList.add(subOrder);
				// 保存子订单项
				String subOrderId = subOrder.getSubOrderId();
				//订单商品行会很多，需要将相同的skuid订单合并
				List<Suborderitem> suborderitems = new ArrayList<Suborderitem>();
				Suborderitem subOrderItem = null;
				BigDecimal benefitUsage = BigDecimal.ZERO;// 优惠券
				for (int j = 0; j < cartItems.size(); j++) {
					cartItem = cartItems.get(j);
					if (cartItem.isBuyFlag()) {
						subOrderItem = new Suborderitem();
						Product product = productService.findById(cartItem.getProductId(),false);
						if(StringUtils.isEmpty(subOrder.getShopId())) {
							subOrder.setShopId(product.getShopId());
						}
						SupplierCategory sc = supplierCategoryDao.getBySupplierAndCategory(product.getSupplierId() , product.getCategoryId(), product.getShopId());
						if (sc != null) {
							subOrderItem.setCommissionRatio(sc.getCommissionRatio() == null ? 0f : sc.getCommissionRatio());
						}
						subOrderItem.setSubOrderId(subOrderId);
						subOrderItem.setPartNumber(cartItem.getPartNumber());// 商品SKU
						subOrderItem.setPrice(cartItem.getPrice());// 价格
						subOrderItem.setNumber(cartItem.getQuantity());// 数量
						subOrderItem.setCreateTime(new Date());// 创建日期
						subOrderItem.setSkuId(Long.parseLong(cartItem.getPartNumber()));
						subOrderItem.setProductId(cartItem.getProductId());
						subOrderItem.setPromotionId(cartItem.getPromotionId());
						subOrderItem.setPromotionProductId(cartItem.getPromotionProductId());
						subOrderItem.setOrderId(orderId);
						if(j==0) {
							subOrderItem.setShipping(subTotalShipping);
						} else {
							subOrderItem.setShipping(cartItem.getFreight());
						}
						subOrderItem.setSubOrderId(subOrderId);
						subOrderItem.setCommentFlag(0);
						subOrderItem.setSaleKbn(cartItem.getSaleKbn());
						//商品名称
						subOrderItem.setProductName(product.getFullName());
						//员工特价
						subOrderItem.setEmpPrice(product.getEmpPrice());
						//品类id
						subOrderItem.setCategoryId(product.getCategoryId());
						//品类名称
						subOrderItem.setCategoryName(productCategoryService.findById(product.getCategoryId()).getName());
						//商品条形码
						subOrderItem.setProductCode(cartItem.getProductCode());
						//商品规格
						subOrderItem.setItemValues(cartItem.getItemValues());
						//图片路径
						subOrderItem.setImage(cartItem.getImagePath());
						//增加内购价
						subOrderItem.setInternalPurchasePrice(cartItem.getRealPrice());
						subOrderItem.setCompanyTicket(BigDecimal.ZERO);
									
						//这里换成内购价
						BigDecimal realPay = cartItem.getRealPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));//应付款
						
						//计算换领币使用量 
						if(!NumberUtil.isGreaterZero(exTotalAdjustment)) {
							subOrderItem.setBenefitType(null);
							subOrderItem.setBenefitAmount(BigDecimal.ZERO);
							subOrderItem.setBenefitTicket(BigDecimal.ZERO);
							subOrderItem.setBenefitSelf(BigDecimal.ZERO);
						} else {
							subOrderItem.setBenefitType(3);
							if(realPay.compareTo(exTotalAdjustment)>0) {
								subOrderItem.setBenefitTicket(exTotalAdjustment);
								subOrderItem.setBenefitAmount(exTotalAdjustment);
								exTotalAdjustment=BigDecimal.ZERO;
							} else {
								subOrderItem.setBenefitTicket(realPay);
								subOrderItem.setBenefitAmount(realPay);
								exTotalAdjustment=exTotalAdjustment.subtract(realPay);
							}
							subOrderItem.setBenefitSelf(BigDecimal.ZERO);
						}
						//应付款=货款-内购券-优惠券
						//应付款=内购价-优惠券
						//优惠券累计
						benefitUsage=benefitUsage.add(subOrderItem.getBenefitAmount());
						realPay = realPay.subtract(subOrderItem.getBenefitAmount());
						subOrderItem.setRealPay(realPay);//不记运费
						suborderitems.add(subOrderItem);

						//库存相关操作
						inventoryService.cut(subOrderItem.getSkuId(), subOrderItem.getNumber());
					}
				}
				// 用户内购券使用量计算折价总数
				subOrder.setCompanyTicket(BigDecimal.ZERO);
				subTotalAdjustment=subOrder.getCompanyTicket();
				//优惠券累计
				if (benefitUsage.compareTo(BigDecimal.ZERO)>0) {// > 0
					subOrder.setBenefitType(3);
					subOrder.setBenefitAmount(benefitUsage);
				} else {
					subOrder.setBenefitType(null);
					subOrder.setBenefitAmount(BigDecimal.ZERO);
				}
				subTotalAdjustment =subTotalAdjustment.add(benefitUsage); //BigDecimal.valueOf(fljUsage * freeTicket);
				
				//实付金额及优惠
				subRealPrice = subTotalPrice.subtract(subTotalAdjustment);
				subOrder.setRealPrice(subRealPrice);
				subOrder.setTotalAdjustment(subTotalAdjustment);				
				//现金券抵扣
				if(NumberUtil.isGreaterZero(exRealPrice)) {
					if(subOrder.getRealPrice().compareTo(exRealPrice)>0) {
						subOrder.setCashPay(exRealPrice);
						exRealPrice=BigDecimal.ZERO;
					} else {
						subOrder.setCashPay(subOrder.getRealPrice());
						exRealPrice=exRealPrice.subtract(subOrder.getRealPrice());
					}
				} else {
					subOrder.setCashPay(BigDecimal.ZERO);
				}
				
				useCash=useCash.add(subOrder.getCashPay());
				if(subOrder.getRealPrice().compareTo(subOrder.getCashPay())==0) {
					subOrder.setStatus(1);// 状态
					subOrder.setPayTime(now);
					subIds.add(subOrder.getSubOrderId());
				}

				subOrder.setOrderType("5");			//标记团订单
				subOrder.setRelationId(orders.getOrderId());
				ordersFacade.dealOrders(null, subOrder, suborderitems);
				// 用户计算优惠总价格
				totalAdjustment=totalAdjustment.add(subTotalAdjustment);
			}

			realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
			orders.setTotalProduct(totalProduc);
			orders.setTotalShipping(totalShipping);
			orders.setTotalAdjustment(totalAdjustment);
			orders.setRealPrice(realPrice);
			orders.setItems(suborderList);
			//根据支付方式、还需支付金额判断订单状态
			if (realPrice.compareTo(BigDecimal.ZERO) == 0 || realPrice.compareTo(useCash)==0) {
				status=1;
			}
			orders.setStatus(status);
			orders.setSelfDelivery(orders.getSelfDelivery()==null?"0":orders.getSelfDelivery());
			ordersFacade.dealOrders(orders,null,null);
			
			for (ExchangeSuborder eso : esos) {

				//将团订单改为2 已开团
				eso.setExchangeStatus(4);
				//eso.setRelationId(suborder.getSubOrderId());
				eso.setUpdateBy(updateBy);
				eso.setBatchId(orders.getOrderId());
				eso.setUpdateTime(now);
				suborderService.update(eso);
			}
			
			//计算退款
			//exRealPrice = exRealPrice.add(exTotalShipping);
			int i=esos.size()-1;
			while(i>-1 && (NumberUtil.isGreaterZero(exRealPrice) || NumberUtil.isGreaterZero(exTotalAdjustment))) {
				ExchangeSuborder suborder = esos.get(i);

				BigDecimal refundCache = BigDecimal.ZERO;
				BigDecimal refundTicket = BigDecimal.ZERO;
				// 计算单笔退款
				if(NumberUtil.isGreaterZero(suborder.getRealPrice())) {
					if(suborder.getRealPrice().compareTo(exRealPrice) >0) {
						refundCache=exRealPrice;
						exRealPrice=BigDecimal.ZERO;
					} else {
						refundCache=suborder.getRealPrice();
						exRealPrice=exRealPrice.subtract(suborder.getRealPrice());
					}
				}
				
				// 计算单笔退款
				if(NumberUtil.isGreaterZero(suborder.getBenefitAmount())) {
					if(suborder.getBenefitAmount().compareTo(exTotalAdjustment) >0) {
						refundTicket=exTotalAdjustment;
						exTotalAdjustment=BigDecimal.ZERO;
					} else {
						refundTicket=suborder.getBenefitAmount();
						exTotalAdjustment=exTotalAdjustment.subtract(suborder.getBenefitAmount());
					}
				}
				
				this.dealReturn(user, suborder, refundCache, refundTicket,true);
				i--;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 处理退款
	 * @param suborder
	 * @param refundCache
	 * @param refundTicket
	 */
	private void dealReturn(UserFactory user,ExchangeSuborder subOrder,BigDecimal refundCache,BigDecimal refundTicket,boolean flowResult) {
		
		if(refundCache==null) refundCache=BigDecimal.ZERO;
		if(refundTicket==null) refundTicket=BigDecimal.ZERO;
		
		//释放库存
		ExchangeSuborderitem queryi = new ExchangeSuborderitem();
		queryi.setSubOrderId(subOrder.getSubOrderId());
		List<ExchangeSuborderitem> items = suborderitemService.selectByModel(queryi);
		Map<Long,UseExchangeTicketVo> mapExchange = new HashMap<Long,UseExchangeTicketVo>();
		
		// 先退换领券
		if(NumberUtil.isGreaterZero(refundTicket)) {
			for (ExchangeSuborderitem item : items) {
	
				if(item.getBenefitType()!=null && item.getBenefitType()==3) {
					UseExchangeTicketVo uext = null;
					if(mapExchange.containsKey(item.getProductId())) {
						uext = mapExchange.get(item.getProductId());
					} else {
						uext= new UseExchangeTicketVo();
						uext.setProductId(item.getProductId());
						uext.setCash(BigDecimal.ZERO);
						uext.setTicket(BigDecimal.ZERO);
						uext.setSelf(BigDecimal.ZERO);
						uext.setNote("订单取消,商品："+item.getProductName()+",订单ID:"+item.getSubOrderId());
						uext.setSubOrderId(item.getSubOrderId());
						mapExchange.put(uext.getProductId(), uext);
					}
					
					BigDecimal rtnCash = item.getBenefitAmount()==null?BigDecimal.ZERO:item.getBenefitAmount();
					if(rtnCash.compareTo(refundTicket)>0) {
						rtnCash=refundTicket;
						refundTicket=BigDecimal.ZERO;
					} else {
						refundTicket = refundTicket.subtract(rtnCash);
					}
					
					uext.setCash(uext.getCash().add(rtnCash));
					uext.setTicket(uext.getTicket().add(rtnCash));
					if(NumberUtil.isGreaterZero(item.getBenefitSelf())) {
						if(item.getBenefitSelf().compareTo(rtnCash)>0) {
							uext.setSelf(uext.getSelf().add(rtnCash));
						} else {
							uext.setSelf(uext.getSelf().add(item.getBenefitSelf()));
						}
					}
				}
			}
		}

		// 换领币消费处理
		if(mapExchange.size()>0) {
			int deal = userTicketFacade.cancelOrderTicket(user, mapExchange, user.getUserName(), subOrder.getOrderId(),subOrder.getCreateTime());
			if(deal==-1) {
				throw new BenefitLessException(user.getUserName(),user.getUserName()+"");
			} else if(deal==-2) {
				throw new SupplierShardeLessException("换领币");
			}
		}

		// 再退现金
		if(NumberUtil.isGreaterZero(refundCache)) {
			Map<String, Object> comMap = new HashMap<String, Object>();
			comMap.put("empId", user.getId());
			comMap.put("key", subOrder.getSubOrderId());//订单id
			comMap.put("desrc","取消订单，订单编号："+subOrder.getSubOrderId());//备注+订单id 
			comMap.put("updUser", user.getUserName());//更新着
			comMap.put("absTicket", subOrder.getCompanyTicket());//内购券
			
			/**
			 * 取消订单
			 * 		如果订单未付款只退还内购券金额。
			 * 		反之订单已付款将会退还内购券金额+现金金额
			 * */
			//1为已支付       已支付后，进行取消订单。  退还金额
			Payment payment = null;
			BigDecimal refundFee = BigDecimal.ZERO;
			BigDecimal cashThirdPay = BigDecimal.ZERO;
			if(subOrder.getStatus()>0) {
				// 已支付的场合
				if(NumberUtil.isGreaterZero(subOrder.getThirdPay())) {
					// 本期先返还微信
					if("wxpay".equals(subOrder.getThirdType()) || "zhifubao".equals(subOrder.getThirdType())) {
						// 支付宝、微信原路返还
						payment = paymentService.getByTradeNo(subOrder.getThirdType(), subOrder.getThirdNo());
						
						if(payment==null || StringUtils.isEmpty(payment.getAppId())) {
							// 全部退还到现金券
							cashThirdPay = refundCache;
							comMap.put("absCash", refundCache);
						} else {
							BigDecimal cashPay =null;	// subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
							//refundFee = refundCache.subtract(cashPay);
							if(refundCache.compareTo(subOrder.getThirdPay())>0) {
								refundFee = subOrder.getThirdPay();
								cashPay = refundCache.subtract(refundFee);
							} else {
								cashPay=BigDecimal.ZERO;
								refundFee = refundCache;
							}
							cashThirdPay = cashPay;//记录现金券抵扣
							//现金券抵扣部分退还
							comMap.put("absCash", cashPay);
						}
					} else {
						cashThirdPay = refundCache;
						// 全部退还到现金券
						comMap.put("absCash", refundCache);
					}
				} else {
					//记录现金券抵扣
					cashThirdPay = refundCache;
					// 全部退还到现金券
					comMap.put("absCash", refundCache);
				}
			} else {
				cashThirdPay = subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
				comMap.put("absCash", cashThirdPay);
			}
			
			if(flowResult) {
				String ticketResult = HttpClientUtil.sendHttpRequest("post", qiyeApiUrl
						+ "api/cancelOrder", comMap);		
				
				//返还用户使用的各种优惠抵消
				//流水记录
				ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);
				if(!acTicket.isSuccess()){
					throw new RuntimeException(acTicket.getMsg());
				}else{//退款成功生成现金券退款数据
					if(NumberUtil.isGreaterZero(cashThirdPay)){
						Payment rp = new Payment();
						rp.setOutTradeNo(dbUtils.CreateID()+"");
						rp.setOrderId(subOrder.getOrderId());
						rp.setSubOrderId(subOrder.getSubOrderId());
						rp.setStatus(2);
						rp.setCreateTime(new Date());
						rp.setWay("pingtaiyue");
						rp.setTradeNo(acTicket.getData().toString());
						rp.setPayType(-1);
						rp.setOrderType(5);
						rp.setTotalFee(cashThirdPay);
						paymentService.save(rp);
					}
				}
			}
			
			// 支付宝、微信原路返还
			if(payment!= null && NumberUtil.isGreaterZero(refundFee)) {
				try {
					
					Payment rp = new Payment();
					rp.setOutTradeNo(dbUtils.CreateID()+"");
					rp.setOrderId(null);
					rp.setSubOrderId(subOrder.getSubOrderId());
					rp.setStatus(0);
					rp.setCreateTime(new Date());
					rp.setWay(payment.getWay());
					rp.setAppId(payment.getAppId());
					rp.setPayType(-1);
					rp.setOrderType(5);
					rp.setTotalFee(refundFee);
					rp.setNote(payment.getOutTradeNo());
					if("wxpay".equals(payment.getWay())) {
						if(wxPay.payRefund(payment.getOutTradeNo(), payment.getTotalFee(), payment.getAppId(), refundFee, rp.getOutTradeNo())) {
							/*rp.setTradeNo(payment.getTradeNo());
							rp.setStatus(2);*/
						}
					} else if ( "zhifubao".equals(payment.getWay())) {  // && AlipayService.APP_ID_NEW.equals(payment.getAppId())
						alipay.refund(payment.getOutTradeNo(), payment.getTradeNo(), rp.getOutTradeNo(), refundFee,notifyUrl);
					}
					paymentService.save(rp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 生成临时购物车以便下单处理,不用做一般性检查
	 *
	 * @param sku_nums skuId_数量  多个，以逗号分隔
	 * @return
	 */
	private ActResult<Cart> creatCart(String sku_nums) {

		Cart cart = new Cart();

		String[] arySkuNum = sku_nums.split(",");
		for (String string : arySkuNum) {
			String[] ary = string.split("_");
			Long skuId= NumberUtil.toLong(ary[0]);
			int quantity= NumberUtil.toInt(ary[1]);
			
			Product product = productService.findBySku(skuId);
			ProductSpecifications sku =specificationsService.findByIdCache(skuId, product.getId()+"");
			//是否修改了内购券使用数量
			/*if(companyTicket!=null&&ps.getMaxFucoin().multiply(NumberUtil.toBigDecimal(quantity)).compareTo(NumberUtil.toBigDecimal(companyTicket))!=0){
				return ActResult.fail("商家已修改商品内购券的使用数量,请重新下单");
			}*/
			Integer stock = inventoryService.getInventoryFromRedis(sku.getId());
			stock = stock == null ? 0 : stock;
			if (quantity > stock)
				return ActResult.fail("商品库存不足");

			
			String supplierJsonStr = redisUtil.getMapData(RedisConstant.PRODUCT_PRE + product.getId(), RedisConstant.PRODUCT_REDIS_SUPPLIER);
			if (StringUtils.isEmpty(supplierJsonStr)) {
				logger.error("商品" + product.getId() + "供应商不存在");
				return ActResult.fail("商品供应商不存在");
			}
			Supplier supplier = JSONObject.parseObject(supplierJsonStr, Supplier.class);
//			Set<Long> list = new HashSet<Long>();
//			list.add(skuId);
//			cart.setSelectProduct(list);
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setPartNumber(skuId + "");
			cartItem.setProductCode(sku.getProductCode());
			cartItem.setItemValues(sku.getItemValues());
			cartItem.setPrice(sku.getPrice());
			cartItem.setSupplierId(supplier.getId());
			cartItem.setSupplierName(supplier.getComName());
			cartItem.setShopId(product.getShopId());
			cartItem.setProductId(product.getId());
			cartItem.setProductName(product.getFullName());
			cartItem.setSaleKbn(product.getSaleKbn());
			cartItem.setImagePath(productSpecificationsImageService.findProductPicture(sku.getId(), product.getId()).get(0).getSource());
			cartItem.setCompanyTicket(BigDecimal.ZERO); 	// 不考虑内购券
			cartItem.setBenefitAmount(BigDecimal.ZERO);		// 设置成0
			cartItem.setBenefitTicket(BigDecimal.ZERO);
			cartItem.setBenefitSelf(BigDecimal.ZERO);
			cartItem.setMaxCompanyTicket(BigDecimal.ZERO);	// 不考虑内购券
			cartItem.setRealAmount(sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(quantity)));
			cartItem.setRealPrice(sku.getInternalPurchasePrice());
			if (null != sku.getItemValues() && !"".equals(sku.getItemValues())) {
				String specificationJson = sku.getItemValues();
				cartItem.setSpecificationList(getSpecificationList(specificationJson));
			}
			cartItem.setIsLadder(false);
			cartItem.setFreight(BigDecimal.ZERO);
			cartItem.setBuyFlag(true);
			cart.addItem(cartItem);
		}
		
		return ActResult.success(cart);
	}
	


	private BigDecimal calculateShipping(String acode,UserFactory user,List<CartItem> cartItems) {
		BigDecimal nowShipping = BigDecimal.ZERO;
		Long sid = 0L;
		Map<Long,BigDecimal> freightMap = new HashMap<Long,BigDecimal>();			
		Map<Long,Product> productMap = new HashMap<Long,Product>();
		Map<Long,Integer> numMap = new HashMap<Long,Integer>();
		Map<Long,BigDecimal> amountMap = new HashMap<Long,BigDecimal>();
		List<Long> pids = new ArrayList<Long>();

		for (CartItem item:cartItems) {
			ProductSpecificationsVo sku = this.specificationsService.findByIdCache(NumberUtil.toLong(item.getPartNumber()), item.getProductId()+"");
			Product p = productService.findById(sku.getProductId(),false);
			BigDecimal skuAmout = sku.getInternalPurchasePrice().multiply(NumberUtil.toBigDecimal(item.getQuantity()));
			if(freightMap.containsKey(p.getId())) {
				numMap.put(p.getId(),numMap.get(p.getId()) + item.getQuantity());
				amountMap.put(p.getId(),amountMap.get(p.getId()).add(skuAmout));
			} else {
				sid = p.getSupplierId();
				// 默认先放0
				freightMap.put(p.getId(), BigDecimal.ZERO);
				pids.add(p.getId());
				productMap.put(p.getId(),p);
				numMap.put(p.getId(), item.getQuantity());
				amountMap.put(p.getId(), skuAmout);
			}
		}
		
		shippingFacade.calculateSupplierShippingFee("0", acode, user,sid, pids, productMap, numMap, amountMap, freightMap,null);
		
		for (BigDecimal pshippin : freightMap.values()) {
			nowShipping=nowShipping.add(pshippin);
		}
		
		return nowShipping;
	}

	/**
	 * 将规格JSON数据转换成 list
	 *
	 * @param specificationJson
	 * @return
	 */
	private List<String> getSpecificationList(String specificationJson) {
		List<String> list = new ArrayList<String>();
		specificationJson = specificationJson.replace("{", "").replace("}", "").replace("\"", "").replace("\\", "");
		String[] strs = specificationJson.split(",");
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i].substring(0, strs[i].indexOf(":")) + "_" + strs[i].substring(strs[i].indexOf(":") + 1));
		}
		return list;
	}
}
