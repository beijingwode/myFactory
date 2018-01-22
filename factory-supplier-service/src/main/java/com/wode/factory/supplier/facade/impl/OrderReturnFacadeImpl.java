package com.wode.factory.supplier.facade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.common.db.DBUtils;
import com.wode.common.util.ActResult;
import com.wode.common.util.HttpClientUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.service.PaymentService;
import com.wode.factory.model.Payment;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.enums.OrderStatus;
import com.wode.factory.model.enums.RefundOrderStatus;
import com.wode.factory.model.enums.ReturnOrderStatus;
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.supplier.facade.OrderReturnFacade;
import com.wode.factory.supplier.service.RefundorderService;
import com.wode.factory.supplier.service.ReturnorderService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.util.Constant;

@Service("orderReturnFacade")
public class OrderReturnFacadeImpl implements OrderReturnFacade {

	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	@Autowired
	@Qualifier("returnorderService")
	private ReturnorderService returnorderService;
	@Autowired
	private RefundorderService refundorderService;
	
	@Autowired
	private PaymentService paymentService;
	@Autowired
    DBUtils dbUtils;
	
	public final static String notifyUrl=Constant.ALIPAY_NOTIFY_URL;
	static WxPayService wxPay = ServiceFactory.getWxPayService(Constant.OUTSIDE_SERVICE_URL);
	static AlipayService alipay = ServiceFactory.getAlipayService(Constant.OUTSIDE_SERVICE_URL);

	@Override
	public ActResult<String> argeeRetrun(Returnorder returnorder) {
		/////////////////////////////////////////////////////////////////////////////
		// 退货退款单 状态更新
		// 状态 2;卖家同意退款
		returnorder.setStatus(ReturnOrderStatus.SELLER_ARGEE.getValue());
		// 更新时间
		returnorder.setUpdateTime(new Date());
		returnorderService.update(returnorder);
		/////////////////////////////////////////////////////////////////////////////
		// 订单状态更新
		Suborder suborder = suborderService.getById(returnorder.getSubOrderId());
		suborder.setStatus(OrderStatus.AGREE_RETURN.getValue());
		// 更新时间
		suborder.setUpdateTime(new Date());
		suborderService.update(suborder);
		return new ActResult<String>();
	}

	@Transactional
	@Override
	public ActResult<String> deal(Long returnOrderId, Long refundOrderId, Integer action, Integer type, String reason, String updName,String returnedAddress) throws Exception {
		ActResult<String> ar = new ActResult<String>();
		Suborder suborder = null;
		if(type == 2){//处理只退款的订单
			Refundorder refundorder = refundorderService.getById(refundOrderId);
			refundorder.setStatus(getStatus(action, type));
			//更新时间
			refundorder.setUpdateTime(new Date());
			refundorderService.update(refundorder);
			/////////////////////////////////////////////////////////////////////////////
			suborder = suborderService.getSuborderByRefundOrderId(refundOrderId);
			suborder.setStatus(getOrderStatus(refundorder.getStatus(),type));
			if(refundorder.getStatus() == RefundOrderStatus.REFUND_FINISHED.getValue()){//触发退款操作
				refund(refundorder, null, suborder, refundorder.getUserId(), updName, ar);
			}
		}else{
			//退货单
			Returnorder returnorder = returnorderService.getById(returnOrderId);
			// 退货退款单 状态更新
			// 状态 2;卖家同意退款
			returnorder.setStatus(getStatus(action, type));
			// 更新时间
			returnorder.setUpdateTime(new Date());
			returnorderService.update(returnorder);
			suborder = suborderService.getById(returnorder.getSubOrderId());
			suborder.setStatus(getOrderStatus(returnorder.getStatus(),type));
			if(!StringUtils.isNullOrEmpty(returnedAddress)){
				suborder.setReturnedAddress(returnedAddress);
			}
            if(returnorder.getStatus() == ReturnOrderStatus.RETURN_SUCCESS.getValue()){//触发退款操作
            	Refundorder refundorder = refundorderService.getById(suborder.getRefundOrderId());
            	refundorder.setStatus(RefundOrderStatus.REFUND_FINISHED.getValue());
            	refundorderService.update(refundorder);
            	
            	refund(refundorder, returnorder, suborder, refundorder.getUserId(), updName, ar);
			}
		}
		/////////////////////////////////////////////////////////////////////////////
		// 订单状态更新
		if(!StringUtils.isNullOrEmpty(reason)){
			suborder.setRefuseNote(reason);
		}
		// 更新时间
		suborder.setUpdateTime(new Date());
		suborderService.update(suborder);
		return ar;
	}

