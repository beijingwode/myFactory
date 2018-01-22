package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierAddress;
import com.wode.factory.supplier.dao.SupplierAddressDao;
import com.wode.factory.supplier.query.SupplierAddressQuery;
import com.wode.factory.supplier.service.SupplierAddressService;

import cn.org.rapid_framework.page.Page;
@Service("supplierAddressService")
public class SupplierAddressServiceImpl extends BaseService<SupplierAddress,java.lang.Long> implements SupplierAddressService {
    @Autowired
    private SupplierAddressDao supplierAddressDao;
    
	@Override
	public Page findPage(SupplierAddressQuery query) {
		return supplierAddressDao.findPage(query);
	}

	@Override
	public List<SupplierAddress> findbyMap(Map<String, Object> reparm) {
		return supplierAddressDao.findbyMap(reparm);
	}

	@Override
	public List<SupplierAddress> fetchSupplierAddress(Map<String, Object> reparm) {
		return supplierAddressDao.fetchSupplierAddress(reparm);
	}

	@Override
	public void updatedefault(Map<String, Object> reparm) {
		supplierAddressDao.updatedefault(reparm);
		
	}

	@Override
	public void setdefault(Map<String, Object> reparm) {
		supplierAddressDao.setdefault(reparm);
		
	}

	@Override
	public EntityDao getEntityDao() {
		
		return this.supplierAddressDao;
	}

	

}
