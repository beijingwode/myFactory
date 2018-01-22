package com.wode.factory.supplier.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductLadder;
import com.wode.factory.supplier.query.SkuLadderVo;

public interface ProductLadderService  extends EntityService<ProductLadder,Long> {

	/**
	 * 增加阶梯价格
	 * @param supplier
	 * @param productId
	 * @param nums
	 * @param prices
	 * @param skuids
	 * @return
	 */
	public boolean saveProductLadder(Long supplier, Long productId,List<Integer> nums,List<BigDecimal> prices, List<List<Long>> skuids,Integer type);
	
	/**
	 * 根据productId删除所有的阶梯价
	 * @param map
	 */
	public void removeAllByProductid(Map<String,String> map);

	public List<ProductLadder> getlistByProductid(Long productId);

	public PageInfo<SkuLadderVo> findPageVo(SkuLadderVo query);
}
