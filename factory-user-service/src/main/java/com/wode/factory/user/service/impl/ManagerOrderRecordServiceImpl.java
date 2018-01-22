/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.model.ManagerOrderRecord;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.UserFactory;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxOpenService;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.user.dao.ManagerOrderRecordDao;
import com.wode.factory.user.facade.ExchangeOrdersFacade;
import com.wode.factory.user.facade.GroupOrdersFacade;
import com.wode.factory.user.facade.OrdersFacade;
import com.wode.factory.user.query.SuborderQuery;
import com.wode.factory.user.service.ExchangeOrdersService;
import com.wode.factory.user.service.ExchangeSuborderService;
import com.wode.factory.user.service.ExchangeSuborderitemService;
import com.wode.factory.user.service.ManagerOrderRecordService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.SuborderService;
import com.wode.factory.user.service.SuborderitemService;
import com.wode.factory.user.service.UserService;
import com.wode.factory.user.util.Constant;
import com.wode.factory.user.util.ShopPushUtil;
import com.wode.factory.user.vo.SubOrderVo;

@Service("managerOrderRecordService")
public class ManagerOrderRecordServiceImpl extends FactoryEntityServiceImpl<ManagerOrderRecord> implements  ManagerOrderRecordService{

	@Autowired
	private ManagerOrderRecordDao dao;

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private SuborderService suborderService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SuborderitemService suborderitemService;
	
	@Autowired
	private OrdersFacade ordersFacade;
	
	@Autowired
	private ExchangeOrdersFacade exchangeOrdersFacade;
	
	@Autowired
	private ExchangeOrdersService exchangeOrdersService;
	
	@Autowired
	private ExchangeSuborderService exchangeSuborderService;
	
	@Autowired
	private ExchangeSuborderitemService exchangeSuborderitemService;
	
