/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Account;
import com.wode.factory.user.query.AccountQuery;

public interface AccountDao extends  EntityDao<Account,Long>{
	public Page findPage(AccountQuery query);
	public void saveOrUpdate(Account entity);
	public Account findByUserId(long userId);

}
