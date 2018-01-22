package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserShareTicketDao;
import com.wode.factory.model.UserShareTicket;
import com.wode.factory.service.UserShareTicketService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userShareTicketService")
public class UserShareTicketServiceImpl extends FactoryEntityServiceImpl<UserShareTicket> implements UserShareTicketService {
	@Autowired
	UserShareTicketDao dao;
	
	@Override
	public UserShareTicketDao getDao() {
		return dao;
	}
	@Override
	public Long getId(UserShareTicket entity) {
		return entity.getId();
	}
	@Override
	public void setId(UserShareTicket entity, Long id) {
		entity.setId(id);
	}
	@Override
	public UserShareTicket findByTicketIdBySuppliderId(Map prm) {
		return dao.findByTicketIdBySuppliderId(prm);
	}
	@Override
	public List<UserShareTicket> getByShareId(Long shareId) {
		return dao.getByShareId(shareId);
	}
}
