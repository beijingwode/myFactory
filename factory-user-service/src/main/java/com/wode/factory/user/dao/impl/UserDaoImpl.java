/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseUser;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.dao.UserDao;
import com.wode.factory.user.query.UserQuery;
import com.wode.factory.user.vo.ContactsVo;

import cn.org.rapid_framework.page.Page;

@Repository("userDao")
public class UserDaoImpl extends BaseDao<UserFactory,java.lang.Long> implements UserDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserFactoryMapper";
	}
	
	public void saveOrUpdate(UserFactory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(UserQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public int specialSave(UserFactory user) {
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insert",user);
	}

	@Override
	public UserFactory getByPhone(String userName) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByPhone", userName);
	}

	@Override
	public UserFactory getByEmail(String userName) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByEmail", userName);
	}

	@Override
	public UserFactory findByOrderId(Long orderId) {
		if(orderId == null){
			return null;
		}
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByOrderId", orderId);
	}

	@Override
	public EnterpriseUser getEmpById(Long id) throws DataAccessException {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getEmpById", id);
	}

	@Override
	public int updEmp(EnterpriseUser entity) {
		return getSqlSession().update(getIbatisMapperNamesapce()+".updEmp", entity);
	}

	@Override
	public List<Enterprise> findForEmailPostfix() {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findForEmailPostfix");
	}

	@Override
	public Enterprise findByEmailPostfix(String emailPostfix) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByEmailPostfix", emailPostfix);
	}

	@Override
	public List<ContactsVo> findColleagueWithIm(Map<String, Object> param) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findColleagueWithIm",param);
	}
}
