/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.supplier.dao.ProductSpecificationsImageDao;
import com.wode.factory.supplier.query.ProductSpecificationsImageQuery;
import com.wode.factory.supplier.service.ProductSpecificationsImageService;

import cn.org.rapid_framework.page.Page;

@Service("productSpecificationsImageService")
public class ProductSpecificationsImageServiceImpl extends BaseService<ProductSpecificationsImage,java.lang.Long> implements  ProductSpecificationsImageService{
	@Autowired
	@Qualifier("productSpecificationsImageDao")
	private ProductSpecificationsImageDao productSpecificationsImageDao;
	
	public EntityDao getEntityDao() {
		return this.productSpecificationsImageDao;
	}
	
	public Page findPage(ProductSpecificationsImageQuery query) {
		return productSpecificationsImageDao.findPage(query);
	}
	/**
	 * 根据productSpecificationsId删除图片信息
	 */
	public void removeByMap(Map map){
		productSpecificationsImageDao.removeByMap(map);
	}

	@Override
	public List<ProductSpecificationsImage> getSkuImglistByProductId(Long productId) {
		return productSpecificationsImageDao.getSkuImglistByProductId(productId);
	}

	@Override
	public List<ProductSpecificationsImage> getByProductId(Long specificationsId) {
		
		return productSpecificationsImageDao.getByProductId(specificationsId);
	}
	
}
