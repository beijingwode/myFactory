/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.supplier.query.ProductSpecificationsQuery;

public interface ProductSpecificationsDao extends  EntityDao<ProductSpecifications,Long>{
	public Page findPage(ProductSpecificationsQuery query);
	public void saveOrUpdate(ProductSpecifications entity);

	/**
	 * 删除该商品对应的所有sku（isDelete=1）
	 * @param productid
	 */
	public void removeAllByProductid(Map map);
	
	/**
	 * 获取sku列表
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getPagelist(Map map);
	
	/**
	 * 获取所有的条数
	 * @param map
	 * @return
	 */
	public Integer getAllCount(Map map);
	
	/**
	 * 获取sku实体对象
	 * @param id
	 * @return
	 */
	public ProductSpecifications getProductSpecificationsById(Long id);
	
	/**
	 * 获取sku列表
	 * 获取商品的全部skuid
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getlistByProductid(Long id);
	public void insert(ProductSpecifications productSpecifications);
	/**
	 * 删除在编辑数据，删除其关联的信息
	 * @param productId
	 */
	public void deleteApprRelation(Long productId);
	
	/**
	 * 获取sku列表
	 * 根据productid获取商品的全部skuid
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getProductSpecificationsByProductId(Map map);
	/**
	 * 根据productName和规格值集查询sku信息
	 * @param productSpecificationMap
	 * @return
	 */
	public List<ProductSpecifications> findSkuByProductNameAndItemValue(Map map);
}
