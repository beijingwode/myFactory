/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.wode.factory.supplier.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.company.query.EnterpriseUserVo;
import com.wode.factory.model.SupplierSkuImage;
import com.wode.factory.supplier.query.SupplierSkuImageQuery;

@Service("supplierSkuImageService")
public interface SupplierSkuImageService extends EntityService<SupplierSkuImage,Long>{
	
	public EntityDao getEntityDao() ;
	
	public PageInfo<SupplierSkuImageQuery> selectPageInfo(SupplierSkuImageQuery skuImageQuery);
	public void insert(SupplierSkuImage skuImage);
}