	/**
	 * 获取订单状态
	 * @param action
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	private Integer getStatus(Integer action, Integer type) throws Exception{
		//action 1 同意 2 拒绝    type 1.退货申请 2.退款3.同意退款（退款）
		if(action == 1){
			if(type == 1){//退货订单同意
				return ReturnOrderStatus.SELLER_ARGEE.getValue();
			}else if(type == 2){
				return RefundOrderStatus.REFUND_FINISHED.getValue();
			}else if(type == 3){
				return ReturnOrderStatus.RETURN_SUCCESS.getValue();
			}
		}else if(action == 2){
			if(type == 1){//退货订单拒绝
				return ReturnOrderStatus.SELLER_REFUSE.getValue();
			}else if(type == 2){
				return RefundOrderStatus.SUPPLIER_REFUSE.getValue();
			}else if(type == 3){
				return ReturnOrderStatus.RETURN_RECEIVE_REFUSE.getValue();
			}
		}
		throw new Exception("参数错误");
	}
	private Integer getOrderStatus(Integer status,Integer type) throws Exception{
		if(type == 2){
			if(status == RefundOrderStatus.SUPPLIER_REFUSE.getValue()){
				return OrderStatus.REFUSE_REFUND.getValue();//拒绝退款
			}
			if(status == RefundOrderStatus.REFUND_FINISHED.getValue()){
				return OrderStatus.REFUND_END.getValue();//同意退款;
			}
		}else{
			if(status == ReturnOrderStatus.SELLER_ARGEE.getValue()){
				return OrderStatus.AGREE_RETURN.getValue();
			}
			
			if(status == ReturnOrderStatus.RETURN_SUCCESS.getValue()){
				return OrderStatus.RETURN_END.getValue();
			}
			if(status == ReturnOrderStatus.SELLER_REFUSE.getValue()){
				return OrderStatus.REFUSE_RETURN.getValue();//拒绝退货
			}
			
			if(status == ReturnOrderStatus.RETURN_RECEIVE_REFUSE.getValue()){
				return OrderStatus.REFUSE_REFUND.getValue();//拒绝退货
			}
		}
		throw new Exception("参数错误");
	}

	@Transactional
	private void refund(Refundorder refundorder, Returnorder returnorder, Suborder suborder, Long empId, String updName,
			ActResult<String> ar) throws Exception {
		/////////////////////////////////////////////////////////////////////////////
		//金额及内购券返还
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("empId", empId);
		//comMap.put("absCash", refundorder.getRefundPrice());
		comMap.put("absTicket", suborder.getCompanyTicket());
		comMap.put("key", suborder.getSubOrderId());
		comMap.put("updUser", updName);
		/**
		 * 退款订单金额原路退款
		 */
		Payment payment = null;
		BigDecimal cashPay = BigDecimal.ZERO;
		BigDecimal retrunThirdPay = BigDecimal.ZERO;
		BigDecimal cashThirdPay = BigDecimal.ZERO;
		//订单中含有第三方支付金额
		if(NumberUtil.isGreaterZero(suborder.getThirdPay())) {
			//第三方支付方式 微信或支付宝 原路返还
			if("wxpay".equals(suborder.getThirdType()) || "zhifubao".equals(suborder.getThirdType())) {
				payment = paymentService.getByTradeNo(suborder.getThirdType(), suborder.getThirdNo());
				if(payment==null || StringUtils.isEmpty(payment.getAppId())) {
					// 全部退还到现金券
					cashThirdPay = refundorder.getRefundPrice();
					comMap.put("absCash", refundorder.getRefundPrice());
				}else{
					//现金券抵扣部分
					cashPay = suborder.getCashPay()==null?BigDecimal.ZERO:suborder.getCashPay();
					//第三方支付金额
					BigDecimal thirdPay = suborder.getThirdPay();
					cashPay = refundorder.getRefundPrice().subtract(thirdPay);
					//退款金额大于第三方金额
					if(NumberUtil.isGreaterZero(cashPay)){
						//退部分现金券
						cashThirdPay = cashPay;
						comMap.put("absCash", cashPay);
						retrunThirdPay = thirdPay;
					}else{
						//不退现金券只退还退款金额
						comMap.put("absCash", BigDecimal.ZERO);
						retrunThirdPay = refundorder.getRefundPrice();
					}
				
				}
			}else{
				cashThirdPay = refundorder.getRefundPrice();
				// 全部退还到现金券
				comMap.put("absCash", refundorder.getRefundPrice());
			}
		}else{
			cashThirdPay = refundorder.getRefundPrice();
			// 全部退还到现金券
			comMap.put("absCash", refundorder.getRefundPrice());
		}
		
