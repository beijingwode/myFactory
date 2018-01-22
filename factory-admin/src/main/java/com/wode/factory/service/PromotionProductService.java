package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PromotionProduct;
import com.wode.factory.vo.PromotionProductVo;

public interface PromotionProductService {

	// 促销商品详细
	public Map<String, Object> detail(Long id, Long userId);
	// 对促销商品进行审核
	public Map<String, Object> doReview(Long id, Long userId, boolean result, String startTime);
	// 根据不同条件查询促销商品列表
	public List<PromotionProduct> promotionProductList(PromotionProduct promotionProduct);
	
	public List<PromotionProduct> findOverduePromotionProduct(PromotionProduct record);
	
	// 促销商品未审核与审核中之间的状态转换
	public int updateUnreviewOrReviewing(PromotionProduct record);
	// 审核下一条数据的详细
    public Map<String, Object> nextDetail(Long id, Long userId);
	/**
	 * 
	 * 功能说明：分页查询活动商品
	 * 日期:	2015年8月5日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	PageInfo<PromotionProductVo> findList(Map<String, Object> params);
	
	/**
	 * 
	 * 功能说明：分页查询已审核过的
	 * 日期:	2015年8月6日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	public PageInfo<PromotionProductVo> findListSet(Map<String, Object> params);
	
	/**
	 * 
	 * 功能说明：更新促销状态
	 * 日期:	2015年8月10日
	 * 开发者:宋艳垒
	 *
	 * @param proProduct
	 * @return
	 */
	public int updateStatus(PromotionProduct proProduct);
	/**
	 * 
	 * 功能说明：根据创建日期查询
	 * 日期:	2015年8月17日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	public List<PromotionProductVo> findByCreateDate(Map<String, Object> params);

}
