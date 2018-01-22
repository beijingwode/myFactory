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
import com.wode.factory.model.CommentsStatistics;
import com.wode.factory.supplier.dao.CommentsStatisticsDao;
import com.wode.factory.supplier.query.CommentsStatisticsQuery;

@Repository("commentsStatisticsDao")
public class CommentsStatisticsDaoImpl extends BaseDao<CommentsStatistics,java.lang.Long> implements CommentsStatisticsDao{
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "CommentsStatisticsMapper";
	}
	
	public void saveOrUpdate(CommentsStatistics entity){
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}
	
	public Page findPage(CommentsStatisticsQuery query) {
		return pageQuery(getIbatisMapperNamesapce()+".findPage",query);
	}

	@Override
	public void deleteall() {
		this.getSqlSession().delete(getIbatisMapperNamesapce()+".deleteall");
	}

	@Override
	public List<CommentsStatistics> findbymap(Map<String, Object> reparm) {
		return this.getSqlSession().selectList(getIbatisMapperNamesapce()+".findbymap",reparm);
	}
	

}
