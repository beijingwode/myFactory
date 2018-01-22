package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SupplierAddress;
import com.wode.factory.supplier.query.SupplierAddressQuery;

import cn.org.rapid_framework.page.Page;

public interface SupplierAddressService extends EntityService<SupplierAddress,Long>{
	public EntityDao getEntityDao() ;
	
	public Page findPage(SupplierAddressQuery query);

	public List<SupplierAddress> findbyMap(Map<String, Object> reparm);
	public List<SupplierAddress> fetchSupplierAddress(Map<String, Object> reparm);

	public void updatedefault(Map<String, Object> reparm);

	public void setdefault(Map<String, Object> reparm);

}
