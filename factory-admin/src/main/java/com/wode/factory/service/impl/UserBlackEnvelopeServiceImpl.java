package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserBlackEnvelopeDao;
import com.wode.factory.model.UserBlackEnvelope;
import com.wode.factory.service.UserBlackEnvelopeService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userBlackEnvelopeService")
public class UserBlackEnvelopeServiceImpl extends FactoryEntityServiceImpl<UserBlackEnvelope> implements UserBlackEnvelopeService {
	@Autowired
	UserBlackEnvelopeDao userBlackEnvelopeMapper;
	
	@Override
	public UserBlackEnvelopeDao getDao() {
		return userBlackEnvelopeMapper;
	}


	@Override
	public Long getId(UserBlackEnvelope entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserBlackEnvelope entity, Long id) {
		entity.setId(id);
	}
}
