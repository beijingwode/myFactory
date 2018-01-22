/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.UserIm;
import com.wode.factory.supplier.dao.UserImDao;

@Repository("userImDao")
public class UserImDaoImpl extends BasePageDaoImpl<UserIm> implements UserImDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserImMapper";
	}
	
	@Override
	public Long getId(UserIm model) {
		return model.getId();
	}
}
