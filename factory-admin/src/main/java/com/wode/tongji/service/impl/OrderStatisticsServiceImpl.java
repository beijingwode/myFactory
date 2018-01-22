
package com.wode.tongji.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.SuborderDao;
import com.wode.tongji.mapper.OrderDayStatisticMapper;
import com.wode.tongji.model.OrderDayStatistic;
import com.wode.tongji.service.OrderStatisticsService;
import com.wode.tongji.vo.OrderStatisticsSelectVo;
import com.wode.tongji.vo.SubOrdersVo;

@Service("orderStatisticsService")
public class OrderStatisticsServiceImpl implements OrderStatisticsService{
	
	@Autowired
	private SuborderDao SuborderDao;
	
	@Autowired
	private OrderDayStatisticMapper orderDayStatisticMapper;
	
	
	/**
	 * 功能说明：定时任务结算统计，并把统计结果插入结算表t_order_day_statistic：每天晚上23:59分跑
	 */
	public void doSettleAcount() {
		List<SubOrdersVo> listSOVo = SuborderDao.selectByDay(null);
		for(SubOrdersVo sa : listSOVo){
			OrderDayStatistic vo = new OrderDayStatistic();
			changeSOVoTosA(sa,vo);
			orderDayStatisticMapper.insert(vo);
		}
		
	}
	
	private void changeSOVoTosA(SubOrdersVo sovo,OrderDayStatistic sa){
		if(sovo.getMonth() != null){
			sa.setDay(sovo.getMonth());
		}
		sa.setDealAmount(sovo.getTotalDealAmount());
	}

	@Override
	public List<OrderDayStatistic> selectByDay(OrderStatisticsSelectVo ossVo) {
		return orderDayStatisticMapper.selectByDay(ossVo);
	}
}
