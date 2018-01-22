/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Account;
import com.wode.factory.user.dao.AccountDao;
import com.wode.factory.user.query.AccountQuery;
import com.wode.factory.user.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends BaseService<Account,java.lang.Long> implements  AccountService{
	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;
	
	public EntityDao getEntityDao() {
		return this.accountDao;
	}
	
	public Page findPage(AccountQuery query) {
		return accountDao.findPage(query);
	}

	@Override
	public Account findByUserId(long userId) {
		return accountDao.findByUserId(userId);
	}
}
