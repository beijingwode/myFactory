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
import com.wode.factory.model.SupplierSkuImage;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;

public interface SupplierSkuImageDao extends  EntityDao<SupplierSkuImage,Long>{
	public Page findPage(SupplierSkuImage query);
	public void saveOrUpdate(SupplierSkuImage entity);
	/**
	 * @param skuImageQuery
	 * @return
	 */
	PageInfo<SupplierSkuImageQuery> selectPageInfo(SupplierSkuImageQuery skuImageQuery);
	
	public void insert(SupplierSkuImage skuImage);
}
