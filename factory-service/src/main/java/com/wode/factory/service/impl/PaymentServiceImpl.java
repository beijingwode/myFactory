/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.Payment;
import com.wode.factory.service.PaymentService;

@Service("appPaymentService")
public class PaymentServiceImpl implements  PaymentService{

	private Dao dao;
	
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Override
	public Payment create(Payment payment){
		return dao.insert(payment);
	}
}
