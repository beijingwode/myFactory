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
import com.wode.factory.model.Suborderstatuslog;
import com.wode.factory.user.dao.SuborderstatuslogDao;
import com.wode.factory.user.query.SuborderstatuslogQuery;

@Repository("suborderstatuslogDao")
public class SuborderstatuslogDaoImpl extends BaseDao<Suborderstatuslog,java.lang.Long> implements SuborderstatuslogDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SuborderstatuslogMapper";
	}
	
	public void saveOrUpdate(Suborderstatuslog entity){
		if(entity.getOrderStatusLogId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SuborderstatuslogQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
