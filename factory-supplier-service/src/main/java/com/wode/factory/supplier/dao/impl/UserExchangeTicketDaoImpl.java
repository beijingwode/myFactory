/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.company.query.UserExchangeTicketVo;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.supplier.dao.UserExchangeTicketDao;

@Repository("UserExchangeTicketDao")
public class UserExchangeTicketDaoImpl extends BasePageDaoImpl<UserExchangeTicket> implements UserExchangeTicketDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserExchangeTicketMapper";
	}
	
	@Override
	public Long getId(UserExchangeTicket model) {
		return model.getId();
	}

	@Override
	public int updateEnds(UserExchangeTicket entity) throws DataAccessException {
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateEnds", entity);
	}

	@Override
	public List<UserExchangeTicketVo> findListByMap(Map<String, Object> map) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findListByMap", map);
	}

}
