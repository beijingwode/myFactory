/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CheckOpinion;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.query.SupplierQuery;

import cn.org.rapid_framework.page.Page;

public interface SupplierService extends EntityService<Supplier,Long>{

	public static final String REDIS_SUPPLIER = "REDIS_SUPPLIER_";
	
	
	public Page findPage(SupplierQuery query);

	public Supplier getByUserId(Long userId);

	public void updateEnter(Map<String, Object> reparm);
	public void updateShippingFree(Double shippingFree, Long id);

	public List<Supplier> getBymap(Map<String, Object> map);
	public List<CheckOpinion> getCheckOpinionListBySupplierId(Long id);

	public void updatePeopleNumber(Supplier supplier);

	public void updateFirmLogo(Supplier supplier);
}
