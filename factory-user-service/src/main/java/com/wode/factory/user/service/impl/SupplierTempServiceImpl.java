/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.user.dao.SupplierTempDao;
import com.wode.factory.user.model.SupplierTemp;
import com.wode.factory.user.service.SupplierTempService;

@Service("supplierTempService")
public class SupplierTempServiceImpl extends FactoryEntityServiceImpl<SupplierTemp> implements  SupplierTempService{
	@Autowired
	private SupplierTempDao dao;
	
	@Override
	public SupplierTempDao getDao() {
		return dao;
	}

	@Override
	public Long getId(SupplierTemp entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierTemp entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

}