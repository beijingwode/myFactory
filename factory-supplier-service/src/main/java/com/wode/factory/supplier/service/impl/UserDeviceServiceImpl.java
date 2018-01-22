/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserDevice;
import com.wode.factory.supplier.dao.UserDeviceDao;
import com.wode.factory.supplier.service.UserDeviceService;

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
	

	@Override
	public UserDevice selectByShop(Long shopId) {
		List<UserDevice> ls = userDeviceDao.selectByShop(shopId);
		if(ls!=null && !ls.isEmpty()) {
			for (UserDevice userDevice : ls) {
				if(userDevice.getId() != null) {
					return userDevice;
				}
			}
			return ls.get(0);
		}
		return null;
	}

	@Override
	public UserDevice selectBySupplier(Long supplierId) {
		List<UserDevice> ls = userDeviceDao.selectBySupplier(supplierId);
		if(ls!=null && !ls.isEmpty()) {
			for (UserDevice userDevice : ls) {
				if(userDevice.getId() != null) {
					return userDevice;
				}
			}
			return ls.get(0);
		}
		return null;
	}

	@Override
	public UserDevice selectByShopAndSupplier(Long shopId, Long supplierId) {
		UserDevice rtn = this.selectByShop(shopId);
		if(rtn!=null) return rtn;
		return this.selectBySupplier(supplierId);
	}

}