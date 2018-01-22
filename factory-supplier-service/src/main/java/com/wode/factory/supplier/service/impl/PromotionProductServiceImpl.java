/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.BaseService;

import cn.org.rapid_framework.page.Page;

import com.wode.factory.model.PromotionProduct;
import com.wode.factory.supplier.dao.PromotionProductDao;
import com.wode.factory.supplier.query.PromotionProductQuery;
import com.wode.factory.supplier.service.PromotionProductService;

@Service("promotionProductService")
public class PromotionProductServiceImpl extends BaseService<PromotionProduct,java.lang.Long> implements  PromotionProductService{
	@Autowired
	@Qualifier("promotionProductDao")
	private PromotionProductDao promotionProductDao;
	
	public EntityDao getEntityDao() {
		return this.promotionProductDao;
	}
	
	public Page findPage(PromotionProductQuery query) {
		return promotionProductDao.findPage(query);
	}
	
	/**
	 * 根据商品SKU查询正在参加活动的商品列表（包括待审核、审核中、已通过）
	 */
	public List<PromotionProduct> findPromotionProductBySKU(Long skuID) {
		return promotionProductDao.findPromotionProductBySKU(skuID);
	}
}
