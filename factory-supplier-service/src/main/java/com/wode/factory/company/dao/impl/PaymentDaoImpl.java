package com.wode.factory.company.dao.impl;


import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.company.dao.PaymentDao;
import com.wode.factory.model.Payment;
@Repository("paymentDao")
public class PaymentDaoImpl extends FactoryBaseDaoImpl<Payment> implements PaymentDao {

	@Override
	public String getIbatisMapperNamesapce() {
		return "PaymentMapper";
	}
}
