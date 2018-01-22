/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierImageResource;
import com.wode.factory.supplier.query.SupplierImageResourceQuery;

public interface SupplierImageResourceDao extends  EntityDao<SupplierImageResource,Long>{
	public Page findPage(SupplierImageResource query);
	public void saveOrUpdate(SupplierImageResource entity);
	PageInfo<SupplierImageResourceQuery> selectPageInfo(SupplierImageResourceQuery imageResourceQuery);
	
	public PageInfo<SupplierImageResourceQuery> findDateGroupBy(SupplierImageResourceQuery imageResourceQuery);
	public List<SupplierImageResource> findPageImage(SupplierImageResource supplierImageResource);
}
