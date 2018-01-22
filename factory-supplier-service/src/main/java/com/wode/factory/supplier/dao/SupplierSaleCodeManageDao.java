/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierSaleCodeManage;
import com.wode.factory.supplier.query.SupplierSaleCodeManageQuery;

public interface SupplierSaleCodeManageDao extends  EntityDao<SupplierSaleCodeManage,Long>{
	public Page findPage(SupplierSaleCodeManageQuery query);
	public void saveOrUpdate(SupplierSaleCodeManage entity);

}
