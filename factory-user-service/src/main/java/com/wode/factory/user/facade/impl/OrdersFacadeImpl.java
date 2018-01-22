package com.wode.factory.user.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.wode.factory.model.Currency;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Product;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.SuborderLimitTicket;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.SuborderitemLimitTicket;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductService;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.PromotionDao;
import com.wode.factory.user.dao.PromotionProductDao;
import com.wode.factory.user.dao.SupplierCategoryDao;
import com.wode.factory.user.dao.UserBalanceDao;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.GroupOrdersFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.facade.UserExchangeTicketFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.model.UseExchangeTicketVo;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.query.UserBalanceQuery;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.InvoiceService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.SuborderLimitTicketService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemLimitTicketService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserBalanceService;
import com.wode.factory.user.service.UserLimitTicketService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.service.UserTicketHisService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.OrderVO;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.user.vo.SubOrderVo;
import com.wode.search.WodeSearchManager;

@SuppressWarnings("unchecked")
@Service("ordersFacade")
public class OrdersFacadeImpl implements  OrdersFacade {
	private static Logger logger = LoggerFactory.getLogger(OrdersFacadeImpl.class);
	@Qualifier("ordersService")
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private SuborderService suborderService;
	
	@Autowired
	private GroupOrdersFacade groupOrdersFacade;
	@Autowired
	private ExchangeOrdersFacade exchangeOrdersFacade;

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
	private CurrencyService currencyService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("supplierCategoryDao")
	private SupplierCategoryDao supplierCategoryDao;
	@Autowired
	private SuborderitemService suborderitemService;
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
	@Autowired
	private UserLimitTicketService userLimitTicketService;
	@Autowired
	private SuborderitemLimitTicketService suborderitemLimitTicketService;
	@Autowired
	private SuborderLimitTicketService suborderLimitTicketService;
	@Autowired
	private UserTicketHisService userTicketHisService;
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;

	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	
	@Override
	@Transactional
	public ActResult<Object> createOrder(OrderVO vo, Cart cart, BigDecimal useCash, List<String> subIds, String message)
			throws BenefitLessException, SupplierShardeLessException {
		//校验订单信息
		ActResult<Object> ar = checkOrder(cart,userService.getById(vo.getOrders().getUserId()));
		if (!ar.isSuccess()) {
			return ar;
		}
		Orders order = vo.getOrders();
		Map<String, List<SuborderitemLimitTicket>> mapSuborderitemLimitTicket = vo.getMapSuborderitemLimitTicket();
		
		ActResult<Object> result = ActResult.success(null);
		BigDecimal totalProduc = cart.calculateTotalRealPriceWithMaxCompanyTicket();// 商品总价，不含运费
		BigDecimal totalShipping = BigDecimal.ZERO; //cart.calculateTotalFreight();// 总运费
		BigDecimal totalAdjustment = BigDecimal.ZERO;// 总折扣金额
		BigDecimal realPrice = BigDecimal.ZERO;// 实际支付金额 = 商品总价 + 总运费 - 总折扣金额
		int status = 0;// 订单状态 具体规则未定(0：待付款，1：支付成功)
		// TODO 根据活动规则、优惠券、使用余额等计算总折扣
		realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
		//商品价格
		order.setTotalProduct(totalProduc);
		//商品运费
		order.setTotalShipping(totalShipping);
		//总折扣
		order.setTotalAdjustment(totalAdjustment);
		
		order.setRealPrice(realPrice);
		order.setSelfDelivery(order.getSelfDelivery()==null?"0":order.getSelfDelivery());

		ordersService.save(order);// 保存订单

		Map<Long,UserLimitTicket> userLimitTicketMap = vo.getUserLimitTicketMap();//我的券消费信息
		if(userLimitTicketMap!=null && userLimitTicketMap.size()>0) {
			CartItem cartItem = cart.getAllItems().get(0);
			//检查修改userLimitTicket;
			checkAndUpdateUserLimitTicket(userLimitTicketMap, order.getUserId(), order.getOrderId(),
					cartItem.getProductName(), cartItem.getProductId());
		}

		long orderId = order.getOrderId();// 母单号
		Map<Long, List<CartItem>> map = cart.groupBySupplier();

		Suborder subOrder = null;
		CartItem cartItem = null;
		int num = 0;
		UserBalanceQuery query = new UserBalanceQuery();
		query.setUserId(order.getUserId());
		BigDecimal allUseTicket=BigDecimal.ZERO;	//使用全部内购券
		BigDecimal allUseCash=BigDecimal.ZERO;		// 现金券抵扣总额
		Iterator<Map.Entry<Long, List<CartItem>>> it = map.entrySet().iterator();
		BigDecimal useCashLast = useCash==null?BigDecimal.ZERO:useCash;

		Currency currency = currencyService.findByName("companyTicket");
//		Currency cashCurrency = currencyService.findByName("balance");
		
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
		List<Suborderitem> cutItems = new ArrayList<Suborderitem>();
		Map<String,SuborderLimitTicket> mapSuborderLimitTicket = new HashMap<String,SuborderLimitTicket>();
		List<Suborder> suborderList = new ArrayList<Suborder>();
		int limitTicketNums = 0;
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
			subOrder = new Suborder();
			subOrder.setSubOrderId(orderId + "-" + num);
			subOrder.setOrderId(orderId);
			subOrder.setSupplierId(entry.getKey());
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
					subTotalProduc = subTotalProduc.add(cartItem.getRealPrice().add(cartItem.getMaxCompanyTicket()).multiply(new BigDecimal(cartItem.getQuantity())));
					Product product = productService.findById(cartItem.getProductId(),false);
					productName += product.getFullName()+",";
				}
			}
			//生成subOrder(不包含折扣)
			subTotalPrice = subTotalProduc.add(subTotalShipping);
			subOrder.setTotalProduct(subTotalPrice);
			subOrder.setTotalShipping(subTotalShipping);
			//订单运费加算
			totalShipping = totalShipping.add(subTotalShipping);
			subOrder.setRealPrice(subRealPrice);
			subOrder.setStatus(order.getStatus());// 状态
			Date date = new Date();
			subOrder.setCreateTime(date);// 创建日期
			// 延长15天
			subOrder.setLasttakeTime(TimeUtil.addDay(date, 15));// 最后确认收货时间
			subOrder.setInvoiceStatus(0);//默认不申请发票
			subOrder.setOrderType("0");
			productName = productName.substring(0, productName.length()-1);
			if(productName.length()>64) {//防止超出长度
				productName = productName.substring(0, 64);
			}
			subOrder.setProductName(productName);
			subOrder.setUserId(order.getUserId());
			Suborder so = suborderService.save(subOrder);
			suborderList.add(so);
			// 保存子订单项
			String subOrderId = subOrder.getSubOrderId();
			List<Suborderitem> itemsCut = new ArrayList<Suborderitem>();
			Suborderitem subOrderItem = null;
			BigDecimal fljUsage = BigDecimal.ZERO;// 内购券使用量
			BigDecimal benefitUsage = BigDecimal.ZERO;// 优惠券
			Double freeTicket = currency.getPercentage();
