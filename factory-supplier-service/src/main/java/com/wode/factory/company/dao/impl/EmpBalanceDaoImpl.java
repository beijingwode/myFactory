/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.EmpBalanceDao;
import com.wode.factory.model.UserBalance;

@Repository("empBalanceDao")
public class EmpBalanceDaoImpl extends BasePageDaoImpl<UserBalance> implements EmpBalanceDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EmpBalanceMapper";
	}

	@Override
	public UserBalance findByUserAndCurrency(UserBalance query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByUserAndCurrency", query);
	}

	@Override
	public void saveOrUpdate(UserBalance entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insert(UserBalance userBalance) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insert", userBalance);
	}

	@Override
	public Long getId(UserBalance model) {
		return model.getId();
	}
}
