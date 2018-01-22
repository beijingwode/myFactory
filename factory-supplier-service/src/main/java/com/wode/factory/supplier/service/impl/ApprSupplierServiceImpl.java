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
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.supplier.dao.ApprSupplierDao;
import com.wode.factory.supplier.service.ApprSupplierService;

@Service("apprSupplierService")
public class ApprSupplierServiceImpl extends BasePageServiceImpl<ApprSupplier> implements  ApprSupplierService{
	@Autowired
	private ApprSupplierDao apprSupplierDao;
	
	@Override
	protected ApprSupplierDao getBaseDao() {
		return apprSupplierDao;
	}

	@Override
	public ApprSupplier getSupplierApprIng(Long supplierId) {
		return apprSupplierDao.getSupplierApprIng(supplierId);
	}
}
