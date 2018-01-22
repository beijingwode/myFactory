/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.UserImGroupMember;

public interface UserImGroupMemberService extends EntityService<UserImGroupMember,Long>{
	List<UserImGroupMember> selectByModel(UserImGroupMember query);
	List<UserImGroupMember> selectEmployeeDels(Long groupId);
	List<UserImGroupMember> selectEmployeeAdds(Long groupId);
	List<UserImGroupMember> selectByImIds(String[] imIds);
	List<UserImGroupMember> select4delByImIds(String[] imIds,Long groupId);
	int updateUserNick(UserImGroupMember entity);
}
