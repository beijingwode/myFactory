/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.mapper.InventoryDao;
import com.wode.factory.mapper.PromotionDao;
import com.wode.factory.mapper.PromotionProductDao;
import com.wode.factory.model.Inventory;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.service.InventoryService;

@Service("inventoryService")
public class InventoryServiceImpl implements  InventoryService{
	
	@Autowired
	private InventoryDao dao;

	@Autowired
	private PromotionDao promotionDao;

	@Autowired
	private PromotionProductDao promotionProductDao;
	
	@Autowired
	private RedisUtil redis;

	@Override
	public List<Inventory> findAll() {
		return dao.findAll();
	}

	@Override
	public int batchUpdate(List<Inventory> listInv) {
		return dao.batchUpdate(listInv);
	}

	@Override
	public int update(Inventory entity) {
		return dao.update(entity);
	}

	@Override
	public void allocateStock(Long skuId, Long id, Boolean isPromoteContinued) {
		PromotionProduct promotionProduct = promotionProductDao.selectByPrimaryKey(id);
		if(promotionProduct != null) {
			Integer promotion_quantity = promotionProduct.getJoinQuantity();
			String sku_key =RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
			String promotion_key = String.valueOf(promotionProduct.getPromotionId()) + "_" + String.valueOf(promotionProduct.getId());
			String _sku_stock = redis.getData(sku_key);
			Integer sku_stock = 0;
			Map<String, String> _promotion_stock = redis.getMap(promotion_key);
			Integer promotion_stock = 0;
			if (StringUtils.isBlank(_sku_stock)) {
				Inventory entity = dao.getBySku(skuId);
				sku_stock = entity.getQuantity()==null ? 0 : entity.getQuantity();
				redis.setData(sku_key, String.valueOf(sku_stock));
			} else {
				sku_stock = Integer.valueOf(_sku_stock);
			}
			if (_promotion_stock.size()==0) {
				Map promotion_map = new HashMap();
				if(sku_stock >= promotion_quantity) {
					promotion_map.put("stock", String.valueOf(promotion_quantity));
					promotion_map.put("locked", "0");
					redis.setData(promotion_key, promotion_map);
					redis.incr(sku_key, -promotion_quantity);
				} else {
					promotion_map.put("stock",String.valueOf(sku_stock));
					promotion_map.put("locked", "0");
					redis.setData(promotion_key, promotion_map);
					redis.incr(sku_key, -sku_stock);
				}
			} else {
				promotion_stock = Integer.valueOf(_promotion_stock.get("stock"));
				Integer needed_stock = promotion_quantity.intValue() - promotion_stock;
				if (isPromoteContinued) {
					if(promotion_stock < promotion_quantity && sku_stock >= needed_stock) {
						redis.hincr(promotion_key, "stock", needed_stock);
						redis.setMapData(promotion_key, "locked", "0");
						redis.incr(sku_key, -needed_stock);
					}
					if (sku_stock < needed_stock) {
						redis.hincr(promotion_key, "stock", sku_stock);
						redis.setMapData(promotion_key, "locked", "0");
						redis.incr(sku_key, -sku_stock);
					}
				} else {
					redis.incr(sku_key, promotion_stock);
					redis.del(promotion_key);
				}
			}
		}
	}
 
	@Override
	public void putPromotionStock(Long skuId, Long promotionProductId, Long promotionId) {
		Promotion promotion = promotionDao.selectByPrimaryKey(promotionId);
		if(promotion != null && !promotion.getStockShared()) {
			PromotionProduct promotionProduct = promotionProductDao.selectByPrimaryKey(promotionProductId);
			if(promotionProduct != null) {
				Integer promotionProduct_quantity = promotionProduct.getJoinQuantity();
				String sku_key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
				String promotion_key = String.valueOf(promotionId)  + "_" + String.valueOf(skuId);
				String _sku_stock = redis.getData(sku_key);
				Integer sku_stock = 0;
				if (StringUtils.isBlank(_sku_stock)) {
					Inventory entity = dao.getBySku(skuId);
					sku_stock = entity.getQuantity()==null ? 0 : entity.getQuantity();
					redis.setData(sku_key, String.valueOf(sku_stock));
				} else {
					sku_stock = Integer.valueOf(_sku_stock);
				}
				Map<String, String> promotion_map = new HashMap();
				if(sku_stock >= promotionProduct_quantity) {
					promotion_map.put("stock", String.valueOf(promotionProduct_quantity));
					promotion_map.put("locked", "0");
					redis.setData(promotion_key, promotion_map);
					redis.incr(sku_key, -promotionProduct_quantity);
				} else {
					promotion_map.put("stock", String.valueOf(sku_stock));
					promotion_map.put("locked", "0");
					redis.setData(promotion_key, promotion_map);
					redis.incr(sku_key, -sku_stock);
				}
			}
		}
	}

	@Override
	public void delPromotionStock(Long skuId, Long promotionProductId, Long promotionId) {
		Promotion promotion = promotionDao.selectByPrimaryKey(promotionId);
		if(promotion != null && !promotion.getStockShared()) {
			String sku_key = RedisConstant.REDIS_SKU_INVENTORY + String.valueOf(skuId);
			String promotion_key = String.valueOf(promotionId)  + "_" + String.valueOf(promotionProductId);
			Map<String, String> _promotion_stock = redis.getMap(promotion_key);
			if(_promotion_stock.get("stock") != null){
				Integer promotion_stock = Integer.valueOf(_promotion_stock.get("stock"));
				redis.incr(sku_key, promotion_stock);
				redis.del(promotion_key);
			}
		}
	}

	@Override
	public void updateBySkuId(Inventory entity) {
		dao.updateBySkuId(entity);

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
			redis.setData(key, String.valueOf(entity.getQuantity()));
			return entity.getQuantity();
		}
	}
	@Override
	public Inventory findBySpecId(long productSpecificationsId) {
		return dao.getBySku(productSpecificationsId);
	}

	@Override
	public List<Inventory> getByProductId(Long productId) {
		
		return dao.getByProductId(productId);
	}

	@Override
	public void saveOrUpdate(Inventory entity) throws DataAccessException {
		dao.saveOrUpdate(entity);
		
	}

	@Override
	public List<Inventory> findAllBymap(Map map) {
		
		return dao.findAllBymap(map);
	}
}
