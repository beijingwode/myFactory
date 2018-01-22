package com.wode.factory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserTicketDao;
import com.wode.factory.model.UserTicket;
import com.wode.factory.service.UserTicketService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userTicketService")
public class UserTicketServiceImpl extends FactoryEntityServiceImpl<UserTicket> implements UserTicketService {
	@Autowired
	UserTicketDao UserTicketMapper;
	
	@Override
	public List<UserTicket> selectByModel(UserTicket query) {
		return getDao().selectByModel(query);
	}

	@Override
	public UserTicketDao getDao() {
		return UserTicketMapper;
	}


	@Override
	public Long getId(UserTicket entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserTicket entity, Long id) {
		entity.setId(id);
	}

	@Override
	public void updateLimitByTicketId(Long ticketId, Date ticketLimitDate) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("ticketId", ticketId);
		map.put("ticketLimitDate", ticketLimitDate);
		this.getDao().updateLimitByTicketId(map);
	}

}
