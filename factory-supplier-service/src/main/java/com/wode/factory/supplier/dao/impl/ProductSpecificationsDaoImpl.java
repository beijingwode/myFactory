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
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.supplier.dao.ProductSpecificationsDao;
import com.wode.factory.supplier.query.ProductSpecificationsQuery;

@Repository("productSpecificationsDao")
public class ProductSpecificationsDaoImpl extends BaseDao<ProductSpecifications,java.lang.Long> implements ProductSpecificationsDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductSpecificationsMapper";
	}
	
	public void saveOrUpdate(ProductSpecifications entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductSpecificationsQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 删除该商品对应的所有sku（isDelete=1）
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		this.getSqlSession().update(getIbatisMapperNamesapce()+".removeAllByProductid",map);
	}
	
	/**
	 * 获取sku列表
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getPagelist(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getPagelist", map);
	}
	
	/**
	 * 获取所有的条数
	 * @param map
	 * @return
	 */
	public Integer getAllCount(Map map){
		Number num = this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getAllCount", map);
		return num.intValue();
	}
	
	/**
	 * 获取sku实体对象
	 * @param id
	 * @return
	 */
	public ProductSpecifications getProductSpecificationsById(Long id){
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getProductSpecificationsById",id);
	}
	
	/**
	 * 获取sku列表
	 * 获取商品的全部skuid
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getlistByProductid(Long id){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getlistByProductid", id);
	}

	@Override
	public void insert(ProductSpecifications productSpecifications) {
		getSqlSession().insert(getIbatisMapperNamesapce()+".insert", productSpecifications);
	}

	@Override
	public void deleteApprRelation(Long productId) {
		getSqlSession().delete(getIbatisMapperNamesapce()+".deleteApprRelation", productId);
		
	}

	@Override
	public List<ProductSpecifications> getProductSpecificationsByProductId(Map map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductSpecificationsByProductId", map);
	}

	@Override
	public List<ProductSpecifications> findSkuByProductNameAndItemValue(Map map) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findSkuByProductNameAndItemValue", map);
	}
}
