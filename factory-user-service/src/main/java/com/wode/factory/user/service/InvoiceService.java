/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Invoice;
import com.wode.factory.user.query.InvoiceQuery;

@Service("invoiceService")
public interface InvoiceService extends EntityService<Invoice,Long>{
	
	
	public Page findPage(InvoiceQuery query);
	
	public List<Invoice> findByUserId(long userId);
	//更新发票信息
	public Invoice updateInvoice(Invoice invoice);
	
	public void deleteInvoice(long userId,long invoiceId);
	
}
