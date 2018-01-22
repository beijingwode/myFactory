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

import com.wode.factory.model.ProductSpecificationsImage;


public interface ProductSpecificationsImageService {
	
	/**
	 * 获取sku所对应的图片list
	 * @param productSpecificationid
	 * @return
	 */
	List<ProductSpecificationsImage> findlistByProductSpecificationsid(Long id);
	public List<ProductSpecificationsImage> getByProductId(Long productId);
	public void saveOrUpdate(ProductSpecificationsImage entity) throws DataAccessException;
	void updateImg(Map<String,Object> param); 
}
