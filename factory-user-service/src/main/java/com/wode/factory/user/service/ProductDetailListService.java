/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.user.query.ProductDetailListQuery;

@Service("productDetailListService")
public interface ProductDetailListService extends EntityService<ProductDetailList,Long>{
	
	
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
	public Page findPage(ProductDetailListQuery query);

	/**
	 * 
	 * 功能说明：根据产品id查询清单
	 * 日期:	2015年3月31日
	 * 开发者:宋艳垒
	 *
	 * @param productId
	 * @return
	 */
	public List<ProductDetailList> selectByProductId(Long productId);
	
}
