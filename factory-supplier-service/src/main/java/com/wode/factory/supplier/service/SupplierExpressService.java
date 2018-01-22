package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.factory.model.SupplierExpress;

public interface SupplierExpressService {

	public List<SupplierExpress> getBySupplierId(Long supplierId);
	public void deletebySupplierId(Long supplierId);
	public void insert(SupplierExpress supplierExpress);
	
}
