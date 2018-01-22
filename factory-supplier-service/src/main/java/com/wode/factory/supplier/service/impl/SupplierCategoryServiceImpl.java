/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.BaseService;

import cn.org.rapid_framework.page.Page;

import com.wode.factory.model.SupplierCategory;
import com.wode.factory.supplier.dao.SupplierCategoryDao;
import com.wode.factory.supplier.query.SupplierCategoryQuery;
import com.wode.factory.supplier.service.SupplierCategoryService;

@Service("supplierCategoryService")
public class SupplierCategoryServiceImpl extends BaseService<SupplierCategory,java.lang.Long> implements  SupplierCategoryService{
	@Autowired
	@Qualifier("supplierCategoryDao")
	private SupplierCategoryDao supplierCategoryDao;
	
	public EntityDao getEntityDao() {
		return this.supplierCategoryDao;
	}
	
	public Page findPage(SupplierCategoryQuery query) {
		return supplierCategoryDao.findPage(query);
	}

	@Override
	public void removeBySupplierId(Long supplierId,Long shopId) {
		supplierCategoryDao.removeBySupplierId(supplierId,shopId);
	}
	
	/**
	 * 根据条件获取全部符合条件的数据
	 * @param map
	 * @return
	 */
	public List<SupplierCategory> findAll(Map map){
		return supplierCategoryDao.findAll(map);
	}

	@Override
	public List<SupplierCategory> getBySupplierId(Long supplierId,Long shopId) {
		return supplierCategoryDao.getBySupplierId(supplierId,shopId);	
	}

	@Override
	public void deletebymap(Map delMap) {
		supplierCategoryDao.deletebymap(delMap);
	}
	
	public List<SupplierCategory> getlistByMap(Map map){
		return supplierCategoryDao.getlistByMap(map);
	}

	@Override
	public List<SupplierCategory> getShopCategory(Long supplierId,Long shopId, String categoryName) {
		return this.supplierCategoryDao.getShopCategory(supplierId,shopId, categoryName);
	}

	@Override
	public List<SupplierCategory> getAddCategorys(Long supplierId, Long shopId, Long oldId) {
		return supplierCategoryDao.getAddCategorys(supplierId, shopId, oldId);
	}

	@Override
	public List<SupplierCategory> getByShopAndBrand(Long supplierId, Long shopId, Long brandId) {
		return supplierCategoryDao.getByShopAndBrand(supplierId, shopId, brandId);
	}

	@Override
	public void copyByShop(Long supplierId, Long shopId, Long oldId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("oldId", oldId);
		supplierCategoryDao.copyByShop(map);
	}

	@Override
	public void insertBatch(Long supplierId, Long shopId, String categoryids) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("categoryids", categoryids);
		supplierCategoryDao.insertBatch(map);
		
	}
		
}
