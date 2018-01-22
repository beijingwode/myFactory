/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.Inventory;
import com.wode.factory.user.dao.InventoryDao;
import com.wode.factory.user.dao.SuborderitemDao;
import com.wode.factory.user.service.InventoryService;
import org.springframework.util.Assert;

import java.util.Map;

@Service("inventoryService")
public class InventoryServiceImpl extends BaseService<Inventory, java.lang.Long> implements InventoryService {
	@Autowired
	@Qualifier("inventoryDao")
	private InventoryDao inventoryDao;

	@Autowired
	private RedisUtil redis;

	@Autowired
	@Qualifier("suborderitemDao")
	private SuborderitemDao suborderitemDao;


	public EntityDao getEntityDao() {
		return this.inventoryDao;
	}


	@Override
	public Inventory findBySpecId(long productSpecificationsId) {
		return inventoryDao.findBySpecId(productSpecificationsId);
	}


	public ActResult cut(Long skuId, Integer num) {
		if (num < 0) {
			return ActResult.fail("数量必须大于0");
		}

		Integer inventory = getInventoryFromRedis(skuId);

		if (num > inventory) {
			return ActResult.fail("库存不足");
		}

		String key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
		redis.incr(key, num * -1);
		redis.addToSet(RedisConstant.Inventory_CHANGE, skuId + "");
		return ActResult.success(null);
	}

	@Override
	public ActResult lockPromotionStock(Long promotionProductId, Integer num, Promotion promotion) {
		Assert.notNull(promotion);
		String key = String.valueOf(promotion.getId() + "_" + String.valueOf(promotionProductId));
		Map<String, String> map = redis.getMap(key);
		if (map.isEmpty()) {
			return ActResult.fail("活动商品无库存");
		}
		Integer locked = Integer.valueOf(map.get("locked"));
		Integer stock = Integer.valueOf(map.get("stock"));
		if (locked + 1 > stock) {
			return ActResult.fail("活动商品库存不足!");
		}
		redis.hincr(key, "locked", num);
		return ActResult.success(null);
	}

	@Override
	public ActResult cutPromotionStock(Long promotionProductId, Integer num, Promotion promotion) {
		Assert.notNull(promotion);
		String key = String.valueOf(promotion.getId() + "_" + String.valueOf(promotionProductId));
		redis.hincr(key, "locked", -num);
		redis.hincr(key, "stock", -num);
		return ActResult.success(null);
	}

	@Override
	public int getPromotionProductStock(PromotionProduct pp) {
		Assert.notNull(pp,"请指定活动商品!");
		String key = String.valueOf(pp.getPromotionId() + "_" +pp.getId());
		Map<String, String> map = redis.getMap(key);
		Integer locked = Integer.valueOf(map.get("locked"));
		Integer stock = Integer.valueOf(map.get("stock"));
		return stock - locked;
	}

	public ActResult unlockPromotionStock(Long promotionProductId, Integer num, Promotion promotion) {
		Assert.notNull(promotion);
		String key = String.valueOf(promotion.getId() + "_" + String.valueOf(promotionProductId));
		Map<String, String> map = redis.getMap(key);
		if (map.isEmpty()) {
			return ActResult.fail("活动商品库存未记录");
		}
		Integer locked = Integer.valueOf(map.get("locked"));
		if (locked >= num) {
			redis.hincr(key, "locked", -num);
			return ActResult.success(null);
		} else {
			return ActResult.fail("活动商品无锁定库存");
		}

	}

	/**
	 * 从redis中获取库存数量, 如果获取不到则从数据库中获取并放置到redis中
	 *
	 * @param skuId
	 * @return
	 */
	public Integer getInventoryFromRedis(long skuId) {
		String key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
		String inventory = redis.getData(key);
		if (inventory != null) {
			return Integer.valueOf(inventory);
		} else {
			Inventory entity = findBySpecId(skuId);
			if(entity == null) return null;
			redis.setData(key, String.valueOf(entity.getQuantity()));
			return entity.getQuantity();
		}
	}

	public ActResult addNum(long skuId, int num) {
		if (num < 0) {
			return ActResult.fail("数量必须大于0");
		}

		String key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
		redis.incr(key, num);
		redis.addToSet(RedisConstant.Inventory_CHANGE, skuId + "");
		return ActResult.success(null);
	}

}
