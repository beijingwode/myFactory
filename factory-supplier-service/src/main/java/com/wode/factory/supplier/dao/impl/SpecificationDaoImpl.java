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
import com.wode.factory.model.Specification;
import com.wode.factory.supplier.dao.SpecificationDao;
import com.wode.factory.supplier.query.SpecificationQuery;

@Repository("specificationDao")
public class SpecificationDaoImpl extends BaseDao<Specification,java.lang.Long> implements SpecificationDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SpecificationMapper";
	}
	
	public void saveOrUpdate(Specification entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SpecificationQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	/**
	 * 根据供应商id获取该供应商所有的用于生产sku的规格及其规格值 
	 * @return
	 */
	public List<Specification> getSpecificationlistByCategoryid(Map map){
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".getSpecificationlistByCategoryid",map);
	}

	@Override
	public List<Specification> selectByModel(Specification specification) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", specification);
	}

}
