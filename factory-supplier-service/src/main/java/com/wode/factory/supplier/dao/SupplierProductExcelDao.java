/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.dao;

import cn.org.rapid_framework.page.Page;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierProductExcel;
import com.wode.factory.supplier.query.SupplierProductExcelQuery;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;

public interface SupplierProductExcelDao extends  EntityDao<SupplierProductExcel,Long>{
	public Page findPage(SupplierProductExcel query);
	public void saveOrUpdate(SupplierProductExcel entity);
	/**
	 * @param productExcelQuery
	 * @return
	 */
	PageInfo<SupplierProductExcelQuery> selectPageInfo(SupplierProductExcelQuery productExcelQuery);
	
	public SupplierProductExcel getUndisposedByTimeAsc();
}
