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

import com.wode.common.frame.base.BaseService;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.supplier.dao.ProductSpecificationValueDao;
import com.wode.factory.supplier.query.ProductSpecificationValueQuery;
import com.wode.factory.supplier.service.ProductSpecificationValueService;

import cn.org.rapid_framework.page.Page;

@Service("productSpecificationValueService")
public class ProductSpecificationValueServiceImpl extends BaseService<ProductSpecificationValue,java.lang.Long> implements  ProductSpecificationValueService{
	@Autowired
	@Qualifier("productSpecificationValueDao")
	private ProductSpecificationValueDao productSpecificationValueDao;
	
	public ProductSpecificationValueDao getEntityDao() {
		return this.productSpecificationValueDao;
	}
	
	public Page findPage(ProductSpecificationValueQuery query) {
		return productSpecificationValueDao.findPage(query);
	}
	
	/**
	 * 删除该商品对应的所有规格（isDelete=1）
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		productSpecificationValueDao.removeAllByProductid(map);
	}
	
	/**
	 * 商品list
	 * @param productid
	 */
	public List<ProductSpecificationValue> findAllBymap(Map map){
		return productSpecificationValueDao.findAllBymap(map);
	}

	@Override
	public int copyFromOther(Long productId, Long oId, Long nId) {
		return productSpecificationValueDao.copyFromOther(productId, oId, nId);
	}

	@Override
	public int updateFromOther(Long productId, Long oId, Long nId) {
		return productSpecificationValueDao.updateFromOther(productId, oId, nId);
	}

	@Override
	public List<ProductSpecificationValue> getByProductId(Long productId) {
		return productSpecificationValueDao.getByProductId(productId);
	}
}