//			UserFactory user = userService.getById(order.getUserId());
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
					subOrderItem.setSkuId(Long.parseLong(cartItem
							.getPartNumber()));
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
					if(mapSuborderitemLimitTicket!=null && mapSuborderitemLimitTicket.size()>0) {
						List<SuborderitemLimitTicket> list = mapSuborderitemLimitTicket.get(cartItem.getPartNumber());
						if(list!=null && list.size()>0) {
							for (SuborderitemLimitTicket suborderitemLimitTicket : list) {
								suborderitemLimitTicket.setOrderId(orderId);
								suborderitemLimitTicket.setSubOrderId(subOrderId);
								suborderitemLimitTicket.setSubOrderItemId(subOrderItem.getSubOrderItemId());
								
								//设置优惠券类型
								subOrderItem.setBenefitType(9);			// 9优惠券
								limitTicketNums++;
								
								dealSuborderLimitTicket(suborderitemLimitTicket,mapSuborderLimitTicket);
							}
						}
					}
					
					// 计算企业券使用量
					if(cartItem.getCompanyTicket()==null) cartItem.setCompanyTicket(BigDecimal.ZERO);
					allUseTicket= allUseTicket.add(cartItem.getCompanyTicket());
					fljUsage=fljUsage.add(cartItem.getCompanyTicket());
					
					subOrderItem.setCompanyTicket(cartItem.getCompanyTicket());
					subOrderItem.setBenefitTicket(cartItem.getBenefitTicket());
					subOrderItem.setBenefitAmount(cartItem.getBenefitAmount());
					subOrderItem.setBenefitSelf(cartItem.getBenefitSelf());
					//优惠券累计
					benefitUsage=benefitUsage.add(cartItem.getBenefitAmount());
					
					//这里换成内购价
					BigDecimal realPay = cartItem.getRealPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));//应付款
					//应付款=货款-内购券-优惠券
					//应付款=内购价-优惠券
					realPay = realPay.subtract(cartItem.getBenefitAmount()==null?BigDecimal.ZERO:cartItem.getBenefitAmount());//BigDecimal.valueOf(cartItem.getCompanyTicket() * freeTicket)
					subOrderItem.setRealPay(realPay);//不记运费
					suborderitemService.update(subOrderItem);
					
					//现在券大于0
					if(useCashLast.compareTo(BigDecimal.ZERO)>0) {
						BigDecimal pay = subOrderItem.getRealPay().add(subOrderItem.getShipping());
						if (useCashLast.compareTo(pay) > 0) {
							allUseCash = allUseCash.add(pay);
							useCashLast = useCashLast.subtract(pay);
							subTotalCash=subTotalCash.add(pay);
						} else {
							allUseCash = allUseCash.add(useCashLast);
							subTotalCash=subTotalCash.add(useCashLast);
							useCashLast = BigDecimal.ZERO;
						}
					}
					//库存相关操作
					ActResult<Object> stockResult = new ActResult<Object>();
					if (cartItem.getPromotionId() != null) {
						Promotion p = promotionDao.getById(cartItem.getPromotionId());
						//有活动时库存计算，下单成功先锁定库存，付款成功后操作减库存
						if (p == null) {
							return ActResult.fail("商品活动不存在");
						} else {
							stockResult = inventoryService.lockPromotionStock(cartItem.getPromotionProductId(), subOrderItem.getNumber(), p);
							//如果为活动商品，则把订单信息加入到redies队列中以便支付超时取消订单
							if (stockResult.isSuccess()) {
								Map<Long, Long> promotionMap = new HashMap<Long, Long>();
								promotionMap.put(order.getOrderId(), order.getCreateTime().getTime());
								redisUtil.rpush(RedisConstant.PROMOTION_ORDERS, JsonUtil.toJsonString(promotionMap));
							}
						}
					} else {
						if (null != product.getStockLockType() && product.getStockLockType() == 1) {
							stockResult = inventoryService.cut(Long.parseLong(cartItem.getPartNumber()), subOrderItem.getNumber());
							// 已减库存记录
							cutItems.add(subOrderItem);
						} else {

							Integer stock = inventoryService.getInventoryFromRedis(Long.parseLong(cartItem.getPartNumber()));
							if(stock<subOrderItem.getNumber()) stockResult.setSuccess(false);
							
							if(product.getStockLockType() == 2) {
								itemsCut.add(subOrderItem);
							}
						
						}
					}
					if (!stockResult.isSuccess()) {
						return ActResult.fail("商品库存不足");
					}
					
				}
			}
			// 用户内购券使用量计算折价总数
			if (fljUsage.compareTo(BigDecimal.ZERO)==1) {// > 0
				so.setCompanyTicket(fljUsage.multiply(new BigDecimal(freeTicket)));
			} else {
				so.setCompanyTicket(BigDecimal.ZERO);
			}
			subTotalAdjustment=so.getCompanyTicket();
			// 处理suborderLimitTicket
			if(mapSuborderLimitTicket.size()>0) {
				SuborderLimitTicket suborderLimitTicket = mapSuborderLimitTicket.get(subOrderId);
				if(suborderLimitTicket!=null) {
					if(suborderLimitTicket.getTotalBenefitTicket()!=null) {
						subTotalAdjustment = subTotalAdjustment.add(suborderLimitTicket.getTotalBenefitTicket());
					}
					suborderLimitTicketService.save(suborderLimitTicket);
				}
			}
			//优惠券累计
			if (benefitUsage.compareTo(BigDecimal.ZERO)>0) {// > 0
				so.setBenefitType(9);
				so.setBenefitAmount(benefitUsage);
			} else {
				so.setBenefitType(null);
				so.setBenefitAmount(BigDecimal.ZERO);
			}
			subTotalAdjustment =subTotalAdjustment.add(benefitUsage); //BigDecimal.valueOf(fljUsage * freeTicket);
			
			//实付金额及优惠
			so.setTotalAdjustment(subTotalAdjustment);
			subRealPrice = subTotalPrice.subtract(subTotalAdjustment);
			so.setRealPrice(subRealPrice);
			
			//现金券抵扣
			if (subTotalCash.compareTo(BigDecimal.ZERO)>0) {// > 0
				so.setCashPay(subTotalCash);
			} else {
				so.setCashPay(BigDecimal.ZERO);
			}
			
			if(so.getRealPrice().compareTo(so.getCashPay())==0) {
				so.setStatus(1);// 状态
				so.setPayTime(date);
				subOrder.setStatus(1);// 状态
				subOrder.setPayTime(date);
				subIds.add(subOrder.getSubOrderId());
				for (Suborderitem suborderitem2 : itemsCut) {
					inventoryService.cut(Long.parseLong(suborderitem2.getPartNumber()), suborderitem2.getNumber());
					
					// 已减库存记录
					cutItems.add(suborderitem2);
				}
			}
			so.setLimitTicketCnt(limitTicketNums);
			suborderService.update(so);
			
			// 用户计算优惠总价格
			totalAdjustment=totalAdjustment.add(subTotalAdjustment);
		}
		
		/*if(mapSuborderLimitTicket.size()>0) {
			for (Map.Entry<String, SuborderLimitTicket> suborderLimitTicketMap : mapSuborderLimitTicket.entrySet()) {
				suborderLimitTicketService.save(suborderLimitTicketMap.getValue());
			}
		}*/

		realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
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
		
		// 记录现金券抵扣
		if(NumberUtil.isGreaterZero(allUseTicket) || NumberUtil.isGreaterZero(allUseCash)){
			Payment rp = new Payment();
			rp.setOutTradeNo(dbUtils.CreateID()+"");
			rp.setOrderId(order.getOrderId());
			rp.setSubOrderId(null);
			rp.setStatus(0);
			rp.setCreateTime(new Date());
			rp.setWay("pingtaiyue");
			rp.setTradeNo(null);
			rp.setPayType(11);		//现金券抵扣
			rp.setOrderType(0);		//普通订单
			rp.setTotalFee(allUseCash);
			rp.setExp1(allUseTicket.toString());
			paymentService.save(rp);
		}
		return result;
	}
	
	private void dealSuborderLimitTicket(SuborderitemLimitTicket suborderitemLimitTicket,
			Map<String, SuborderLimitTicket> mapSuborderLimitTicket) {
		SuborderLimitTicket suborderLimitTicket = null;
		if(mapSuborderLimitTicket.containsKey(suborderitemLimitTicket.getSubOrderId())) {
			suborderLimitTicket = mapSuborderLimitTicket.get(suborderitemLimitTicket.getSubOrderId());
			suborderLimitTicket.setTotalBenefitCash(suborderLimitTicket.getTotalBenefitCash().add(suborderitemLimitTicket.getBenefitCash()));
			suborderLimitTicket.setTotalBenefitTicket(suborderLimitTicket.getTotalBenefitTicket().add(suborderitemLimitTicket.getBenefitTicket()));
		}else {
			suborderLimitTicket = new SuborderLimitTicket();
			suborderLimitTicket.setOrderId(suborderitemLimitTicket.getOrderId());
			suborderLimitTicket.setSubOrderId(suborderitemLimitTicket.getSubOrderId());
			suborderLimitTicket.setOrderType(suborderitemLimitTicket.getOrderType());
			suborderLimitTicket.setUserId(suborderitemLimitTicket.getUserId());
			suborderLimitTicket.setStatus(1);
			suborderLimitTicket.setCreateTime(new Date());
			suborderLimitTicket.setTotalBenefitCash(suborderitemLimitTicket.getBenefitCash());
			suborderLimitTicket.setTotalBenefitTicket(suborderitemLimitTicket.getBenefitTicket());
		}
		if(suborderLimitTicket!=null) {
			mapSuborderLimitTicket.put(suborderitemLimitTicket.getSubOrderId(), suborderLimitTicket);
		}
		suborderitemLimitTicketService.save(suborderitemLimitTicket);
	}

	/**
	 * 检查优惠券余额并完成扣款
	 * @param userLimitTicketList
	 * @param userId
	 * @param orderId
	 * @param productName
	 * @param productId
	 */
	private void checkAndUpdateUserLimitTicket(Map<Long,UserLimitTicket> userLimitTicketMap,Long userId,Long orderId,String productName,Long productId) {
		if(userLimitTicketMap!=null && userLimitTicketMap.size()>0) {
			UserLimitTicket newUserLimitTicket =null;
			for (UserLimitTicket userLimitTicket : userLimitTicketMap.values()) {
				newUserLimitTicket = userLimitTicketService.getById(userLimitTicket.getId());
				if(newUserLimitTicket!=null) {
					if(newUserLimitTicket.getStatus()==0 || newUserLimitTicket.getStatus()==1) {
						if(NumberUtil.isGreaterZero(userLimitTicket.getCashBalance())) {
							// 使用现金抵扣 内购券全免
							if(newUserLimitTicket.getCashBalance().compareTo(userLimitTicket.getCashBalance())<0) {
								throw new BenefitLessException(newUserLimitTicket.getUserNickname(),newUserLimitTicket.getId()+"","limit");
							}
						} else {
							if(newUserLimitTicket.getTicketBalance().compareTo(userLimitTicket.getTicketBalance())<0) {
								throw new BenefitLessException(newUserLimitTicket.getUserNickname(),newUserLimitTicket.getId()+"","limit");
							}
						}

						if(NumberUtil.isGreaterZero(userLimitTicket.getCashBalance())) {
							// 使用现金抵扣 内购券全免
							newUserLimitTicket.setCashBalance(newUserLimitTicket.getCashBalance().subtract(userLimitTicket.getCashBalance()));
						} else {
							newUserLimitTicket.setTicketBalance(newUserLimitTicket.getTicketBalance().subtract(userLimitTicket.getTicketBalance()));
						}
						
						//记录使用
						UserTicketHis uhis = new UserTicketHis();
						uhis.setId(dbUtils.CreateID());
						uhis.setOpCode("203");
						uhis.setOpDate(new Date());
						uhis.setTicketId(userLimitTicket.getId());
						if (NumberUtil.isGreaterZero(userLimitTicket.getCashBalance())) {
							uhis.setTicket(userLimitTicket.getCashBalance());
							uhis.setTicketBalance(newUserLimitTicket.getCashBalance());
						} else {
							uhis.setTicket(userLimitTicket.getTicketBalance());
							uhis.setTicketBalance(newUserLimitTicket.getTicketBalance());
						}
						uhis.setUserId(userId);
						uhis.setKeyId(orderId);
						uhis.setNote("购买商品：" + productName + ",订单ID:" + orderId);
						uhis.setUserName(userLimitTicket.getUserNickname());
						uhis.setSupplierId(productId);
						userTicketHisService.save(uhis);
						if(newUserLimitTicket.getOneceFlag()==1) {
							newUserLimitTicket.setStatus(2);// 全部使用
						}else {
							if(NumberUtil.isGreaterZero(userLimitTicket.getCashBalance())) {
								// 使用现金抵扣 内购券全免
								if (!NumberUtil.isGreaterZero(newUserLimitTicket.getCashBalance())) {
									newUserLimitTicket.setStatus(2);// 全部使用
								} else {
									newUserLimitTicket.setStatus(1);// 部分使用
								}
							} else {
								
								if (!NumberUtil.isGreaterZero(newUserLimitTicket.getTicketBalance())) {
									newUserLimitTicket.setStatus(2);// 全部使用
								} else {
									newUserLimitTicket.setStatus(1);// 部分使用
								}
							}
						}
						newUserLimitTicket.setUpdateDate(new Date());
						userLimitTicketService.update(newUserLimitTicket);
					
					}else {//券过期或者失效
						throw new BenefitLessException(newUserLimitTicket.getUserNickname(),newUserLimitTicket.getId()+"","limit");
					}
				}else {
					throw new BenefitLessException("userId:"+userId,userLimitTicket.getId()+"","limit");
				}
			}
		}		
	}

	/**
	 * 订单合法性检测 检查限购及余额
	 *
	 * @param vo
	 * @param cart
	 * @return
	 */
	public ActResult<Object> checkOrder(Cart cart,UserFactory user) {
		BigDecimal companyTicket = BigDecimal.ZERO;;//企业券使用总量
		List<CartItem> cartItems = cart.getAllItems();
		for (CartItem item : cartItems) {// 只计算购物车勾选的商品
			if (item.isBuyFlag()) {
				
				if (item.getQuantity() < 1) {
					return ActResult.fail("商品购买数量不正确");
				}
				ProductSpecificationsVo sku = specificationsService.findByIdCache(Long.parseLong(item.getPartNumber()),item.getProductId()+"");
				if (sku.getIsMarketable() != 1) {
					return ActResult.fail(item.getProductName() + "已下架");
				}
				Product product  = productService.findById(sku.getProductId(),false);
				//查询企业限购
				if(productService.checkProductLimit(product, user)){
					return ActResult.fail(product.getName() + "只允许企业级用户购买");
				}
				//查询起售
				if(item.getQuantity()< sku.getMinLimitNum()){
					return ActResult.fail(product.getName() + "起售数量为" +  sku.getMinLimitNum());
				}
				//员工特价
				specificationsService.resetPrice(sku, product, user,false,item.getQuantity());

                item.setPrice(sku.getPrice());
				Integer stock = inventoryService.getInventoryFromRedis(Long.valueOf(item.getPartNumber()));
				stock = stock == null ? 0 : stock;
				if (item.getQuantity() > stock)
					return ActResult.fail("商品库存不足");
				
				if (item.getPrice().subtract(sku.getPrice()).compareTo(BigDecimal.ZERO) != 0) {
					return ActResult.fail(item.getProductName() + "价格发生变化,最新价格为:" + sku.getPrice());
				}
//					item.setFreight(productSpecifications.getCarriage());
//				companyTicket += item.getCompanyTicket() == null ? 0 : item.getCompanyTicket(); 
				
				companyTicket=companyTicket.add(item.getCompanyTicket()).equals(null)?BigDecimal.ZERO:item.getCompanyTicket();
			}
		}

		return ActResult.success(null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public ActResult<String> cancel(UserFactory user, String subOrderId, String closeReason,boolean flowResult) {
		SuborderQuery query = new SuborderQuery();
		query.setUserId(user.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
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
		// 团订单
		if("1".equals(subOrder.getOrderType()) || "4".equals(subOrder.getOrderType())) {

			//修改订单状态信息
			subOrder.setStatus(-1);
			subOrder.setCloseReason(closeReason);
			subOrder.setCancelTime(new Date());
			suborderService.update(subOrder);
			
			// 取消团订单
			return groupOrdersFacade.cancelAfterOpen(subOrder.getRelationId(), closeReason,-1);
			
		} else if("5".equals(subOrder.getOrderType())){
			
			//修改订单状态信息
//			subOrder.setStatus(-1);
//			subOrder.setCloseReason(closeReason);
//			subOrder.setCancelTime(new Date());
//			suborderService.update(subOrder);
//			
//			return exchangeOrdersFacade.cancel(user, subOrderId, closeReason,-1);

			return ActResult.fail("换领订单暂不能取消。");
		}
		
		//释放库存
		List<Suborderitem> items = suborderitemService.findBySubOrderId(subOrderId);
		Map<Long,UseExchangeTicketVo> mapExchange = new HashMap<Long,UseExchangeTicketVo>();
		for (Suborderitem item : items) {
			//活动商品
			if(!StringUtils.isEmpty(item.getPromotionProductId())){
				Promotion promotion = promotionDao.getById(item.getPromotionId());
				int num = item.getNumber()==null?0:item.getNumber();
				inventoryService.unlockPromotionStock(item.getPromotionProductId(), num, promotion);
				continue;
			}
			Product product = productService.findById(item.getProductId(),false);
			if (null != product.getStockLockType() && product.getStockLockType() == 1) {//下单就减库存，取消订单加库存
				inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());
				//更新销售数量
				//product.setSellNum(product.getSellNum()-1);
				//productService.update(product);
			}else{//当付款订单减库存，取消订单加库存（stockLockType=2）
				if(subOrder.getStatus()>0){
					inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());	
				}
			}
			
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
				
				uext.setCash(uext.getCash().add(item.getBenefitAmount()==null?BigDecimal.ZERO:item.getBenefitAmount()));
				uext.setTicket(uext.getTicket().add(item.getBenefitTicket()));
				uext.setSelf(uext.getSelf().add(item.getBenefitSelf()==null?BigDecimal.ZERO:item.getBenefitSelf()));
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
		
		// 优惠券处理
		if(subOrder.getLimitTicketCnt()!=null && subOrder.getLimitTicketCnt()>0) {
			dealSuborderItemLimit(user,subOrder);
		}
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
		BigDecimal cashThirdPay = BigDecimal.ZERO;//现金券支付和现金券抵扣总额
		if(subOrder.getStatus()>0) {
			// 已支付的场合
			if(NumberUtil.isGreaterZero(subOrder.getThirdPay())) {
				// 本期先返还微信
				if("wxpay".equals(subOrder.getThirdType()) || "zhifubao".equals(subOrder.getThirdType())) {
					// 支付宝、微信原路返还
					payment = paymentService.getByTradeNo(subOrder.getThirdType(), subOrder.getThirdNo());
					
					if(payment==null || StringUtils.isEmpty(payment.getAppId())) {
						// 全部退还到现金券
						cashThirdPay = subOrder.getRealPrice();//记录现金券抵扣
						comMap.put("absCash", subOrder.getRealPrice());
					} else {

//						if("zhifubao".equals(subOrder.getThirdType()) && !AlipayService.APP_ID_NEW.equals(payment.getAppId())) {
//							// 全部退还到现金券
//							comMap.put("absCash", subOrder.getRealPrice());
//						} else {
							BigDecimal cashPay = subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
							refundFee = subOrder.getRealPrice().subtract(cashPay);
							cashThirdPay = cashPay;//记录现金券抵扣
							//现金券抵扣部分退还
							comMap.put("absCash", cashPay);
//						}
					}
				} else {
					cashThirdPay = subOrder.getRealPrice();
					// 全部退还到现金券
					comMap.put("absCash", subOrder.getRealPrice());
				}
			} else {
				cashThirdPay = subOrder.getRealPrice();
				// 全部退还到现金券
				comMap.put("absCash", subOrder.getRealPrice());
			}
		} else {
			cashThirdPay = subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
			comMap.put("absCash", cashThirdPay);
		}
		//修改订单状态信息
		subOrder.setStatus(-1);
		subOrder.setCloseReason(closeReason);
		subOrder.setCancelTime(new Date());
		suborderService.update(subOrder);
		
		if(flowResult) {
			if(Long.valueOf("201712221700825").equals(user.getSupplierId())) {
				comMap.put("aceTicket", user.getEmail());
				comMap.put("aceUserId", user.getAddress());
			}
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
					rp.setSubOrderId(subOrderId);
					rp.setStatus(2);
					rp.setCreateTime(new Date());
					rp.setWay("pingtaiyue");
					rp.setTradeNo(acTicket.getData().toString());
					rp.setPayType(-1);
					rp.setOrderType(0);
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
				rp.setSubOrderId(subOrderId);
				rp.setStatus(0);
				rp.setCreateTime(new Date());
				rp.setWay(payment.getWay());
				rp.setAppId(payment.getAppId());
				rp.setPayType(-1);
				rp.setOrderType(0);
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
		return ActResult.success("订单取消成功");
	}
	

	private void dealSuborderItemLimit(UserFactory user,SubOrderVo subOrder) {
		SuborderLimitTicket suborderLimitTicket = suborderLimitTicketService.findBySuborderId(subOrder.getSubOrderId());
		List<SuborderitemLimitTicket> suborderItemLimitTicketList =suborderitemLimitTicketService.findBySuborderId(subOrder.getSubOrderId());
		Date now = new Date();
		if(suborderItemLimitTicketList!=null && suborderItemLimitTicketList.size()>0) {
			UserLimitTicket userLimitTicket = null;
			for (SuborderitemLimitTicket suborderitemLimitTicket : suborderItemLimitTicketList) {
				 userLimitTicket = userLimitTicketService.getById(suborderitemLimitTicket.getUserLimitTicketId());
				 if(userLimitTicket!=null) {
					//记录券信息
					UserTicketHis uhis = new UserTicketHis();
					uhis.setId(dbUtils.CreateID());
					uhis.setKeyId(suborderitemLimitTicket.getOrderId());
					uhis.setNote("订单取消,商品："+subOrder.getProductName()+",订单ID:"+subOrder.getSubOrderId());
					uhis.setOpCode("203");
					uhis.setOpDate(now);
					uhis.setSupplierId(suborderitemLimitTicket.getProductId());
					BigDecimal benefitCash = suborderitemLimitTicket.getBenefitCash();
					BigDecimal benefitTicket = suborderitemLimitTicket.getBenefitTicket();
					if(benefitCash!=null && NumberUtil.isGreaterZero(benefitCash)) {//现金抵扣
						uhis.setTicket(benefitCash);
						uhis.setTicketBalance(userLimitTicket.getCashBalance().add(benefitCash));
					}else {
						uhis.setTicket(benefitTicket);
						uhis.setTicketBalance(benefitTicket.add(suborderitemLimitTicket.getBenefitTicket()));
					}
					uhis.setTicketId(userLimitTicket.getId());
					uhis.setUserId(user.getId());
					uhis.setUserName(user.getNickName());
					// DB更新
					userTicketHisService.save(uhis);
					
					//修改现金余额
					userLimitTicket.setCashBalance(userLimitTicket.getCashBalance().add(suborderitemLimitTicket.getBenefitCash()));
					//修改内购余额
					userLimitTicket.setTicketBalance(userLimitTicket.getTicketBalance().add(suborderitemLimitTicket.getBenefitTicket()));
					// 复原状态
					if(userLimitTicket.getStatus() == 2) {
						if(benefitCash!=null && NumberUtil.isGreaterZero(benefitCash)) {//现金抵扣
							int cmp = userLimitTicket.getCashTotal().compareTo(userLimitTicket.getCashBalance());
							if(cmp==0) {
								userLimitTicket.setStatus(0);
							} else {
								if(NumberUtil.isGreaterZero(userLimitTicket.getCashBalance())) {
									userLimitTicket.setStatus(1);
								} else {
									userLimitTicket.setStatus(2);
								}
							}
						} else {
							int cmp = userLimitTicket.getTicketTotal().compareTo(userLimitTicket.getTicketBalance());
							if(cmp==0) {
								userLimitTicket.setStatus(0);
							} else {
								if(NumberUtil.isGreaterZero(userLimitTicket.getTicketBalance())) {
									userLimitTicket.setStatus(1);
								} else {
									userLimitTicket.setStatus(2);
								}
							}
						}
					}
					userLimitTicket.setUpdateDate(now);
					
					userLimitTicketService.update(userLimitTicket);
					
					suborderitemLimitTicket.setStatus(0);		// 暂时设置成0 以便计算专享抵用数量
					suborderitemLimitTicketService.update(suborderitemLimitTicket);
				 }else {
					 throw new BenefitLessException("userId:"+user.getId(),suborderitemLimitTicket.getUserLimitTicketId()+"","limit");
				 }
			}
		}
		if(suborderLimitTicket!=null) {
			suborderLimitTicket.setStatus(-1);
			suborderLimitTicket.setUpdateTime(now);
			suborderLimitTicketService.update(suborderLimitTicket);
		}else {
			 throw new BenefitLessException("userId:"+user.getId(),subOrder.getSubOrderId()+"","limit");
		 }
	}

	//0未支付，1已支付，2已发货  3退单申请中，4已收货  10买家已评价  11已退货完毕 -1已取消
	@Override
	public boolean updateOrderStatus4(UserFactory userFactory, String subOrderId) {
		boolean result = false;
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userFactory.getId());
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		subOrder.setUpdateBy(userFactory.getUserName());//更新者
		subOrder.setUpdateTime(new Date());//更新时间
		if (null != subOrder) {
			int beforeStatus = subOrder.getStatus();
			
			if(beforeStatus == 1) {
				Orders o =ordersService.getById(subOrder.getOrderId());
				if(!"1".equals(o.getSelfDelivery())){
					return false;
				}
			}
			if (beforeStatus == 2 || beforeStatus==1) {//确认收货（条件：已发货），赠送内购券，运费不计入赠送
				Date now = new Date();
				subOrder.setStatus(4);
				subOrder.setCommentStatus(0);//待评价
				subOrder.setTakeTime(now);
				
				boolean prizePoint = true;
				// 团订单
				if("1".equals(subOrder.getOrderType())) {
					groupOrdersFacade.masterTakeOrder(subOrder.getRelationId(),userFactory);
					prizePoint = false;
				}

				if(prizePoint) {
					//确认收货增加用户内购券
					int addPoint = subOrder.getRealPrice().subtract(subOrder.getTotalShipping()).intValue();
					
					if(addPoint>0) {
						String note = "购买商品并积极确认收货，平台奖励内购券："+addPoint;
						Map<String,Object> paramMap=new HashMap<String,Object>();
						paramMap.put("limitm", addPoint);
						paramMap.put("desrc", note);
						paramMap.put("empId", userFactory.getId());
						paramMap.put("empName", userFactory.getNickName());
						paramMap.put("updName","auto");
		
						HttpClientUtil.sendHttpRequest("post", qiyeApiUrl+"api/fisrtPrize", paramMap);
					}
				}
				
			} else {
				return false;
			}
			suborderService.update(subOrder);
			result = true;
		}
		return result;
	}

	/**
	 * 支付成功的处理
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ActResult paySuccess(Long userId, String subOrderId,String way,String trade_no,BigDecimal totalFee) throws Exception {
		Date now = new Date();
		SuborderQuery query = new SuborderQuery();
		query.setUserId(userId);
		query.setSubOrderId(subOrderId);
		SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		if (subOrder.getStatus() != 0) {
			return ActResult.success(null);
		}
		List<Suborderitem> items = suborderitemService.findBySubOrderId(subOrderId);
		UserFactory user = userService.getById(userId);
		for (Suborderitem item : items) {
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
					cancel(user, subOrderId, "库存不足，系统自动取消订单",true);
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
		List<String> ls =new ArrayList<String>();
		ls.add(subOrderId);
		ShopPushUtil.pushMsg4order(redisUtil,ls,ShopPushUtil.PUSH_TYPE_ORDER_PAY);
		//更新母单状态
		List<Suborder> list = this.suborderService.findByOrderId(subOrder.getOrderId());
		Boolean check = true;
		for(Suborder sub : list){
			if(sub.getStatus()==0){
				check = false;
				break;
			}
		}
		Orders order = ordersService.getById(subOrder.getOrderId());
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
	@SuppressWarnings("rawtypes")
	@Override
	public ActResult<Object> cancelOrder(UserFactory user,Long orderId , String closeReason,boolean flowResult) {
		List<Suborder> list = suborderService.findByOrderId(orderId);
		ActResult ac =  new ActResult();
		Orders order = ordersService.getById(orderId);
		if(order.getStatus()>0){
			return ActResult.fail("订单状态不正确");
		}
		for(Suborder subOrder :list){
			ac = cancel(user, subOrder.getSubOrderId(), closeReason,flowResult);
			if(!ac.isSuccess()){
				break;
			}
		}
		order.setStatus(-1);
		ordersService.update(order);
		return ac;
	}
	
	@Override
	public void listAdd(List<Suborderitem> suborderitems, Suborderitem suborderitem) {
		boolean isHas = false; //是否有相同商品
		for (Suborderitem sub : suborderitems) {
			if(sub.getPartNumber().equals(suborderitem.getPartNumber())) {
				isHas = true;//有相同商品那么，修改就可以了
				sub.setNumber(sub.getNumber() + suborderitem.getNumber());
				sub.setRealPay(sub.getRealPay().add(suborderitem.getRealPay()));
				sub.setShipping(sub.getShipping().add(suborderitem.getShipping()));
				sub.setCompanyTicket(sub.getCompanyTicket().add(suborderitem.getCompanyTicket()));
			}
		}
		if(!isHas) {//如果没有直接add
			suborderitems.add(suborderitem);
		}
	}
	/**
	 * 支付成功且异步回调成功后更新记录以及订单状态(status=2:回调成功)
	 * @param orderId
	 * @param subOrderId
	 */
	@Override
	public void updateOrderToPay(Payment payment) {
		Date date = new Date();
		//团购订单
		if(payment.getPayType()!=null && payment.getPayType()==4){
			//订单支付
			groupOrdersFacade.updateOrderToPay(payment);
			return;
		}

		//团购订单
		if(payment.getPayType()!=null && payment.getPayType()==5){
			//订单支付
			exchangeOrdersFacade.updateOrderToPay(payment);
			return;
		}
		
		if(payment.getPayType()!=null && payment.getPayType()==2) {
			//现金券 储值
			
			UserFactory user = userService.getById(payment.getOrderId());
			if(user == null) return;
					
			//返还用户使用的各种优惠抵消
			Map<String,Object> comMap=new HashMap<String,Object>();
			comMap.put("empId", payment.getOrderId());
			comMap.put("amount", payment.getTotalFee());
			comMap.put("desrc", "现金券储值");
			comMap.put("updUser", user.getUserName());
			comMap.put("key", payment.getOutTradeNo());
			HttpClientUtil.sendHttpRequest("post", Constant.QIYE_API_URL+"api/userCharge", comMap);

		} else {
			//订单支付

			//更改母单及子弹状态
			if(!StringUtils.isEmpty(payment.getOrderId())){
				Orders order = ordersService.getById(payment.getOrderId());
				if(order!=null) {
					List<Suborder> list = suborderService.findByOrderId(payment.getOrderId());
					for(Suborder subOrder : list){
						try {
							this.paySuccess(order.getUserId(), subOrder.getSubOrderId(),payment.getWay(),payment.getTradeNo(),payment.getTotalFee());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
			} else if(!StringUtils.isEmpty(payment.getSubOrderId())){
				//更新子单
				Suborder subOrder = this.suborderService.getById(payment.getSubOrderId());
				if(StringUtils.isEmpty(subOrder)){
					return;
				}
				Orders order = ordersService.getById(subOrder.getOrderId());
				if(StringUtils.isEmpty(order)){
					return;
				}
				try {
					this.paySuccess(order.getUserId(), subOrder.getSubOrderId(),payment.getWay(),payment.getTradeNo(),payment.getTotalFee());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
				
		payment.setStatus(2);
		payment.setUpdateTime(date);
		paymentService.update(payment);
	}
	
	@Override
	public void dealOrders(Orders orders, Suborder suborder, List<Suborderitem> suborderitems) {
		if(orders!=null) {
			ordersService.save(orders);
		}
		if(suborder!=null) {
			suborder.setPayTime(new Date());
			suborderService.save(suborder);
		}
		if(suborderitems!=null) {
			for (Suborderitem suborderitem : suborderitems) {
				suborderitemService.save(suborderitem);
			}
		}
	}
}
