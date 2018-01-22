package com.wode.tongji.mapper;

import java.util.List;
import java.util.Map;

import com.wode.tongji.model.PvDayStatistical;

public interface PvDayStatisticalMapper {

    void insert(PvDayStatistical model);
    /**根据时间节点查询统计数据，如果为空，查询的是最新一月的数据
     * 功能说明：
     * 日期:	2015年5月14日
     * 开发者:张晨旭
     * 版本号:1.0
     *
     * @return
     */
    List<PvDayStatistical> selectByTime(Map<String,Object> map);
   
    
}