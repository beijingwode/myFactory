/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.dao.PromotionProductDao;
import com.wode.factory.user.query.PromotionProductQuery;
import com.wode.factory.user.service.PromotionProductService;
import com.wode.factory.user.vo.PromotionProductVo;

@Service("promotionProductService")
public class PromotionProductServiceImpl extends BaseService<PromotionProduct,java.lang.Long> implements  PromotionProductService{
	@Autowired
	@Qualifier("promotionProductDao")
	private PromotionProductDao promotionProductDao;
	
	public EntityDao getEntityDao() {
		return this.promotionProductDao;
	}
	
	@QueryCached(timeout=60*60*2)
	public PageInfo<PromotionProductVo> findPage(PromotionProductQuery query) {
		return promotionProductDao.findPage(query);
	}

	@Override
	public PromotionProductVo findById(PromotionProductQuery query) {
		return promotionProductDao.findById(query);
	}

	@Override
	public PromotionProduct findByAttribute(PromotionProductQuery query) {
		return promotionProductDao.findByAttribute(query);
	}

	@Override
	public PromotionProduct findByCare(PromotionProductQuery query) {
		return promotionProductDao.findByCare(query);
	}

	@Override
	public PromotionProductVo findOneCare(PromotionProductQuery query) {
		return promotionProductDao.findOneCare(query);
	}
}
