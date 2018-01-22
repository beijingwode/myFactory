/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.CollectionShop;
import com.wode.factory.model.Shop;
import com.wode.factory.user.dao.CollectionShopDao;
import com.wode.factory.user.query.CollectionShopQuery;

@Repository("collectionShopDao")
public class CollectionShopDaoImpl extends BaseDao<CollectionShop,java.lang.Long> implements CollectionShopDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CollectionShopMapper";
	}
	
	public void saveOrUpdate(CollectionShop entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public PageInfo<Shop> findPage(CollectionShopQuery query) {
		List<Shop> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
		return new PageInfo(list);
	}

	@Override
	public Boolean selectOne(Long userId, Long shopId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("shopId", shopId);
		CollectionShop cs = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findOne", map);
		Boolean b = false;
		if(cs!=null)
			b=true;
		return b;
	}
	
	@Override
	public Integer selectShopCount(Long userId) {
		Integer count = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findShopCount",userId);
		return count;
	}

	@Override
	public List<Shop> selectAll(Long userId, Integer pages,Integer sizes) {
		List<Shop> list = new ArrayList<Shop>();
		String sqlComment = "select a.* from t_shop a "
				+ "left join t_collection_shop b on a.id = b.shop_id where b.user_id="+userId+" order by b.creat_time desc "
				+ "limit "+(pages==1?0:(pages*sizes-sizes))+","+sizes;
		list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findStore",sqlComment);
		return list;
	}

	@Override
	public Boolean canelCollectionShop(Long userId, Long shopId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("shopId", shopId);
		int cs = getSqlSession().delete(getIbatisMapperNamesapce()+".cancel", map);
		Boolean b = false;
		if(cs>0)
			b=true;
		return b;
	}

	@Override
	public Boolean canelCollectionShop(Long userId, Collection<Long> shopId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("shopId", shopId);
		int cs = getSqlSession().delete(getIbatisMapperNamesapce()+".batchDelete", map);
		Boolean b = false;
		if(cs>0)
			b=true;
		return b;
	}

	@Override
	public List<CollectionShop> selectByShopId(Long shopId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByShopId", shopId);
	}

}
