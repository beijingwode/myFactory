/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.user.query.ProductDetailListQuery;

public interface ProductDetailListDao extends  EntityDao<ProductDetailList,Long>{
	public Page findPage(ProductDetailListQuery query);
	public void saveOrUpdate(ProductDetailList entity);
	public List<ProductDetailList> findByProductId(Long productId);

}
