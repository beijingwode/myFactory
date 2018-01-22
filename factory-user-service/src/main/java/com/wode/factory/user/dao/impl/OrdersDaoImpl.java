/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.exception.SystemException;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Orders;
import com.wode.factory.user.dao.OrdersDao;
import com.wode.factory.user.query.OrdersQuery;

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

	@Override
    public Orders save(Orders entity) throws SystemException {
		if(entity.getOrderId() == null) {
			return super.save(entity);
		} else {
			int affectCount = getSqlSession().insert(getInsertStatement(), entity); 
		}
		return entity;
	}
	
	@Override
	public Orders findById(long userId,long orderId) {
		OrdersQuery query = new OrdersQuery();
		query.setUserId(userId);
		query.setOrderId(orderId);
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".queryOrder", query);
	}

	@Override
	public int findBoughtCount(Long userId, Long productId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("productId", productId);
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findBoughtCount",map);
	}
}
