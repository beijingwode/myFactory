/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.UserIm;
import com.wode.factory.supplier.dao.UserImDao;
import com.wode.factory.supplier.service.UserImService;

@Service("userImService")
public class UserImServiceImpl extends BasePageServiceImpl<UserIm> implements  UserImService{
	@Autowired
	private UserImDao UserImDao;
	
	@Override
	protected UserImDao getBaseDao() {
		return UserImDao;
	}
}
