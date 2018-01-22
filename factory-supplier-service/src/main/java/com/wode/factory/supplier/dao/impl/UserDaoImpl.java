/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Role;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.dao.UserDao;
import com.wode.factory.supplier.query.RoleQuery;
import com.wode.factory.supplier.query.UserFactoryQuery;

import cn.org.rapid_framework.page.Page;

@Repository("userDao")
public class UserDaoImpl extends BaseDao<UserFactory,java.lang.Long> implements UserDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserMapper";
	}
	
	public void saveOrUpdate(UserFactory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(UserFactoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public UserFactory getByEmail(String toEmail) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByEmail",toEmail);
	}
	@Override
	public UserFactory getByPhone(String phone) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByPhone", phone);
	} 
	@Override
	public UserFactory getByUserName(String userName) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByUserName", userName);
	}
	
	@Override
	public UserFactory getByEmailNew(String toEmail) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByEmailNew",toEmail);
	}

	@Override
	public void saveId(UserFactory fus) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".insert",fus);
	}

	@Override
	public Integer addRole(Role role) {
		return this.getSqlSession().insert(getIbatisMapperNamesapce() + ".addRole", role);
	}

	@Override
	public void deleteRole(Role role) {
		Assert.notNull(role.getSupplierId());
		Assert.notNull(role.getId());
		this.getSqlSession().delete(getIbatisMapperNamesapce() + ".deleteRole", role);
	}

	@Override
	public void updateRole(Role role) {
		this.getSqlSession().update(getIbatisMapperNamesapce() + ".updateRole", role);
	}

	@Override
	public List<Role> selectRoles(Map<String, Object> params) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".selectRoles", params);
	}

	public Role getRole(Integer roleId, Long supplierId) {
		Map<String, Object> params = new HashMap();
		params.put("id", roleId);
		params.put("supplierId", supplierId);
		List<Role> roles = this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getRole", params);
		if(roles.size()>0) return roles.get(0);
		return null;
	}

	@Override
	public void delAuthResources(Integer roleId, Integer resource) {
		Map<String, Object> conditions = new HashMap();
		conditions.put("role", roleId);
		conditions.put("resource", resource);
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteAuthResource", conditions);
	}

	@Override
	public void addAuthResources(Integer roleId, Integer resource) {
		Map<String, Object> editorResource = new HashMap<>();
		editorResource.put("role", roleId);
		editorResource.put("resource", resource);
		editorResource.put("createTime", new Date());
		this.getSqlSession().insert(getIbatisMapperNamesapce() + ".insertAuthResource", editorResource);
	}

	@Override
	public List<RoleQuery> selectRoleAndUserName(RoleQuery roleQuery) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectUserName", roleQuery);
	}

	@Override
	public List<UserFactory> getUserList(UserFactory uf) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage", uf);
	}

	@Override
	public List<com.wode.factory.model.Resource> getAuth(Long userId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getAuth", userId);
	}

	@Override
	public PageInfo<RoleQuery> findRolePage(RoleQuery roleQuery) {
		List<RoleQuery> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectRole", roleQuery, new RowBounds(roleQuery.getPageNumber(), roleQuery.getPageSize()));
		return new PageInfo(list);
	}

	@Override
	public PageInfo<RoleQuery> findUserNamePage(RoleQuery roleQuery) {
		List<RoleQuery> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectUserName", roleQuery, new RowBounds(roleQuery.getPageNumber(), roleQuery.getPageSize()));
		return new PageInfo(list);
	}
}
