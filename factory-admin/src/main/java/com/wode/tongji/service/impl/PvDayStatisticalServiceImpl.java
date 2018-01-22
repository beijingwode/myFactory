package com.wode.tongji.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.StringUtils;
import com.wode.tongji.mapper.PvDayStatisticalMapper;
import com.wode.tongji.mapper.UserGeoMapper;
import com.wode.tongji.mapper.UserMonthStatisticalMapper;
import com.wode.tongji.model.PvDayStatistical;
import com.wode.tongji.service.PvDayStatisticalService;

@Service("pvDayStatisticalService")
public class PvDayStatisticalServiceImpl implements PvDayStatisticalService {
	@Autowired
	PvDayStatisticalMapper PvDayStatisticalMapper;
	@Autowired
	UserGeoMapper userGeoMapper;
	@Autowired
	UserMonthStatisticalMapper userMonthStatisticalMapper;
	
	private Map<String,Object> map = new HashMap<String, Object>();
	
	/* 日统计
	 * @see com.wode.tongji.service.PvDayStatisticalService#day(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PvDayStatistical> day(String startTime, String endTime) {
		List<PvDayStatistical> li_userDay = null;
		// 开始时间是否为空
		Boolean start = StringUtils.isNullOrEmpty(startTime);
		// 结束时间是否为空
		Boolean end = StringUtils.isNullOrEmpty(endTime);
		/**
		 * 如果time字段为空，查询的是最新的一月数据
		 * */
		if (start && end) {
			map.put("startTime",new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime()));
			map.put("endTime",null);
			li_userDay = this.PvDayStatisticalMapper.selectByTime(map);			
		}else if(start||end){
			map.put("startTime", StringUtils.isEmpty(startTime)?endTime:startTime);
			map.put("endTime",null);
			li_userDay = this.PvDayStatisticalMapper.selectByTime(map);
		}else if(!start&&!end){
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			li_userDay = this.PvDayStatisticalMapper.selectByTime(map);
		}else{
			return new ArrayList<PvDayStatistical>();
		}
		return li_userDay;
	}
}
