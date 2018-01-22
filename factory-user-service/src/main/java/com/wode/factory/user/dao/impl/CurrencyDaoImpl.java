/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Currency;
import com.wode.factory.user.dao.CurrencyDao;
import com.wode.factory.user.query.CurrencyQuery;

@Repository("currencyDao")
public class CurrencyDaoImpl extends BaseDao<Currency,java.lang.Long> implements CurrencyDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CurrencyMapper";
	}
	
	public void saveOrUpdate(Currency entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Currency getByName(java.lang.String v) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() +".getByName",v);
	}

	@Override
	public List<Currency> findPage(CurrencyQuery query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() +".findPage", query);
	}

}
