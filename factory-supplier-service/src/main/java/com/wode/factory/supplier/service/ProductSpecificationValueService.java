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
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.supplier.query.ProductSpecificationValueQuery;

public interface ProductSpecificationValueService extends EntityService<ProductSpecificationValue,Long>{
	

	
	public Page findPage(ProductSpecificationValueQuery query);
	
	/**
	 * 删除该商品对应的所有参数
	 * @param productid
	 */
	public void removeAllByProductid(Map map);
	
	
	/**
	 * 商品list
	 * @param productid
	 */
	public List<ProductSpecificationValue> findAllBymap(Map map);
	

	public int copyFromOther(Long productId, Long oId,Long nId);
	public int updateFromOther(Long productId, Long oId,Long nId);
	
	public List<ProductSpecificationValue> getByProductId(Long productId); 
}
