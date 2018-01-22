/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.SupplierTicketFlow;
import com.wode.factory.user.dao.SupplierTicketFlowDao;
import com.wode.factory.user.service.SupplierTicketFlowService;

@Service("supplierTicketFlowService")
public class SupplierTicketFlowServiceImpl extends BaseService<SupplierTicketFlow,java.lang.Long> implements  SupplierTicketFlowService{
	@Autowired
	private SupplierTicketFlowDao dao;
	
	public SupplierTicketFlowDao getEntityDao() {
		return this.dao;
	}

}