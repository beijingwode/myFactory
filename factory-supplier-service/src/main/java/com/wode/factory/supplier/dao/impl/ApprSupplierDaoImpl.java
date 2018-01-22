/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ApprSupplier;
import com.wode.factory.supplier.dao.ApprSupplierDao;

@Repository("apprSupplierDao")
public class ApprSupplierDaoImpl extends BasePageDaoImpl<ApprSupplier> implements ApprSupplierDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ApprSupplierMapper";
	}
	
	@Override
	public Long getId(ApprSupplier model) {
		return model.getId();
	}
	
	public ApprSupplier getSupplierApprIng(Long supplierId) {
		List<ApprSupplier> lst = this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getSupplierApprIng",supplierId);
		if(lst!=null && !lst.isEmpty()) return lst.get(0);
		
		return null;
	}
}
