/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CollectionProduct;
import com.wode.factory.model.Product;
import com.wode.factory.user.dao.CollectionProductDao;
import com.wode.factory.user.query.CollectionProductQuery;
import com.wode.factory.user.service.CollectionProductService;

@Service("collectionProductService")
public class CollectionProductServiceImpl extends BaseService<CollectionProduct,java.lang.Long> implements  CollectionProductService{
	@Autowired
	@Qualifier("collectionProductDao")
	private CollectionProductDao collectionProductDao;
	
	public EntityDao getEntityDao() {
		return this.collectionProductDao;
	}
	
	@Override
	public Boolean selectOne(Long userId, Long productId) {
		Boolean b = collectionProductDao.selectOne(userId,productId);
		return b;
	}

	@Override
	public Integer selectProductCount(Long userId) {
		return collectionProductDao.selectProductCount(userId);
	}

	@Override
	public PageInfo<Product> selectAll(Long userId, Integer pages,Integer sizes) {
		if(pages==null || pages==0){
			pages = 1;
		}
		if(sizes==null||sizes<1){
			sizes = 10;
		}
		CollectionProductQuery query = new CollectionProductQuery();
		query.setUserId(userId);
		query.setPageNumber(pages);
		query.setPageSize(sizes);
		return collectionProductDao.findPage(query);
	}

	@Override
	public Boolean canelCollectionProduct(Long userId, Collection<Long> productId) {
		return collectionProductDao.canelCollectionProduct(userId,productId);
	}

	@Override
	public Boolean canelCollectionProduct(Long userId, Long productId) {
		boolean a = this.selectOne(userId,productId);
		if(!a){
			return true;
		}
		return collectionProductDao.canelCollectionProduct(userId,productId);
	}

}
