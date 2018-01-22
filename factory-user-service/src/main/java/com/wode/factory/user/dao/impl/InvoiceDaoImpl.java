/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Invoice;
import com.wode.factory.user.dao.InvoiceDao;
import com.wode.factory.user.query.InvoiceQuery;

@Repository("invoiceDao")
public class InvoiceDaoImpl extends BaseDao<Invoice,java.lang.Long> implements InvoiceDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "InvoiceMapper";
	}
	
	public void saveOrUpdate(Invoice entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(InvoiceQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<Invoice> findByUserId(long userId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByUserId",userId);
	}

	@Override
	public Invoice findByQuery(InvoiceQuery query) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByQuery",query);
	}
	

}
