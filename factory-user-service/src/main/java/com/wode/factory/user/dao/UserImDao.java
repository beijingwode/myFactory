/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.UserIm;

public interface UserImDao extends  EntityDao<UserIm,Long>{
	public List<UserIm> selectByModel(UserIm query);
	public List<UserIm> selectByShop(Long shopId);
	public List<UserIm> selectBySupplier(Long supplierId);
}
