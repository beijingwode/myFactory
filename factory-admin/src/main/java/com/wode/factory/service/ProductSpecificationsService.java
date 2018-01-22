/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.model.ProductSpecifications;

public interface ProductSpecificationsService{
	
	/**
	 * 获取所以的sku
	 * @param productid
	 * @return
	 */
	public List<ProductSpecifications> findlistByProductid(Long productid);
	
	/**
	 * 获取所以的sku
	 * @param productid
	 * @return
	 */
	public ProductSpecifications getById(Long id);
	

	public List<ProductSpecificationValue> findProductSpecificationValueBymap(Map map);
	public List<ProductSpecifications> getlistByProductid(Long id);
	public void saveOrUpdate(ProductSpecifications entity) throws DataAccessException;
}
