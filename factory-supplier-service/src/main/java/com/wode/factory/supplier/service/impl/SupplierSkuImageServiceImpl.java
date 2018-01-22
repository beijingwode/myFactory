/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.SupplierSkuImage;
import com.wode.factory.supplier.dao.SupplierSkuImageDao;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;
import com.wode.factory.supplier.service.SupplierSkuImageService;

@Service("supplierSkuImageService")
public class SupplierSkuImageServiceImpl extends BaseService<SupplierSkuImage,java.lang.Long> implements  SupplierSkuImageService{
	@Autowired
	@Qualifier("supplierSkuImageDao")
	private SupplierSkuImageDao supplierSkuImageDao;
	
	public EntityDao getEntityDao() {
		return this.supplierSkuImageDao;
	}

	@Override
	public PageInfo<SupplierSkuImageQuery> selectPageInfo(SupplierSkuImageQuery skuImageQuery) {
		return this.supplierSkuImageDao.selectPageInfo(skuImageQuery);
	}

	@Override
	public void insert(SupplierSkuImage skuImage) {
		// TODO Auto-generated method stub
	this.supplierSkuImageDao.insert(skuImage);	
	}
	
}
