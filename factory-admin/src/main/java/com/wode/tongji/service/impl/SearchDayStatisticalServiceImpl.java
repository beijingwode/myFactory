package com.wode.tongji.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.common.util.StringUtils;
import com.wode.tongji.mapper.SearchDayStatisticalMapper;
import com.wode.tongji.model.SearchDayStatistical;
import com.wode.tongji.service.SearchDayStatisticalService;

@Service("searchDayStatisticalService")
public class SearchDayStatisticalServiceImpl implements SearchDayStatisticalService {
	@Autowired
	SearchDayStatisticalMapper dao;
	private Map<String,Object> map = new HashMap<String, Object>();
	
	/* 日统计
	 * @see com.wode.tongji.service.SearchDayStatisticalService#day(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SearchDayStatistical> day(String searchKey,String startTime, String endTime) {
		List<SearchDayStatistical> li_userDay = null;
		// 开始时间是否为空
		Boolean start = StringUtils.isNullOrEmpty(startTime);
		// 结束时间是否为空
		Boolean end = StringUtils.isNullOrEmpty(endTime);
		/**
		 * 如果time字段为空，查询的是最新的一月数据
		 * */
		map.put("searchKey", StringUtils.isNullOrEmpty(searchKey)?"":searchKey);
		if (start && end) {
			map.put("startTime",new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime()));
			map.put("endTime",null);
			li_userDay = this.dao.selectByTime(map);			
		}else if(start||end){
			map.put("startTime", StringUtils.isEmpty(startTime)?endTime:startTime);
			map.put("endTime",null);
			li_userDay = this.dao.selectByTime(map);
		}else if(!start&&!end){
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			li_userDay = this.dao.selectByTime(map);
		}else{
			return new ArrayList<SearchDayStatistical>();
		}
		return li_userDay;
	}
	/* 日统计
	 * @see com.wode.tongji.service.SearchDayStatisticalService#day(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SearchDayStatistical> month(String searchKey,String startTime, String endTime) throws ParseException {
		List<SearchDayStatistical> li_userDay = null;
		// 开始时间是否为空
		Boolean start = StringUtils.isNullOrEmpty(startTime);
		// 结束时间是否为空
		Boolean end = StringUtils.isNullOrEmpty(endTime);
		/**
		 * 如果time字段为空，查询的是最新的一月数据
		 * */
		map.put("searchKey", StringUtils.isNullOrEmpty(searchKey)?"":searchKey);
		if (start && end) {
			map.put("startTime",new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
			map.put("endTime",null);
			li_userDay = this.dao.countMonth(map);
		}else if(start||end){
			map.put("startTime", StringUtils.isEmpty(startTime)?endTime:startTime);
			map.put("endTime",null);
			li_userDay = this.dao.countMonth(map);
		}else if(!start&&!end){
			SimpleDateFormat si = new SimpleDateFormat("yyyy-MM");
			Calendar date = Calendar.getInstance();
			date.setTime(si.parse(endTime));
			date.set(Calendar.MONTH, date.get(Calendar.MONTH)+1);
			map.put("startTime", startTime);
			map.put("endTime", si.format(date.getTime()));
			
			li_userDay = this.dao.countMonth(map);
		}else{
			return new ArrayList<SearchDayStatistical>();
		}
		return li_userDay;
	}

	@Override
	public PageInfo<SearchDayStatistical> getPage(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<SearchDayStatistical> lst = this.dao.countNormal(params);
		return new PageInfo(lst);
	}
}
