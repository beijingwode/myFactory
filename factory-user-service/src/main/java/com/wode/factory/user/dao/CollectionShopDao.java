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
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Shop;
import com.wode.factory.user.query.CollectionShopQuery;

public interface CollectionShopDao extends  EntityDao<CollectionShop,Long>{
	public PageInfo<Shop> findPage(CollectionShopQuery query);
	public void saveOrUpdate(CollectionShop entity);
	public Boolean selectOne(Long userId, Long shopId);
	List<Shop> selectAll(Long userId, Integer pages, Integer sizes);
	Integer selectShopCount(Long userId);
	public Boolean canelCollectionShop(Long userId, Long shopId);
	public Boolean canelCollectionShop(Long userId, Collection<Long> shopId);
	public List<CollectionShop> selectByShopId(Long shopId);

}
