/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.util.ActResult;
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Shop;
import com.wode.factory.user.dao.CollectionShopDao;
import com.wode.factory.user.query.CollectionShopQuery;
import com.wode.factory.user.service.CollectionShopService;

import cn.org.rapid_framework.page.Page;

@Service("collectionShopService")
public class CollectionShopServiceImpl extends BaseService<CollectionShop,java.lang.Long> implements  CollectionShopService{
	@Autowired
	@Qualifier("collectionShopDao")
	private CollectionShopDao collectionShopDao;
	
	public EntityDao getEntityDao() {
		return this.collectionShopDao;
	}
	
	@Override
	public Boolean selectOne(Long userId, Long shopId) {
		Boolean b = collectionShopDao.selectOne(userId,shopId);
		return b;
	}
	
	@Override
	public PageInfo<Shop> selectAll(Long userId,Integer pages,Integer sizes) {
		if(pages==null || pages==0){
			pages = 1;
		}
		if(sizes==null||sizes<1){
			sizes = 10;
		}
		CollectionShopQuery query = new CollectionShopQuery();
		query.setUserId(userId);
		query.setPageNumber(pages);
		query.setPageSize(sizes);
		PageInfo<Shop> page = collectionShopDao.findPage(query);
		for(int i=0;i<page.getList().size();i++){
			Shop ss = page.getList().get(i);
			page.getList().remove(i);
			List<CollectionShop> list = collectionShopDao.selectByShopId(ss.getId());
			ss.setCollectionNum(list.size());
			page.getList().add(i, ss);
		}
		return page;
	}

	//@Override
	public ActResult<Page<Shop>> selectAllWeb(Long userId,Integer pages,Integer sizes) {
		ActResult<Page<Shop>> ar = new ActResult<Page<Shop>>();
		List<Shop> list = collectionShopDao.selectAll(userId,pages,sizes);
		if(list.size()<1){
			ar.setData(null);
			ar.setSuccess(false);
		}else{
			int pageSizes = list.size()%sizes!=0?list.size()%sizes+1:list.size()/sizes;
			Page<Shop> page = new Page<Shop>(1, pageSizes,list.size(), list);
			ar.setData(page);
		}
		return ar;
	}
	
	@Override
	public Integer selectShopCount(Long userId) {
		return collectionShopDao.selectShopCount(userId);
	}

	@Override
	public Boolean canelCollectionShop(Long userId, Long shopId) {
		return collectionShopDao.canelCollectionShop(userId,shopId);
	}

	@Override
	public Boolean canelCollectionShop(Long userId, Collection<Long> shopId) {
		return collectionShopDao.canelCollectionShop(userId,shopId);
	}

	@Override
	public List<CollectionShop> selectByShopId(Long shopId) {
		return collectionShopDao.selectByShopId(shopId);
	}
	
}
