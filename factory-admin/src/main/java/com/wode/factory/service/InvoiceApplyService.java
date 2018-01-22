package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.InvoiceApply;

public interface InvoiceApplyService {

	List<InvoiceApply> getInvoiceApplyBySuborderId(String id);

}
