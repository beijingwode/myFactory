package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.InvoiceApplyDao;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.service.InvoiceApplyService;
@Service("invoiceApplyServiceImpl")
public class InvoiceApplyServiceImpl implements InvoiceApplyService {
	
	@Autowired
	private InvoiceApplyDao invoiceApplyDao;
	
	@Override
	public List<InvoiceApply> getInvoiceApplyBySuborderId(String id) {
		// TODO Auto-generated method stub
		return invoiceApplyDao.getInvoiceApplyBySuborderId(id);
	}

}
