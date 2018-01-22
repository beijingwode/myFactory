package com.wode.tongji.mapper;

import java.util.List;
import java.util.Map;

import com.wode.tongji.model.UserDayStatistical;


public interface UserDayStatisticalMapper {
    int insertSelective(UserDayStatistical record);
    /**
     * 功能说明：根据每日的注册量查询月注量
     * 日期:	2015年5月14日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @param time
     * @return
     */
    List<UserDayStatistical> selectMonthRegisterNumber(String time);
    /**根据时间节点查询统计数据，如果为空，查询的是最新一月的数据
     * 功能说明：
     * 日期:	2015年5月14日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    List<UserDayStatistical> selectByTime(Map<String,Object> map);
    
}