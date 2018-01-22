/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.user.dao.UserShareAutoBuyDao;
import com.wode.factory.user.model.UserShareAutoBuy;

@Repository("userShareAutoBuyDao")
public class UserShareAutoBuyDaoImpl extends FactoryBaseDaoImpl<UserShareAutoBuy> implements UserShareAutoBuyDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "UserShareAutoBuyMapper";
	}
	
}
