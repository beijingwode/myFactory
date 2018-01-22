/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.dao;

import java.util.Date;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.mongo.MongoBaseDao;
import com.wode.factory.model.Comments;
import com.wode.factory.vo.CommentsQuery;

public interface CommentsDao extends  MongoBaseDao<Comments>{

	public static final String MAP_KEY_ALL_CNT = "totalCount";
	public static final String MAP_KEY_BAD_CNT = "badCount";
	public static final String MAP_KEY_NOMAL_CNT = "nomalCount";
	public static final String MAP_KEY_PRAISE_CNT = "praiseCount";
	public static final String MAP_KEY_GOODS_SUM = "goodsAll";
	public static final String MAP_KEY_LOGISTICS_SUM = "logisticsAll";
	public static final String MAP_KEY_SERVICE_SUM = "serviceAll";
	
	/**
	 * 分页查询
	 * 
	 */
	public PageInfo<Comments> findPage(CommentsQuery query);
	
    /**
     * 获取评论数量
     * @param query
     * @return
     */
    public Long findCount(CommentsQuery query);
    
	public Map<String, Object> getAggregate(Comments bean);
	public Map<String, Object> getSupplierLevelCnt(Comments bean,Date begin,Date end);
}
