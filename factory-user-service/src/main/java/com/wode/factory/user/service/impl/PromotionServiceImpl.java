/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Promotion;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.dao.PromotionDao;
import com.wode.factory.user.query.PromotionQuery;
import com.wode.factory.user.service.PromotionService;

@Service("promotionService")
public class PromotionServiceImpl extends BaseService<Promotion,java.lang.Long> implements  PromotionService{
	@Autowired
	@Qualifier("promotionDao")
	private PromotionDao promotionDao;
	
	public EntityDao getEntityDao() {
		return this.promotionDao;
	}
	
	public Page findPage(PromotionQuery query) {
		return promotionDao.findPage(query);
	}

	@Override
	public Integer findAllCount(Map<String, Object> map) {
		return promotionDao.findAllCount(map);
	}

	@Override
	public List<Promotion> findByMap(Map<String, Object> map) {
		return promotionDao.findByMap(map);
	}

	@Override
	public Integer findAllProductCount(Map<String, Object> map) {
		return promotionDao.findAllProductCount(map);
	}

	@Override
	public List<PromotionProduct> findProductByMap(Map<String, Object> map) {
		return promotionDao.findProductByMap(map);
	}
	
}
