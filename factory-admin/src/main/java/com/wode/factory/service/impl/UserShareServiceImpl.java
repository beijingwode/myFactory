package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.UserShareDao;
import com.wode.factory.model.UserShare;
import com.wode.factory.service.UserShareService;
@Service("userShareServiceImpl")
public class UserShareServiceImpl extends FactoryEntityServiceImpl<UserShare> implements UserShareService {
	@Autowired
	private UserShareDao dao;
	public UserShareDao getDao() {
		return dao;
	}
	@Override
	public UserShare getByUserId(Long id) {
		UserShare q =new UserShare();
		q.setUserId(id);
		List<UserShare> ls = dao.selectByModel(q);
		if(ls!=null && !ls.isEmpty()) {
			return ls.get(0);
		}
		return null;
	}
	@Override
	public Long getId(UserShare entity) {
		return entity.getId();
	}
	@Override
	public void setId(UserShare entity, Long id) {
		entity.setId(id);
	}

}
