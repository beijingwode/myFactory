/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.supplier.query.ProductSpecificationsImageQuery;

import cn.org.rapid_framework.page.Page;

public interface ProductSpecificationsImageService extends EntityService<ProductSpecificationsImage,Long>{
	
	
	
	public Page findPage(ProductSpecificationsImageQuery query);
	
	public void removeByMap(Map map);

	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<ProductSpecificationsImage> getSkuImglistByProductId(Long productId);
	public List<ProductSpecificationsImage> getByProductId(Long specificationsId);
}
