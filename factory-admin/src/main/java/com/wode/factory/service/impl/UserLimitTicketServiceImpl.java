/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserLimitTicketDao;
import com.wode.factory.model.UserLimitTicket;
import com.wode.factory.service.UserLimitTicketService;

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
	public List<UserLimitTicket> getpastLimitTicket(Long limitTicketId) {
		return dao.getpastLimitTicket(limitTicketId);
	}

	@Override
	public List<UserLimitTicket> getUserLimitTicketByMap(Map<String, Long> map) {
		return dao.getUserLimitTicketByMap(map);
	}
}