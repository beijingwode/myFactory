package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserBlackEnvelopeItemDao;
import com.wode.factory.model.UserBlackEnvelopeItem;
import com.wode.factory.service.UserBlackEnvelopeItemService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("userBlackEnvelopeItemService")
public class UserBlackEnvelopeItemServiceImpl extends FactoryEntityServiceImpl<UserBlackEnvelopeItem> implements UserBlackEnvelopeItemService {
	@Autowired
	UserBlackEnvelopeItemDao UserBlackEnvelopeItemMapper;
	
	@Override
	public UserBlackEnvelopeItemDao getDao() {
		return UserBlackEnvelopeItemMapper;
	}


	@Override
	public Long getId(UserBlackEnvelopeItem entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserBlackEnvelopeItem entity, Long id) {
		entity.setId(id);
	}
}
