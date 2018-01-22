/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.service;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.Payment;



public interface PaymentService extends FactoryEntityService<Payment>{
	
	/**
	 * 通过外部流水号获取支付记录
	 * @param way		支付方式
	 * @param tradeNo	流水号
	 * @return
	 */
	Payment getByTradeNo(String way, String tradeNo);
}
