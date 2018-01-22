/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CollectionProduct;
import com.wode.factory.model.Product;

@Service("collectionProductService")
public interface CollectionProductService extends EntityService<CollectionProduct,Long>{

	public Boolean selectOne(Long userId, Long productId);

	public Integer selectProductCount(Long userId);

	public PageInfo<Product> selectAll(Long userId, Integer pages,Integer size);

	public Boolean canelCollectionProduct(Long userId, Collection<Long> productIds);

	public Boolean canelCollectionProduct(Long id, Long productId);
	
}
