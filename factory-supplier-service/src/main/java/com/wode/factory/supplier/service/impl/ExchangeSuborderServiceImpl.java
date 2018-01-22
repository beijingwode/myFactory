/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.supplier.dao.ExchangeSuborderDao;
import com.wode.factory.supplier.service.ExchangeSuborderService;

@Service("exchangeSuborderService")
public class ExchangeSuborderServiceImpl extends FactoryEntityServiceImpl<ExchangeSuborder> implements  ExchangeSuborderService{
	@Autowired
	private ExchangeSuborderDao dao;
	
	@Override
	public ExchangeSuborderDao getDao() {
		return dao;
	}


	@Override
	public Long getId(ExchangeSuborder entity) {
		return -1L;
	}

	@Override
	public void setId(ExchangeSuborder entity, Long id) {
		
	}


	@Override
	public Integer findReservedNumByMap(Map<String, Object> map) {
		return dao.findReservedNumByMap(map);
	}


	@Override
	public List<ExchangeProductVo> findExchangeSubOrderItemByMap(Map<String, Object> map) {
		return dao.findExchangeSubOrderItemByMap(map);
	}


	@Override
	public Integer findOrderTotalByMap(Map<String, Object> map) {
		return dao.findOrderTotalByMap(map);
	}

}