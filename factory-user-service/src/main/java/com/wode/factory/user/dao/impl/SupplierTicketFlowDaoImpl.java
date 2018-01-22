/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.BaseDao;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.SupplierTicketFlow;
import com.wode.factory.user.dao.SupplierTicketFlowDao;

@Repository("supplierTicketFlowDao")
public class SupplierTicketFlowDaoImpl extends BaseDao<SupplierTicketFlow,java.lang.Long> implements SupplierTicketFlowDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierTicketFlowMapper";
	}
	
	public void saveOrUpdate(SupplierTicketFlow entity){
		if(StringUtils.isNullOrEmpty(entity.getId())) 
			save(entity);
		else 
			update(entity);
	}
}
