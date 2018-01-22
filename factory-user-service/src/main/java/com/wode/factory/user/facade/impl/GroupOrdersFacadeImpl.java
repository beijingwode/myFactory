package com.wode.factory.user.facade.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
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
import com.wode.factory.model.GroupBuy;
import com.wode.factory.model.GroupBuyUser;
import com.wode.factory.model.GroupOrders;
import com.wode.factory.model.GroupSuborder;
import com.wode.factory.model.GroupSuborderitem;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.Shop;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.model.UserFactory;
import com.wode.factory.model.UserImGroup;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.service.GroupBuyService;
import com.wode.factory.service.ProductCategoryService;
import com.wode.factory.service.ProductLadderService;
import com.wode.factory.service.ProductService;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateService;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.dao.SupplierCategoryDao;
import com.wode.factory.user.dao.UserBalanceDao;
import com.wode.factory.user.facade.GroupOrdersFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.facade.UserExchangeTicketFacade;
import com.wode.factory.user.model.Cart;
import com.wode.factory.user.model.CartItem;
import com.wode.factory.user.model.GroupBuyProductsVo;
import com.wode.factory.user.model.UseExchangeTicketVo;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.query.UserBalanceQuery;
import com.wode.factory.user.service.CurrencyService;
import com.wode.factory.user.service.EntParamCodeService;
import com.wode.factory.user.service.GroupOrdersService;
import com.wode.factory.user.service.GroupSuborderItemService;
import com.wode.factory.user.service.GroupSuborderService;
import com.wode.factory.user.service.InventoryService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.ProductSpecificationsService;
import com.wode.factory.user.service.ShopService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserImGroupService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.EasemobIMUtils;
import com.wode.factory.user.vo.GroupAndOrderVO;
import com.wode.factory.user.vo.GroupOrderVO;
import com.wode.factory.user.vo.ProductSpecificationsVo;
import com.wode.factory.vo.GroupBuyVo;
import com.wode.search.WodeSearchManager;

@Service("groupOrdersFacade")
public class GroupOrdersFacadeImpl implements GroupOrdersFacade  {
	
	//用户
	@Autowired
	private UserService userService;
	@Autowired
	private OrdersFacade ordersFacade;
	//商品
	@Autowired
	private ProductService productService;
	//团购总单表
	@Autowired
	private GroupOrdersService groupOrdersService;
	
	@Autowired
	private CurrencyService currencyService;
	//团购订单表
	@Autowired
	private GroupSuborderService groupSuborderService;
	
	@Autowired
	@Qualifier("supplierCategoryDao")
	private SupplierCategoryDao supplierCategoryDao;
	
	//商品分类
	@Autowired
	private ProductCategoryService productCategoryService;  
	
	@Autowired
	private RedisUtil redisUtil;
	//库存
	@Autowired
	private InventoryService inventoryService;
	
	//换领币
	@Autowired
	private UserExchangeTicketFacade userTicketFacade;
	
	@Autowired
    private GroupSuborderItemService groupSuborderItemService;
	
	@Autowired
	@Qualifier("userBalanceDao")
	private UserBalanceDao userBalanceDao;
	//员工特价
	@Autowired
	ProductSpecificationsService specificationsService;
	@Autowired
	private Dao dao;
	
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private SuborderService suborderService;
	
	@Autowired
	private SuborderitemService suborderitemService;
	
	@Autowired
	private GroupBuyService groupBuyService;
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ShippingTemplateService shippingTemplateService;
	@Autowired
	private ShippingFreeRuleService shippingFreeRuleService;
	
    @Autowired
    DBUtils dbUtils;
	private String qiyeApiUrl = Constant.QIYE_API_URL;

	@Autowired
	@Qualifier("productDao")
	private ProductDao productDao;
	
