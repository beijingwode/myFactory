/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.user.dao.ProductDetailListDao;
import com.wode.factory.user.query.ProductDetailListQuery;
import com.wode.factory.user.service.ProductDetailListService;

@Service("productDetailListService")
public class ProductDetailListServiceImpl extends BaseService<ProductDetailList,java.lang.Long> implements  ProductDetailListService{
	@Autowired
	@Qualifier("productDetailListDao")
	private ProductDetailListDao productDetailListDao;
	
	public EntityDao getEntityDao() {
		return this.productDetailListDao;
	}
	
	public Page findPage(ProductDetailListQuery query) {
		return productDetailListDao.findPage(query);
	}

	@Override
	public List<ProductDetailList> selectByProductId(Long productId) {
		return productDetailListDao.findByProductId(productId);
	}
	
}
