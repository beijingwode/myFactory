/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.UserPrizeTake;
import com.wode.factory.user.dao.UserPrizeTakeDao;

@Repository("userPrizeTakeDao")
public class UserPrizeTakeDaoImpl extends FactoryBaseDaoImpl<UserPrizeTake> implements UserPrizeTakeDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "UserPrizeTakeMapper";
	}

	@Override
	public UserPrizeTake findUserPrizeByMap(Map<String, Object> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findUserPrizeByMap", map);
	}
	
}
