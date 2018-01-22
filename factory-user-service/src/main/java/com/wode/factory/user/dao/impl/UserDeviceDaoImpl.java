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
import com.wode.factory.model.UserDevice;
import com.wode.factory.user.dao.UserDeviceDao;

@Repository("userDeviceDao")
public class UserDeviceDaoImpl extends BaseDao<UserDevice,java.lang.Long> implements UserDeviceDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "UserDeviceMapper";
	}
	
	public void saveOrUpdate(UserDevice entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public List<UserDevice> findByAttribute(UserDevice query) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getByAttribute", query);
	}
	
}
