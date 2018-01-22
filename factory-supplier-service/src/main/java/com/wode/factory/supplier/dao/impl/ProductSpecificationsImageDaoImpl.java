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
import com.wode.factory.model.ProductSpecificationsImage;
import com.wode.factory.supplier.dao.ProductSpecificationsImageDao;
import com.wode.factory.supplier.query.ProductSpecificationsImageQuery;

@Repository("productSpecificationsImageDao")
public class ProductSpecificationsImageDaoImpl extends BaseDao<ProductSpecificationsImage,java.lang.Long> implements ProductSpecificationsImageDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductSpecificationsImageMapper";
	}
	
	public void saveOrUpdate(ProductSpecificationsImage entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductSpecificationsImageQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据productSpecificationsId删除图片信息
	 */
	public void removeByMap(Map map){
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeByMap",map);
	}

	@Override
	public List<ProductSpecificationsImage> getSkuImglistByProductId(Long productId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getSkuImglistByProductId", productId);
	}

	@Override
	public List<ProductSpecificationsImage> getByProductId(Long specificationsId) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId", specificationsId);
	}

	@Override
	public void deleteApprRelation(Long productId) {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteApprRelation", productId);
		
	}
}
