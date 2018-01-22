package com.wode.tongji.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wode.tongji.model.AppDayStatistic;

public interface AppDayStatisticMapper {

    AppDayStatistic selectByPrimaryKey(Long id);
    /**
     * 功能说明：查询数据库中最新的一条时间记录
     * 日期:	2015年5月19日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    public AppDayStatistic selectByLatestTime();
    /**
     * 功能说明：根据时间查询
     * 日期:	2015年5月19日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @param time
     * @return
     */
    public List<AppDayStatistic> selectByTime(@Param("time")String time,@Param("mark")String mark);
    
    /**
     * 
     * 功能说明：根据appcode和时间查询
     * 日期:	2015年5月20日
     * 开发者:宋艳垒
     *
     * @param time
     * @param mark
     * @return
     */
    public List<AppDayStatistic> selectByCodeDate(AppDayStatistic ads);
    
    /**
     * 
     * 功能说明：更新日活量
     * 日期:	2015年5月20日
     * 开发者:宋艳垒
     *
     * @param time
     * @param mark
     * @return
     */
    public Integer updateDayAppStatistics(AppDayStatistic ads);
    
    /**
     * 
     * 功能说明：添加日活量
     * 日期:	2015年5月20日
     * 开发者:宋艳垒
     *
     * @param time
     * @param mark
     * @return
     */
    public Integer insertSelective(AppDayStatistic ads);
    
    /**
     * 功能说明：根据时间区间查询
     * 日期:	2015年5月19日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @param start
     * @param end
     * @param mark
     * @return
     */
    public List<AppDayStatistic> selectByStartAndEndTime(@Param("start")String start,@Param("end")String end,@Param("mark")String mark);
    /**
     * 功能说明：查询apk的名称并且去重
     * 日期:	2015年5月19日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    public List<String> selectDistinctApkCode();
    /**
     * 功能说明：根据AppCode进行查询，查询的是最新一月的数据
     * 日期:	2015年5月20日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @param code
     * @return
     */
    public List<AppDayStatistic> selectByCode(String code);
    
}