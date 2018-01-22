/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao.impl;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Returnorderlog;
import com.wode.factory.user.dao.ReturnorderlogDao;
import com.wode.factory.user.query.ReturnorderlogQuery;

@Repository("returnorderlogDao")
public class ReturnorderlogDaoImpl extends BaseDao<Returnorderlog,java.lang.Long> implements ReturnorderlogDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ReturnorderlogMapper";
	}
	
	public void saveOrUpdate(Returnorderlog entity){
		if(entity.getReturnOrderLogId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReturnorderlogQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
