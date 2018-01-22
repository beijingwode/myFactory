package com.wode.factory.company.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.util.NumberUtil;
import com.wode.factory.company.dao.PaymentDao;
import com.wode.factory.company.service.PaymentService;
import com.wode.factory.model.Payment;
@Service("paymentService")
public class PaymentServiceImpl extends FactoryEntityServiceImpl<Payment> implements
		PaymentService {
	
	@Autowired
	PaymentDao paymentDao;

	@Override
	public Payment getByTradeNo(String way, String tradeNo) {
		Payment p = new Payment();
		p.setWay(way);
		p.setTradeNo(tradeNo);
		List<Payment> list =this.selectByModel(p);
		if(list!=null || !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PaymentDao getDao() {
		return paymentDao;
	}

	@Override
	public Long getId(Payment entity) {
		return NumberUtil.toLong(entity.getOutTradeNo());
	}

	@Override
	public void setId(Payment entity, Long id) {
		entity.setOutTradeNo(id+"");
	}

}
