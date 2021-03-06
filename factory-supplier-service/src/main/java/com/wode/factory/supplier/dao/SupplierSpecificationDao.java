/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.SupplierSpecification;

public interface SupplierSpecificationDao extends  BasePageDao<SupplierSpecification>{
	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<SupplierSpecification> getSpecificationlistByCategoryid(Map map);
	public Integer getOtherUseCount(Map map);
	public SupplierSpecification getSpecificationByitemid(Long vid);
}
