package com.wode.tongji.mapper;

import java.util.List;

import com.wode.tongji.model.OrderDayStatistic;
import com.wode.tongji.vo.OrderStatisticsSelectVo;



public interface OrderDayStatisticMapper {
	
	public void insert(OrderDayStatistic ods);
	
	/**
	 * 
	 * 功能说明: 根据日期统计订单金额
	 * 日期:	2015年5月18日
	 * 开发者:宋艳垒
	 *
	 * @param time
	 * @return
	 */
	public List<OrderDayStatistic> selectByDay(OrderStatisticsSelectVo ossVo);

}