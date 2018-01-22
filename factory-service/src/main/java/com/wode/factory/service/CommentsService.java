/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Comments;
import com.wode.factory.vo.CommentsVo;

@Service("commentsService")
public interface CommentsService {

	public static final String CACHE_MAP_KEY_GOODS_SUM = "goodsAll";
	public static final String CACHE_MAP_KEY_LOGISTICS_SUM = "logisticsAll";
	public static final String CACHE_MAP_KEY_SERVICE_SUM = "serviceAll";
	
	public static final String CACHE_MAP_KEY_GOODS_AVG = "goodsRatings";
	public static final String CACHE_MAP_KEY_LOGISTICS_AVG = "logisticsRatings";
	public static final String CACHE_MAP_KEY_SERVICE_AVG = "serviceRatings";

	public static final String CACHE_MAP_KEY_ALL_CNT = "totalCount";
	public static final String CACHE_MAP_KEY_BAD_CNT = "badCount";
	public static final String CACHE_MAP_KEY_NOMAL_CNT = "nomalCount";
	public static final String CACHE_MAP_KEY_PRAISE_CNT = "praiseCount";
	
	Long selectComentsCount(Long productId);
	
	/**
	 * 分页显示评论
	 * @param commentFlag
	 * @return
	 */
	PageInfo<Comments> findPageByFlag(Long userId, Boolean commentFlag, Integer page, String subOrderid);

	PageInfo<CommentsVo> findPageComments(Comments model, Integer page, Integer pageSize);
	/**
	 * app分页显示评论
	 * @return
	 */
	List<Comments> findComments(Comments model, Integer page, Integer pageSize);

    /**
     * 获取商品的各项评分
     * @param productId
     * @return
     */
    Map<String, Object> getProductScore(Long productId);
    
    /**
     * 获取商家的各项评分
     * @param supplierId
     * @return
     */
    Map<String, Object> getSupplierScore(Long supplierId);
    /**
     * 获取商家的各项评分
     * @param supplierId
     * @return
     */
    Map<String, Object> getSupplierLevelCnt(Long supplierId,Date begin,Date end);

    Comments saveToCacheAndMongo(Comments comment);

}
