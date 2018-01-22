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
import com.wode.factory.model.UserShareItem;
import com.wode.factory.user.dao.UserShareItemDao;

@Repository("userShareItemDao")
public class UserShareItemDaoImpl extends BaseDao<UserShareItem,java.lang.Long> implements UserShareItemDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserShareItemMapper";
	}
	
	public void saveOrUpdate(UserShareItem entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserShareItem> selectByModel(UserShareItem query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
