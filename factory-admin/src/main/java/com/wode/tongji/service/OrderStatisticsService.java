package com.wode.tongji.service;

import java.util.List;

import com.wode.tongji.model.OrderDayStatistic;
import com.wode.tongji.vo.OrderStatisticsSelectVo;



public interface OrderStatisticsService {

	public void doSettleAcount();
	
	public List<OrderDayStatistic> selectByDay(OrderStatisticsSelectVo ossVo);
}
