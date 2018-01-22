/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.user.query.PromotionProductQuery;
import com.wode.factory.user.vo.PromotionProductVo;

public interface PromotionProductDao extends  EntityDao<PromotionProduct,Long>{
	public PageInfo<PromotionProductVo> findPage(PromotionProductQuery query);
	public void saveOrUpdate(PromotionProduct entity);
	public PromotionProductVo findById(PromotionProductQuery query);
	public PromotionProduct findByAttribute(PromotionProductQuery query);
	/**
	 * 
	 * 功能说明：根据skuid查询活动商品信息
	 * 日期:	2015年8月26日
	 * 开发者:宋艳垒
	 *
	 * @param specificationId
	 * @return
	 */
	public PromotionProduct findBySkuId(Long specificationId);
	/**
	 * 查询可关注的活动商品
	 * @param query
	 * @return
	 */
	public PromotionProduct findByCare(PromotionProductQuery query);
	/**
	 * 查询关注的商品
	 * @param query
	 * @return
	 */
	public PromotionProductVo findOneCare(PromotionProductQuery query);

}
