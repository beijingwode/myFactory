/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Product;
import com.wode.factory.user.query.ProductQuery;

public interface ProductDao extends  EntityDao<Product,Long>{
	public Page findPage(ProductQuery query);
	public void saveOrUpdate(Product entity);
	public List<Product> selectByShop(Long id,Long shopId);

    public PageInfo findProducts(ProductQuery query);
    public Long getBrandLevel(String name);
    public Long getQuestionnaireId(Long id);
    
}
