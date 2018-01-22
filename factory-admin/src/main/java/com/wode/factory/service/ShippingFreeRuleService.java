package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.ShippingFreeRule;



public interface ShippingFreeRuleService{

	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param pageTypeId
	 * @return
	 */
	public List<ShippingFreeRule> getByProductId(Long productId);
	public List<ShippingFreeRule> selectByModel(ShippingFreeRule record);
	public void deleteByTemplateId(Long templateId);
}
