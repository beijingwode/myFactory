/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ShippingAddress;
import com.wode.factory.user.query.ShippingAddressQuery;

public interface ShippingAddressDao extends  EntityDao<ShippingAddress,Long>{
	public Page findPage(ShippingAddressQuery query);
	public void saveOrUpdate(ShippingAddress entity);
	public List<ShippingAddress> findByUserId(long userId);
	public ShippingAddress findByQuery(ShippingAddressQuery query);
}
