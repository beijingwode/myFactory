/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.supplier.dao.ProductCategoryDao;
import com.wode.factory.supplier.query.ProductCategoryQuery;


public class ProductCategoryServiceImpl extends BaseService<ProductCategory,java.lang.Long> {
	@Autowired
	@Qualifier("productCategoryDao")
	private ProductCategoryDao productCategoryDao;
	
	public EntityDao getEntityDao() {
		return this.productCategoryDao;
	}
	
	public Page findPage(ProductCategoryQuery query) {
		return productCategoryDao.findPage(query);
	}
	
	/**
	 * 根据parentid获取类型列表，默认加载第一级别的所以
	 * @param map
	 * @return
	 */
	public List<ProductCategory> getProductCategoryListByparentid(Map map){
		return productCategoryDao.getProductCategoryListByparentid(map);
	}
}
