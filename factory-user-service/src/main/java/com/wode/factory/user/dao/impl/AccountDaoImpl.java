/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Account;
import com.wode.factory.user.dao.AccountDao;
import com.wode.factory.user.query.AccountQuery;

@Repository("accountDao")
public class AccountDaoImpl extends BaseDao<Account,java.lang.Long> implements AccountDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "AccountMapper";
	}
	
	public void saveOrUpdate(Account entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(AccountQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public Account findByUserId(long userId) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByUserId", userId);
	}
	

}
