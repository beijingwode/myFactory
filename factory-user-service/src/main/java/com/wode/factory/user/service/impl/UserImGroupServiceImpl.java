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
import com.wode.factory.model.UserImGroup;
import com.wode.factory.user.dao.UserImGroupDao;
import com.wode.factory.user.service.UserImGroupService;

@Service("userImGroupService")
public class UserImGroupServiceImpl extends BaseService<UserImGroup,java.lang.Long> implements  UserImGroupService{
	@Autowired
	private UserImGroupDao userImGroupDao;
	
	public UserImGroupDao getEntityDao() {
		return this.userImGroupDao;
	}

	@Override
	public List<UserImGroup> selectByModel(UserImGroup query) {
		return userImGroupDao.selectByModel(query);
	}

	@Override
	public List<UserImGroup> selectByMember(Long uid) {
		return userImGroupDao.selectByMember(uid);
	}

	@Override
	public List<UserImGroup> selectByMemberAndGroupId(Long uid, Long groupId) {
		return userImGroupDao.selectByMemberAndGroupId(uid,groupId);
	}
}