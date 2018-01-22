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
import com.wode.factory.model.ParameterGroup;
import com.wode.factory.model.ProductDetailList;
import com.wode.factory.supplier.dao.ParameterGroupDao;
import com.wode.factory.supplier.query.ParameterGroupQuery;

@Repository("parameterGroupDao")
public class ParameterGroupDaoImpl extends BaseDao<ParameterGroup,java.lang.Long> implements ParameterGroupDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ParameterGroupMapper";
	}
	
	public void saveOrUpdate(ParameterGroup entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ParameterGroupQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据商品类型id获取该类型的参数列表
	 * @param map
	 * @return
	 */
	public List<ParameterGroup> getParameterGrouplistByCategoryid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getParameterGrouplistByCategoryid",map);
	}

}
