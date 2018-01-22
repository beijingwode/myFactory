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
import com.wode.factory.model.Attribute;
import com.wode.factory.supplier.dao.AttributeDao;
import com.wode.factory.supplier.query.AttributeQuery;

@Repository("attributeDao")
public class AttributeDaoImpl extends BaseDao<Attribute,java.lang.Long> implements AttributeDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "AttributeMapper";
	}
	
	public void saveOrUpdate(Attribute entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(AttributeQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	/**
	 * 根据商品类型id获取该商品类型对应的属性
	 * @param map
	 * @return
	 */
	public List<Attribute> getAttributelistByCategoryid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getAttributelistByCategoryid",map);
	}

}
