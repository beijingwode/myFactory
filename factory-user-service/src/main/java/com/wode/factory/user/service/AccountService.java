/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Account;
import com.wode.factory.user.query.AccountQuery;

@Service("accountService")
public interface AccountService extends EntityService<Account,Long>{
	
	
	public Page findPage(AccountQuery query);
	
	public Account findByUserId(long userId);
	
}
