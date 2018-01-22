package com.wode.tongji.service;

import java.text.ParseException;
import java.util.List;

import com.wode.tongji.model.UserDayStatistical;
import com.wode.tongji.model.UserGeoInfo;
import com.wode.tongji.model.UserMonthStatistical;


public interface UserStatisticalService {
	
	/**
	 * 功能说明：根据时间节点查询统计信息，不传时间，则表示查询最新一月的统计数据
	 * 日期:	2015年5月14日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param time   年—月
	 * @return
	 */
	public List<UserDayStatistical> day(String startTime,String endTime);
	
	/**
	 * 功能说明：查询全部城市信息
	 * 日期:	2015年5月22日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public UserGeoInfo allCity();
	
	
	/**
	 * 功能说明：根据时间节点查询月统计数据
	 * 日期:	2015年5月18日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param startEndTime
	 * @param monthEndTime
	 * @return
	 */
	public List<UserMonthStatistical> month(String monthStartTime,String monthEndTime) throws ParseException;
	
	
}
