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
import com.wode.factory.model.UserRedEnvelopeItem;
import com.wode.factory.user.dao.UserRedEnvelopeItemDao;

@Repository("userRedEnvelopeItemDao")
public class UserRedEnvelopeItemDaoImpl extends BaseDao<UserRedEnvelopeItem,java.lang.Long> implements UserRedEnvelopeItemDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserRedEnvelopeItemMapper";
	}
	
	public void saveOrUpdate(UserRedEnvelopeItem entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserRedEnvelopeItem> selectByModel(UserRedEnvelopeItem query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
