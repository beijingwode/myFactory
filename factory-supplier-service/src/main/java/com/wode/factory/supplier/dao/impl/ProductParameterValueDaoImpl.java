/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.ProductParameterValue;
import com.wode.factory.supplier.dao.ProductParameterValueDao;
import com.wode.factory.supplier.query.ProductParameterValueQuery;

@Repository("productParameterValueDao")
public class ProductParameterValueDaoImpl extends BaseDao<ProductParameterValue,java.lang.Long> implements ProductParameterValueDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ProductParameterValueMapper";
	}
	
	public void saveOrUpdate(ProductParameterValue entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ProductParameterValueQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 删除该商品对应的所有参数
	 * @param productid
	 */
	public void removeAllByProductid(Map map){
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".removeAllByProductid",map);
	}

	@Override
	public List<ProductParameterValue> getByProductId(Long productId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".getByProductId",productId);
	}

}
