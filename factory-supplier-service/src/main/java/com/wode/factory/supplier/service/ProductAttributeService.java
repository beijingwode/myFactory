/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.supplier.query.ProductAttributeQuery;

public interface ProductAttributeService extends EntityService<ProductAttribute,Long>{
	
	
	
	
	/**
	 * 根据类型id获取该类型所有的属性及其属性值
	 * @param map（typeid:商品类型id）
	 * @return
	 */
	public List<ProductAttribute> getAttributelistByTypeid(Map map);
	
	/**
	 * 删除该商品对应的所有属性
	 * @param productid
	 */
	public void removeAllByProductid(Map map);
	public List<ProductAttribute> getByProductId(Long productId);
}
