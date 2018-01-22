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
import com.wode.factory.model.ApprShop;
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.supplier.dao.SupplierTransferDao;
import com.wode.factory.supplier.service.SupplierTransferService;

@Service("supplierTransferService")
public class SupplierTransferServiceImpl extends BasePageServiceImpl<SupplierTransfer> implements  SupplierTransferService{
	@Autowired
	private SupplierTransferDao supplierTransferDao;
	
	@Override
	protected SupplierTransferDao getBaseDao() {
		return supplierTransferDao;
	}

	@Override
	public SupplierTransfer getApprIng(Long supplierId) {
		return supplierTransferDao.getApprIng(supplierId);
	}
}
