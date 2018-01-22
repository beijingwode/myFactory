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
import com.wode.factory.model.UserBlackEnvelopeItem;
import com.wode.factory.user.dao.UserBlackEnvelopeItemDao;

@Repository("userBlackEnvelopeItemDao")
public class UserBlackEnvelopeItemDaoImpl extends BaseDao<UserBlackEnvelopeItem,java.lang.Long> implements UserBlackEnvelopeItemDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserBlackEnvelopeItemMapper";
	}
	
	public void saveOrUpdate(UserBlackEnvelopeItem entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserBlackEnvelopeItem> selectByModel(UserBlackEnvelopeItem query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
