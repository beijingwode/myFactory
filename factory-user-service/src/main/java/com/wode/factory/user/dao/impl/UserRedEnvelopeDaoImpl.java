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
import com.wode.factory.model.UserRedEnvelope;
import com.wode.factory.user.dao.UserRedEnvelopeDao;

@Repository("userRedEnvelopeDao")
public class UserRedEnvelopeDaoImpl extends BaseDao<UserRedEnvelope,java.lang.Long> implements UserRedEnvelopeDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserRedEnvelopeMapper";
	}
	
	public void saveOrUpdate(UserRedEnvelope entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserRedEnvelope> selectByModel(UserRedEnvelope query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
