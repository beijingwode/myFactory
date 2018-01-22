/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.supplier.query.ProductCategoryQuery;

public interface ProductCategoryDao extends  EntityDao<ProductCategory,Long>{
	public Page findPage(ProductCategoryQuery query);
	public void saveOrUpdate(ProductCategory entity);
	
	/**
	 * 根据parentid获取类型列表，默认加载第一级别的所以
	 * @param map
	 * @return
	 */
	public List<ProductCategory> getProductCategoryListByparentid(Map map);
	public List<ProductCategory> getProductCategorys(ProductCategoryQuery productGoryQuery);

}
