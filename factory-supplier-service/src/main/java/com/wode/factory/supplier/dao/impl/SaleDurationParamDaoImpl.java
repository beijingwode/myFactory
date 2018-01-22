/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.supplier.dao.SaleDurationParamDao;
import com.wode.factory.supplier.query.SaleDurationParamQuery;

@Repository("saleDurationParamDao")
public class SaleDurationParamDaoImpl extends BaseDao<SaleDurationParam,java.lang.Long> implements SaleDurationParamDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SaleDurationParamMapper";
	}
	
	public void saveOrUpdate(SaleDurationParam entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SaleDurationParamQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	
	
	public SaleDurationParam findSaleDurationParamByKey(String key){
		
		Map filters = new HashMap();
		filters.put("key",key);
		SaleDurationParam s =	getSqlSession().selectOne(getIbatisMapperNamesapce()+ ".findPage", filters);
		return s;
	}

}
