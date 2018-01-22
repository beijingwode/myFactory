/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.SupplierExchangeProduct;
import com.wode.factory.supplier.dao.SupplierExchangeProductDao;
import com.wode.factory.supplier.service.SupplierExchangeProductService;

@Service("supplierExchangeProductService")
public class SupplierExchangeProductServiceImpl extends BasePageServiceImpl<SupplierExchangeProduct> implements  SupplierExchangeProductService{
	@Autowired
	private SupplierExchangeProductDao dao;
	
	@Override
	protected SupplierExchangeProductDao getBaseDao() {
		return dao;
	}

	@Override
	public List<SupplierExchangeProduct> findListByMap(Map<String, Object> param) {
		return dao.findListByMap(param);
	}

	@Override
	public SupplierExchangeProduct getByProductId(Long productId) {
		return dao.getByProductId(productId);
	}
}
