/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Parameter;
import com.wode.factory.supplier.dao.ParameterDao;
import com.wode.factory.supplier.query.ParameterQuery;

@Repository("parameterDao")
public class ParameterDaoImpl extends BaseDao<Parameter,java.lang.Long> implements ParameterDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ParameterMapper";
	}
	
	public void saveOrUpdate(Parameter entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ParameterQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
