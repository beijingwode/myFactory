package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.user.dao.IssuedInvoiceDao;
import com.wode.factory.user.service.IssuedInvoiceService;

@Service("issuedInvoiceService")
public class IssuedInvoiceServiceImpl extends FactoryEntityServiceImpl<IssuedInvoice> implements IssuedInvoiceService {

	@Autowired
	private IssuedInvoiceDao issuedInvoiceDao;
	
	@Override
	public FactoryBaseDao<IssuedInvoice> getDao() {
		// TODO Auto-generated method stub
		return issuedInvoiceDao;
	}

	@Override
	public Long getId(IssuedInvoice entity) {
		return entity.getId();
	}

	@Override
	public void setId(IssuedInvoice entity, Long id) {
		entity.setId(id);
	}

	@Override
	public IssuedInvoice getIssuedInvoice(String suborder) {
		// TODO Auto-generated method stub
		return issuedInvoiceDao.getIssuedInvoice(suborder);
	}

}
