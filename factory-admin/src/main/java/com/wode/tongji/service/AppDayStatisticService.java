package com.wode.tongji.service;

import java.util.List;

import com.wode.tongji.model.AppDayStatistic;


public interface AppDayStatisticService{

    
    /**
     * 
     * 功能说明：根据日期和appcode更新日活量
     * 日期:	2015年5月20日
     * 开发者:宋艳垒
     *
     * @param time
     * @param mark
     * @return
     */
    public Integer updateDayActive(AppDayStatistic ads);
    /**
     * 功能说明：获取app的日活量
     * 日期:	2015年5月20日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @param startTime
     * @param endTime
     * @param mark
     * @return
     */
    public List<AppDayStatistic> selectAppDayStatistic(String startTime,String endTime,String mark);
    /**
     * 功能说明：查询去重后的appCode
     * 日期:	2015年5月21日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    public List<String> selectDistinctApkCode();
}