	private String qiyeApiUrl = Constant.QIYE_API_URL;
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
    DBUtils dbUtils;
	
	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);
	
	@Override
	public ManagerOrderRecordDao getDao() {
		return dao;
	}

	@Override
	public Long getId(ManagerOrderRecord entity) {
		return entity.getId();
	}

	@Override
	public void setId(ManagerOrderRecord entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public List<ManagerOrderRecord> getManagerOrderRecordList(Map<String, Object> query) {
		return dao.getManagerOrderRecordList(query);
	}

	@Transactional
	public void addManagerOrderRecord(ManagerOrderRecord managerOrderRecord, UserFactory loginUser,Integer flag) {
		if(flag == 0) {//换领匹配中订单取消
			ExchangeSuborder exchangeSuborder = exchangeSuborderService.getById(managerOrderRecord.getSubOrderId());
			List<ExchangeSuborderitem> exchangeSuborderitemList = exchangeSuborderitemService.getItemsListBySubOrderId(exchangeSuborder.getSubOrderId());
			ExchangeOrders exchangeorders = exchangeOrdersService.getById(exchangeSuborder.getOrderId());
			UserFactory user = userService.getById(exchangeorders.getUserId());
			managerOrderRecord.setProductName(exchangeSuborderitemList.get(0).getProductName());
			managerOrderRecord.setItemValues(exchangeSuborderitemList.get(0).getItemValues());
			managerOrderRecord.setSkuNumber(exchangeSuborderitemList.get(0).getNumber());
			if(user != null) {
				managerOrderRecord.setUserName(user.getNickName());
			}else {
				managerOrderRecord.setUserName(exchangeorders.getName());
			}
			managerOrderRecord.setUserId(loginUser.getId());
			managerOrderRecord.setCreateDate(new Date());
			managerOrderRecord.setOrderType(5);
			dao.save(managerOrderRecord);
			//取消换领匹配中订单
			exchangeOrdersFacade.cancel(user, exchangeSuborder.getSubOrderId(), "客服人员现场取消",-1,true);
		}else {
			
			if(flag == 1) {
				ExchangeSuborder subOrder = exchangeSuborderService.getById(managerOrderRecord.getSubOrderId());
				try {
					if(exchangeOrdersFacade.submitOrder(subOrder.getSubOrderId(),String.valueOf(loginUser.getId()))) {
						subOrder.setUpdateBy(loginUser.getUserName());//更新者
						subOrder.setUpdateTime(new Date());//更新时间
						Date now = new Date();
						subOrder.setStatus(4);
						subOrder.setCommentStatus(0);//待评价
						subOrder.setTakeTime(now);
						subOrder.setSendTime(now);
						exchangeSuborderService.update(subOrder);

					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
			SuborderQuery query = new SuborderQuery();
			query.setSubOrderId(managerOrderRecord.getSubOrderId());
			SubOrderVo subOrder = this.suborderService.findOrderDetailById(query);
			//List<Suborderitem> suborderitemList = suborderitemService.findBySubOrderId(subOrder.getSubOrderId());
			Orders orders = ordersService.getById(subOrder.getOrderId());
			UserFactory user = userService.getById(orders.getUserId());
			managerOrderRecord.setProductName(subOrder.getProductName());
			//managerOrderRecord.setItemValues(suborderitemList.get(0).getItemValues());
			//managerOrderRecord.setSkuNumber(suborderitemList.get(0).getNumber());
			if(user != null) {
				managerOrderRecord.setUserName(user.getNickName());
			}else {
				managerOrderRecord.setUserName(subOrder.getName());
			}
			managerOrderRecord.setUserId(loginUser.getId());
			managerOrderRecord.setCreateDate(new Date());;
			if(flag == 1) {
				managerOrderRecord.setOrderType(5);
			}else {
				managerOrderRecord.setOrderType(0);
			}
			dao.save(managerOrderRecord);
			
			//取消和确认后续操作处理
			if(managerOrderRecord.getOperationStatus() == 0) {//取消订单
				//普通订单和团订单(同用户取消)
				if(subOrder.getOrderType().equals("0")|| subOrder.getOrderType().equals("1") || subOrder.getOrderType().equals("4")) {
					ordersFacade.cancel(user, subOrder.getSubOrderId(), "客服人员现场取消",true);
				}else {//换领
					//更新订单状态
					Date now = new Date();
					subOrder.setStatus(-1);
					subOrder.setCancelTime(now);
					suborderService.update(subOrder);
					//换领订单取消
					exchangeOrdersFacade.cancel(user, subOrder.getSubOrderId(), "客服人员现场取消",-1,true);
				}
			}else {//确认发货
				subOrder.setUpdateBy(loginUser.getUserName());//更新者
				subOrder.setUpdateTime(new Date());//更新时间
				if (null != subOrder) {
					int beforeStatus = subOrder.getStatus();
					
					if(beforeStatus == 1) {
						if(!"1".equals(orders.getSelfDelivery()) && (subOrder.getTotalShipping().compareTo(BigDecimal.ZERO) == 1)){//非自提(换领)订单，如果已支付运费，则原路返还账户
							Map<String, Object> comMap = new HashMap<String, Object>();
							comMap.put("empId", user.getId());
							comMap.put("key", subOrder.getSubOrderId());//订单id
							comMap.put("desrc","确认发货退运费，订单编号："+subOrder.getSubOrderId());//备注+订单id 
							comMap.put("updUser", user.getUserName());//更新者
							comMap.put("absTicket", subOrder.getCompanyTicket());//内购券
							
							Payment payment = null;
							BigDecimal refundFee = BigDecimal.ZERO;//第三方
							BigDecimal cashThirdPay = BigDecimal.ZERO;//现金券支付和现金券抵扣总额
							// 已支付的场合
							if(NumberUtil.isGreaterZero(subOrder.getThirdPay())) {
								// 本期先返还微信
								if("wxpay".equals(subOrder.getThirdType()) || "zhifubao".equals(subOrder.getThirdType())) {
									// 支付宝、微信原路返还
									payment = paymentService.getByTradeNo(subOrder.getThirdType(), subOrder.getThirdNo());
									
									if(payment==null || StringUtils.isEmpty(payment.getAppId())) {//无第三方支付
										// 运费所使用的现金卷 全部 退还到现金券
										cashThirdPay = subOrder.getTotalShipping();//记录现金券抵扣(运费直接抵扣)
										comMap.put("absCash", subOrder.getTotalShipping());
									} else {//有第三方支付
										BigDecimal cashPay = subOrder.getCashPay()==null?BigDecimal.ZERO:subOrder.getCashPay();
										refundFee = subOrder.getThirdPay();
										if(refundFee.compareTo(subOrder.getTotalShipping()) == 1 || refundFee.compareTo(subOrder.getTotalShipping())==0) {
											refundFee = subOrder.getTotalShipping();
										}else {
											cashThirdPay = subOrder.getTotalShipping().subtract(refundFee);//记录现金券抵扣
										}
										//现金券抵扣部分退还
										comMap.put("absCash", cashThirdPay);
									}
								} else {
									cashThirdPay = subOrder.getTotalShipping();
									// 全部退还到现金券
									comMap.put("absCash", subOrder.getTotalShipping());
								}
							} else {
								cashThirdPay = subOrder.getTotalShipping();
								// 全部退还到现金券
								comMap.put("absCash", subOrder.getTotalShipping());
							}
							
							//退还现金卷抵扣金额
							String ticketResult = HttpClientUtil.sendHttpRequest("post", qiyeApiUrl
									+ "api/cancelOrder", comMap);		
							
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
									rp.setPayType(-3);
									rp.setOrderType(Integer.valueOf(subOrder.getOrderType()));
									rp.setTotalFee(cashThirdPay);
									paymentService.save(rp);
								}
							}
							
							// 支付宝、微信原路返还
							if(payment!= null && NumberUtil.isGreaterZero(refundFee)) {
								try {
									
									Payment rp = new Payment();
									rp.setOutTradeNo(dbUtils.CreateID()+"");
									rp.setOrderId(null);
									rp.setSubOrderId(subOrder.getSubOrderId());
									rp.setStatus(2);
									rp.setCreateTime(new Date());
									rp.setWay(payment.getWay());
									rp.setAppId(payment.getAppId());
									rp.setPayType(-3);
									rp.setOrderType(Integer.valueOf(subOrder.getOrderType()));
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
					Date now = new Date();
					subOrder.setStatus(4);
					subOrder.setCommentStatus(0);//待评价
					subOrder.setTakeTime(now);
					subOrder.setSendTime(now);
					suborderService.update(subOrder);
				}
			}
		}
	}	

}