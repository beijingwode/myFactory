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
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.dao.ProductDetailListDao;
import com.wode.factory.supplier.query.ProductDetailListQuery;
import com.wode.factory.supplier.service.ProductDetailListService;

@Service("productDetailListService")
public class ProductDetailListServiceImpl extends BaseService<ProductDetailList,java.lang.Long> implements  ProductDetailListService{
	@Autowired
	@Qualifier("productDetailListDao")
	private ProductDetailListDao productDetailListDao;
	
	public EntityDao getEntityDao() {
		return this.productDetailListDao;
	}
	
	public Page findPage(ProductDetailListQuery query) {
		return productDetailListDao.findPage(query);
	}
	
	/**
	 * 根据商品id获取该商品的清单列表
	 * @param map
	 * @return
	 */
	public List<ProductDetailList> getProductdetaillistByProductid(Map map){
		return productDetailListDao.getProductdetaillistByProductid(map);
	}
	
	/**
	 * 删除该商品对应的所有清单list
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		productDetailListDao.removeAllByProductid(map);
	}

	@Override
	public List<ProductDetailList> getByProductId(Long productId) {
		return productDetailListDao.getByProductId(productId);
	}
}
