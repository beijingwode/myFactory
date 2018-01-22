package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.IssuedInvoiceDao;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.service.IssuedInvoiceService;
@Service("issuedInvoiceServiceImpl")
public class IssuedInvoiceServiceImpl implements IssuedInvoiceService {
	
	@Autowired
	private IssuedInvoiceDao issuedInvoiceDao;
	
	@Override
	public IssuedInvoice getIssuedInvoice(String suborderId) {
		return issuedInvoiceDao.getIssuedInvoice(suborderId);
	}

}
