/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

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
import com.wode.factory.outside.service.AlipayService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.factory.outside.service.WxPayService;
import com.wode.factory.supplier.facade.OrderRefundFacade;
import com.wode.factory.supplier.service.RefundorderService;
import com.wode.factory.supplier.service.ReturnorderService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.util.Constant;


@Service("orderRefundFacade")
public class OrderRefundFacadeImpl implements OrderRefundFacade {

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
	@Transactional
	public ActResult<String> refundToUser(Refundorder refundorder, Returnorder returnorder, Suborder suborder, Long empId, String updName, String reason) throws Exception {

		ActResult<String> ar = new ActResult<String>();
		ar.setSuccess(false);
		ar.setMsg("系统异常");

		/////////////////////////////////////////////////////////////////////////////
		//退货单 状态更新
		if (returnorder != null) {
			//状态 1：退货成功
			returnorder.setStatus(1);
			//更新时间
			returnorder.setUpdateTime(new Date());
			//修改者
			returnorder.setUpdateBy(updName);
			returnorderService.update(returnorder);
		}
		/////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////
		//退款单 状态更新
		//状态   10：退款完毕
		refundorder.setStatus(10);
		//更新时间
		refundorder.setUpdateTime(new Date());
		//修改者
		refundorder.setUpdateBy(updName);
		refundorderService.update(refundorder);
		/////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////
		//订单状态更新
		if (returnorder != null) {
			//11已退货退款完毕
			suborder.setStatus(11);
		} else {
			//12已仅退款完成
			suborder.setStatus(12);
		}

		if (!StringUtils.isNullOrEmpty(reason)) {
			suborder.setRefuseNote(reason);
		}

		//更新时间
		suborder.setUpdateTime(new Date());
		//修改者
		suborder.setUpdateBy(updName);
		suborderService.update(suborder);
		/////////////////////////////////////////////////////////////////////////////

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
			throw new Exception(ar.getMsg());
		}
		return ar;
	}

	@Override
	public ActResult<String> refuseApply(Refundorder refundorder,
	                                     Returnorder returnorder, Suborder suborder, String refuseNote,
	                                     String updName) {

		ActResult<String> ar = new ActResult<String>();
		ar.setSuccess(false);
		ar.setMsg("系统异常");

		/////////////////////////////////////////////////////////////////////////////
		//退货单 状态更新
		if (returnorder != null) {
			//状态 -1:退货失败
			returnorder.setStatus(-1);
			//更新时间
			returnorder.setUpdateTime(new Date());
			//修改者
			returnorder.setUpdateBy(updName);
			returnorderService.update(returnorder);
		}
		/////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////
		//退款单 状态更新
		//状态   3;退款异常
		refundorder.setStatus(3);
		//更新时间
		refundorder.setUpdateTime(new Date());
		//修改者
		refundorder.setUpdateBy(updName);
		refundorderService.update(refundorder);
		/////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////
		//订单状态更新
		if (returnorder != null) {
			//11已退货退款完毕
			suborder.setStatus(-11);
		} else {
			//12已仅退款完成
			suborder.setStatus(-12);
		}
		if (!StringUtils.isNullOrEmpty(refuseNote)) {
			suborder.setRefuseNote(refuseNote);
		}

		//更新时间
		suborder.setUpdateTime(new Date());
		//修改者
		suborder.setUpdateBy(updName);
		suborderService.update(suborder);
		/////////////////////////////////////////////////////////////////////////////

		ar.setSuccess(true);
		ar.setMsg("处理成功");

		return ar;
	}

}
