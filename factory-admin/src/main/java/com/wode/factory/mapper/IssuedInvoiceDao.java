package com.wode.factory.mapper;

import com.wode.factory.model.IssuedInvoice;

public interface IssuedInvoiceDao {

	IssuedInvoice getIssuedInvoice(String suborderId);
	
}
