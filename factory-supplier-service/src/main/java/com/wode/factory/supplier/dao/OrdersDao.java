/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Orders;
import com.wode.factory.supplier.query.OrdersQuery;

public interface OrdersDao extends  EntityDao<Orders,Long>{
	public Page findPage(OrdersQuery query);
	public void saveOrUpdate(Orders entity);

}