		String ticketResult = HttpClientUtil.sendHttpRequest("post", "http://" + Constant.SYSTEM_DOMAIN + "/api/cancelOrder", comMap);
		// 支付宝、微信原路返还
		if (payment != null && NumberUtil.isGreaterZero(retrunThirdPay)) {
			try {

				Payment rp = new Payment();
				rp.setOutTradeNo(dbUtils.CreateID() + "");
				rp.setOrderId(null);
				rp.setSubOrderId(suborder.getSubOrderId());
				rp.setStatus(0);
				rp.setCreateTime(new Date());
				rp.setWay(payment.getWay());
				rp.setAppId(payment.getAppId());
				rp.setPayType(-2);
				rp.setOrderType(0);
				rp.setPayConfirm(0);
				rp.setTotalFee(retrunThirdPay);
				if ("wxpay".equals(payment.getWay())) {
					if (wxPay.payRefund(payment.getOutTradeNo(), payment.getTotalFee(), payment.getAppId(), retrunThirdPay,rp.getOutTradeNo())) {
						//rp.setStatus(2);
					}
				} else if ("zhifubao".equals(payment.getWay())) { // &&// AlipayService.APP_ID_NEW.equals(payment.getAppId())
					alipay.refund(payment.getOutTradeNo(), payment.getTradeNo(), rp.getOutTradeNo(), retrunThirdPay,notifyUrl);
				}
				paymentService.save(rp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ActResult acTicket = JsonUtil.getObject(ticketResult, ActResult.class);
		if (acTicket.isSuccess()) {
			ar.setSuccess(true);
			ar.setMsg("用户：" + empId + " 退货退款成功，退款：" + refundorder.getRefundPrice() + " 元(" + suborder.getCompanyTicket() + ")，退款单ID：" + refundorder.getRefundOrderId() + " ，退货单ID：" + (returnorder != null ? returnorder.getReturnOrderId() : ""));
			if(NumberUtil.isGreaterZero(cashThirdPay)){
				Payment rp = new Payment();
				rp.setOutTradeNo(dbUtils.CreateID()+"");
				rp.setOrderId(null);
				rp.setSubOrderId(suborder.getSubOrderId());
				rp.setStatus(2);
				rp.setCreateTime(new Date());
				rp.setWay("pingtaiyue");
				rp.setTradeNo(acTicket.getData().toString());
				rp.setPayType(-2);
				rp.setOrderType(0);
				rp.setPayConfirm(0);
				rp.setTotalFee(cashThirdPay);
				paymentService.save(rp);
			}
		} else {
			throw new Exception(acTicket.getMsg());
		}
	}
}
