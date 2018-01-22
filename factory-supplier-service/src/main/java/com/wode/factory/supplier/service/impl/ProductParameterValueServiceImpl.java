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
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.supplier.dao.ProductParameterValueDao;
import com.wode.factory.supplier.query.ProductParameterValueQuery;
import com.wode.factory.supplier.service.ProductParameterValueService;

@Service("productParameterValueService")
public class ProductParameterValueServiceImpl extends BaseService<ProductParameterValue,java.lang.Long> implements  ProductParameterValueService{
	@Autowired
	@Qualifier("productParameterValueDao")
	private ProductParameterValueDao productParameterValueDao;
	
	public EntityDao getEntityDao() {
		return this.productParameterValueDao;
	}
	
	public Page findPage(ProductParameterValueQuery query) {
		return productParameterValueDao.findPage(query);
	}
	
	/**
	 * 删除该商品对应的所有参数
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		productParameterValueDao.removeAllByProductid(map);
	}

	@Override
	public List<ProductParameterValue> getByProductId(Long productId) {
		return productParameterValueDao.getByProductId(productId);
	}
	
}
