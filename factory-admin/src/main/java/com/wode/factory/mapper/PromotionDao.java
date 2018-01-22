package com.wode.factory.mapper;

import com.wode.factory.model.Promotion;

public interface PromotionDao {
    int deleteByPrimaryKey(Long id);

    int insert(Promotion record);

    int insertSelective(Promotion record);

    Promotion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Promotion record);

    int updateByPrimaryKey(Promotion record);

    /**
     * 
     * 功能说明：成功参加活动的商品数量
     * 日期:	2015年8月14日
     * 开发者:宋艳垒
     *
     * @param record
     * @return
     */
	int updateJoinTotal(Promotion record);
}