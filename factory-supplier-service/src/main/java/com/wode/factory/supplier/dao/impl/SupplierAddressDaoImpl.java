package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SupplierAddress;
import com.wode.factory.supplier.dao.SupplierAddressDao;
import com.wode.factory.supplier.query.SupplierAddressQuery;

import cn.org.rapid_framework.page.Page;

@Repository("SupplierAddressDao")
public class SupplierAddressDaoImpl extends BaseDao<SupplierAddress,java.lang.Long> implements SupplierAddressDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierAddressMapper";
	}
	
	
	@Override
	public Page findPage(SupplierAddressQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public void saveOrUpdate(SupplierAddress entity) {
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
		
	}

	@Override
	public List<SupplierAddress> findbyMap(Map<String, Object> reparm) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findbyMap",reparm);
	}

	@Override
	public void updatedefault(Map<String, Object> reparm) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".updatedefault",reparm);
		
	}

	@Override
	public void setdefault(Map<String, Object> reparm) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".setdefault",reparm);
		
	}

	@Override
	public List<SupplierAddress> fetchSupplierAddress(Map<String, Object> reparm) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".fetchSupplierAddress",reparm);
	}

}
