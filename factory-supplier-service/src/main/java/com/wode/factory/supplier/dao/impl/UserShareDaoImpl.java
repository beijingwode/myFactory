/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.UserShare;
import com.wode.factory.supplier.dao.UserShareDao;

@Repository("userShareDao")
public class UserShareDaoImpl extends BaseDao<UserShare,java.lang.Long> implements UserShareDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserShareMapper";
	}
	
	public void saveOrUpdate(UserShare entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserShare> selectByModel(UserShare query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
