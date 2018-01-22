package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SupplierExpress;
import com.wode.factory.supplier.dao.SupplierExpressDao;

public class SupplierExpressDaoImpl extends BaseDao<SupplierExpress,java.lang.Long> implements SupplierExpressDao {

	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierExpressMapper";
	}

	@Override
	public List<SupplierExpress> getBySupplierId(Long supplierId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce() + ".getBysupplierId", supplierId);
	}

	@Override
	public void deletebySupplierId(Long supplierId) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deletebySupplierId",supplierId);
	}

	@Override
	public void insert(SupplierExpress supplierExpress) {
		this.getSqlSession().insert(getIbatisMapperNamesapce()+".insert", supplierExpress);
		
	}

	@Override
	public void saveOrUpdate(SupplierExpress entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	

}
