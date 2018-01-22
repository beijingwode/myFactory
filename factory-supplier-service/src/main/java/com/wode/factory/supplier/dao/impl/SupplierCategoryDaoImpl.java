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

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.supplier.dao.SupplierCategoryDao;
import com.wode.factory.supplier.query.SupplierCategoryQuery;

import cn.org.rapid_framework.page.Page;

@Repository("supplierCategoryDao")
public class SupplierCategoryDaoImpl extends BaseDao<SupplierCategory,java.lang.Long> implements SupplierCategoryDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierCategoryMapper";
	}
	
	public void saveOrUpdate(SupplierCategory entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SupplierCategoryQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public void removeBySupplierId(Long supplierId,Long shopId) {
		Map map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeBySupplierId",map);
	}
	
	/**
	 * 根据条件获取全部符合条件的数据
	 * @param map
	 * @return
	 */
	public List<SupplierCategory> findAll(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap",map);
	}

	@Override
	public List<SupplierCategory> getBySupplierId(Long supplierId,Long shopId) {
		Map map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getBySupplierId",map);
	}

	@Override
	public List<SupplierCategory> getByShopAndBrand(Long supplierId,Long shopId,Long brandId) {
		Map map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("brandId", brandId);
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByShopAndBrand",map);
	}
	
	@Override
	public List<SupplierCategory> getAddCategorys(Long supplierId,Long shopId,Long oldId) {
		Map map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("oldId", oldId);
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getAddCategorys",map);
	}
	
	@Override
	public void deletebymap(Map delMap) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deletebymap",delMap);
	}
	
	public List<SupplierCategory> getlistByMap(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getlistByMap",map);
	}

	@Override
	public List<SupplierCategory> getShopCategory(Long supplierId,Long shopId,String categoryName) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("categoryName", categoryName);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getShopCategory", map);
	}

	@Override
	public void copyByShop(Map<String, Long> map) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".copyByShop",map);
	}

	@Override
	public void insertBatch(Map<String, Object> map) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".insertBatch",map);
	}
}
