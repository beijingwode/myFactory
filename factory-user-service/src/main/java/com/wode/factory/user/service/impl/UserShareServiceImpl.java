/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.UserShare;
import com.wode.factory.user.dao.UserShareDao;
import com.wode.factory.user.service.UserShareService;

@Service("userShareService")
public class UserShareServiceImpl extends BaseService<UserShare,java.lang.Long> implements  UserShareService{
	@Autowired
	private UserShareDao userShareDao;
	
	public UserShareDao getEntityDao() {
		return this.userShareDao;
	}

	@Override
	public List<UserShare> selectByModel(UserShare query) {
		return userShareDao.selectByModel(query);
	}
}