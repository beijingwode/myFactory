/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import com.wode.factory.model.Payment;

/**
 * App支付结果调用接口
 * @author user
 *
 */
public interface PaymentService{

	Payment create(Payment payment);
	
	
}
