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

import com.wode.factory.model.SupplierDuration;
import com.wode.factory.supplier.dao.SupplierDurationDao;
import com.wode.factory.supplier.query.SupplierDurationQuery;
import com.wode.factory.supplier.service.SupplierDurationService;

@Service("supplierDurationService")
public class SupplierDurationServiceImpl extends BaseService<SupplierDuration,java.lang.Long> implements  SupplierDurationService{
	@Autowired
	@Qualifier("supplierDurationDao")
	private SupplierDurationDao supplierDurationDao;
	
	public EntityDao getEntityDao() {
		return this.supplierDurationDao;
	}
	
	public Page findPage(SupplierDurationQuery query) {
		return supplierDurationDao.findPage(query);
	}
	
	/**
	 * 根据供应商id获取该供应商的对账单类型
	 * @param supplierId
	 * @return
	 */
	public SupplierDuration getBySupplierId(Long supplierId){
		return supplierDurationDao.getBySupplierId(supplierId);
	}
	
}
