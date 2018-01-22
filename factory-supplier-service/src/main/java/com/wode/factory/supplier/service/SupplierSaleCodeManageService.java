/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.SupplierSaleCodeManage;
import com.wode.factory.supplier.query.SupplierSaleCodeManageQuery;

import org.springframework.stereotype.Service;
import cn.org.rapid_framework.page.Page;

@Service("supplierSaleCodeManageService")
public interface SupplierSaleCodeManageService extends EntityService<SupplierSaleCodeManage,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(SupplierSaleCodeManageQuery query);
	
}
