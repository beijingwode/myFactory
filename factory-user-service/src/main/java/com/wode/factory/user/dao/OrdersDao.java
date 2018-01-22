/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Orders;

public interface OrdersDao extends  EntityDao<Orders,Long>{
	public void saveOrUpdate(Orders entity);
	// 根据用户ID、订单ID查找订单信息
	public Orders findById(long userId,long orderId);
	/**
     * 查询不同状态下的订单个数
     * @param query
     * @author 刘聪
     * @since 2015-06-19
     */
	public int findBoughtCount(Long userId, Long product);
}
