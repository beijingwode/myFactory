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
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.query.ProductDetailListQuery;

public interface ProductDetailListService extends EntityService<ProductDetailList,Long>{
	
	
	
	public Page findPage(ProductDetailListQuery query);
	
	/**
	 * 根据商品id获取该商品的清单列表
	 * @param map
	 * @return
	 */
	public List<ProductDetailList> getProductdetaillistByProductid(Map map);
	
	/**
	 * 删除该商品对应的所有参数
	 * @param productid
	 */
	public void removeAllByProductid(Map map);
	
	public List<ProductDetailList> getByProductId(Long productId);
}
