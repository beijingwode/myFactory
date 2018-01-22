/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.ExchangeOrders;
import com.wode.factory.user.dao.ExchangeOrdersDao;
import com.wode.factory.user.service.ExchangeOrdersService;

@Service("exchangeOrdersService")
public class ExchangeOrdersServiceImpl extends FactoryEntityServiceImpl<ExchangeOrders> implements  ExchangeOrdersService{
	@Autowired
	private ExchangeOrdersDao dao;

	@Override
	public ExchangeOrdersDao getDao() {
		return dao;
	}
	@Override
	public Long getId(ExchangeOrders entity) {
		return entity.getOrderId();
	}

	@Override
	public void setId(ExchangeOrders entity, Long id) {
		if(entity!=null) {
			entity.setOrderId(id);
		}
	}
}