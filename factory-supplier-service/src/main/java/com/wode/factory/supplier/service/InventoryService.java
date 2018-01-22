/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Inventory;
import com.wode.factory.supplier.query.InventoryQuery;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

public interface InventoryService extends EntityService<Inventory,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(InventoryQuery query);
	
	/**
	 * 根据某一个或几个属性值获取唯一对象
	 * @param map
	 * @return
	 */
	public List<Inventory> findAllBymap(Map map);

	public List<Inventory> findByProduct(Long productId);
	
	public List<Inventory> getByProductId(Long id);
	
	/**
	 * 通过skuid获取库存信息
	 * @param skuId
	 * @return
	 */
	public Inventory findBySpecId(long skuId);
}
