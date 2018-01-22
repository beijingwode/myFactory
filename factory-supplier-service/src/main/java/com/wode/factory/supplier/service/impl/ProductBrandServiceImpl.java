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

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.supplier.dao.ProductBrandDao;
import com.wode.factory.supplier.query.ProductBrandQuery;
import com.wode.factory.supplier.service.ProductBrandService;

@Service("productBrandService")
public class ProductBrandServiceImpl extends BaseService<ProductBrand,java.lang.Long> implements  ProductBrandService{
	@Autowired
	@Qualifier("productBrandDao")
	private ProductBrandDao productBrandDao;
	
	public EntityDao getEntityDao() {
		return this.productBrandDao;
	}
	
	public Page findPage(ProductBrandQuery query) {
		return productBrandDao.findPage(query);
	}
	
	/**
	 *
	 * @param map
	 * @return
	 */
	public List<ProductBrand> findAllBymap(Map map){
		return productBrandDao.findAllBymap(map);
	}

	@Override
	public List<ProductBrand> getAddBrands(Long supplierId, Long shopId, Long oldId) {
		return productBrandDao.getAddBrands(supplierId, shopId, oldId);
	}

	@Override
	public List<ProductBrand> getByShopAndCategory(Long supplierId, Long shopId, Long categoryId) {
		return productBrandDao.getByShopAndCategory(supplierId, shopId, categoryId);
	}

	@Override
	public void deleteByShop(Long shopId) {
		productBrandDao.deleteByShop(shopId);
	}

	@Override
	public void copyByShop(Long supplierId, Long shopId, Long oldId) {
		Map<String ,Long> map = new HashMap<String ,Long>();
		map.put("supplierId", supplierId);
		map.put("shopId", shopId);
		map.put("oldId", oldId);
		productBrandDao.copyByShop(map);
	}
}
