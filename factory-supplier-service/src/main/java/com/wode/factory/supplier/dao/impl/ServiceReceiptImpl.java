/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ServiceReceipt;
import com.wode.factory.supplier.dao.ServiceReceiptDao;

@Repository("serviceReceiptDao")
public class ServiceReceiptImpl extends BasePageDaoImpl<ServiceReceipt> implements ServiceReceiptDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ServiceReceiptMapper";
	}
	
	@Override
	public Long getId(ServiceReceipt model) {
		return model.getId();
	}
}
