/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SupplierDuration;
import com.wode.factory.supplier.query.SupplierDurationQuery;

import org.springframework.stereotype.Service;
import cn.org.rapid_framework.page.Page;

@Service("supplierDurationService")
public interface SupplierDurationService extends EntityService<SupplierDuration,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(SupplierDurationQuery query);
	
	/**
	 * 根据供应商id获取该供应商的对账单类型
	 * @param supplierId
	 * @return
	 */
	public SupplierDuration getBySupplierId(Long supplierId);
}
