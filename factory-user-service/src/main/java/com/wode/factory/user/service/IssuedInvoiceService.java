/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.IssuedInvoice;

public interface IssuedInvoiceService extends FactoryEntityService<IssuedInvoice>{
	
	/**
	 * 查询发票详情
	 * @param suborder
	 * @return
	 */
	public IssuedInvoice getIssuedInvoice(String suborder);
}
