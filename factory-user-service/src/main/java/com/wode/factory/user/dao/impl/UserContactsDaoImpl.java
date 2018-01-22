/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.user.dao.UserContactsDao;
import com.wode.factory.user.model.UserContacts;
import com.wode.factory.user.vo.ContactsVo;
import com.wode.factory.user.vo.UserContactsVo;

@Repository("userContactsDao")
public class UserContactsDaoImpl extends FactoryBaseDaoImpl<UserContacts> implements UserContactsDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "UserContactsMapper";
	}

	@Override
	public List<UserContactsVo> selectVoByModel(UserContacts query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectVoByModel", query);
	}

	@Override
	public List<ContactsVo> selectFriendWithIm(Map<String, Object> param) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectFriendWithIm", param);
	}
	
}
