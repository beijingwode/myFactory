/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.user.dao.UserShareAutoBuyDao;
import com.wode.factory.user.model.UserShareAutoBuy;
import com.wode.factory.user.service.UserShareAutoBuyService;

@Service("userShareAutoBuyService")
public class UserShareAutoBuyServiceImpl extends FactoryEntityServiceImpl<UserShareAutoBuy> implements  UserShareAutoBuyService{
	@Autowired
	private UserShareAutoBuyDao dao;
	
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
		if(entity!=null) {
			entity.setId(id);
		}
	}
}