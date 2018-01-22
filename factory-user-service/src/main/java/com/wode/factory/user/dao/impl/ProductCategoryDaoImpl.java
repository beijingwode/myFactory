/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.user.dao.ProductCategoryDao;
import com.wode.factory.user.query.ProductCategoryQuery;

@Repository("productCategoryDao")
public class ProductCategoryDaoImpl extends BaseDao<ProductCategory,java.lang.Long> implements ProductCategoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductCategoryMapper";
	}
	
	public void saveOrUpdate(ProductCategory entity){
		throw new RuntimeException("no permissions");
	}
	
	public Page findPage(ProductCategoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
