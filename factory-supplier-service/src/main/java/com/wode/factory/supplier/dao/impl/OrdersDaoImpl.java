/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Orders;
import com.wode.factory.supplier.dao.OrdersDao;
import com.wode.factory.supplier.query.OrdersQuery;

@Repository("ordersDao")
public class OrdersDaoImpl extends BaseDao<Orders,java.lang.Long> implements OrdersDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "OrdersMapper";
	}
	
	public void saveOrUpdate(Orders entity){
		if(entity.getOrderId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(OrdersQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
