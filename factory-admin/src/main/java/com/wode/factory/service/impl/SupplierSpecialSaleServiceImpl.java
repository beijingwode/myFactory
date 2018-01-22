package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SupplierSpecialSaleDao;
import com.wode.factory.model.SupplierSpecialSale;
import com.wode.factory.service.SupplierSpecialSaleService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("supplierSpecialSaleService")
public class SupplierSpecialSaleServiceImpl extends FactoryEntityServiceImpl<SupplierSpecialSale> implements SupplierSpecialSaleService {
	@Autowired
	SupplierSpecialSaleDao dao;
	
	@Override
	public SupplierSpecialSaleDao getDao() {
		return dao;
	}


	@Override
	public Long getId(SupplierSpecialSale entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierSpecialSale entity, Long id) {
		entity.setId(id);
	}
}
