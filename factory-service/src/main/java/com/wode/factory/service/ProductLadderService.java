package com.wode.factory.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.ProductLadder;

public interface ProductLadderService  {
	
	/**
	 * 通过skuid查询阶梯价
	 * @param skuid
	 * @return
	 */
	public List<ProductLadder> getListBySkuid(Long skuid);

	/**
	 * 通过skuid 返回促销信息
	 * @param skuid
	 * @return
	 */
	public String getStringBySkuid(Long skuid);
	
	/**
	 * 通过sku和数量返回阶梯价，不满足的阶段返回null
	 * @param skuid
	 * @param quantity
	 * @return
	 */
	public BigDecimal getPriceBySkuidAndPrice(Long skuid, Integer quantity);

	public Map<String, BigDecimal> getSalePromotionMapBySkuid(Long id);
}
