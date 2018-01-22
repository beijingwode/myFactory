/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserDevice;

public interface UserDeviceDao extends  EntityDao<UserDevice,Long>{
	public List<UserDevice> findByAttribute(UserDevice query);
	public List<UserDevice> selectByShop(Long shopId);
	public List<UserDevice> selectBySupplier(Long supplierId);

}
