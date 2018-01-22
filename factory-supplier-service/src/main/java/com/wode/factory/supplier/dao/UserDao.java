/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Resource;
import com.wode.factory.model.Role;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.RoleQuery;
import com.wode.factory.supplier.query.UserFactoryQuery;

import cn.org.rapid_framework.page.Page;

public interface UserDao extends EntityDao<UserFactory, Long> {
	public Page findPage(UserFactoryQuery query);

	public void saveOrUpdate(UserFactory entity);

	public com.wode.factory.model.UserFactory getByEmail(String toEmail);
	public List<UserFactory> getUserList(UserFactory uf);
	public com.wode.factory.model.UserFactory getByEmailNew(String toEmail);

	public com.wode.factory.model.UserFactory getByPhone(String phone);
	public com.wode.factory.model.UserFactory getByUserName(String userName);

	public void saveId(UserFactory fus);
	
	Integer addRole(Role role);

	void deleteRole(Role role);

	void updateRole(Role role);

	List<Role> selectRoles(Map<String, Object> params);

	void delAuthResources(Integer roleId, Integer resources);

	void addAuthResources(Integer roleId, Integer resources);
	/**
	 * 查询角色
	 * @param roleQuery
	 * @return
	 */
	public PageInfo<RoleQuery> findRolePage(RoleQuery roleQuery);
	/**
	 * 查询用户
	 * @param roleQuery
	 * @return
	 */
	public PageInfo<RoleQuery> findUserNamePage(RoleQuery roleQuery);
	public List<RoleQuery> selectRoleAndUserName(RoleQuery roleQuery);

	List<Resource> getAuth(Long userId);

	Role getRole(Integer roleId, Long supplierId);
}
