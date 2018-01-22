/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.supplier.dao.ProductSpecificationValueDao;
import com.wode.factory.supplier.query.ProductSpecificationValueQuery;

@Repository("productSpecificationValueDao")
public class ProductSpecificationValueDaoImpl extends BaseDao<ProductSpecificationValue,java.lang.Long> implements ProductSpecificationValueDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductSpecificationValueMapper";
	}
	
	public void saveOrUpdate(ProductSpecificationValue entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductSpecificationValueQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 删除该商品对应的所有规格（isDelete=1）
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		this.getSqlSession().update(getIbatisMapperNamesapce()+".removeAllByProductid",map);
	}

	/**
	 * 商品list
	 * @param productid
	 */
	public List<ProductSpecificationValue> findAllBymap(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap",map);
	}

	@Override
	public void insert(ProductSpecificationValue productSpecificationValue) {
		getSqlSession().insert(getIbatisMapperNamesapce()+".insert", productSpecificationValue);
	}

	@Override
	public List<ProductSpecificationValue> selectByModel(ProductSpecificationValue productSpecificationValue) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findPage",productSpecificationValue);
	}

	@Override
	public int copyFromOther(Long productId, Long oId, Long nId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oId", oId);
		map.put("nId", nId);
		map.put("productId", productId);
		return getSqlSession().insert(getIbatisMapperNamesapce()+".copyFromOther", map); 
	}

	@Override
	public int updateFromOther(Long productId, Long oId, Long nId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oId", oId);
		map.put("nId", nId);
		map.put("productId", productId);
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateFromOther", map); 
	}

	@Override
	public List<ProductSpecificationValue> getByProductId(Long productId) {
		
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId",productId);
	}

	@Override
	public void deleteApprRelation(Long productId) {
		getSqlSession().delete(getIbatisMapperNamesapce()+".deleteApprRelation", productId);
		
	}
}
