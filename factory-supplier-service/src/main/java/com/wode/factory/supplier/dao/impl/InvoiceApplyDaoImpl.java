/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.supplier.dao.InvoiceApplyDao;

@Repository("invoiceApplyDao")
public class InvoiceApplyDaoImpl extends FactoryBaseDaoImpl<InvoiceApply> implements InvoiceApplyDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "InvoiceApplyMapper";
	}
	
}
