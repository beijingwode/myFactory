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

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.supplier.dao.SupplierExchangeProductDao;

@Repository("supplierExchangeProductDao")
public class SupplierExchangeProductDaoImpl extends BasePageDaoImpl<SupplierExchangeProduct> implements SupplierExchangeProductDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierExchangeProductMapper";
	}
	
	@Override
	public Long getId(SupplierExchangeProduct model) {
		return model.getId();
	}

	@Override
	public List<SupplierExchangeProduct> findListByMap(Map<String, Object> param) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findListByMap",param);
	}

	@Override
	public SupplierExchangeProduct getByProductId(Long productId) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getByProductId",productId);
	}
}
