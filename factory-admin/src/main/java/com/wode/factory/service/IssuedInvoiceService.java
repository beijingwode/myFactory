package com.wode.factory.service;

import com.wode.factory.model.IssuedInvoice;

public interface IssuedInvoiceService {
	/**
	 * 查询发票详情
	 * @param suborder
	 * @return
	 */
	public IssuedInvoice getIssuedInvoice(String suborderId);
}
