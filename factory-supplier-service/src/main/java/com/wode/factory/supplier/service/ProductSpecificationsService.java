/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.supplier.query.ProductSpecificationsQuery;

public interface ProductSpecificationsService extends EntityService<ProductSpecifications,Long>{
	
	
	
	public Page findPage(ProductSpecificationsQuery query);
	
	/**
	 * 删除该商品对应的所有sku（isDelete=1）
	 * @param productid
	 */
	public void removeAllByProductid(Map map);
	
	/**
	 * 获取所有的条数
	 * @param map
	 * @return
	 */
	public Integer getAllCount(Map map);
	
	/**
	 * 获取sku列表
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getPagelist(Map map);
	
	/**
	 * 
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

	/**
	 * 根据productid查询所有sku列表
	 * @param id
	 * @return
	 */
	public List<ProductSpecifications> getProductSpecificationsByProductId(Map map);
	/**
	 * 根据productName和规格值集查询sku信息
	 * @param productSpecificationMap
	 * @return
	 */
	public List<ProductSpecifications> findSkuByProductNameAndItemValue(Map productSpecificationMap);
}
