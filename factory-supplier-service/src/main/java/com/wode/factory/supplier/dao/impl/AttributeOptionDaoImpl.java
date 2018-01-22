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
import com.wode.factory.model.AttributeOption;
import com.wode.factory.supplier.dao.AttributeOptionDao;
import com.wode.factory.supplier.query.AttributeOptionQuery;

@Repository("attributeOptionDao")
public class AttributeOptionDaoImpl extends BaseDao<AttributeOption,java.lang.Long> implements AttributeOptionDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "AttributeOptionMapper";
	}
	
	public void saveOrUpdate(AttributeOption entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(AttributeOptionQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
