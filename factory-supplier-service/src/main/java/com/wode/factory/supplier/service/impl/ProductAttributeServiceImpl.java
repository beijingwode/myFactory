/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductAttribute;
import com.wode.factory.supplier.dao.ProductAttributeDao;
import com.wode.factory.supplier.query.ProductAttributeQuery;
import com.wode.factory.supplier.service.ProductAttributeService;

@Service("productAttributeService")
public class ProductAttributeServiceImpl extends BaseService<ProductAttribute,java.lang.Long> implements  ProductAttributeService{
	@Autowired
	@Qualifier("productAttributeDao")
	private ProductAttributeDao productAttributeDao;
	
	public EntityDao getEntityDao() {
		return this.productAttributeDao;
	}
	
	public Page findPage(ProductAttributeQuery query) {
		return productAttributeDao.findPage(query);
	}
	
	/**
	 * 根据类型id获取该类型所有的属性及其属性值
	 * @param map（typeid:商品类型id）
	 * @return
	 */
	public List<ProductAttribute> getAttributelistByTypeid(Map map){
		return productAttributeDao.getAttributelistByTypeid(map);
	}
	
	/**
	 * 删除该商品对应的所有属性
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		productAttributeDao.removeAllByProductid(map);
	}

	@Override
	public List<ProductAttribute> getByProductId(Long productId) {
		
		return productAttributeDao.getByProductId(productId);
	}
	
}
