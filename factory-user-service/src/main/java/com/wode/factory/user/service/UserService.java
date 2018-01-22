/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.query.UserQuery;

import cn.org.rapid_framework.page.Page;

@Service("userService")
public interface UserService {

	
	public EntityDao getEntityDao() ;
	
	public Page findPage(UserQuery query);

	public UserFactory getById(Long id);
	public void update(UserFactory entity);
	public void saveOrUpdate(UserFactory entity);
	
	@Deprecated
	public UserFactory selectById(Long userId);
	
	public int specialSave(UserFactory user);

	public ActResult<UserFactory> findByPhone(String userName);

	public ActResult<UserFactory> findByEmail(String userName);

	public UserFactory findByOrderId(Long orderId);	
	
	public EnterpriseUser getEmpById(Long id) throws DataAccessException;
	public int updEmp(EnterpriseUser entity);
	
	/**
	 * 获取企业设置邮箱后缀 以便于注册，长期缓存，企业信息变更时需清空缓存
	 * @return
	 */
	List<String> emailPostfixs();
	
	/**
	 * 根据邮箱后缀获得企业信息，以便激活时自动计入员工信息
	 * @param emailPostfix
	 * @return
	 */
	Enterprise findByEmailPostfix(String emailPostfix);
}
