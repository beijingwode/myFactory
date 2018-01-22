/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Product;
import com.wode.factory.user.query.ProductQuery;

import cn.org.rapid_framework.page.Page;

@Service("productService")
public interface ProductService extends EntityService<Product,Long>{
	
	public EntityDao getEntityDao() ;
	
	/**
	 * 
	 * 功能说明：分页查询
	 * 日期:	2015年3月31日
	 * 开发者:宋艳垒
	 *
	 * @param query
	 * @return
	 */
	public Page findPage(ProductQuery query);

	/**
	 * 
	 * 功能说明：根据商家id查询产品
	 * 日期:	2015年3月31日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	public List<Product> selectByShop(Long id,Long shopId);
    
	public String[] sortArray(String[] old);
	
	public Integer getBrandLevel(String name);
}
