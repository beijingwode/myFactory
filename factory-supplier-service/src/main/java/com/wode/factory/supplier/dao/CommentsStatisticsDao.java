/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.dao;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommentsStatistics;
import com.wode.factory.supplier.query.CommentsStatisticsQuery;

public interface CommentsStatisticsDao extends  EntityDao<CommentsStatistics,Long>{
	public Page findPage(CommentsStatisticsQuery query);
	public void saveOrUpdate(CommentsStatistics entity);
	public void deleteall();
	public List<CommentsStatistics> findbymap(Map<String, Object> reparm);

}
