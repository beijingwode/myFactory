package com.wode.tongji.mapper;

import java.util.List;
import java.util.Map;

import com.wode.tongji.model.UserMonthStatistical;


public interface UserMonthStatisticalMapper {
    /**
     * 功能说明：添加月统计数据
     * 日期:	2015年5月18日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @param record
     * @return
     */
    int insertSelective(UserMonthStatistical record);
    /**根据时间节点查询统计数据，如果为空，查询的是最新一月的数据
     * 功能说明：
     * 日期:	2015年5月14日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    List<UserMonthStatistical> selectByTime(Map<String,Object> map);
}