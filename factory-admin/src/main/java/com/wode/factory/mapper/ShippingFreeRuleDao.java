package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.ShippingFreeRule;


public interface ShippingFreeRuleDao {

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
	public void deleteBySupplierId(Long supplierId);
	public void deleteSupplierAddressBySupplierId(Long supplierId);
	public void deleteSupplierExpressBySupplierId(Long supplierId);
	public void deleteSupplierAppSecurityBySupplierId(Long supplierId);
	

}