/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.BaseService;

import cn.org.rapid_framework.page.Page;

import com.wode.factory.model.Orders;
import com.wode.factory.supplier.dao.OrdersDao;
import com.wode.factory.supplier.query.OrdersQuery;
import com.wode.factory.supplier.service.OrdersService;

@Service("ordersService")
public class OrdersServiceImpl extends BaseService<Orders,java.lang.Long> implements  OrdersService{
	@Autowired
	@Qualifier("ordersDao")
	private OrdersDao ordersDao;
	
	public EntityDao getEntityDao() {
		return this.ordersDao;
	}
	
	public Page findPage(OrdersQuery query) {
		return ordersDao.findPage(query);
	}
	
}
