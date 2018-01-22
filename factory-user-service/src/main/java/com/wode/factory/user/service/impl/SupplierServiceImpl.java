package com.wode.factory.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Supplier;
import com.wode.factory.user.dao.SupplierDao;
import com.wode.factory.user.service.SupplierService;
@Service("supplierService")
public class SupplierServiceImpl extends BaseService<Supplier, java.lang.String> implements SupplierService {
	@Autowired
	@Qualifier("supplierDao")
	private SupplierDao supplierDao;

	@Override
	protected EntityDao getEntityDao() {
		return this.supplierDao;
	}
	@Override
	public List<Supplier> findByManagerId(Map<String, String> queryMap) {
		// TODO Auto-generated method stub
		return supplierDao.findByManagerId(queryMap);
	}

	
}
