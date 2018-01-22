/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Resource;
import com.wode.factory.model.Role;
import com.wode.factory.model.UserFactory;
import com.wode.factory.supplier.query.RoleQuery;
import com.wode.factory.supplier.query.UserFactoryQuery;

import cn.org.rapid_framework.page.Page;

public interface UserService {
	
	public UserFactory getById(Long id);
	public void update(UserFactory entity);
	public void saveOrUpdate(UserFactory entity);
		
	public Page findPage(UserFactoryQuery query);

	/**
	 * 通过邮箱查找账号
	 * @param randomId
	 * @return boolean
	 */
	public com.wode.factory.model.UserFactory getByEmail(String toEmail);
	/**
	 * 通过邮箱查找账号
	 * @param randomId
	 * @return boolean
	 */
	public com.wode.factory.model.UserFactory getByPhone(String toPhone);
	public com.wode.factory.model.UserFactory getByUserName(String toUserName);
	
	/**
	 * 通过邮箱查找账号
	 * @param randomId
	 * @return boolean
	 */
	public com.wode.factory.model.UserFactory getByEmailNew(String toEmail);

	/**
	 * 插入本地用户同步线上ID
	 */
	public void saveId(com.wode.factory.model.UserFactory fus);

	/**
	 * 添加一个编辑
	 * @param admin 添加人
	 * @param editor 被添加人 
	 * @param userId 被添加人id
	 */
	void addEditor(UserFactory admin, RoleQuery editor,Long userId,String empDefultAvatar,String shopLink);
	/**
	 * 添加角色
	 * @param admin     管理员
	 * @param role      角色对象
	 * @param adds      添加的资源权限
	 */
	void saveRole(UserFactory admin, Role role, Integer[] adds);
	/**
	 * 删除角色
	 * @param supplierId
	 * @param roleQuery
	 * @return
	 */
	public void deleteRole(Long supplierId,RoleQuery roleQuery);
	/**
	 * 获取用户的资源权限
	 * @param userId
	 * @return
	 */
	List<Resource> getAuth(Long userId);

	/**
	 * 查询角色信息
	 * @param id 角色id
	 * @param enterpriseId 商家id
	 * @return
	 */
	public List<Role> selectRoles(Long id,Long enterpriseId);
	
	public PageInfo<RoleQuery> findRolePage(RoleQuery roleQuery);
	public PageInfo<RoleQuery> findUserNamePage(RoleQuery roleQuery);
	
	public List<RoleQuery> selectRoleAndUserName(RoleQuery roleQuery);

	/**
	 * 查询用户
	 * @param user
	 * @return
	 */
	public List<UserFactory> getUserList(UserFactory user);
	/**根据id删除用户
	 * @param id
	 */
	public void deleteUserFactoryById(Long id);

	Role getRole(Integer id, Long enterpriseId);
}
