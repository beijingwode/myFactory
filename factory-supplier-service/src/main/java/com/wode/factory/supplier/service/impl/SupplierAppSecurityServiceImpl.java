/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.supplier.dao.SupplierAppSecurityDao;
import com.wode.factory.supplier.model.SupplierAppSecurity;
import com.wode.factory.supplier.service.SupplierAppSecurityService;

@Service("supplierAppSecurityService")
public class SupplierAppSecurityServiceImpl extends FactoryEntityServiceImpl<SupplierAppSecurity> implements  SupplierAppSecurityService{
	@Autowired
	private SupplierAppSecurityDao dao;

	@Override
	public SupplierAppSecurityDao getDao() {
		return dao;
	}

	@Override
	public Long getId(SupplierAppSecurity entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierAppSecurity entity, Long id) {
		entity.setId(id);
	}
	
}
