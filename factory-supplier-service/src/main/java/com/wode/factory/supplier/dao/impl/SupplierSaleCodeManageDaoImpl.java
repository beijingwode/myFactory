/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SupplierSaleCodeManage;
import com.wode.factory.supplier.dao.SupplierSaleCodeManageDao;
import com.wode.factory.supplier.query.SupplierSaleCodeManageQuery;

@Repository("supplierSaleCodeManageDao")
public class SupplierSaleCodeManageDaoImpl extends BaseDao<SupplierSaleCodeManage,java.lang.Long> implements SupplierSaleCodeManageDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierSaleCodeManageMapper";
	}
	
	public void saveOrUpdate(SupplierSaleCodeManage entity){
		if(entity.getSupplierId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SupplierSaleCodeManageQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
