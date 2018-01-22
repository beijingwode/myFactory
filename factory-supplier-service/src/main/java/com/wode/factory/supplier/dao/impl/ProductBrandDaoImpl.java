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
import com.wode.factory.model.ProductBrand;
import com.wode.factory.supplier.dao.ProductBrandDao;
import com.wode.factory.supplier.query.ProductBrandQuery;

import cn.org.rapid_framework.page.Page;

@Repository("productBrandDao")
public class ProductBrandDaoImpl extends BaseDao<ProductBrand,java.lang.Long> implements ProductBrandDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductBrandMapper";
	}
	
	public void saveOrUpdate(ProductBrand entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public void deleteByShop(Long shopId) {
		getSqlSession().delete(getIbatisMapperNamesapce()+".deleteByShop", shopId);
	}
	
	public Page findPage(ProductBrandQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	/**
	 *
	 * @param map
	 * @return
	 */
	public List<ProductBrand> findAllBymap(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findAllBymap",map);
	}

	@Override
	public List<ProductBrand> getAddBrands(Long supplierId,Long shopId,Long oldId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("oldId", oldId);
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getAddBrands",map);
	}

	@Override
	public List<ProductBrand> getByShopAndCategory(Long supplierId, Long shopId, Long categoryId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("categoryId", categoryId);
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByShopAndCategory",map);
	}

	@Override
	public void copyByShop(Map<String, Long> map) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".copyByShop",map);
	}
}
