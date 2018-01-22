/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.wode.common.mongo.MongoBaseService;
import com.wode.common.redis.RedisUtil;
import com.wode.common.util.JsonUtil;
import com.wode.common.util.NumberUtil;
import com.wode.factory.constant.RedisConstant;
import com.wode.factory.dao.CommentsDao;
import com.wode.factory.model.Comments;
import com.wode.factory.service.CommentsService;
import com.wode.factory.vo.CommentsQuery;
import com.wode.factory.vo.CommentsVo;


@Service("commentsService")
public class CommentsServiceImpl extends MongoBaseService<Comments>
        implements CommentsService {
	
    
    @Autowired
    private CommentsDao commentsDao;

    
    @Qualifier("redis")
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Long selectComentsCount(Long productId) {
    	CommentsQuery query = new CommentsQuery();
        query.setProductId(productId);
        Long count = commentsDao.findCount(query);
        return count == null ? 0 : count;
    }
    
    @Override
    public PageInfo findPageByFlag(Long userId, Boolean commentFlag, Integer page, String subOrderId) {
        CommentsQuery query = new CommentsQuery();
        query.setUserId(userId);
        query.setSubOrderid(subOrderId);
        if (null != commentFlag) {
            query.setCommentFlag(commentFlag);
        }
        query.setPageNumber(page);
        return commentsDao.findPage(query);
    }

    @Override
    public Comments saveToCacheAndMongo(Comments comment) {
    		getMongoDao().insert(comment);
        refreshCache(comment);
        return comment;
    }

	private void refreshCache(Comments comment) {
		Map<String,Object> productComment = this.getProductScore(comment.getProductId());
		Map<String,Object> supplierComment = this.getSupplierScore(comment.getSupplyid());
		
        Long p_commentCount = NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_ALL_CNT));
        p_commentCount++;	//评论数+1
        Long s_commentCount = NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_ALL_CNT));
        s_commentCount++;	//评论数+1

        Long p_goodsSum = comment.getStar1() + 0L;
        Long p_logisticsSum = comment.getStar2() + 0L;
        Long p_serviceSum = comment.getStar2() + 0L;     
        Long s_goodsSum = comment.getStar1() + 0L;
        Long s_logisticsSum = comment.getStar2() + 0L;
        Long s_serviceSum = comment.getStar2() + 0L;
        if(p_commentCount>1) {
            p_goodsSum = comment.getStar1() + NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_GOODS_SUM));
            p_logisticsSum = comment.getStar2() + NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_SUM));
            p_serviceSum = comment.getStar2() + NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_SERVICE_SUM));        
        } 
        if(s_commentCount>1) {
            s_goodsSum = comment.getStar1() + NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_GOODS_SUM));
            s_logisticsSum = comment.getStar2() + NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_LOGISTICS_SUM));
            s_serviceSum = comment.getStar2() + NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_SERVICE_SUM));
        }
        
        Long p_badCount = NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_BAD_CNT));
        Long p_nomalCount = NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT));
        Long p_praiseCount = NumberUtil.toLong(productComment.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT));
        Long s_badCount = NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_BAD_CNT));
        Long s_nomalCount = NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_NOMAL_CNT));
        Long s_praiseCount = NumberUtil.toLong(supplierComment.get(CommentsService.CACHE_MAP_KEY_PRAISE_CNT));
        
        if(comment.getCommentDegree() == 1) {
        	p_badCount++;
        	s_badCount++;
        } else if(comment.getCommentDegree() == 3) {
        	p_nomalCount++;
        	s_nomalCount++;
        } else {
        	p_praiseCount++;
        	s_praiseCount++;
        }

		// 商品cache更新
		refreshMap(comment.getProductId().toString(), productComment, p_commentCount, p_goodsSum, p_logisticsSum,
				p_serviceSum, p_badCount, p_nomalCount, p_praiseCount);

		// 商家cache更新
		refreshMap("sj_" + comment.getSupplyid(), productComment, s_commentCount, s_goodsSum, s_logisticsSum,
				s_serviceSum, s_badCount, s_nomalCount, s_praiseCount);
	}

	private void refreshMap(String redisKey,Map<String, Object> productComment, Long commentCount, Long goodsSum, Long logisticsSum,
			Long serviceSum, Long badCount, Long nomalCount, Long praiseCount) {
		
		productComment.put(CommentsService.CACHE_MAP_KEY_ALL_CNT, commentCount);
        productComment.put(CommentsService.CACHE_MAP_KEY_GOODS_SUM, goodsSum);
        productComment.put(CommentsService.CACHE_MAP_KEY_LOGISTICS_SUM, logisticsSum);
        productComment.put(CommentsService.CACHE_MAP_KEY_SERVICE_SUM, serviceSum);
        if(commentCount > 0) {
	        DecimalFormat df = new DecimalFormat("0.0");
	        productComment.put(CommentsService.CACHE_MAP_KEY_GOODS_AVG, df.format(goodsSum.doubleValue()/commentCount));
	        productComment.put(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG, df.format(logisticsSum.doubleValue()/commentCount));
	        productComment.put(CommentsService.CACHE_MAP_KEY_SERVICE_AVG, df.format(serviceSum.doubleValue()/commentCount));
        } else {
	        productComment.put(CommentsService.CACHE_MAP_KEY_GOODS_AVG, 5.0D);
	        productComment.put(CommentsService.CACHE_MAP_KEY_LOGISTICS_AVG, 5.0D);
	        productComment.put(CommentsService.CACHE_MAP_KEY_SERVICE_AVG,5.0D);
        }

        productComment.put(CommentsService.CACHE_MAP_KEY_BAD_CNT, badCount);
        productComment.put(CommentsService.CACHE_MAP_KEY_NOMAL_CNT, nomalCount);
        productComment.put(CommentsService.CACHE_MAP_KEY_PRAISE_CNT, praiseCount);
        
        redisUtil.setMapData(RedisConstant.COMMENTS, redisKey, JsonUtil.toJson(productComment));
	}
    

    @Override
    public Map<String, Object> getProductScore(Long productId) {
        String score = redisUtil.getMapData(RedisConstant.COMMENTS, productId.toString());
        if(!StringUtils.isEmpty(score)) {
            return JsonUtil.getObject(score, HashMap.class);
        }
        Map<String, Object> productComment = new HashMap<String, Object>();
        
        Comments bean = new Comments();
        bean.setProductId(productId);
        Map<String, Object> dbSum = getMongoDao().getAggregate(bean);
		
        refreshMap(productId.toString(), productComment, 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_ALL_CNT)),  
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_GOODS_SUM)), 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_LOGISTICS_SUM)),
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_SERVICE_SUM)),
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_BAD_CNT)), 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_NOMAL_CNT)), 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_PRAISE_CNT)));
		
        return productComment;
    }
    
	@Override
	public Map<String, Object> getSupplierScore(Long supplierId) {
        String score = redisUtil.getMapData(RedisConstant.COMMENTS, "sj_" + supplierId);
        if(!StringUtils.isBlank(score)) {
            return JsonUtil.getObject(score, HashMap.class);
        }
        Map<String, Object> productComment = new HashMap<String, Object>();
        
        Comments bean = new Comments();
        bean.setSupplyid(supplierId);
        Map<String, Object> dbSum = getMongoDao().getAggregate(bean);
		
        refreshMap("sj_" + supplierId, productComment, 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_ALL_CNT)),  
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_GOODS_SUM)), 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_LOGISTICS_SUM)),
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_SERVICE_SUM)),
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_BAD_CNT)), 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_NOMAL_CNT)), 
        		NumberUtil.toLong(dbSum.get(CommentsDao.MAP_KEY_PRAISE_CNT)));
		
        return productComment;
	}


	@Override
	public PageInfo<CommentsVo> findPageComments(Comments model, Integer page, Integer pageSize) {
    	Page<CommentsVo> rtn = new Page<CommentsVo>(page==null?1:page,pageSize);
    	Long cnt = this.count(model);
    	if(cnt > 0) {
    		List<Comments> ls = this.findComments(model,page, pageSize);
    		for (Comments comments : ls) {
    			rtn.add(CommentsVo.CreateByComment(comments));
			}
    		rtn.setTotal(cnt);
    	}
    	
    	return new PageInfo<CommentsVo>(rtn);
	}
	@Override
	public List<Comments> findComments(Comments model, Integer page, Integer pageSize) {
		return this.find(model,"creatTime",-1,page, pageSize);
	}

	@Override
	public Map<String, Object> getSupplierLevelCnt(Long supplierId, Date begin, Date end) {
		Comments bean= new Comments();
		bean.setSupplyid(supplierId);
		return getMongoDao().getSupplierLevelCnt(bean, begin, end);
	}

	@Override
	public CommentsDao getMongoDao() {
		return this.commentsDao;
	}


}
