/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ProductBrandImage;
import com.wode.factory.supplier.dao.ProductBrandImageDao;

@Repository("productBrandImageDao")
public class ProductBrandImageDaoImpl extends BasePageDaoImpl<ProductBrandImage> implements ProductBrandImageDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductBrandImageMapper";
	}
	

	@Override
	public Long getId(ProductBrandImage model) {
		return model.getId();
	}


	@Override
	public void deleteByShop(Long shopId) {
		getSqlSession().delete(getIbatisMapperNamesapce()+".deleteByShop", shopId);
	}
	@Override
	public void copyByShop(Map<String, Long> map) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".copyByShop",map);
	}
}
