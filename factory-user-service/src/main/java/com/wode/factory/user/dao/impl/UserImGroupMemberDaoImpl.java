/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserImGroupMember;
import com.wode.factory.user.dao.UserImGroupMemberDao;

@Repository("userImGroupMemberDao")
public class UserImGroupMemberDaoImpl extends BaseDao<UserImGroupMember,java.lang.Long> implements UserImGroupMemberDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserImGroupMemberMapper";
	}
	
	public void saveOrUpdate(UserImGroupMember entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserImGroupMember> selectByModel(UserImGroupMember query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}

	@Override
	public List<UserImGroupMember> selectEmployeeDels(Long groupId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectEmployeeDels", groupId);
	}

	@Override
	public List<UserImGroupMember> selectEmployeeAdds(Long groupId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectEmployeeAdds", groupId);
	}
	
	@Override
	public List<UserImGroupMember> selectByImIds(String[] imIds) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByImIds", imIds);
	}
	@Override
	public List<UserImGroupMember> select4delByImIds(String[] imIds,Long groupId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("imIds", imIds);
		map.put("groupId", groupId);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".select4delByImIds", map);
	}
	
	@Override
	public int updateUserNick(UserImGroupMember entity) {
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateUserNick", entity);
	}
}
