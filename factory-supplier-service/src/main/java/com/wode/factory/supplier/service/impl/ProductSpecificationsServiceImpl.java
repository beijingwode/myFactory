/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.common.redis.RedisUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.model.ProductSpecifications;
import com.wode.factory.supplier.dao.ProductSpecificationsDao;
import com.wode.factory.supplier.query.ProductSpecificationsQuery;
import com.wode.factory.supplier.service.ProductSpecificationsService;

@Service("productSpecificationsService")
public class ProductSpecificationsServiceImpl extends BaseService<ProductSpecifications,java.lang.Long> implements  ProductSpecificationsService{
	@Autowired
	@Qualifier("productSpecificationsDao")
	private ProductSpecificationsDao productSpecificationsDao;
	
	@Autowired
	private RedisUtil redis;
	
	public EntityDao getEntityDao() {
		return this.productSpecificationsDao;
	}
	
	public Page findPage(ProductSpecificationsQuery query) {
		return productSpecificationsDao.findPage(query);
	}
	
	/**
	 * 删除该商品对应的所有sku（isDelete=1）
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		productSpecificationsDao.removeAllByProductid(map);
	}

	@Override
	public List<ProductSpecifications> getPagelist(Map map) {
		return productSpecificationsDao.getPagelist(map);
	}
	
	/**
	 * 获取所有的条数
	 * @param map
	 * @return
	 */
	public Integer getAllCount(Map map){
		return productSpecificationsDao.getAllCount(map);
	}
	
	/**
	 * 获取sku实体对象
	 * @param id
	 * @return
	 */
	public ProductSpecifications getProductSpecificationsById(Long id){
		return productSpecificationsDao.getProductSpecificationsById(id);
	}
	
	/**
	 * 获取sku列表
	 * 获取商品的全部skuid
	 * @param map
	 * @return
	 */
	public List<ProductSpecifications> getlistByProductid(Long id){
		return productSpecificationsDao.getlistByProductid(id);
	}

	@Override
	public List<ProductSpecifications> getProductSpecificationsByProductId(Map map) {
		List<ProductSpecifications> productSpecificationsList = productSpecificationsDao.getProductSpecificationsByProductId(map);
		for (ProductSpecifications productSpecifications : productSpecificationsList) {
			String stock = redis.getData(RedisConstant.REDIS_SKU_INVENTORY + productSpecifications.getId());
			if (StringUtils.isNotBlank(stock))
				productSpecifications.setQuantity(Integer.valueOf(stock));
		}
		return productSpecificationsList;
		
	}

	@Override
	public List<ProductSpecifications> findSkuByProductNameAndItemValue(Map map) {
		return productSpecificationsDao.findSkuByProductNameAndItemValue(map);
	}
 
}
