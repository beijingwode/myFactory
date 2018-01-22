/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.ExchangeSuborderitem;
import com.wode.factory.user.dao.ExchangeSuborderitemDao;

@Repository("exchangeSuborderitemDao")
public class ExchangeSuborderitemDaoImpl extends FactoryBaseDaoImpl<ExchangeSuborderitem> implements ExchangeSuborderitemDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "ExchangeSuborderitemMapper";
	}

	@Override
	public Integer getOrderCount(Long skuId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getOrderCount", skuId);
	}

	@Override
	public List<ExchangeSuborderitem> getItemsListBySubOrderId(String subOrderId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getItemsListBySubOrderId", subOrderId);
	}	
}
