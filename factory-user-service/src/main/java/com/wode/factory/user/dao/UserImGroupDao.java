/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserImGroup;

public interface UserImGroupDao extends  EntityDao<UserImGroup,Long>{
	public List<UserImGroup> selectByModel(UserImGroup query);
	public List<UserImGroup> selectByMember(Long uid);
	public List<UserImGroup> selectByMemberAndGroupId(Long uid, Long groupId);
}
