package com.wode.tongji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wode.tongji.model.OrderDayStatistic;
import com.wode.tongji.service.OrderStatisticsService;
import com.wode.tongji.vo.OrderStatisticsSelectVo;

@Controller
@RequestMapping("order")
public class OrderStatisticsController {
	
	@Autowired
	OrderStatisticsService orderStatisticsService;
	
	@RequestMapping
	public String toOrder(Model model){
		return "sys/order/order";
	}

	
	/**
	 * 
	 * 功能说明：日销售额
	 * 日期:	2015年5月18日
	 * 开发者:宋艳垒
	 *
	 * @param ossVo
	 * @return
	 */
	@RequestMapping(value = "orderDayStatistics", method = RequestMethod.POST)
	@ResponseBody
	public List<OrderDayStatistic> getAreaTreeList(OrderStatisticsSelectVo ossVo) {
		
		List<OrderDayStatistic> listOds = orderStatisticsService.selectByDay(ossVo);
		return listOds;
	}
	
}
