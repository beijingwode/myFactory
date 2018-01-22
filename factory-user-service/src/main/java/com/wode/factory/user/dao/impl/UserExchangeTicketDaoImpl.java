/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserExchangeTicket;
import com.wode.factory.user.dao.UserExchangeTicketDao;

@Repository("userExchangeTicketDao")
public class UserExchangeTicketDaoImpl extends BaseDao<UserExchangeTicket,java.lang.Long> implements UserExchangeTicketDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserExchangeTicketMapper";
	}
	
	public void saveOrUpdate(UserExchangeTicket entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserExchangeTicket> selectByModel(UserExchangeTicket query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
	

	@Override
	public BigDecimal getShareAmout(Long userId){

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("limitEnd", new Date());
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getShareAmout",map);
	}
}
