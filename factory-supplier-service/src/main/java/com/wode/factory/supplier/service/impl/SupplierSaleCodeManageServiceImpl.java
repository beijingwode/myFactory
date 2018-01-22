/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.BaseService;
import cn.org.rapid_framework.page.Page;

import com.wode.factory.model.SupplierSaleCodeManage;
import com.wode.factory.supplier.dao.SupplierSaleCodeManageDao;
import com.wode.factory.supplier.query.SupplierSaleCodeManageQuery;
import com.wode.factory.supplier.service.SupplierSaleCodeManageService;

@Service("supplierSaleCodeManageService")
public class SupplierSaleCodeManageServiceImpl extends BaseService<SupplierSaleCodeManage,java.lang.Long> implements  SupplierSaleCodeManageService{
	@Autowired
	@Qualifier("supplierSaleCodeManageDao")
	private SupplierSaleCodeManageDao supplierSaleCodeManageDao;
	
	public EntityDao getEntityDao() {
		return this.supplierSaleCodeManageDao;
	}
	
	public Page findPage(SupplierSaleCodeManageQuery query) {
		return supplierSaleCodeManageDao.findPage(query);
	}
	
}
