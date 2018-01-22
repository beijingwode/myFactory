package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserTicketHisDao;
import com.wode.factory.model.UserTicketHis;
import com.wode.factory.service.UserTicketHisService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("UserTicketHisService")
public class UserTicketHisServiceImpl extends FactoryEntityServiceImpl<UserTicketHis> implements UserTicketHisService {

	@Autowired
	UserTicketHisDao UserTicketHisMapper;

	@Override
	public List<UserTicketHis> selectByModel(UserTicketHis query) {
		return getDao().selectByModel(query);
	}

	@Override
	public UserTicketHisDao getDao() {
		return UserTicketHisMapper;
	}

	@Override
	public Long getId(UserTicketHis entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserTicketHis entity, Long id) {
		entity.setId(id);
	}
	
}
