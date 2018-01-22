/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.Collection;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CollectionProduct;
import com.wode.factory.model.Product;
import com.wode.factory.user.query.CollectionProductQuery;

public interface CollectionProductDao extends  EntityDao<CollectionProduct,Long>{
	public PageInfo<Product> findPage(CollectionProductQuery query);
	public void saveOrUpdate(CollectionProduct entity);
	public Boolean selectOne(Long userId, Long productId);
	public Integer selectProductCount(Long userId);
	public List<Product> selectAll(Long userId, Integer pages, int sizes);
	public Boolean canelCollectionProduct(Long userId, Collection<Long> productId);
	public Boolean canelCollectionProduct(Long userId, Long productId);

}
