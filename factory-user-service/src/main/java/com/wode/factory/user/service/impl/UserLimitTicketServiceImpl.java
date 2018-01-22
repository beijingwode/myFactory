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
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.user.dao.UserLimitTicketDao;
import com.wode.factory.user.service.UserLimitTicketService;
import com.wode.factory.user.vo.UserLimitTicketVo;

@Service("userLimitTicketService")
public class UserLimitTicketServiceImpl extends FactoryEntityServiceImpl<UserLimitTicket> implements  UserLimitTicketService{
	@Autowired
	private UserLimitTicketDao dao;
	
	@Override
	public UserLimitTicketDao getDao() {
		return dao;
	}

	@Override
	public Long getId(UserLimitTicket entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserLimitTicket entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public Integer getTicketCount(Long id) {
		
		return dao.getTicketCount(id);
	}

	@Override
	public UserLimitTicketVo selectUnusedById(String ticketId, String userId) {
		return dao.selectUnusedById(ticketId,userId);
	}

	@Override
	public List<UserLimitTicketVo> getByUserId(Long userId) {
		return dao.getByUserId(userId);
	}

	@Override
	public List<UserLimitTicketVo> getAvailableTickets(Long id) {
		return dao.getAvailableTickets(id);
	}

	@Override
	public UserLimitTicket getByLimitTicketIdAndUserId(Long id, Long uid) {
		return dao.getByLimitTicketIdAndUserId(id,uid);
	}

	@Override
	public List<UserLimitTicket> getAvailableTicketMap(Long userId, String skuIds,Integer limitType) {
		return dao.getAvailableTicketMap(userId, skuIds,limitType);
	}

}