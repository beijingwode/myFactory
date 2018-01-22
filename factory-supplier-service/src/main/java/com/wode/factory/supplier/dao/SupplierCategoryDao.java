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
import com.wode.factory.model.SupplierCategory;
import com.wode.factory.supplier.query.SupplierCategoryQuery;

public interface SupplierCategoryDao extends  EntityDao<SupplierCategory,Long>{
	public Page findPage(SupplierCategoryQuery query);
	public void saveOrUpdate(SupplierCategory entity);
	public void removeBySupplierId(Long supplierId,Long shopId);

	/**
	 * 根据条件获取全部符合条件的数据
	 * @param map
	 * @return
	 */
	public List<SupplierCategory> findAll(Map map);
	public List<SupplierCategory> getBySupplierId(Long supplierId,Long shopId);
	public List<SupplierCategory> getByShopAndBrand(Long supplierId,Long shopId,Long brandId);
	

	public List<SupplierCategory> getAddCategorys(Long supplierId,Long shopId,Long oldId);
	public void deletebymap(Map delMap);
	
	public List<SupplierCategory> getlistByMap(Map map);
	
	/**
	 * 查询店铺分类
	 * @param supplierCategory
	 * @return
	 */
	public List<SupplierCategory> getShopCategory(Long supplierId,Long shopId,String categoryName);
	
	public void copyByShop(Map<String,Long> map);
	public void insertBatch(Map<String, Object> map);
}
