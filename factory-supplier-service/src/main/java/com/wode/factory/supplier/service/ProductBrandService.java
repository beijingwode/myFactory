/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductBrand;
import com.wode.factory.supplier.query.ProductBrandQuery;

import cn.org.rapid_framework.page.Page;

public interface ProductBrandService extends EntityService<ProductBrand,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(ProductBrandQuery query);
	
	/**
	 * @param map
	 * @return
	 */
	public List<ProductBrand> findAllBymap(Map map);
	public List<ProductBrand> getByShopAndCategory(Long supplierId,Long shopId,Long categoryId);

	public List<ProductBrand> getAddBrands(Long supplierId,Long shopId,Long oldId);

	public void deleteByShop(Long shopId);
	public void copyByShop(Long supplierId,Long shopId,Long oldId);
}
