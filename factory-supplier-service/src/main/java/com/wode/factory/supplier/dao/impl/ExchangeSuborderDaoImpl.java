/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.company.query.ExchangeProductVo;
import com.wode.factory.model.ExchangeSuborder;
import com.wode.factory.supplier.dao.ExchangeSuborderDao;

@Repository("exchangeSuborderDao")
public class ExchangeSuborderDaoImpl extends FactoryBaseDaoImpl<ExchangeSuborder> implements ExchangeSuborderDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "ExchangeSuborderMapper";
	}

	@Override
	public Integer findReservedNumByMap(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findReservedNumByMap",map);
	}

	@Override
	public List<ExchangeProductVo> findExchangeSubOrderItemByMap(Map<String, Object> map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".findExchangeSubOrderItemByMap",map);
	}

	@Override
	public Integer findOrderTotalByMap(Map<String, Object> map) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce() + ".findOrderTotalByMap",map);
	}

}
