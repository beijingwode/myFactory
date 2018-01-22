/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.dao.ProductDetailListDao;
import com.wode.factory.supplier.query.ProductDetailListQuery;

@Repository("productDetailListDao")
public class ProductDetailListDaoImpl extends BaseDao<ProductDetailList,java.lang.Long> implements ProductDetailListDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductDetailListMapper";
	}
	
	public void saveOrUpdate(ProductDetailList entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductDetailListQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据商品id获取该商品的清单列表
	 * @param map
	 * @return
	 */
	public List<ProductDetailList> getProductdetaillistByProductid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getProductdetaillistByProductid",map);
	}
	
	/**
	 * 删除该商品对应的所有清单list
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		 this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeAllByProductid",map);
	}

	@Override
	public List<ProductDetailList> getByProductId(Long productId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId",productId);
	}

}
