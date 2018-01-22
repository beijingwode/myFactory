package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserShareAutoBuyDao;
import com.wode.factory.model.UserShareAutoBuy;
import com.wode.factory.service.UserShareAutoBuyService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("UserShareAutoBuyService")
public class UserShareAutoBuyServiceImpl extends FactoryEntityServiceImpl<UserShareAutoBuy> implements UserShareAutoBuyService {
	@Autowired
	UserShareAutoBuyDao dao;
	
	@Override
	public UserShareAutoBuyDao getDao() {
		return dao;
	}
	@Override
	public Long getId(UserShareAutoBuy entity) {
		return entity.getId();
	}
	@Override
	public void setId(UserShareAutoBuy entity, Long id) {
		entity.setId(id);
	}
}
