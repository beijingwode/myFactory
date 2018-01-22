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
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.supplier.dao.SupplierDurationDao;
import com.wode.factory.supplier.query.SupplierDurationQuery;

@Repository("supplierDurationDao")
public class SupplierDurationDaoImpl extends BaseDao<SupplierDuration,java.lang.Long> implements SupplierDurationDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierDurationMapper";
	}
	
	public void saveOrUpdate(SupplierDuration entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SupplierDurationQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

	/**
	 * 根据供应商id获取该供应商的对账单类型
	 * @param supplierId
	 * @return
	 */
	public SupplierDuration getBySupplierId(Long supplierId){
		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".getBySupplierId",supplierId);
	}
}
