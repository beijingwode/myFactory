/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import com.wode.factory.model.PromotionProduct;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityService;
import com.wode.common.util.ActResult;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Promotion;

@Service("inventoryService")
public interface InventoryService extends EntityService<Inventory,Long>{
	
	

	/**
	 * 根据规格ID查找库存
	 * @param productSpecificationsId
	 * @return
	 */
	public Inventory findBySpecId(long productSpecificationsId);
	/**
	 * 根据skuid减库存
	 * @param skuId
	 * @param num
	 * 减的数量
	 * @return
	 */
	public ActResult cut(Long skuId, Integer num);
	
	/**
	 * 根据skuid恢复库存
	 * @param skuId
	 * @param num
	 * 加的数量
	 * @return
	 */
	public ActResult addNum(long skuId,int num );

	/**
	 * 锁定活动库存
	 * @param promotionProductId
	 * @param num
	 * @param promotion
	 * @return
	 */
	public ActResult lockPromotionStock(Long promotionProductId, Integer num, Promotion promotion);

	/**
	 * 减掉活动库存
	 * @param promotionProductId
	 * @param num
	 * @param promotion
	 * @return
	 */
	public ActResult cutPromotionStock(Long promotionProductId, Integer num, Promotion promotion);

	/**
	 * 获取活动商品的库存
	 * @param pp
	 * @return
	 */
	int getPromotionProductStock(PromotionProduct pp);

	/**
	 * 查询库存
	 * @param skuId
	 * @return
	 */
	public Integer getInventoryFromRedis(long skuId);

	/**
	 * 取消活动订单的库存
	 *
	 * @param promotionProductId
	 * @param num
	 * @param promotion
	 * @return
	 */
	ActResult unlockPromotionStock(Long promotionProductId, Integer num, Promotion promotion);
}
