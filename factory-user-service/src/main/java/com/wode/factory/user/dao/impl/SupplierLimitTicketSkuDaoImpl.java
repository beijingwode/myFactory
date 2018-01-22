/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.SupplierLimitTicketSku;
import com.wode.factory.user.dao.SupplierLimitTicketSkuDao;

@Repository("supplierLimitTicketSkuDao")
public class SupplierLimitTicketSkuDaoImpl extends FactoryBaseDaoImpl<SupplierLimitTicketSku> implements SupplierLimitTicketSkuDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "SupplierLimitTicketSkuMapper";
	}

	@Override
	public List<SupplierLimitTicketSku> getBySkuId(Long skuId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getBySkuId",skuId);
	}

	@Override
	public List<SupplierLimitTicketSku> getByLimitTicketId(Long id) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getByLimitTicketId",id);
	}
	
}
