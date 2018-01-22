/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.model.SupplierExchangeProduct;

public interface SupplierExchangeProductDao extends  BasePageDao<SupplierExchangeProduct>{

	List<SupplierExchangeProduct> findListByMap(Map<String, Object> param);

	SupplierExchangeProduct getByProductId(Long productId);
}
