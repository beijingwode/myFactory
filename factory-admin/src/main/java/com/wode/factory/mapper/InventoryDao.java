/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Inventory;

public interface InventoryDao extends  EntityDao<Inventory,Long>{
	
	/**
	 * 
	 * 功能说明：查询所有库存
	 * 日期:	2015年8月12日
	 * 开发者:宋艳垒
	 *
	 * @return
	 */
	public List<Inventory> findAll();
	
	/**
	 * 
	 * 功能说明：批量更新库存
	 * 日期:	2015年8月12日
	 * 开发者:宋艳垒
	 *
	 * @param listInv
	 * @return
	 */
	public int batchUpdate(List<Inventory> listInv);

	
	public int update(Inventory entity);
    public void insert(Inventory entity);
	/**
	 * 获取库存
	 * @param id
	 * @return
	 */
	public Inventory getById(Long id);

	Inventory getBySku(Long skuId);

	public void updateBySkuId(Inventory entity);
	
	public List<Inventory> getByProductId(Long productId);
	public void saveOrUpdate(Inventory entity);
	public List<Inventory> findAllBymap(Map map);

	public void deleteBySupplierId(Long supplierId);
}
