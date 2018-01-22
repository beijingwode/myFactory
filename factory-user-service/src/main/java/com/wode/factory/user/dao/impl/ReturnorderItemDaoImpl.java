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
import com.wode.factory.model.Returnorderitem;
import com.wode.factory.user.dao.ReturnorderItemDao;
import com.wode.factory.user.query.ReturnorderItemQuery;

@Repository("returnorderItemDao")
public class ReturnorderItemDaoImpl extends BaseDao<Returnorderitem,java.lang.Long> implements ReturnorderItemDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ReturnorderItemMapper";
	}
	
	public void saveOrUpdate(Returnorderitem entity){
		if(entity.getReturnOrderItemId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReturnorderItemQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}
	

}
