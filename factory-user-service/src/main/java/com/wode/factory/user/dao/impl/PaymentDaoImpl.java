/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Payment;
import com.wode.factory.user.dao.PaymentDao;

@Repository("paymentDao")
public class PaymentDaoImpl extends BaseDao<Payment,java.lang.Long> implements PaymentDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "PaymentMapper";
	}

	@Override
	public void saveOrUpdate(Payment entity){
		if(entity.getOutTradeNo() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	@Override
	public Payment getByTradeNo(String way,String tradeNo) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("way", way);
		param.put("tradeNo", tradeNo);
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByTradeNo", param);
	}
	

}
