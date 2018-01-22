package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.InvoiceApply;

public interface InvoiceApplyDao {

	List<InvoiceApply> getInvoiceApplyBySuborderId(String suborderId);

}
