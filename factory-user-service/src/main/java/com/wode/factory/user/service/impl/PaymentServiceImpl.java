/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Payment;
import com.wode.factory.user.dao.PaymentDao;
import com.wode.factory.user.service.PaymentService;
import com.wode.factory.user.service.UserService;

@Service("paymentService")
public class PaymentServiceImpl extends BaseService<Payment,java.lang.Long> implements  PaymentService{
	@Autowired
	@Qualifier("paymentDao")
	private PaymentDao paymentDao;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public PaymentDao getEntityDao() {
		return this.paymentDao;
	}
	
	/**
	 * App支付成功后更新记录以及订单状态(status=1:支付成功待回调)
	 */
	@Override
	public ActResult<String> appPaySuccess(Payment payment, Map<String,String> params) {
		//商户订单号(针对支付平台的订单号)
		String out_trade_no = params.get("out_trade_no");
		//订单支付总价
		String total_fee = params.get("total_fee");
		if(!out_trade_no.equals(payment.getOutTradeNo()+"")){
			return ActResult.fail("参数错误");
		}
		//验证支付记录：存在已支付||存在未支付
		//payment.setTotalFee(new BigDecimal(total_fee));
		payment.setUpdateTime(new Date());
		paymentDao.update(payment);
			
		return ActResult.successSetMsg("等待确认");
	}

	@Override
	public Payment getByTradeNo(String way,String tradeNo) {
		return getEntityDao().getByTradeNo(way,tradeNo);
	}
}
