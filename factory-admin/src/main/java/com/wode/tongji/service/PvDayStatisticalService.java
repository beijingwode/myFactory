package com.wode.tongji.service;

import java.util.List;

import com.wode.tongji.model.PvDayStatistical;


public interface PvDayStatisticalService {
	
	/**
	 * 功能说明：根据时间节点查询统计信息，不传时间，则表示查询最新一月的统计数据
	 * 日期:	2015年5月14日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param time   年—月
	 * @return
	 */
	public List<PvDayStatistical> day(String startTime,String endTime);
	
}
