/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.user.dao.ProductDetailListDao;
import com.wode.factory.user.query.ProductDetailListQuery;

@Repository("productDetailListDao")
public class ProductDetailListDaoImpl extends BaseDao<ProductDetailList,java.lang.Long> implements ProductDetailListDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductDetailListMapper";
	}
	
	public void saveOrUpdate(ProductDetailList entity){
		throw new RuntimeException("no permissions");
	}
	
	public Page findPage(ProductDetailListQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<ProductDetailList> findByProductId(Long productId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByProduct", productId);
	}
	

}
