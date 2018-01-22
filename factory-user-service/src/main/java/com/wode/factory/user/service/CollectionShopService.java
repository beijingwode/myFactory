/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Shop;

@Service("collectionShopService")
public interface CollectionShopService extends EntityService<CollectionShop,Long>{
		
	public Boolean selectOne(Long userId, Long shopId);

	PageInfo<Shop> selectAll(Long userId, Integer pages,Integer sizes);

	//ActResult<Page<ShopSetting>> selectAllWeb(Long userId, Integer pages,Integer sizes);
	
	public Integer selectShopCount(Long userId);

	public Boolean canelCollectionShop(Long userId, Long shopId);

	public Boolean canelCollectionShop(Long userId, Collection<Long> li);

	public List<CollectionShop> selectByShopId(Long shopId);

}
