/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.factory.model.Inventory;

@Service("inventoryService")
public interface InventoryService{
	
	/**
	 * 
	 * 功能说明：查询全部库存
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

	/**
	 * 调配普通销售与活动销售的库存
	 * @param skuId
	 * @param promotionProductId   活动ID
	 * @param isPromoteContinued    活动是否继续
	 */
	void allocateStock(Long skuId, Long promotionId, Boolean isPromoteContinued);

	/**
	 * 像redis中添加促销库存
	 * @param skuId
	 * @param promotionId
	 */
	void putPromotionStock(Long skuId, Long promotionProductId, Long promotionId);

	/**
	 * 删除活动商品库存
	 * @param skuId
	 * @param promotionId
	 */
	void delPromotionStock(Long skuId, Long promotionProductId, Long promotionId);

	/**
	 * 
	 * 功能说明：根据skuid更新库存
	 * 日期:	2015年11月10日
	 * 开发者:宋艳垒
	 *
	 * @param entity
	 */
	public void updateBySkuId(Inventory entity);

	/**
	 * 查询库存
	 * @param skuId
	 * @return
	 */
	public Integer getInventoryFromRedis(long skuId);
	/**
	 * 根据规格ID查找库存
	 * @param productSpecificationsId
	 * @return
	 */
	public Inventory findBySpecId(long productSpecificationsId);
	public List<Inventory> getByProductId(Long productId);
	
	public void saveOrUpdate(Inventory entity) throws DataAccessException;
	/**
	 * 根据某一个或几个属性值获取条件的对象集合
	 * @param map
	 * @return
	 */
	public List<Inventory> findAllBymap(Map map);
		
}
