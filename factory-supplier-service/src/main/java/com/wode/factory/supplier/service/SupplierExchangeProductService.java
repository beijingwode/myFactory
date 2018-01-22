/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.SupplierExchangeProduct;

public interface SupplierExchangeProductService extends BasePageService<SupplierExchangeProduct>{

	List<SupplierExchangeProduct> findListByMap(Map<String, Object> param);

	SupplierExchangeProduct getByProductId(Long id);
}
