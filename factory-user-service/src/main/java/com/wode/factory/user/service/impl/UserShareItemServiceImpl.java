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
import com.wode.factory.model.UserShareItem;
import com.wode.factory.user.dao.UserShareItemDao;
import com.wode.factory.user.service.UserShareItemService;

@Service("userShareItemService")
public class UserShareItemServiceImpl extends BaseService<UserShareItem,java.lang.Long> implements  UserShareItemService{
	@Autowired
	private UserShareItemDao userShareItemDao;
	
	public UserShareItemDao getEntityDao() {
		return this.userShareItemDao;
	}

	@Override
	public List<UserShareItem> selectByModel(UserShareItem query) {
		return userShareItemDao.selectByModel(query);
	}
}