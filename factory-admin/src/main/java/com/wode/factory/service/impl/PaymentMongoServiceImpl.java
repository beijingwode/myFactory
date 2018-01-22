package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.mongo.MongoBaseDao;
import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.dao.PaymentMongoDao;
import com.wode.factory.model.Payment;
import com.wode.factory.service.PaymentMongoService;
@Service("paymentMongoService")
public class PaymentMongoServiceImpl extends MongoBaseService<Payment> implements PaymentMongoService {

	@Autowired
	private PaymentMongoDao paymentMongoDao;
	@Override
	public MongoBaseDao<Payment> getMongoDao() {
		return paymentMongoDao;
	}
	
	@Override
	public void save(Payment model) {
		this.getMongoDao().insert(model);
	}
}
