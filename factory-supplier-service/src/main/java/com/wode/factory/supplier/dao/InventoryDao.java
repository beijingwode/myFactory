/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Inventory;
import com.wode.factory.supplier.query.InventoryQuery;

public interface InventoryDao extends  EntityDao<Inventory,Long>{
	public Page findPage(InventoryQuery query);
	public void saveOrUpdate(Inventory entity);
	/**
	 * 根据某一个或几个属性值获取条件的对象集合
	 * @param map
	 * @return
	 */
	public List<Inventory> findAllBymap(Map map);
	public List<Inventory> findByProduct(Long productId);
	public List<Inventory> getByProductId(Long id);
	public void deleteApprRelation(Long productId);

	/**
	 * 通过skuid获取库存信息
	 * @param skuId
	 * @return
	 */
	public Inventory findBySpecId(long skuId);
}
