/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserBlackEnvelope;
import com.wode.factory.user.dao.UserBlackEnvelopeDao;

@Repository("userBlackEnvelopeDao")
public class UserBlackEnvelopeDaoImpl extends BaseDao<UserBlackEnvelope,java.lang.Long> implements UserBlackEnvelopeDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserBlackEnvelopeMapper";
	}
	
	public void saveOrUpdate(UserBlackEnvelope entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserBlackEnvelope> selectByModel(UserBlackEnvelope query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
