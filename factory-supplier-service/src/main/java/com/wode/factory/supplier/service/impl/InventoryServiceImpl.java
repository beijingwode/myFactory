/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.Inventory;
import com.wode.factory.supplier.dao.InventoryDao;
import com.wode.factory.supplier.query.InventoryQuery;
import com.wode.factory.supplier.service.InventoryService;

import cn.org.rapid_framework.page.Page;

@Service("inventoryService")
public class InventoryServiceImpl extends BaseService<Inventory, java.lang.Long> implements InventoryService {
	@Autowired
	@Qualifier("inventoryDao")
	private InventoryDao inventoryDao;

	public InventoryDao getEntityDao() {
		return this.inventoryDao;
	}

	public Page findPage(InventoryQuery query) {
		return inventoryDao.findPage(query);
	}

	/**
	 * 根据某一个或几个属性值获取条件的对象集合
	 *
	 * @param map
	 * @return
	 */
	public List<Inventory> findAllBymap(Map map) {
		return inventoryDao.findAllBymap(map);
	}

	@Override
	public List<Inventory> findByProduct(Long productId) {
		return inventoryDao.findByProduct(productId);
	}

	@Override
	public List<Inventory> getByProductId(Long id) {
		return inventoryDao.getByProductId(id);
	}

	@Override
	public Inventory findBySpecId(long skuId) {
		return inventoryDao.findBySpecId(skuId);
	}
}
