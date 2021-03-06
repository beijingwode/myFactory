/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.user.dao.UserContactsApprDao;
import com.wode.factory.user.model.UserContactsAppr;
import com.wode.factory.user.vo.UserContactsApprVo;

@Repository("userContactsApprDao")
public class UserContactsApprDaoImpl extends FactoryBaseDaoImpl<UserContactsAppr> implements UserContactsApprDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "UserContactsApprMapper";
	}

	@Override
	public List<UserContactsApprVo> selectVoByModel(UserContactsAppr query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectVoByModel", query);
	}
	
}
