package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.ShippingFreeRule;


public interface ShippingFreeRuleService {

	/**
	 * 根据商品ID查询运费模板规则
	 * @param productId
	 * @return
	 */
	public List<ShippingFreeRule> findByProduct(Long productId);


	/**
	 * 根据运费模板ID查询运费模板规则
	 * @param templateId
	 * @return
	 */
	public List<ShippingFreeRule> findByTemplate(Long templateId);
	
	
	/**
	 * 根据商家ID查询运费模板规则
	 * @param productId
	 * @return
	 */
	public List<ShippingFreeRule> findBySupplier(Long supplierId);


	public List<ShippingFreeRule> findByTemplateOrCountTypeDes(Long id);

}
