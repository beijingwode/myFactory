/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommentsStatistics;
import com.wode.factory.supplier.dao.CommentsStatisticsDao;
import com.wode.factory.supplier.query.CommentsStatisticsQuery;
import com.wode.factory.supplier.service.CommentsStatisticsService;

@Service("commentsStatisticsService")
public class CommentsStatisticsServiceImpl extends BaseService<CommentsStatistics,java.lang.Long> implements  CommentsStatisticsService{
	@Autowired
	@Qualifier("commentsStatisticsDao")
	private CommentsStatisticsDao commentsStatisticsDao;
	
	public EntityDao getEntityDao() {
		return this.commentsStatisticsDao;
	}
	
	public Page findPage(CommentsStatisticsQuery query) {
		return commentsStatisticsDao.findPage(query);
	}

	@Override
	public void deleteall() {
		commentsStatisticsDao.deleteall();
	}

	@Override
	public List<CommentsStatistics> findbymap(Map<String, Object> reparm) {
		return commentsStatisticsDao.findbymap(reparm);
	}
	
}
