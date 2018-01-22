package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PromotionProduct;
import com.wode.factory.vo.PromotionProductVo;

public interface PromotionProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(PromotionProduct record);

    int insertSelective(PromotionProduct record);

    PromotionProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PromotionProduct record);

    int updateByPrimaryKey(PromotionProduct record);

    /**
     * 
     * 功能说明：分页查询活动商品
     * 日期:	2015年8月5日
     * 开发者:宋艳垒
     *
     * @param params
     * @return
     */
	List<PromotionProductVo> findList(Map<String, Object> params);
    // 促销商品详细
    public PromotionProduct details(Long id);
    // 根据不同条件查询促销商品列表
    public List<PromotionProduct> selectListSelective(PromotionProduct record);
    /**
     * 查询超过秒杀活动日期的商品
     * @param record
     * @return
     */
    public List<PromotionProduct> findOverduePromotionProduct(PromotionProduct record);
    // 促销商品未审核与审核中之间的状态转换
    public int updateUnreviewOrReviewing(PromotionProduct record);
    // 促销商品审核通过与不通过
    public int updateReviewed(PromotionProduct record);
    // 促销商品未审核与本人审核中的列表
    public List<PromotionProductVo> selectUnreviewAndUserReviewingList(Map<String, Object> map);
    /**
     * 
     * 功能说明:分页查询已审核过的
     * 日期:	2015年8月6日
     * 开发者:宋艳垒
     *
     * @param params
     * @return
     */
	List<PromotionProductVo> findListSet(Map<String, Object> params);

	List<PromotionProductVo> findByCreateDate(Map<String, Object> params);

	void deleteBySupplierId(Long supplierId);

	void deleteCollectionProductBySupplierId(Long supplierId);

	void deleteProductTagBySupplierId(Long supplierId);

	void deleteProductStoreCategoryBySupplierId(Long supplierId);
}