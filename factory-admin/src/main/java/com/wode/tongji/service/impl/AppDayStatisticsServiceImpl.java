package com.wode.tongji.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.CacheManager;
import com.wode.common.util.StringUtils;
import com.wode.tongji.mapper.AppDayStatisticMapper;
import com.wode.tongji.model.AppDayStatistic;
import com.wode.tongji.service.AppDayStatisticService;

@Service("appDayStatisticService")
public class AppDayStatisticsServiceImpl implements AppDayStatisticService{
	
	@Autowired
	private AppDayStatisticMapper appDayStatisticMapper;

	@Override
	public Integer updateDayActive(AppDayStatistic ads) {
		//查询当天有没日活量记录
		List<AppDayStatistic> listAppDaySta = appDayStatisticMapper.selectByCodeDate(ads);
		if(listAppDaySta != null && listAppDaySta.size()>0){
			//更新日活量
			ads.setActiveAmount(ads.getActiveAmount()+1);
			return appDayStatisticMapper.updateDayAppStatistics(ads);
		}else{
			//每天第一个打开app，向日活表添加记录
			return appDayStatisticMapper.insertSelective(ads);
		}
		
	}

	/* 获取app的日活量
	 * @see com.wode.tongji.service.AppDayStatisticService#selectAppDayStatistic(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<AppDayStatistic> selectAppDayStatistic(String startTime,
			String endTime, String mark) {
		// TODO Auto-generated method stub
		List<AppDayStatistic> li_appDay= null;
		Boolean startBoo = StringUtils.isNullOrEmpty(startTime);
		Boolean endBoo = StringUtils.isNullOrEmpty(endTime);
		Boolean markBoo = StringUtils.isNullOrEmpty(mark);
		if(startBoo&&endBoo&&markBoo){
			li_appDay = this.byLatestTimeSelectAppDayInfo();
		}else{
			li_appDay = this.byStartAndEndTimeSelectAppDayInfo(startBoo, endBoo, markBoo, startTime, endTime, mark);
		}
		Collections.sort(li_appDay);
		return li_appDay;
	}
	/**
	 * 功能说明：根据start时间和end时间和appCode标示查询
	 * 日期:	2015年5月20日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param startBoo
	 * @param endBoo
	 * @param markBoo
	 * @param startTime
	 * @param endTime
	 * @param mark
	 * @return
	 */
	private List<AppDayStatistic> byStartAndEndTimeSelectAppDayInfo(Boolean startBoo,Boolean endBoo,Boolean markBoo,String startTime,String endTime, String mark){
		//开始时间和结束时间和appCode标示都不为空
		if(!startBoo&&!endBoo&&!markBoo){
			return this.appDayStatisticMapper.selectByStartAndEndTime(startTime, endTime, mark);
		//开始时间和结束时间和appCode标示都不为空
		}else if((!startBoo||!endBoo)&&!markBoo){
			return this.appDayStatisticMapper.selectByTime(startBoo?endTime:startTime, mark);
		//开始时间和结束时间为空，appCode不为空
		}else if(startBoo&&endBoo&&!markBoo){
			return this.appDayStatisticMapper.selectByCode(mark);
		//反之参数不符合标准，返回空对象
		}else{
			return new ArrayList<AppDayStatistic>();
		}
	}
	
	/**
	 * 功能说明：根据最新的时间和appCode标示查询最新的一月数据
	 * 日期:	2015年5月20日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	private List<AppDayStatistic> byLatestTimeSelectAppDayInfo(){
		AppDayStatistic appDay = this.appDayStatisticMapper.selectByLatestTime();
		if(StringUtils.isNullOrEmpty(appDay)){
			return new ArrayList<AppDayStatistic>();
		}else{
			SimpleDateFormat si = new SimpleDateFormat("yyyy-MM");
			return this.appDayStatisticMapper.selectByTime(si.format(appDay.getDay()), appDay.getAppCode());
		}
	}

	/* 去重后的appCode
	 * @see com.wode.tongji.service.AppDayStatisticService#selectDistinctApkCode()
	 */
	@Override
	public List<String> selectDistinctApkCode() {
		// TODO Auto-generated method stub
		return this.appDayStatisticMapper.selectDistinctApkCode();
	}
	
	/**
	 * 
	 * 功能说明：删除所有打开app是缓存的客户IMEI码
	 * 日期:	2015年5月21日
	 * 开发者:宋艳垒
	 *
	 */
	private void clearCache(){
		CacheManager.clearAll();
	}
	
}
