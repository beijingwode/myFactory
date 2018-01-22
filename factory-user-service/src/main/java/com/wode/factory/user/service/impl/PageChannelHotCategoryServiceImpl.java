/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.StringUtils;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.PageChannelHotCategory;
import com.wode.factory.model.Product;
import com.wode.factory.model.ProductCategory;
import com.wode.factory.user.dao.PageChannelHotCategoryDao;
import com.wode.factory.user.dao.ProductCategoryDao;
import com.wode.factory.user.query.PageChannelHotCategoryQuery;
import com.wode.factory.user.service.PageChannelHotCategoryService;

@Service("pageChannelHotCategoryService")
public class PageChannelHotCategoryServiceImpl extends BaseService<PageChannelHotCategory,java.lang.Long> implements  PageChannelHotCategoryService{
	@Autowired
	@Qualifier("pageChannelHotCategoryDao")
	private PageChannelHotCategoryDao pageChannelHotCategoryDao;
	
	@Autowired
	@Qualifier("productCategoryDao")
	private ProductCategoryDao productCategoryDao;
	
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	private final static Logger log= LoggerFactory.getLogger(PageChannelHotCategory.class);
	
	public EntityDao getEntityDao() {
		return this.pageChannelHotCategoryDao;
	}
	
	public Page findPage(PageChannelHotCategoryQuery query) {
		return pageChannelHotCategoryDao.findPage(query);
	}

	@Override
	public ActResult<List<Map<String, List<Product>>>> selectProductByCategory(Long categoryId) {
		ActResult<List<Map<String, List<Product>>>> ar= new ActResult<List<Map<String, List<Product>>>>();
		List<Map<String, List<Product>>> list = new ArrayList<Map<String, List<Product>>>();
		Map<String, List<Product>> map = null;
		List<Product> plist = null;
		List<PageChannelHotCategory> pchcList = pageChannelHotCategoryDao.selectByChannelId(categoryId);
		if(pchcList.size()>3)
			pchcList = pchcList.subList(0, 3);
		for(PageChannelHotCategory pchc : pchcList){
			map = new HashMap<String, List<Product>>();
			plist = new ArrayList<Product>();
			ProductCategory productCategory = productCategoryDao.getById(pchc.getHotCategoryId());
			if(StringUtils.isNullOrEmpty(productCategory)){
				ar.setSuccess(false);
				ar.setMsg("分类ID："+pchc.getHotCategoryId()+" 不存在，请检查分类热销表t_pagechannel_hot_category中hot_category_id数据");
				continue;
			}
			String key = productCategory.getName();
			String productIdResult = redisUtil.getMapData(RedisConstant.HOT_SELL, pchc.getHotCategoryId().toString());
			if(StringUtils.isNullOrEmpty(productIdResult)){
				ar.setSuccess(false);
				ar.setMsg("分类ID："+pchc.getHotCategoryId()+" 在缓存中未查到相关数据");
				continue;
			}
			List<String> productIdList = JsonUtil.getList(productIdResult, String.class);
			for(String productId : productIdList){
				String productString = redisUtil.getMapData(RedisConstant.PRODUCT_PRE+productId, RedisConstant.PRODUCT_REDIS_INFO);
				if(StringUtils.isNullOrEmpty(productString)){
					ar.setSuccess(false);
					ar.setMsg("产品ID："+productId+" 在缓存中未查到相关数据");
					continue;
				}
				Product product = JsonUtil.getObject(productString, Product.class);
				if(plist.size()<5)
					plist.add(product);
				else
					break;
			}
			map.put(key, plist);
			list.add(map);
		}
		ar.setData(list);
		return ar;
	}
	
}
