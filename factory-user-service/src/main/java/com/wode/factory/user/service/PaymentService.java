/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Payment;

@Service("paymentService")
public interface PaymentService extends EntityService<Payment,Long>{

	public ActResult<String> appPaySuccess(Payment payment, Map<String,String> map);

	/**
	 * 通过外部流水号获取支付记录
	 * @param way		支付方式
	 * @param tradeNo	流水号
	 * @return
	 */
	public Payment getByTradeNo(String way,String tradeNo);
	
}
