/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.common.frame.base.BaseSpringController;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.ActResult;
import com.wode.common.util.NumberUtil;
import com.wode.factory.service.CommentsService;
import com.wode.factory.user.model.CommentsCountVo;

@Controller
@RequestMapping("")
public class CommentsController extends BaseSpringController{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("commentsService")
	private CommentsService commentsService;
	
	@Qualifier("redis")
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping(value="commentsMsg")
	@ResponseBody
	public ActResult<CommentsCountVo> commentsMsg(Long productId){
		ActResult<CommentsCountVo> ar = new ActResult<CommentsCountVo>();
		
		Map<String, Object> score = commentsService.getProductScore(productId);
		CommentsCountVo ccv = new CommentsCountVo();
		
		ccv.setPraiseCount(NumberUtil.toInteger(score.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT)));
		ccv.setNomalCount(NumberUtil.toInteger(score.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT)));
		ccv.setBadCount(NumberUtil.toInteger(score.get(CommentsService.CACHE_MAP_KEY_BAD_CNT)));
		ccv.setGoodsRatings(NumberUtil.toDouble(score.get(CommentsService.CACHE_MAP_KEY_GOODS_AVG)));
		ccv.setServiceRatings(NumberUtil.toDouble(score.get(CommentsService.CACHE_MAP_KEY_SERVICE_AVG)));
		ccv.setLogisticsRatings(NumberUtil.toDouble(score.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG)));
		ar.setData(ccv);
		return ar;
	}
}

