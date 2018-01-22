/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Product;
import com.wode.factory.model.StoreCategory;
import com.wode.factory.user.dao.ProductDao;
import com.wode.factory.user.query.ProductQuery;

import cn.org.rapid_framework.page.Page;

@Repository("productDao")
public class ProductDaoImpl extends BaseDao<Product,java.lang.Long> implements ProductDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductMapper";
	}
	
	public void saveOrUpdate(Product entity){
		if(entity.getId() == null) 
			throw new RuntimeException("no permissions");
		else 
			update(entity);
	}
	
	public Page findPage(ProductQuery query) {

        return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<Product> selectByShop(Long supplierId,Long shopId) {
		Map<String,Object> query = new HashMap<String, Object>();
		query.put("supplierId", supplierId);
		query.put("shopId", shopId);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".findByShop", query);
	}

    @Override
    public PageInfo findProducts(ProductQuery query) {
        List<StoreCategory> list = getSqlSession().selectList(getIbatisMapperNamesapce() + ".findPage", query, new RowBounds(query.getPageNumber(), query.getPageSize()));
        return new PageInfo(list);
    }

	@Override
	public Long getBrandLevel(String name) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getBrandLevel", name);
	}

	@Override
	public Long getQuestionnaireId(Long id) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getQuestionnaireId", id);
	}
}
