/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseDao;
import com.wode.factory.model.Returnorder;
import com.wode.factory.supplier.dao.ReturnorderDao;
import com.wode.factory.supplier.query.ReturnorderQuery;

@Repository("returnorderDao")
public class ReturnorderDaoImpl extends BaseDao<Returnorder,java.lang.Long> implements ReturnorderDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ReturnorderMapper";
	}
	
	public void saveOrUpdate(Returnorder entity){
		if(entity.getReturnOrderId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(ReturnorderQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public Returnorder returnOrderById(Long id) {
	return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".returnOrderById",id);
}
//	public Returnorder returnOrderByMap(Map<String, Object> map) {
//		return this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".returnOrderByMap",map);
//	}
	
	@Override
	public void updatebymap(Map<String, Object> map) {
		this.getSqlSession().update(getIbatisMapperNamesapce()+".updatebymap",map);
	}
	

}
