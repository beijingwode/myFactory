/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.List;

import com.wode.factory.model.Product;
import com.wode.factory.model.ProductHis;

public interface ProductHisService {

	/**
	 * app分页显示评论
	 * @return
	 */
	List<ProductHis> find(ProductHis model, Integer page, Integer pageSize);
	

	/**
	 * app分页显示评论
	 * @return
	 */
	Product getLast(Long productId);
	
	void save(ProductHis model);
}
