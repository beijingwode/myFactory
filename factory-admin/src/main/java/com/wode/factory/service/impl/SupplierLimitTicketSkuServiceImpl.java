/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SupplierLimitTicketSkuDao;
import com.wode.factory.model.SupplierLimitTicketSku;
import com.wode.factory.service.SupplierLimitTicketSkuService;

@Service("SupplierLimitTicketSkuService")
public class SupplierLimitTicketSkuServiceImpl extends FactoryEntityServiceImpl<SupplierLimitTicketSku> implements  SupplierLimitTicketSkuService{
	@Autowired
	private SupplierLimitTicketSkuDao dao;
	
	@Override
	public SupplierLimitTicketSkuDao getDao() {
		return dao;
	}

	@Override
	public Long getId(SupplierLimitTicketSku entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierLimitTicketSku entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}
}