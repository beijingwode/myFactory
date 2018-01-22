/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.InvoiceApply;

public interface InvoiceApplyService extends FactoryEntityService<InvoiceApply>{
	
	/**
	 * 通过orderId增加发票申请
	 * @param orderId
	 * @return
	 */
	public boolean addInvoiceByOrderId(Long orderId);
	
	/**
	 * 通过subOrderId增加发票申请
	 * @param subOrderId
	 * @return
	 */
	public boolean addInvoiceBySubOrderId(String subOrderId);
}
