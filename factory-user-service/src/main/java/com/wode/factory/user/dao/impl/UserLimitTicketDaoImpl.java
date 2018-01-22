/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.user.dao.UserLimitTicketDao;
import com.wode.factory.user.vo.UserLimitTicketVo;

@Repository("userLimitTicketDao")
public class UserLimitTicketDaoImpl extends FactoryBaseDaoImpl<UserLimitTicket> implements UserLimitTicketDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "UserLimitTicketMapper";
	}

	@Override
	public Integer getTicketCount(Long id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("uid", id);
		map.put("date", new Date());
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getTicketCount",map);
	}

	@Override
	public UserLimitTicketVo selectUnusedById(String limitTicketId,String userId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("limitTicketId", limitTicketId);
		map.put("userId", userId);
		map.put("date", new Date());
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectUnusedById",map);
	}

	@Override
	public List<UserLimitTicketVo> getByUserId(Long userId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getByUserId",userId);
	}

	@Override
	public List<UserLimitTicketVo> getAvailableTickets(Long userId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getAvailableTickets",userId);
	}

	@Override
	public UserLimitTicket getByLimitTicketIdAndUserId(Long id, Long uid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("limitTicketId", id);
		map.put("userId", uid);
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByLimitTicketIdAndUserId",map);
	}

	@Override
	public List<UserLimitTicket> getAvailableTicketMap(Long userId,String skuIds,Integer limitType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("skuIds", skuIds);
		map.put("userId", userId);
		map.put("limitType", limitType);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getAvailableTicketMap",map);
	}

}
