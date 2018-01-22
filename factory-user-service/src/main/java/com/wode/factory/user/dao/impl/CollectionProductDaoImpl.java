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
import com.wode.factory.model.CollectionProduct;
import com.wode.factory.model.Product;
import com.wode.factory.user.dao.CollectionProductDao;
import com.wode.factory.user.query.CollectionProductQuery;

@Repository("collectionProductDao")
public class CollectionProductDaoImpl extends BaseDao<CollectionProduct,java.lang.Long> implements CollectionProductDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CollectionProductMapper";
	}
	
	public void saveOrUpdate(CollectionProduct entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public PageInfo<Product> findPage(CollectionProductQuery query) {
		List<Product> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
		return new PageInfo(list);
	}

	@Override
	public Boolean selectOne(Long userId, Long productId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("userId", userId);
		map.put("productId", productId);
		CollectionProduct cs = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findOne", map);
		Boolean b = false;
		if(cs!=null)
			b=true;
		return b;
	}

	@Override
	public Integer selectProductCount(Long userId) {
		Integer count = getSqlSession().selectOne(getIbatisMapperNamesapce()+".findProductCount",userId);
		return count;
	}

	@Override
	public List<Product> selectAll(Long userId, Integer pages, int sizes) {
		List<Product> list = new ArrayList<Product>();
		String sqlComment = "select a.* from t_product a "
				+ "left join t_collection_product b on a.id = b.product_id where b.user_id="+userId+" order by b.creat_time desc "
				+ "limit "+(pages==1?0:(pages*sizes-sizes))+","+sizes;
		list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findProduct",sqlComment);
		return list;
	}

	@Override
	public Boolean canelCollectionProduct(Long userId, Collection<Long> productId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("productId", productId);
		int cs = getSqlSession().delete(getIbatisMapperNamesapce()+".batchDelete", map);
		Boolean b = false;
		if(cs>0)
			b=true;
		return b;
	}

	@Override
	public Boolean canelCollectionProduct(Long userId, Long productId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("productId", productId);
		int cs = getSqlSession().delete(getIbatisMapperNamesapce()+".cancel", map);
		Boolean b = false;
		if(cs>0)
			b=true;
		return b;
	}
	
}
