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

import com.wode.common.mongo.MongoBaseDao;
import com.wode.common.mongo.MongoBaseService;
import com.wode.factory.model.SupplierLog;
import com.wode.factory.supplier.dao.SupplierLogDao;
import com.wode.factory.supplier.service.SupplierLogService;

@Service("supplierLogService")
public class SupplierLogServiceImpl extends MongoBaseService<SupplierLog> implements  SupplierLogService{
	@Autowired
	@Qualifier("supplierLogDao")
	private SupplierLogDao supplierLogDao;
	
	@Override
	public MongoBaseDao<SupplierLog> getMongoDao() {
		return supplierLogDao;
	}

	@Override
	public SupplierLog save(SupplierLog supplierLog) {
		getMongoDao().insert(supplierLog);
		return supplierLog;
	}
}
