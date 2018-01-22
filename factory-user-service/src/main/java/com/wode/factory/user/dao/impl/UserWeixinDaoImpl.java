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
import com.wode.factory.model.UserWeixin;
import com.wode.factory.user.dao.UserWeixinDao;

@Repository("userWeixinDao")
public class UserWeixinDaoImpl extends BaseDao<UserWeixin,java.lang.Long> implements UserWeixinDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserWeixinMapper";
	}
	
	public void saveOrUpdate(UserWeixin entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserWeixin> selectByModel(UserWeixin query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", query);
	}
}
