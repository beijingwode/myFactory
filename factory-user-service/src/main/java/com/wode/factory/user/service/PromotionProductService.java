/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.query.PromotionProductQuery;
import com.wode.factory.user.vo.PromotionProductVo;

@Service("promotionProductService")
public interface PromotionProductService extends EntityService<PromotionProduct,Long>{
	
	
	/**
	 * 查询商品列表
	 * @param promotionId
	 * @return
	 */
	public PageInfo<PromotionProductVo> findPage(PromotionProductQuery query);
	/**
	 * 根据ID查询VO
	 * @param query
	 * @return
	 */
	public PromotionProductVo findById(PromotionProductQuery query);
	
	
	/**
	 * 
	 * 功能说明：根据query查询活动商品信息
	 *
	 * @return
	 */
	public PromotionProduct findByAttribute(PromotionProductQuery query);
	/**
	 * 查询是否可以关注该活动商品
	 * @param query
	 * @return
	 */
	public PromotionProduct findByCare(PromotionProductQuery query);
	/**
	 * 查询关注的活动商品
	 * @param query
	 * @return
	 */
	public PromotionProductVo findOneCare(PromotionProductQuery query);
	
}
