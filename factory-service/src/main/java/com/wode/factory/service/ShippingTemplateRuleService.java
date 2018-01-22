package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.ShippingTemplateRule;


public interface ShippingTemplateRuleService {

	/**
	 * 根据商品ID查询运费模板规则
	 * @param productId
	 * @return
	 */
	public List<ShippingTemplateRule> findByProduct(Long productId);


	/**
	 * 根据运费模板ID查询运费模板规则
	 * @param templateId
	 * @return
	 */
	public List<ShippingTemplateRule> findByTemplate(Long templateId);

}
