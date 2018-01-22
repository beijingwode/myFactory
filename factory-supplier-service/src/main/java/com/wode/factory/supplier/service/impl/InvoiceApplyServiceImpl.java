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
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.supplier.dao.InvoiceApplyDao;
import com.wode.factory.supplier.service.InvoiceApplyService;

@Service("invoiceApplyService")
public class InvoiceApplyServiceImpl extends FactoryEntityServiceImpl<InvoiceApply> implements  InvoiceApplyService{
	@Autowired
	private InvoiceApplyDao dao;

	@Override
	public InvoiceApplyDao getDao() {
		return dao;
	}

	@Override
	public Long getId(InvoiceApply entity) {
		return entity.getId();
	}

	@Override
	public void setId(InvoiceApply entity, Long id) {
		entity.setId(id);
	}
	
}
