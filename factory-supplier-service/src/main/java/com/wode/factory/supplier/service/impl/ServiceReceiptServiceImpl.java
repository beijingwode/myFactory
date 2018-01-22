/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ServiceReceipt;
import com.wode.factory.supplier.dao.ServiceReceiptDao;
import com.wode.factory.supplier.service.ServiceReceiptService;

@Service("serviceReceiptService")
public class ServiceReceiptServiceImpl extends BasePageServiceImpl<ServiceReceipt> implements  ServiceReceiptService{
	@Autowired
	private ServiceReceiptDao ServiceReceiptDao;
	
	@Override
	protected ServiceReceiptDao getBaseDao() {
		return ServiceReceiptDao;
	}
}
