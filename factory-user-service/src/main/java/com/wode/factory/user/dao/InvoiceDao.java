/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Invoice;
import com.wode.factory.user.query.InvoiceQuery;

public interface InvoiceDao extends  EntityDao<Invoice,Long>{
	public Page findPage(InvoiceQuery query);
	public void saveOrUpdate(Invoice entity);
	public List<Invoice> findByUserId(long userId);
	public Invoice findByQuery(InvoiceQuery query);
}
