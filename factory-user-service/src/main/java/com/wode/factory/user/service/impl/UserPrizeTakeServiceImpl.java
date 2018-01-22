/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.UserPrizeTake;
import com.wode.factory.user.dao.UserPrizeTakeDao;
import com.wode.factory.user.service.UserPrizeTakeService;

@Service("userPrizeTakeService")
public class UserPrizeTakeServiceImpl extends FactoryEntityServiceImpl<UserPrizeTake> implements  UserPrizeTakeService{
	@Autowired
	private UserPrizeTakeDao dao;
	
	@Override
	public UserPrizeTakeDao getDao() {
		return dao;
	}

	@Override
	public Long getId(UserPrizeTake entity) {
		return entity.getId();
	}

	@Override
	public void setId(UserPrizeTake entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public UserPrizeTake findUserPrizeByMap(Map<String, Object> map) {
		return dao.findUserPrizeByMap(map);
	}
}