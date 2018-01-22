/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Invoice;
import com.wode.factory.user.dao.InvoiceDao;
import com.wode.factory.user.query.InvoiceQuery;
import com.wode.factory.user.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl extends BaseService<Invoice,java.lang.Long> implements  InvoiceService{
	@Autowired
	@Qualifier("invoiceDao")
	private InvoiceDao invoiceDao;
	
	public EntityDao getEntityDao() {
		return this.invoiceDao;
	}
	
	public Page findPage(InvoiceQuery query) {
		return invoiceDao.findPage(query);
	}

	@Override
	public List<Invoice> findByUserId(long userId) {
		return invoiceDao.findByUserId(userId);
	}

	//更新发票策略，只保存一条发票信息
	@Override
	public Invoice updateInvoice(Invoice invoice) {
		InvoiceQuery query = new InvoiceQuery();
		query.setUserId(invoice.getUserId());
		query.setIsDefault(1);
		if(invoice.getId() == null || invoice.getId().longValue() == Long.valueOf(0)){//新增
			Invoice defInvoice = invoiceDao.findByQuery(query);
			if(null != defInvoice){
//				defInvoice.setIsDefault(0);
//				invoiceDao.update(defInvoice);
				invoiceDao.deleteById(defInvoice.getId());
			}
			invoice.setIsDefault(1);
			return invoiceDao.save(invoice);
		}else{//更新、切换默认发票
			Invoice defInvoice = invoiceDao.findByQuery(query);
			if(null != defInvoice){
				if(defInvoice.getId() != invoice.getId()){
					defInvoice.setIsDefault(0);
					invoiceDao.update(defInvoice);
				}
			}
			invoice.setIsDefault(1);
			invoiceDao.update(invoice);
			return invoice;
		}
	}

	@Override
	public void deleteInvoice(long userId, long invoiceId) {
		InvoiceQuery query = new InvoiceQuery();
		query.setUserId(userId);
		query.setId(invoiceId);
		Invoice invoice = invoiceDao.findByQuery(query);
		if(invoice != null){
			invoiceDao.deleteById(invoiceId);
		}
	}
}
