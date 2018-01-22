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
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.supplier.dao.ProductAttributeDao;
import com.wode.factory.supplier.query.ProductAttributeQuery;

@Repository("productAttributeDao")
public class ProductAttributeDaoImpl extends BaseDao<ProductAttribute,java.lang.Long> implements ProductAttributeDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductAttributeMapper";
	}
	
	public void saveOrUpdate(ProductAttribute entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductAttributeQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据类型id获取该类型所有的属性及其属性值
	 * @param map（typeid:商品类型id）
	 * @return
	 */
	public List<ProductAttribute> getAttributelistByTypeid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getAttributelistByTypeid",map);
	}
	
	/**
	 * 删除该商品对应的所有属性
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeAllByProductid",map);
	}

	@Override
	public List<ProductAttribute> getByProductId(Long productId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId",productId);
	}

}
