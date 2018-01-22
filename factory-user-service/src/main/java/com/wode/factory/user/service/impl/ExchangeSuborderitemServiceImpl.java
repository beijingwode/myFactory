/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.user.dao.ExchangeSuborderitemDao;
import com.wode.factory.user.service.ExchangeSuborderitemService;

@Service("exchangeSuborderitemService")
public class ExchangeSuborderitemServiceImpl extends FactoryEntityServiceImpl<ExchangeSuborderitem> implements  ExchangeSuborderitemService{
	@Autowired
	private ExchangeSuborderitemDao dao;

	@Override
	public ExchangeSuborderitemDao getDao() {
		return dao;
	}

	@Override
	public Long getId(ExchangeSuborderitem entity) {
		return entity.getSubOrderItemId();
	}

	@Override
	public void setId(ExchangeSuborderitem entity, Long id) {
		if(entity!=null) {
			entity.setSubOrderItemId(id);
		}
	}

	@Override
	public Integer getOrderCount(Long skuId) {
		Integer cnt = dao.getOrderCount(skuId);
		return cnt==null?0:cnt;
	}

	@Override
	public List<ExchangeSuborderitem> getItemsListBySubOrderId(String subOrderId) {
		return dao.getItemsListBySubOrderId(subOrderId);
	}
}