	@Autowired
    private ShopService shopService;
	@Autowired
	private WodeSearchManager wsm;
	@Autowired
	private UserImGroupService userImGroupService;
	@Autowired
	private EntParamCodeService entParamCodeService;
	@Autowired
	private ProductLadderService productLadderService;
	
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;
	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);

	Logger log = LoggerFactory.getLogger(GroupOrdersFacadeImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ActResult<Object> createOrder(GroupOrderVO vo, Cart cart, BigDecimal useCash,
			List<String> subIds, String message) throws BenefitLessException, SupplierShardeLessException {
		// 校验订单信息
		ActResult<Object> ar = checkOrder(vo, cart, userService.getById(vo.getGroupOrders().getUserId()));
		if (!ar.isSuccess()) {
			return ar;
		}
		GroupOrders order = vo.getGroupOrders();
		ActResult<Object> result = ActResult.success(null);
		BigDecimal totalProduc = cart.calculateTotalRealPriceWithMaxCompanyTicket();// 商品总价，不含运费
		BigDecimal totalShipping = BigDecimal.ZERO; // cart.calculateTotalFreight();// 总运费
		BigDecimal totalAdjustment = BigDecimal.ZERO;// 总折扣金额
		BigDecimal realPrice = BigDecimal.ZERO;// 实际支付金额 = 商品总价 + 总运费 - 总折扣金额
		int status = 0;// 订单状态 具体规则未定(0：待付款，1：支付成功)
		// TODO 根据活动规则、优惠券、使用余额等计算总折扣
		realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
		// 商品价格
		order.setTotalProduct(totalProduc);
		// 商品运费
		order.setTotalShipping(totalShipping);
		// 总折扣
		order.setTotalAdjustment(totalAdjustment);

		order.setRealPrice(realPrice);

		groupOrdersService.save(order);// 保存订单
		long orderId = order.getOrderId();// 母单号
		Map<Long, List<CartItem>> map = cart.groupBySupplier();

		GroupSuborder subOrder = null;
		CartItem cartItem = null;
		int num = 0;
		UserBalanceQuery query = new UserBalanceQuery();
		query.setUserId(order.getUserId());
		BigDecimal allUseTicket=BigDecimal.ZERO;	//使用全部内购券
		BigDecimal allUseCash=BigDecimal.ZERO;		// 现金券抵扣总额
		Iterator<Map.Entry<Long, List<CartItem>>> it = map.entrySet().iterator();
		BigDecimal useCashLast = useCash == null ? BigDecimal.ZERO : useCash;

		Currency currency = currencyService.findByName("companyTicket");

		Map<String, String> supplierNote = new HashMap<String, String>();// 对应商家留言集合
		if (!StringUtils.isEmpty(message)) {// pc端
			String[] noto = message.split(",");
			for (String string : noto) {
				String[] ary = string.split("_");
				if (ary.length > 1 && !StringUtils.isEmpty(ary[1])) {
					supplierNote.put(ary[0], ary[1]);
				}
			}
		}
		List<GroupSuborderitem> cutItems = new ArrayList<GroupSuborderitem>();
		Map<Long, UseExchangeTicketVo> mapExchange = new HashMap<Long, UseExchangeTicketVo>();
		List<GroupSuborder> suborderList = new ArrayList<GroupSuborder>();
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
			subOrder = new GroupSuborder();
			subOrder.setSubOrderId(orderId + "-" + num);
			subOrder.setOrderId(orderId);
			subOrder.setSupplierId(entry.getKey());
			subOrder.setCloseFlg("0");
			subOrder.setPayConfirm(0);
			if (supplierNote.containsKey(subOrder.getSupplierId() + "")) {// pc端
				subOrder.setNoto(supplierNote.get(subOrder.getSupplierId() + ""));
			} else {// app端
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
					subTotalProduc = subTotalProduc.add(cartItem.getRealPrice().add(cartItem.getMaxCompanyTicket())
							.multiply(new BigDecimal(cartItem.getQuantity())));
					Product product = productService.findById(cartItem.getProductId(), false);
					productName += product.getFullName()+",";
				}
			}
			// 生成subOrder(不包含折扣)
			subTotalPrice = subTotalProduc.add(subTotalShipping);
			subOrder.setTotalProduct(subTotalPrice);
			subOrder.setTotalShipping(subTotalShipping);
			
			subOrder.setNowTotalShipping(subTotalShipping);
			// 订单运费加算
			totalShipping = totalShipping.add(subTotalShipping);
			
			subOrder.setRealPrice(subRealPrice);
			subOrder.setStatus(order.getStatus());// 状态
			Date date = new Date();
			subOrder.setCreateTime(date);// 创建日期
			// 延长15天
			subOrder.setLasttakeTime(TimeUtil.addDay(date, 15));// 最后确认收货时间
			subOrder.setInvoiceStatus(0);// 默认不申请发票
			
			subOrder.setGroupId(order.getGroupId());//子单设置团id
			subOrder.setUserId(order.getUserId());//子单设置用户id
			productName = productName.substring(0, productName.length()-1);
			if(productName.length()>64) {//防止超出长度
				productName = productName.substring(0, 64);
			}
			subOrder.setProductName(productName);
			groupSuborderService.save(subOrder);
			suborderList.add(subOrder);
			// 保存子订单项
			String subOrderId = subOrder.getSubOrderId();
			List<GroupSuborderitem> itemsCut = new ArrayList<GroupSuborderitem>();
			GroupSuborderitem subOrderItem = null;
			BigDecimal fljUsage = BigDecimal.ZERO;// 内购券使用量
			BigDecimal benefitUsage = BigDecimal.ZERO;// 优惠券
			Double freeTicket = currency.getPercentage();
			for (int j = 0; j < cartItems.size(); j++) {
				cartItem = cartItems.get(j);
				if (cartItem.isBuyFlag()) {
					subOrderItem = new GroupSuborderitem();
					Product product = productService.findById(cartItem.getProductId(), false);
					if (StringUtils.isEmpty(subOrder.getShopId())) {
						subOrder.setShopId(product.getShopId());
					}
					SupplierCategory sc = supplierCategoryDao.getBySupplierAndCategory(product.getSupplierId(),
							product.getCategoryId(), product.getShopId());
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
					// 商品名称
					subOrderItem.setProductName(product.getFullName());
					// 员工特价
					subOrderItem.setEmpPrice(product.getEmpPrice());
					// 品类id
					subOrderItem.setCategoryId(product.getCategoryId());
					// 品类名称
					subOrderItem.setCategoryName(productCategoryService.findById(product.getCategoryId()).getName());
					// 商品条形码
					subOrderItem.setProductCode(cartItem.getProductCode());
					// 商品规格
					subOrderItem.setItemValues(cartItem.getItemValues());
					// 图片路径
					subOrderItem.setImage(cartItem.getImagePath());
					// 增加内购价
					subOrderItem.setInternalPurchasePrice(cartItem.getRealPrice());
					// 增加团id
					subOrderItem.setGroupId(order.getGroupId());
					// 增加内购价
					subOrderItem.setNowPrice(cartItem.getRealPrice());
					//来自页面
					subOrderItem.setFromWay(cartItem.getPageKey());
					//来自支付方式
					subOrderItem.setFromType(cartItem.getFromType());
					
					//保存subOrderItem
					groupSuborderItemService.save(subOrderItem);

					// 计算企业券使用量
					if(cartItem.getCompanyTicket()==null) cartItem.setCompanyTicket(BigDecimal.ZERO);
					allUseTicket= allUseTicket.add(cartItem.getCompanyTicket());
					fljUsage=fljUsage.add(cartItem.getCompanyTicket());
					
					subOrderItem.setCompanyTicket(cartItem.getCompanyTicket());	
					// 计算换领币使用量
					if (!NumberUtil.isGreaterZero(cartItem.getBenefitTicket())) {
						subOrderItem.setBenefitType(null);
						subOrderItem.setBenefitAmount(BigDecimal.ZERO);
						subOrderItem.setBenefitTicket(BigDecimal.ZERO);
						subOrderItem.setBenefitSelf(BigDecimal.ZERO);
					} else {
						subOrderItem.setBenefitType(3);
						if (cartItem.getBenefitAmount() == null) {
							cartItem.setBenefitAmount(BigDecimal.ZERO);
						}
						// 临时记录换领币使用状况（商品单位）
						UseExchangeTicketVo uext = null;
						if (mapExchange.containsKey(cartItem.getProductId())) {
							uext = mapExchange.get(cartItem.getProductId());
						} else {
							uext = new UseExchangeTicketVo();
							uext.setProductId(cartItem.getProductId());
							uext.setCash(BigDecimal.ZERO);
							uext.setTicket(BigDecimal.ZERO);
							uext.setSelf(BigDecimal.ZERO);
							uext.setNote("购买商品：" + cartItem.getProductName() + ",订单ID:" + subOrderItem.getSubOrderId());
							uext.setSubOrderId(subOrderItem.getSubOrderId());
							mapExchange.put(uext.getProductId(), uext);
						}
						uext.setCash(uext.getCash().add(cartItem.getBenefitAmount()));
						uext.setTicket(uext.getTicket().add(cartItem.getBenefitTicket()));
						uext.setSelf(uext.getSelf().add(cartItem.getBenefitSelf()));
												
						subOrderItem.setBenefitTicket(cartItem.getBenefitTicket());
						subOrderItem.setBenefitAmount(cartItem.getBenefitAmount());
						subOrderItem.setBenefitSelf(cartItem.getBenefitSelf());

						// 优惠券累计
						benefitUsage = benefitUsage.add(cartItem.getBenefitAmount());
					}
					// 这里换成内购价
					BigDecimal realPay = cartItem.getRealPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));// 应付款
					// 应付款=货款-内购券-优惠券
					// 应付款=内购价-优惠券
					realPay = realPay.subtract(
							cartItem.getBenefitAmount() == null ? BigDecimal.ZERO : cartItem.getBenefitAmount());// BigDecimal.valueOf(cartItem.getCompanyTicket()
																													// *
																													// freeTicket)
					subOrderItem.setRealPay(realPay);// 不记运费
					subOrderItem.setNowRealPay(realPay);//新增留作保存
					
					groupSuborderItemService.update(subOrderItem);

					// 现在券大于0
					if (useCashLast.compareTo(BigDecimal.ZERO) > 0) {
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
					// 库存相关操作
					ActResult<Object> stockResult = new ActResult<Object>();
					if (null != product.getStockLockType() && product.getStockLockType() == 1) {
						stockResult = inventoryService.cut(Long.parseLong(cartItem.getPartNumber()),
								subOrderItem.getNumber());
						// 已减库存记录
						cutItems.add(subOrderItem);
					} else {

						Integer stock = inventoryService
								.getInventoryFromRedis(Long.parseLong(cartItem.getPartNumber()));
						if (stock < subOrderItem.getNumber())
							stockResult.setSuccess(false);

						if (product.getStockLockType() == 2) {
							itemsCut.add(subOrderItem);
						}

					}
					if (!stockResult.isSuccess()) {
						return ActResult.fail("商品库存不足");
					}

				}
			}
			// 用户内购券使用量计算折价总数
			if (fljUsage.compareTo(BigDecimal.ZERO) == 1) {// > 0
				subOrder.setCompanyTicket(fljUsage.multiply(new BigDecimal(freeTicket)));
			} else {
				subOrder.setCompanyTicket(BigDecimal.ZERO);
			}
			subTotalAdjustment = subOrder.getCompanyTicket();
			// 优惠券累计
			if (benefitUsage.compareTo(BigDecimal.ZERO) > 0) {// > 0
				subOrder.setBenefitType(3);
				subOrder.setBenefitAmount(benefitUsage);
			} else {
				subOrder.setBenefitType(null);
				subOrder.setBenefitAmount(BigDecimal.ZERO);
			}
			subTotalAdjustment = subTotalAdjustment.add(benefitUsage); // BigDecimal.valueOf(fljUsage * freeTicket);

			// 实付金额及优惠
			subRealPrice = subTotalPrice.subtract(subTotalAdjustment);
			subOrder.setRealPrice(subRealPrice);
			subOrder.setBeforeOpenRealPrice(subRealPrice);
			subOrder.setAfterOpenRealPrice(subRealPrice);//新增字段已备查询所用
			subOrder.setTotalAdjustment(subTotalAdjustment);

			// 现金券抵扣
			if (subTotalCash.compareTo(BigDecimal.ZERO) > 0) {// > 0
				subOrder.setCashPay(subTotalCash);
			} else {
				subOrder.setCashPay(BigDecimal.ZERO);
			}

			if (subOrder.getRealPrice().compareTo(subOrder.getCashPay()) == 0) {
				subOrder.setStatus(1);// 状态
				subOrder.setPayTime(date);
				subOrder.setStatus(1);// 状态
				subOrder.setPayTime(date);
				subIds.add(subOrder.getSubOrderId());
				for (GroupSuborderitem suborderitem2 : itemsCut) {
					inventoryService.cut(Long.parseLong(suborderitem2.getPartNumber()), suborderitem2.getNumber());

					// 已减库存记录
					cutItems.add(suborderitem2);
				}
			}
			subOrder.setLimitTicketCnt(0);
			groupSuborderService.update(subOrder);

			// 用户计算优惠总价格
			totalAdjustment = totalAdjustment.add(subTotalAdjustment);
		}

		// 换领币消费处理
		if (mapExchange.size() > 0) {
			int deal = userTicketFacade.orderUserTicket(order.getUserId(), mapExchange, order.getName(),
					order.getOrderId());
			if (deal == -1) {
				// 回退库存
				for (GroupSuborderitem item : cutItems) {
					inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());
				}
				throw new BenefitLessException(order.getName(), order.getName() + "");
			} else if (deal == -2) {
				// 回退库存
				for (GroupSuborderitem item : cutItems) {
					inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());
				}
				throw new SupplierShardeLessException("换领币");
			}
		}

		realPrice = totalProduc.add(totalShipping).subtract(totalAdjustment);
		order.setTotalShipping(totalShipping);
		order.setTotalAdjustment(totalAdjustment);
		order.setRealPrice(realPrice);
		order.setItems(suborderList);
		// 根据支付方式、还需支付金额判断订单状态
		if (realPrice.compareTo(BigDecimal.ZERO) == 0 || realPrice.compareTo(useCash) == 0) {
			status = 1;

		}
		order.setNowShipping(order.getTotalShipping());
		order.setStatus(status);
		groupOrdersService.update(order);
		if (status == 1) {
			String imgrouId = groupOrdersService.avgFreight(order);
			if (!StringUtils.isEmpty(imgrouId)) {
				order.setIm_groupId(imgrouId);
				UserImGroup im= userImGroupService.getById(NumberUtil.toLong(imgrouId));
				if(im!=null) {
					List<UserImGroup> groups = new ArrayList<UserImGroup>();
					groups.add(im);
					GroupOrders groupOrders = groupOrdersService.getById(order.getOrderId());
					UserFactory us = userService.getById(groupOrders.getUserId());
					String name =us.getNickName() == null ? us.getUserName() : us.getNickName();
					String msg="@"+name+" 刚刚完成了一笔购物！\r\n" + "为其他团员节省了总计"+groupOrders.getSaveAmount()+"元~\r\n" + "感谢他为大家做出的贡献~";
					EasemobIMUtils.shoppingGroupMessage(msg, groups, us.getId(), null, name,"paySuccess");
				}
			}
		}
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
			rp.setOrderType(4);		//团购单
			rp.setTotalFee(allUseCash);
			rp.setExp1(allUseTicket.toString());
			paymentService.save(rp);
		}
		return result;
	}

	
	
	/**
	 * 订单合法性检测
	 *
	 * @param vo
	 * @param cart
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ActResult<Object> checkOrder(GroupOrderVO vo, Cart cart,UserFactory user) {
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
				/*if(item.getQuantity()< sku.getMinLimitNum()){
					return ActResult.fail(product.getName() + "起售数量为" +  sku.getMinLimitNum());
				}*/
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
				
				companyTicket=companyTicket.add(item.getCompanyTicket()).equals(null)?BigDecimal.ZERO:item.getCompanyTicket();
			}
		}

		return ActResult.success(null);
	}



	/**
	 * 取消订单
	 */
	@Override
	public ActResult<String> cancelOrder(UserFactory user, Long orderId, String closeReason,boolean flowResult) {
		List<GroupSuborder> list = groupSuborderService.findByOrderId(orderId);
		ActResult ac =  new ActResult();
		GroupOrders order = groupOrdersService.getById(orderId);
		if(order.getStatus()>0){
			return ActResult.fail("订单状态不正确");
		}
		for(GroupSuborder subOrder :list){
			ac = cancel(user, subOrder, closeReason,flowResult);
			if(!ac.isSuccess()){
				break;
			}
		}
		order.setStatus(-1);
		groupOrdersService.update(order);
		return ac;
	}

	@Override
	@Transactional
	public ActResult<String> cancelAfterOpen(Long groupId, String closeReason,Integer status) {
		Date now = new Date();
		
		GroupBuyVo gb= groupBuyService.getById(groupId);
		gb.setStatus(status);
		if(gb.getOrderStatus() >0) {
			gb.setOrderStatus(-1);
		}
		gb.setCloseReason(closeReason);
		gb.setOrderCancelTime(now);
		groupBuyService.update(gb);
		
		//查询同一个团购支付过的订单
		List<GroupOrders> groupOrdersList  = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupId).and("status",">","0"));
		for (GroupOrders groupOrders : groupOrdersList) {
			UserFactory user =userService.getById(groupOrders.getUserId());
			List<GroupSuborder> ls = groupSuborderService.findByOrderId(groupOrders.getOrderId());
			for (GroupSuborder groupSuborder : ls) {
				this.cancel(user, groupSuborder, closeReason,true);
			}
			
			groupOrders.setStatus(-1);
			dao.update(groupOrders);
		}
		// 暂不考虑 异常状况
		return ActResult.success("订单取消成功");
	}
	
	
	@Override
	@Transactional
	public ActResult<String> cancel(UserFactory user, GroupSuborder subOrder, String closeReason,boolean flowResult) {

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
		
		//释放库存
		List<GroupSuborderitem> items = groupSuborderItemService.findBySubOrderId(subOrder.getSubOrderId());
		Map<Long,UseExchangeTicketVo> mapExchange = new HashMap<Long,UseExchangeTicketVo>();
		for (GroupSuborderitem item : items) {
			Product product = productService.findById(item.getProductId(),false);
			if (null != product.getStockLockType() && product.getStockLockType() == 1) {//下单就减库存，取消订单加库存
				inventoryService.addNum(Long.parseLong(item.getPartNumber()), item.getNumber());
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
						cashThirdPay = subOrder.getRealPrice();//记录现金券抵扣
						// 全部退还到现金券
						comMap.put("absCash", cashThirdPay);
					} else {
						BigDecimal cashPay = subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
						refundFee = subOrder.getRealPrice().subtract(cashPay);
						//现金券抵扣部分退还
						cashThirdPay = cashPay;//记录现金券抵扣
						comMap.put("absCash", cashThirdPay);
					}
				} else {
					cashThirdPay = subOrder.getRealPrice();
					// 全部退还到现金券
					comMap.put("absCash", cashThirdPay);
				}
			} else {
				cashThirdPay = subOrder.getRealPrice();
				// 全部退还到现金券
				comMap.put("absCash", cashThirdPay);
			}
		} else {
			cashThirdPay = subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
			comMap.put("absCash", cashThirdPay);
		}
		//修改订单状态信息
		subOrder.setStatus(-1);
		subOrder.setCloseReason(closeReason);
		subOrder.setCancelTime(new Date());
		dao.update(subOrder);

		if(flowResult && NumberUtil.isGreaterZero(cashThirdPay)) {
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
					rp.setOrderId(null);
					rp.setSubOrderId(subOrder.getSubOrderId());
					rp.setStatus(2);
					rp.setCreateTime(new Date());
					rp.setWay("pingtaiyue");
					rp.setTradeNo(acTicket.getData().toString());
					rp.setPayType(-1);
					rp.setOrderType(4);
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
				rp.setOrderType(4);
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
	
	/**
	 * 合并团购单(下订单)
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Override
	@Transactional
	public boolean mergerOrder(Long groupId,List<String> subIds) throws IllegalAccessException, InvocationTargetException {
		//拼接原订单 原订单只会有一个合单，一个子单
		Orders orders = null;
		Suborder suborder = null;
		//订单商品行会很多，需要将相同的skuid订单合并
		List<Suborderitem> suborderitems = new ArrayList<Suborderitem>();
		//通过团购id查询所有一个团中的订单
		GroupBuyVo groupBuy= groupBuyService.getById(groupId);
		List<GroupOrders> groupOrdersList = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupId).and("status","=",1).orderBy("createTime", "asc"));
		if(groupBuy!=null &&  null != groupOrdersList && groupOrdersList.size() != 0 ) {
			boolean isFirst = true;
			//复制团长的订单信息
			BigDecimal oldTotalShipping = BigDecimal.ZERO;//支付的总运费
			for (GroupOrders groupOrders : groupOrdersList) {
				
				//第一复制团长信息即可，不是团长信息 。需要将数据进行累加。
				//需要进行计算的 总运费，总价，总折扣，实付金额
				//总运费就是算出来的团购运费，计算一次就可以了
				//总折扣累加就可
				//总价累加就可以
				//总实付金额 需要用  原来的实付金额 - 减去原来的运费 + 现在运费   并且需要累加
				if(orders == null ) {
					orders = new Orders();
					BeanUtils.copyProperties(orders, groupOrders);
					orders.setStatus(1);//订单状态变成已支付
					//总支付金额
					orders.setRealPrice(orders.getRealPrice().add(groupOrders.getNowShipping()).subtract(groupOrders.getTotalShipping()));
					//总运费
					orders.setTotalShipping(groupBuy.getTotalShipping());
				}else {
					//总折扣
					orders.setTotalAdjustment(orders.getTotalAdjustment().add(groupOrders.getTotalAdjustment()));
					//总价格
					orders.setTotalProduct(orders.getTotalProduct().add(groupOrders.getTotalProduct()));
					//总支付金额
					orders.setRealPrice(orders.getRealPrice().add(groupOrders.getNowShipping()).subtract(groupOrders.getTotalShipping()).add(groupOrders.getRealPrice()));
				}
				orders.setSelfDelivery(orders.getSelfDelivery()==null?"0":orders.getSelfDelivery());
				//查询所有子订单
				List<GroupSuborder> groupSuborderList = dao.query(GroupSuborder.class, Cnd.where("orderId","=",groupOrders.getOrderId()));
			    if(null != groupSuborderList && groupSuborderList.size() != 0 ) {
			    	for (GroupSuborder groupSuborder : groupSuborderList) {
			    		if(null ==  suborder) {//只复制第一个团长的信息
			    			suborder = new Suborder();
			    			//复制团长的订单信息
			    			BeanUtils.copyProperties(suborder, groupSuborder);
			    			suborder.setStatus(1);//订单状态变成已支付
			    			suborder.setTotalShipping(orders.getTotalShipping());
			    			suborder.setTotalProduct(suborder.getTotalProduct().subtract(groupSuborder.getTotalShipping()).add(suborder.getTotalShipping()));
			    			//suborder.setTotalProduct(orders.getTotalProduct());
			    			//suborder.setTotalAdjustment(orders.getTotalAdjustment());
			    			suborder.setRealPrice(suborder.getRealPrice().subtract(groupSuborder.getTotalShipping()).add(suborder.getTotalShipping()));
			    			suborder.setOrderType("1");					//标记团订单
			    			suborder.setRelationId(groupId);	//标记团id
			    			if(suborder.getThirdPay()==null) suborder.setThirdPay(BigDecimal.ZERO);//第三方支付为null 设为0
			    			oldTotalShipping = oldTotalShipping.add(groupSuborder.getTotalShipping());//已支付的运费
			    			suborder.setProductName(groupSuborder.getProductName());
			    		}else {
//			    			suborder.setTotalShipping(orders.getTotalShipping());
			    			suborder.setTotalProduct(suborder.getTotalProduct().add(groupSuborder.getTotalProduct().subtract(groupSuborder.getTotalShipping())));
			    			suborder.setTotalAdjustment(suborder.getTotalAdjustment().add(groupSuborder.getTotalAdjustment()));
			    			suborder.setRealPrice(suborder.getRealPrice().add(groupSuborder.getRealPrice().subtract(groupSuborder.getTotalShipping())));
			    			suborder.setCompanyTicket(suborder.getCompanyTicket().add(groupSuborder.getCompanyTicket()));
			    			if(groupSuborder.getThirdPay()!=null) {
			    				suborder.setThirdPay(suborder.getThirdPay().add(groupSuborder.getThirdPay()));//第三支付
			    			}
			    			suborder.setProductName(groupSuborder.getProductName());
			    			suborder.setCashPay(suborder.getCashPay().add(groupSuborder.getCashPay()));//现金抵扣
			    			oldTotalShipping = oldTotalShipping.add(groupSuborder.getTotalShipping());//已支付的运费
			    		}
						//查询所有订单商品行
			    		List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class, Cnd.where("subOrderId","=",groupSuborder.getSubOrderId()));
			    		if(null != groupSuborderitemList && groupSuborderitemList.size() >0) {
			    			//循环订单商品行这里先加进去，处理在其他地方处理
			    			
			    			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
			    				Suborderitem suborderitem = new Suborderitem();
			    				BeanUtils.copyProperties(suborderitem, groupSuborderitem);
			    				if(isFirst) {
			    					suborderitem.setShipping(suborder.getTotalShipping());
			    					isFirst = false;
			    				} else {
			    					suborderitem.setShipping(BigDecimal.ZERO);
			    				}
			    				suborderitem.setSubOrderId(suborder.getSubOrderId());
			    				suborderitem.setOrderId(orders.getOrderId());
			    				ordersFacade.listAdd(suborderitems, suborderitem);
							}
			    			
			    		}
					}
			    }
			    //将团订单改为2 已开团
			    groupOrders.setStatus(2);
			    dao.update(groupOrders);
			}
			//处理阶梯价影响的suborderitems suborder orders 运费不修改
			Map<Long,BigDecimal> skuLadderPriceMap = new HashMap<>();
			BigDecimal subRealPrice = suborder.getRealPrice().add(oldTotalShipping.subtract(groupBuy.getTotalShipping()));
			delSubOrderItem(suborderitems,suborder,orders,skuLadderPriceMap);
			
			//处理阶梯价影响的groupSuborderitems groupSuborder groupOrder
			delGroupSuborderItem(groupOrdersList,skuLadderPriceMap);
			
			if(subRealPrice.compareTo(suborder.getRealPrice())>0) {//已支付的运费大于0且大于团运费
				BigDecimal musPay = subRealPrice.subtract(suborder.getRealPrice());//优惠的运费
				if(musPay.compareTo(suborder.getThirdPay())>0) {//优惠大于第三方支付
					suborder.setCashPay(suborder.getCashPay().subtract(musPay.subtract(suborder.getThirdPay())));
					suborder.setThirdPay(BigDecimal.ZERO);
				} else {
					suborder.setThirdPay(suborder.getThirdPay().subtract(musPay));
				}
			}
			
			//将数据插入数据库
			ordersFacade.dealOrders(orders, suborder, suborderitems);
			// 更新状态
			groupBuy.setOrderStatus(1);
			groupBuy.setOrderCreateTime(new Date());
			groupBuyService.update(groupBuy);
			subIds.add(suborder.getSubOrderId());
			return true;
		}else {
			if(groupBuy.getOrderStatus()!=-1) {
				groupBuy.setStatus(-1);
				groupBuy.setOrderStatus(-1);
				groupBuy.setCloseReason("无一起购有效订单数据");
				groupBuyService.update(groupBuy);
			}
			return false;
		}
	}
	/**
	 * 处理每一个人下的支付的groupOrder，groupSuborder，groupSuborderItem
	 * @param groupOrdersList
	 * @param skuLadderPriceMap 
	 */
	private void delGroupSuborderItem(List<GroupOrders> groupOrdersList, Map<Long, BigDecimal> skuLadderPriceMap) {
		if(groupOrdersList!=null && groupOrdersList.size()>0) {
			for (GroupOrders groupOrders : groupOrdersList) {
				//查询每个人的子订单
				List<GroupSuborder> groupSuborderList = dao.query(GroupSuborder.class, Cnd.where("orderId","=",groupOrders.getOrderId()));
				if(groupSuborderList!=null && groupSuborderList.size()>0) {
					BigDecimal saveProductAmonut= BigDecimal.ZERO;	//商品节省金额
					for (GroupSuborder groupSuborder : groupSuborderList) {
						BigDecimal nowRealPrice =BigDecimal.ZERO;
						//每一个购买的商品信息
						List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class, Cnd.where("subOrderId","=",groupSuborder.getSubOrderId()));
						if(groupSuborderitemList !=null && groupSuborderitemList.size()>0) {
							for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
								if(skuLadderPriceMap.containsKey(groupSuborderitem.getSkuId())) {
									//设置新的内购价
									groupSuborderitem.setNowPrice(skuLadderPriceMap.get(groupSuborderitem.getSkuId()));
									//设置新的realPay
									groupSuborderitem.setNowRealPay(groupSuborderitem.getNowPrice().multiply(NumberUtil.toBigDecimal(groupSuborderitem.getNumber())));
									dao.update(groupSuborderitem);
								}
								//每一个商品新的内购价*数量相加
								nowRealPrice = nowRealPrice.add(groupSuborderitem.getNowRealPay());
								//每一个商品节省金额相加
								saveProductAmonut =saveProductAmonut.add(groupSuborderitem.getRealPay().subtract(nowRealPrice));
							}
						}
						//设置每个订单实付金额
						groupSuborder.setAfterOpenRealPrice(nowRealPrice.add(groupSuborder.getTotalShipping()));
						dao.update(groupSuborder);
					}
					//重新设置节省金额
					groupOrders.setSaveAmount(saveProductAmonut);
					dao.update(groupOrders);
				}
			}
		}
	}



	/**
	 * 阶梯价处理
	 * @param suborder
	 * @param suborderitems
	 * @param orders 
	 * @param suborder 
	 * @param skuLadderPriceMap 
	 */
	private void delSubOrderItem(List<Suborderitem> suborderitems, Suborder suborder, Orders orders, Map<Long, BigDecimal> skuLadderPriceMap) {
		BigDecimal realPrice = BigDecimal.ZERO;//实付金额
		if(suborderitems!=null && !suborderitems.isEmpty()) {
			for (Suborderitem subitem : suborderitems) {
				BigDecimal ladderPrice = productLadderService.getPriceBySkuidAndPrice(subitem.getSkuId(), subitem.getNumber());
				if(ladderPrice!=null) {
					skuLadderPriceMap.put(subitem.getSkuId(), ladderPrice);
					//修改suborderitem
					subitem.setRealPay(ladderPrice.multiply(NumberUtil.toBigDecimal(subitem.getNumber())));
					subitem.setInternalPurchasePrice(ladderPrice);
				}else {
					ProductSpecificationsVo sku = specificationsService.findByIdCache(subitem.getSkuId(), null);
					if(sku!=null) skuLadderPriceMap.put(sku.getId(), sku.getInternalPurchasePrice());
				}
				realPrice=realPrice.add(subitem.getRealPay());
			}
		}
		if (suborder!=null) {
			suborder.setRealPrice(realPrice.add(suborder.getTotalShipping()));
		}
		if(orders!=null) {
			orders.setRealPrice(suborder.getRealPrice());
		}
	}

	@Override
	public GroupAndOrderVO findGroupAndOrder(Long groupId, Long userId) {
		GroupAndOrderVO result = new GroupAndOrderVO();
		List<GroupOrders> groupOrdersList = groupOrdersService.getByGroupIDAndUserId(groupId, userId);
		GroupBuy groupBuy = groupBuyService.getGroupBuyById(groupId + "");
		result.setGroupBuy(groupBuy);// 增加团信息
		if (null != groupOrdersList) {
			result.setGroupOrders(groupOrdersList.get(0));// 增加团订单信息
			// 查询所有子订单
			List<GroupSuborder> groupSuborderList = dao.query(GroupSuborder.class,
					Cnd.where("orderId", "=", groupOrdersList.get(0).getOrderId()));
			if (null != groupSuborderList && groupSuborderList.size() != 0) {
				for (GroupSuborder groupSuborder : groupSuborderList) {
					// 查询所有订单商品行
					List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class,
							Cnd.where("subOrderId", "=", groupSuborder.getSubOrderId()));
					if (null != groupSuborderitemList && groupSuborderitemList.size() > 0) {
						result.setGroupSuborderitems(groupSuborderitemList);
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<GroupAndOrderVO> findAllGroupAndOrder(Long groupId) {
		List<GroupAndOrderVO> list = new ArrayList<GroupAndOrderVO>();
		GroupBuy groupBuy = groupBuyService.getGroupBuyById(groupId + "");
		
		List<GroupOrders> groupOrdersList = groupOrdersService.getByGroupIDAndUserId(groupId, null);

		if (null != groupOrdersList) {
			for (GroupOrders groupOrders : groupOrdersList) {
				GroupAndOrderVO result = new GroupAndOrderVO();
				result.setGroupBuy(groupBuy);// 增加团信息
				GroupBuyUser groupBuyUser = dao.fetch(GroupBuyUser.class,
						Cnd.where("group_id", "=", groupBuy.getId())
						   .and("user_id", "=",groupOrders.getUserId()));
				
				result.setGroupBuyUser(groupBuyUser);
				result.setGroupOrders(groupOrders);// 增加团订单信息
				// 查询所有子订单
				List<GroupSuborder> groupSuborderList = dao.query(GroupSuborder.class,
						Cnd.where("orderId", "=", groupOrders.getOrderId()));
				if (null != groupSuborderList && groupSuborderList.size() != 0) {
					for (GroupSuborder groupSuborder : groupSuborderList) {
						// 查询所有订单商品行
						List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class,
								Cnd.where("subOrderId", "=", groupSuborder.getSubOrderId()));
						if (null != groupSuborderitemList && groupSuborderitemList.size() > 0) {
							result.setGroupSuborderitems(groupSuborderitemList);
						}
					}
				}
				list.add(result);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public boolean refundShipp(Long groupId) throws IllegalAccessException, InvocationTargetException {

		/////////////////////////////////////////////////////////////////////////////
		List<GroupOrders> groupOrdersList = groupOrdersService.getByGroupIdAndStatus(groupId,2);
		if (null != groupOrdersList && groupOrdersList.size() > 0) {
			log.info("退团购运费开始 团id:"+groupId);
			for (GroupOrders groupOrders : groupOrdersList) {
				// 需要退的运费
				groupOrders.setStatus(3);
				groupOrdersService.update(groupOrders);
				BigDecimal refundShippingAll = groupOrders.getTotalShipping().subtract(groupOrders.getNowShipping());
				if (refundShippingAll.compareTo(BigDecimal.ZERO) > 0 || NumberUtil.isGreaterZero(groupOrders.getSaveAmount())) {
					//查询所有子订单
					List<GroupSuborder> groupSuborderList = dao.query(GroupSuborder.class, Cnd.where("orderId","=",groupOrders.getOrderId()));
					if (null != groupSuborderList && groupSuborderList.size() > 0) {
						for(int i=0;i<groupSuborderList.size();i++) {
							GroupSuborder groupSuborder = groupSuborderList.get(i);
							// 退款金额最多不超过支付运费
							BigDecimal refundShipping = BigDecimal.ZERO;
							if(refundShippingAll.compareTo(groupSuborder.getTotalShipping())>0) {
								refundShipping=groupSuborder.getTotalShipping();
								refundShippingAll = refundShippingAll.subtract(groupSuborder.getTotalShipping());
							} else {
								refundShipping=refundShippingAll;
								refundShippingAll=BigDecimal.ZERO;				
							}

							//退款金额 （运费+退的商品金额）
							BigDecimal refundAmount = BigDecimal.ZERO;
							refundAmount = groupSuborder.getRealPrice().subtract(groupSuborder.getAfterOpenRealPrice());							
							refundAmount = refundAmount.add(refundShipping);	
							
							//退款金额》0
							if(NumberUtil.isGreaterZero(refundAmount)) {

								// 金额及内购券返还
								Map<String, Object> comMap = new HashMap<String, Object>();

								comMap.put("empId", groupOrders.getUserId());
								// comMap.put("absCash", refundShipping);
								comMap.put("absTicket", BigDecimal.ZERO);
								comMap.put("key", groupSuborder.getSubOrderId());
								comMap.put("updUser", "开团成功自动退运费");
								/**
								 * 退款订单金额原路退款
								 */
								Payment payment = null;
								BigDecimal retrunCashPay = BigDecimal.ZERO;
								BigDecimal retrunThirdPay = BigDecimal.ZERO;
								BigDecimal cashThirdPay = BigDecimal.ZERO;
								boolean hasPayment= false;
								// 订单中含有第三方支付金额
								if (NumberUtil.isGreaterZero(groupSuborder.getThirdPay())) {
									// 第三方支付方式 微信或支付宝 原路返还
									if ("wxpay".equals(groupSuborder.getThirdType())
											|| "zhifubao".equals(groupSuborder.getThirdType())) {
										
										payment = paymentService.getByTradeNo(groupSuborder.getThirdType(),
												groupSuborder.getThirdNo());
										if (payment == null || StringUtils.isEmpty(payment.getAppId())) {
											// 全部退还到现金券
											comMap.put("absCash", refundAmount);
											cashThirdPay = refundAmount;
											BigDecimal thirdPay = groupSuborder.getThirdPay();
											if(thirdPay.compareTo(refundAmount)>0) {
												retrunThirdPay=refundAmount;
												retrunCashPay=BigDecimal.ZERO;
											} else {
												retrunThirdPay=thirdPay;
												retrunCashPay=refundAmount.subtract(thirdPay);
											}
										} else {
											hasPayment=true;
											// 现金券抵扣部分
//												retrunCashPay = groupSuborder.getCashPay() == null ? BigDecimal.ZERO
//														: groupSuborder.getCashPay();
											// 第三方支付金额
											BigDecimal thirdPay = groupSuborder.getThirdPay();
											retrunCashPay = refundAmount.subtract(thirdPay);
											// 退款金额大于第三方金额
											if (NumberUtil.isGreaterZero(retrunCashPay)) {
												// 退部分现金券
												cashThirdPay = retrunCashPay;
												comMap.put("absCash", retrunCashPay);
												retrunThirdPay = thirdPay;
											} else {
												// 不退现金券只退还退款金额
												retrunCashPay = BigDecimal.ZERO;
												cashThirdPay = retrunCashPay;
												comMap.put("absCash", retrunCashPay);
												retrunThirdPay = refundAmount;
											}
										}
									} else {
										// 全部退还到现金券
										comMap.put("absCash", refundAmount);
										cashThirdPay=refundAmount;

										BigDecimal thirdPay = groupSuborder.getThirdPay();
										if(thirdPay.compareTo(refundAmount)>0) {
											retrunThirdPay=refundAmount;
											retrunCashPay=BigDecimal.ZERO;
										} else {
											retrunThirdPay=thirdPay;
											retrunCashPay=refundAmount.subtract(thirdPay);
										}
									}
								} else {
									// 全部退还到现金券
									retrunThirdPay = BigDecimal.ZERO;
									retrunCashPay = refundAmount;
									cashThirdPay=retrunCashPay;
									comMap.put("absCash", retrunCashPay);
								}

								if(NumberUtil.isGreaterZero(cashThirdPay)) {
									String ticketResult = HttpClientUtil.sendHttpRequest("post", qiyeApiUrl
											+ "api/cancelOrder", comMap);
									
									ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);
									if (acTicket.isSuccess()) {
										if(NumberUtil.isGreaterZero(cashThirdPay)){
											Payment rp = new Payment();
											rp.setOutTradeNo(dbUtils.CreateID()+"");
											rp.setOrderId(null);
											rp.setSubOrderId(groupSuborder.getSubOrderId());
											rp.setStatus(2);
											rp.setCreateTime(new Date());
											rp.setWay("pingtaiyue");
											rp.setTradeNo(acTicket.getData().toString());
											rp.setPayType(-3);
											rp.setOrderType(4);
											rp.setTotalFee(cashThirdPay);
											paymentService.save(rp);
										}
										log.info("用户：" + groupOrders.getUserId() + " 自动退运费成功，退款：" + refundShipping + " 元");
									} else {
										log.info("用户：" + groupOrders.getUserId() + " 自动退运费失败，退款：" + refundShipping + " 元");
									}
								}
								
								// 支付宝、微信原路返还
								if (hasPayment && NumberUtil.isGreaterZero(retrunThirdPay)) {
									try {

										Payment rp = new Payment();
										rp.setOutTradeNo(dbUtils.CreateID() + "");
										rp.setOrderId(null);
										rp.setSubOrderId(groupSuborder.getSubOrderId());
										rp.setStatus(0);
										rp.setCreateTime(new Date());
										rp.setWay(payment.getWay());
										rp.setAppId(payment.getAppId());
										rp.setPayType(-3);
										rp.setOrderType(4);
										rp.setTotalFee(retrunThirdPay);
										rp.setNote(payment.getOutTradeNo());
										if ("wxpay".equals(payment.getWay())) {
											if (wxPay.payRefund(payment.getOutTradeNo(), payment.getTotalFee(),
													payment.getAppId(), retrunThirdPay, rp.getOutTradeNo())) {
												/*rp.setTradeNo(payment.getTradeNo());
												rp.setStatus(2);*/
											}
										} else if ("zhifubao".equals(payment.getWay())) { // &&//
																							// AlipayService.APP_ID_NEW.equals(payment.getAppId())
											alipay.refund(payment.getOutTradeNo(), payment.getTradeNo(), rp.getOutTradeNo(),
													retrunThirdPay, notifyUrl);
										}
										paymentService.save(rp);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								
								// 保留最新运费
								if(NumberUtil.isGreaterZero(retrunThirdPay)) {
									groupSuborder.setThirdPay(groupSuborder.getThirdPay().subtract(retrunThirdPay));
								}
								if(NumberUtil.isGreaterZero(retrunCashPay)) {
									if(groupSuborder.getCashPay()!=null) {
										groupSuborder.setCashPay(groupSuborder.getCashPay().subtract(retrunCashPay));
									}
								}
								groupSuborder.setTotalShipping(groupSuborder.getTotalShipping().subtract(refundShipping));
								groupSuborder.setTotalProduct(groupSuborder.getTotalProduct().subtract(refundAmount));
								groupSuborder.setRealPrice(groupSuborder.getRealPrice().subtract(refundAmount));
								dao.update(groupSuborder);
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> postageStrategy(Long groupId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer cnt = 0;
		GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
		if(groupBuyVo!=null){
			Shop shop = shopService.getShopSettingById(groupBuyVo.getShopId());
			BigDecimal amount = groupBuyVo.getTotalPrice();
			if(amount==null) amount= BigDecimal.ZERO;
			List<GroupOrders> groupOrdersList = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupId).and("status","=",1).orderBy("createTime", "asc"));
			if(groupBuyVo!=null &&  null != groupOrdersList && groupOrdersList.size() != 0 ) {
				for (GroupOrders groupOrders : groupOrdersList) {
				//总数量
				List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class, Cnd.where("orderId","=",groupOrders.getOrderId()));
	    		if(null != groupSuborderitemList && groupSuborderitemList.size() >0) {
	    			//循环订单商品行这里先加进去，处理在其他地方处理
	    			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
	    				cnt+=groupSuborderitem.getNumber();
					}
	    		}
			}
			String cityCode = groupBuyVo.getAid().substring(0, 4) + "00";
			// 省地理位置编码
			String provinceCode = groupBuyVo.getAid().substring(0, 2) + "0000";
			ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(shop.getSupplierId());
			if(shippingTemplate==null){
				result.put("priceNumMatchExpress", null);
				result.put("buyNumMatchExpress", null);
			}else {
					List<ShippingFreeRule> frees = shippingFreeRuleService.findByTemplate(shippingTemplate.getId());
					ShippingFreeRule rule = null;
					for (ShippingFreeRule shippingFreeRule : frees) {
						if (shippingFreeRule.getAreasCode().contains(cityCode)
								|| shippingFreeRule.getAreasCode().contains(provinceCode)) {
							rule = shippingFreeRule;
							break;
						}
					}
					if (rule != null) {
						if ("1".equals(rule.getCountTypeDes())) {
							// 1:按件数
							BigDecimal goods = NumberUtil.toBigDecimal(cnt);
							// 按金额包邮
							// ssDesc = " 全场满" + rule.getParam1().intValue() + "件包邮";
							if (goods.compareTo(rule.getParam1()) < 0) {
								result.put("priceNumMatchExpress", null);
								result.put("buyNumMatchExpress", rule.getParam1().subtract(goods));
								// msg = "再凑" + rule.getParam1().subtract(goods)+ "件即可包邮哦";
							}
						} else if ("2".equals(rule.getCountTypeDes())) {
							// 按金额包邮
							// ssDesc = " 全场满" + rule.getParam2() + "元包邮";
							if (amount.compareTo(rule.getParam2()) < 0) {
								result.put("priceNumMatchExpress", rule.getParam2().subtract(amount));
								result.put("buyNumMatchExpress", null);
								// msg = "再凑" + rule.getParam2().subtract(amount) + "元即可包邮哦";
							}
						} else if ("3".equals(rule.getCountTypeDes())) {
							// 条件叠加方式
							BigDecimal goods = NumberUtil.toBigDecimal(cnt);
							// 按金额包邮
							// ssDesc = " 全场满" + rule.getParam1().intValue() + "件,且 " + rule.getParam2() + "
							// 元以上 包邮";
							if (goods.compareTo(rule.getParam1()) < 0 && amount.compareTo(rule.getParam2()) < 0) {
								result.put("priceNumMatchExpress", rule.getParam2().subtract(amount));
								result.put("buyNumMatchExpress", rule.getParam1().subtract(goods));
								// msg = "再凑" + rule.getParam1().subtract(goods) + "件,且 " +
								// rule.getParam2().subtract(amount)+"元以上即可包邮哦";
							} else if (goods.compareTo(rule.getParam1()) < 0
									&& amount.compareTo(rule.getParam2()) >= 0) {
								result.put("priceNumMatchExpress", null);
								result.put("buyNumMatchExpress", rule.getParam1().subtract(goods));
								// msg = "再凑" + rule.getParam1().subtract(goods) + "件以上即可包邮哦";
							} else if (goods.compareTo(rule.getParam1()) >= 0
									&& amount.compareTo(rule.getParam2()) < 0) {
								result.put("priceNumMatchExpress", rule.getParam2().subtract(amount));
								result.put("buyNumMatchExpress", null);
								// msg = "再凑" + rule.getParam2().subtract(amount)+"元以上即可包邮哦";
							}
						}
					}
				}
			}
		}
		return result;
	}
	

	/**
	 * 团购订单支付成功的处理
	 * @param userId
	 * @param subOrderId
	 * @param way
	 * @param trade_no
	 * @param totalFee
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActResult payGroupSuccess(Long userId, String subOrderId,String way,String trade_no,BigDecimal totalFee) throws Exception {
		SuborderQuery query = new SuborderQuery();
		Date now = new Date();
		query.setUserId(userId);
		query.setSubOrderId(subOrderId);
		//SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
		GroupSuborder groupSuborder = suborderService.findGroupSuborderObjbyId(subOrderId);
		if (groupSuborder.getStatus() != 0) {
			return ActResult.success(null);
		}
		//List<Suborderitem> items = suborderitemService.findBySubOrderId(subOrderId);
		List<GroupSuborderitem> items = suborderitemService.findByGroupSuborederId(subOrderId);
		UserFactory user = userService.getById(userId);
		for (GroupSuborderitem item : items) {
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
					cancel(user, groupSuborder, "库存不足，系统自动取消订单",true);
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
				log.info("索引库无此商品信息");
			}
		}
		groupSuborder.setPayTime(new Date());
		groupSuborder.setStatus(1);
		if(!StringUtils.isEmpty(trade_no)) {
			groupSuborder.setThirdNo(trade_no);
		}
		if(!StringUtils.isEmpty(totalFee)) {
			groupSuborder.setThirdPay(totalFee);
		}
		if(!StringUtils.isEmpty(way)) {
			groupSuborder.setThirdType(way);
		}
		//suborderService.update(groupSuborder);
		suborderService.updateGroupSbuorder(groupSuborder);
		//更新母单状态
		//List<Suborder> list = this.suborderService.findByOrderId(groupSuborder.getOrderId());
		List<GroupSuborder> list = suborderService.findGroupSuborderbyId(groupSuborder.getOrderId());
		Boolean check = true;
		for(GroupSuborder sub : list){
			if(sub.getStatus()==0){
				check = false;
				break;
			}
		}
		//Orders order = ordersService.getById(subOrder.getOrderId());
		GroupOrders groupOrders = ordersService.findGroupOrdersById(groupSuborder.getOrderId());
		groupOrders.setStatus(1);//支付
		groupOrders.setUpdateTime(now);
		groupOrders.setUpdateBy("System");
		//ordersService.update(order);
		ordersService.updateGroupOrder(groupOrders);
		String imgroupId =null;
		try {
			imgroupId = groupOrdersService.avgFreight(groupOrders);
			if(!StringUtils.isEmpty(imgroupId)) {
				UserImGroup im= userImGroupService.getById(NumberUtil.toLong(imgroupId));
				if(im!=null) {
					List<UserImGroup> groups = new ArrayList<UserImGroup>();
					groups.add(im);
					UserFactory us = userService.getById(groupOrders.getUserId());
					String name =us.getNickName() == null ? us.getUserName() : us.getNickName();
					GroupOrders newGroupOrders = groupOrdersService.getById(groupOrders.getOrderId());
					String msg="@"+name+" 刚刚完成了一笔购物！\r\n" + "为其他团员节省了总计"+newGroupOrders.getSaveAmount()+"元~\r\n" + "感谢他为大家做出的贡献~";
					EasemobIMUtils.shoppingGroupMessage(msg, groups, us.getId(), null, name,"paySuccess");
				}
			}
		} catch (Exception e) {
			log.info("团购拆分运费异常",e);
		}
		
		return ActResult.success(imgroupId);
	}



	@Override
	public void updateOrderToPay(Payment payment) {
		//更改母单及子弹状态
		if(!StringUtils.isEmpty(payment.getOrderId())){
			GroupOrders groupOrder = ordersService.findGroupOrdersById(payment.getOrderId());
			if(groupOrder!=null) {
				List<GroupSuborder> list = suborderService.findGroupSuborderbyId(payment.getOrderId());
				for(GroupSuborder groupSuborder : list){
					try {
						this.payGroupSuccess(groupOrder.getUserId(), groupSuborder.getSubOrderId(),payment.getWay(),payment.getTradeNo(),payment.getTotalFee());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else if(!StringUtils.isEmpty(payment.getSubOrderId())){
			//更新子单
			GroupSuborder groupSuborder = suborderService.findGroupSuborderObjbyId(payment.getSubOrderId());
			if(StringUtils.isEmpty(groupSuborder)){
				return;
			}
			GroupOrders groupOrder = ordersService.findGroupOrdersById(groupSuborder.getOrderId());
			if(StringUtils.isEmpty(groupOrder)){
				return;
			}
			try {
				this.payGroupSuccess(groupOrder.getUserId(), groupSuborder.getSubOrderId(),payment.getWay(),payment.getTradeNo(),payment.getTotalFee());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		payment.setStatus(2);
		payment.setUpdateTime(new Date());
		paymentService.update(payment);
	}



	@Override
	public void masterTakeOrder(Long groupId,UserFactory userFactory) {
		GroupBuyVo vo = groupBuyService.getById(groupId);
		log.debug("get group buy:" + vo);
		if(vo!=null) {
			vo.setOrderStatus(4);	// 已发货
			vo.setOrderSendTime(new Date());
			
			groupBuyService.update(vo);
			
			UserImGroup im= userImGroupService.getById(NumberUtil.toLong(vo.getIm_groupId()));
			if(im!=null) {
				List<UserImGroup> groups = new ArrayList<UserImGroup>();
				groups.add(im);
				String msg="@所有人 团长已签收。\r\n" + "请联系团长领取您的商品。\r\n" + "别忘了感谢团长辛苦收货哦~";
				EasemobIMUtils.shoppingGroupMessage(msg, groups, null, null, null,"confirmReceive");
			}
			
			
			BigDecimal prize=entParamCodeService.getGroupMasterPrize();
			if(NumberUtil.isGreaterZero(prize)) {
				BigDecimal allPrize=BigDecimal.ZERO;
				List<GroupOrders> groupOrdersList = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupId).and("status",">=",2).orderBy("createTime", "asc"));
				for (GroupOrders groupOrders : groupOrdersList) {
					allPrize=allPrize.add(groupOrders.getRealPrice());
				}
				
				// 以实付金额的%计算返还
				allPrize = allPrize.multiply(prize).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN);

				if(NumberUtil.isGreaterZero(allPrize)) {
					String note = "团长鼓励基金，感谢您为大家的辛勤服务，平台奖励内购券："+allPrize.doubleValue();
					Map<String,Object> paramMap=new HashMap<String,Object>();
					paramMap.put("limitm", allPrize);
					paramMap.put("desrc", note);
					paramMap.put("empId", userFactory.getId());
					paramMap.put("empName", userFactory.getNickName());
					paramMap.put("updName","auto");
	
					HttpClientUtil.sendHttpRequest("post", qiyeApiUrl+"api/fisrtPrize", paramMap);
				}
			}
		}
	}


	/**
	 * 验证团购订单
	 */
	@Override
	public ActResult<Object> checkGroupOrder(Long groupId) {
		//查询已支付的订单集合
		List<GroupOrders> groupOrdersList = dao.query(GroupOrders.class, Cnd.where("group_id","=",groupId).and("status","=",1).orderBy("createTime", "asc"));
		
		if(null!=groupOrdersList && groupOrdersList.size()>0) {
			Map<String,Object> checkMap = new HashMap<String,Object>();
			Map<Long,Integer> skuMap = new HashMap<Long,Integer>();	
			List<GroupBuyProductsVo> limitedNumProduct =  new ArrayList<>();
			List<GroupBuyProductsVo> limitedPriceProduct =  new ArrayList<>();
			for (GroupOrders groupOrders : groupOrdersList) {
	    		List<GroupSuborderitem> groupSuborderitemList = dao.query(GroupSuborderitem.class, Cnd.where("order_id","=",groupOrders.getOrderId()));
	    		if(null != groupSuborderitemList && groupSuborderitemList.size() >0) {
	    			//循环订单商品行这里先加进去，处理在其他地方处理
	    			for (GroupSuborderitem groupSuborderitem : groupSuborderitemList) {
	    				if(skuMap.containsKey(groupSuborderitem.getSkuId())) {
	        				skuMap.put(groupSuborderitem.getSkuId(), skuMap.get(groupSuborderitem.getSkuId()) + groupSuborderitem.getNumber());
	        			}else {
	        				skuMap.put(groupSuborderitem.getSkuId(), groupSuborderitem.getNumber());
	        			}
					}
	    		}
			}
			for (Long skuId : skuMap.keySet()) {
				ProductSpecificationsVo sku = this.specificationsService.findByIdCache(skuId, null);
				Product p = productService.findById(sku.getProductId(),false);
				//当前团购商品阶梯价
				BigDecimal groupLadderPrice = productLadderService.getPriceBySkuidAndPrice(skuId, skuMap.get(skuId));
				if(groupLadderPrice==null) groupLadderPrice = sku.getInternalPurchasePrice();
				//阶梯价集合
				List<ProductLadder> productLadderList = productLadderService.getListBySkuid(sku.getId());
				
				BigDecimal ladderPrice = BigDecimal.ZERO;
				Integer bestPriceNum = 0;
				if(null!=productLadderList && productLadderList.size()>0) {
					Collections.sort(productLadderList, new Comparator<ProductLadder>() {
						public int compare(ProductLadder o1, ProductLadder o2) {
			                return o1.getNum().compareTo(o2.getNum());
			            }
			     	});
					ProductLadder productLadder = null;
					for (ProductLadder pLadder : productLadderList) {
						if (skuMap.get(skuId) < pLadder.getNum()) {
							productLadder = pLadder;
							break;
						} 
					}
					if(productLadder!=null) {
						 if(productLadder.getType()==1) {
							 ladderPrice = productLadder.getPrice().multiply(new BigDecimal(0.1)).multiply(sku.getPrice());
							 ladderPrice = ladderPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
						 }else {
							 ladderPrice = productLadder.getPrice();
						 }
						 bestPriceNum = productLadder.getNum();
					}
				}
				//起售限制
				if(skuMap.get(skuId)<sku.getMinLimitNum()) {
					GroupBuyProductsVo groupBuyProductsVo = new GroupBuyProductsVo();
					makeGroupBuyProductsVo(skuMap, skuId, sku, p, ladderPrice, bestPriceNum, groupBuyProductsVo);
					if(limitedNumProduct.contains(groupBuyProductsVo)){
					}else {
						limitedNumProduct.add(groupBuyProductsVo);
					}
				}
				//阶梯价验证
				//当前团内阶梯价存在 并大于0 最低阶梯价大于0且对应的数量大于0
				if(null!=groupLadderPrice && NumberUtil.isGreaterZero(groupLadderPrice) && NumberUtil.isGreaterZero(ladderPrice) && NumberUtil.isGreaterZero(bestPriceNum)) {
					if(groupLadderPrice.compareTo(ladderPrice)>0) {//团内阶梯价大于最低阶梯
						GroupBuyProductsVo groupBuyProductsVo = new GroupBuyProductsVo();
						makeGroupBuyProductsVo(skuMap, skuId, sku, p, ladderPrice, bestPriceNum, groupBuyProductsVo);
						if(limitedPriceProduct.contains(groupBuyProductsVo)){
						}else {
							limitedPriceProduct.add(groupBuyProductsVo);
						}
					}
				}
				//包邮信息
				Map<String, Object> postageStrategy = postageStrategy(groupId);
				checkMap.put("limitedNumProduct", limitedNumProduct);
				checkMap.put("limitedPriceProduct", limitedPriceProduct);
				checkMap.put("priceNumMatchExpress", postageStrategy.get("postageStrategy"));
				checkMap.put("buyNumMatchExpress", postageStrategy.get("buyNumMatchExpress"));
				GroupBuyVo groupBuyVo = groupBuyService.getById(groupId);
				BigDecimal saveProductPrice = groupBuyVo.getAllTotalPrice().subtract(groupBuyVo.getTotalPrice());//省掉的商品总价
				BigDecimal saveTotalShippingPrice = groupBuyVo.getAllTotalShipping().subtract(groupBuyVo.getTotalShipping());//省掉的运费
				checkMap.put("totalSavePrice", saveProductPrice.add(saveTotalShippingPrice));
			 }
			return ActResult.success(checkMap);
		}else {
			return ActResult.fail("暂无订单");
		}
		
	}



	private void makeGroupBuyProductsVo(Map<Long, Integer> skuMap, Long skuId, ProductSpecificationsVo sku, Product p,
			BigDecimal ladderPrice, Integer bestPriceNum, GroupBuyProductsVo groupBuyProductsVo) {
		//商品id
		groupBuyProductsVo.setProductId(p.getId());
		//规格id
		groupBuyProductsVo.setSkuId(skuId);
		//商品名称
		groupBuyProductsVo.setProductName(p.getFullName());
		//电商价
		groupBuyProductsVo.setMarketPrice(sku.getMarketPrice());
		//主图
		groupBuyProductsVo.setImage(sku.getMainImage());
		//内购券
		groupBuyProductsVo.setMaxFucoin(sku.getMaxFucoin());
		//内购价
		groupBuyProductsVo.setInternalPurchasePrice(sku.getInternalPurchasePrice());
		//规格值
		groupBuyProductsVo.setItemValues(sku.getItemValues());
		//起售数量
		groupBuyProductsVo.setMinLimitNum(sku.getMinLimitNum());
		//团内已购数量
		groupBuyProductsVo.setGroupBuyProductNum(skuMap.get(skuId));
		//最低阶梯价
		groupBuyProductsVo.setBestPrice(ladderPrice==null?BigDecimal.ZERO:ladderPrice);
		//最低价对应的数量
		groupBuyProductsVo.setBestPriceNum(bestPriceNum);
	}
}
