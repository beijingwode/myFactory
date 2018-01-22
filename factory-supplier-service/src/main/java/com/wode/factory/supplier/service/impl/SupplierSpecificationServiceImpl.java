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

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.SupplierSpecification;
import com.wode.factory.supplier.dao.SupplierSpecificationDao;
import com.wode.factory.supplier.service.SupplierSpecificationService;

@Service("supplierSpecificationService")
public class SupplierSpecificationServiceImpl extends BasePageServiceImpl<SupplierSpecification> implements  SupplierSpecificationService{
	@Autowired
	@Qualifier("supplierSpecificationDao")
	private SupplierSpecificationDao supplierSpecificationDao;
	
	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<SupplierSpecification> getSpecificationlistByCategoryid(Map map){
		return supplierSpecificationDao.getSpecificationlistByCategoryid(map);
	}

	@Override
	protected SupplierSpecificationDao getBaseDao() {
		// TODO Auto-generated method stub
		return supplierSpecificationDao;
	}

	@Override
	public Integer getOtherUseCount(Long specificationId, String specificationValue, Long productId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("specificationId", specificationId);
		map.put("specificationValue", specificationValue);
		map.put("productId", productId);
		return supplierSpecificationDao.getOtherUseCount(map);
	}

	@Override
	public SupplierSpecification getSpecificationByitemid(Long vid) {
		return supplierSpecificationDao.getSpecificationByitemid( vid);
	}
}
