/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserDevice;
import com.wode.factory.user.dao.UserDeviceDao;
import com.wode.factory.user.service.UserDeviceService;

@Service("userDeviceService")
public class UserDeviceServiceImpl extends BaseService<UserDevice,java.lang.Long> implements  UserDeviceService{
	@Autowired
	private UserDeviceDao userDeviceDao;
	@Autowired
	
	public EntityDao getEntityDao() {
		return this.userDeviceDao;
	}

	@Override
	public List<UserDevice> findByAttribute(UserDevice query) {
		return userDeviceDao.findByAttribute(query);
	}
}