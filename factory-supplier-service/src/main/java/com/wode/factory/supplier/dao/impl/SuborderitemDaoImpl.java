/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.supplier.dao.SuborderitemDao;
import com.wode.factory.supplier.query.SuborderitemQuery;

@Repository("suborderitemDao")
public class SuborderitemDaoImpl extends BaseDao<Suborderitem,java.lang.Long> implements SuborderitemDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "SuborderitemMapper";
	}
	
	public void saveOrUpdate(Suborderitem entity){
		if(entity.getSubOrderItemId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(SuborderitemQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public List<Suborderitem> selectByModel(Suborderitem model) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByModel", model);
	}

	@Override
	public List<Suborderitem> selectSuborderItemByrenturnOrderId(Long returnOrderId) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectSuborderItemByrenturnOrderId", returnOrderId);
	}
}
