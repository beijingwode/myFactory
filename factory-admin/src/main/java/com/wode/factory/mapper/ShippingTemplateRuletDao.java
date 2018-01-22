package com.wode.factory.mapper;

import java.util.List;


import com.wode.factory.model.ShippingTemplateRule;


public interface ShippingTemplateRuletDao {

	/**
	 * 
	 * 功能说明：根据id查询
	 *
	 * @param pageTypeId
	 * @return
	 */
	public List<ShippingTemplateRule> getByProductId(Long productId);
	public List<ShippingTemplateRule> selectByModel(ShippingTemplateRule record);
	public void deleteByTemplateId(Long templateId);
	
	public void deleteBySupplierId(Long supplierId);
}