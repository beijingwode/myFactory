/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SupplierSaleCodeManageDao;
import com.wode.factory.model.SupplierSaleCodeManage;
import com.wode.factory.service.SupplierSaleCodeManageService;

@Service("supplierSaleCodeManageService")
public class SupplierSaleCodeManageServiceImpl extends EntityServiceImpl<SupplierSaleCodeManage,Long>  implements  SupplierSaleCodeManageService{
	@Autowired
	@Qualifier("supplierSaleCodeManageDao")
	private SupplierSaleCodeManageDao supplierSaleCodeManageDao;

	@Override
	public void insert(SupplierSaleCodeManage entity) {
		getDao().insert(entity);
	}

	@Override
	public SupplierSaleCodeManageDao getDao() {
		return supplierSaleCodeManageDao;
	}
	

}
