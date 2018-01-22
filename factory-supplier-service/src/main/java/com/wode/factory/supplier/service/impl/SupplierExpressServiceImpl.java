package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wode.factory.model.SupplierExpress;
import com.wode.factory.supplier.dao.SupplierExpressDao;
import com.wode.factory.supplier.service.SupplierExpressService;
@Service
public class SupplierExpressServiceImpl implements SupplierExpressService {
    @Autowired
    private SupplierExpressDao supplierExpressDao;
	@Override
	public List<SupplierExpress> getBySupplierId(Long supplierId) {
		
		return supplierExpressDao.getBySupplierId(supplierId);
	}

	@Override
	public void deletebySupplierId(Long supplierId) {
		supplierExpressDao.deletebySupplierId(supplierId);

	}

	@Override
	public void insert(SupplierExpress supplierExpress) {
		
		supplierExpressDao.insert(supplierExpress);
	}

}
