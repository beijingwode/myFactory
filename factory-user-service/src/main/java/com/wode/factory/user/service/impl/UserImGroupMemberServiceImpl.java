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
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.user.dao.UserImGroupMemberDao;
import com.wode.factory.user.service.UserImGroupMemberService;

@Service("userImGroupMemberService")
public class UserImGroupMemberServiceImpl extends BaseService<UserImGroupMember,java.lang.Long> implements  UserImGroupMemberService{
	@Autowired
	private UserImGroupMemberDao userImGroupMemberDao;
	
	public UserImGroupMemberDao getEntityDao() {
		return this.userImGroupMemberDao;
	}

	@Override
	public List<UserImGroupMember> selectByModel(UserImGroupMember query) {
		return userImGroupMemberDao.selectByModel(query);
	}

	@Override
	public List<UserImGroupMember> selectEmployeeDels(Long groupId) {
		return userImGroupMemberDao.selectEmployeeDels(groupId);
	}

	@Override
	public List<UserImGroupMember> selectEmployeeAdds(Long groupId) {
		return userImGroupMemberDao.selectEmployeeAdds(groupId);
	}

	@Override
	public List<UserImGroupMember> selectByImIds(String[] imIds) {
		return userImGroupMemberDao.selectByImIds(imIds);
	}

	@Override
	public List<UserImGroupMember> select4delByImIds(String[] imIds,Long groupId) {
		return userImGroupMemberDao.select4delByImIds(imIds,groupId);
	}

	@Override
	public int updateUserNick(UserImGroupMember entity) {
		return userImGroupMemberDao.updateUserNick(entity);
	}
}