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
import com.wode.factory.model.UserImGroup;
import com.wode.factory.user.dao.UserImGroupDao;

@Repository("userImGroupDao")
public class UserImGroupDaoImpl extends BaseDao<UserImGroup,java.lang.Long> implements UserImGroupDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserImGroupMapper";
	}
	
	public void saveOrUpdate(UserImGroup entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserImGroup> selectByModel(UserImGroup query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}

	@Override
	public List<UserImGroup> selectByMember(Long uid) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByMember", uid);
	}

	@Override
	public List<UserImGroup> selectByMemberAndGroupId(Long uid, Long groupId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("uid", uid);
		map.put("groupId", groupId);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByMemberAndGroupId",map);
	}
}
