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
import com.wode.factory.model.SupplierTransfer;
import com.wode.factory.supplier.dao.SupplierTransferDao;

@Repository("supplierTransferDao")
public class SupplierTransferDaoImpl extends BasePageDaoImpl<SupplierTransfer> implements SupplierTransferDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierTransferMapper";
	}
	
	@Override
	public Long getId(SupplierTransfer model) {
		return model.getId();
	}
	public SupplierTransfer getApprIng(Long supplierId) {
		List<SupplierTransfer> lst = this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getApprIng",supplierId);
		if(lst!=null && !lst.isEmpty()) return lst.get(0);
		
		return null;
	}
}
