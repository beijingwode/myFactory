/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.IssuedInvoice;
import com.wode.factory.supplier.dao.IssuedInvoiceDao;

@Repository("issuedInvoiceDao")
public class IssuedInvoiceDaoImpl extends FactoryBaseDaoImpl<IssuedInvoice> implements IssuedInvoiceDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "IssuedInvoiceMapper";
	}

	@Override
	public IssuedInvoice getIssuedInvoice(String suborder) {
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getBySuborderid",suborder);
	}
	
}
