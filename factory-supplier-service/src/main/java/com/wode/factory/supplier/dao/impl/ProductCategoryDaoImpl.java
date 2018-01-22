/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.supplier.dao.ProductCategoryDao;
import com.wode.factory.supplier.query.ProductCategoryQuery;

@Repository("productCategoryDao")
public class ProductCategoryDaoImpl extends BaseDao<ProductCategory,java.lang.Long> implements ProductCategoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductCategoryMapper";
	}
	
	public void saveOrUpdate(ProductCategory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductCategoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据parentid获取类型列表，默认加载第一级别的所以
	 * @param map
	 * @return
	 */
	public List<ProductCategory> getProductCategoryListByparentid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductCategoryListByparentid",map);
	}

	@Override
	public List<ProductCategory> getProductCategorys(ProductCategoryQuery productGoryQuery) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage", productGoryQuery);
	}

}
