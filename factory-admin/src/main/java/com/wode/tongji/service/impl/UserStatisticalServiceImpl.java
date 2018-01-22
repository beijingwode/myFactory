package com.wode.tongji.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.StringUtils;
import com.wode.tongji.mapper.UserDayStatisticalMapper;
import com.wode.tongji.mapper.UserGeoMapper;
import com.wode.tongji.mapper.UserMonthStatisticalMapper;
import com.wode.tongji.model.CityInfo;
import com.wode.tongji.model.UserDayStatistical;
import com.wode.tongji.model.UserGeo;
import com.wode.tongji.model.UserGeoInfo;
import com.wode.tongji.model.UserMonthStatistical;
import com.wode.tongji.service.UserStatisticalService;
@Service
public class UserStatisticalServiceImpl implements UserStatisticalService {
	@Autowired
	UserDayStatisticalMapper userDayStatisticalMapper;
	@Autowired
	UserGeoMapper userGeoMapper;
	@Autowired
	UserMonthStatisticalMapper userMonthStatisticalMapper;
	
	private Map<String,Object> map = new HashMap<String, Object>();
	
	/* 日统计
	 * @see com.wode.tongji.service.UserStatisticalService#day(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserDayStatistical> day(String startTime, String endTime) {
		// TODO Auto-generated method stub
		List<UserDayStatistical> li_userDay = null;
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
			li_userDay = this.userDayStatisticalMapper.selectByTime(map);			
		}else if(start||end){
			map.put("startTime", StringUtils.isEmpty(startTime)?endTime:startTime);
			map.put("endTime",null);
			li_userDay = this.userDayStatisticalMapper.selectByTime(map);
		}else if(!start&&!end){
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			li_userDay = this.userDayStatisticalMapper.selectByTime(map);
		}else{
			return new ArrayList<UserDayStatistical>();
		}
		if (!li_userDay.isEmpty()) {
			Collections.sort(li_userDay);
		}
		return li_userDay;
	}

	/* 注册统计
	 * @see com.wode.tongji.service.UserStatisticalService#allCity()
	 */
	@Override
	public UserGeoInfo allCity() {
		// TODO Auto-generated method stub
		List<UserGeo> li_userGeo = this.userGeoMapper.selectAllCity();
		if(li_userGeo.isEmpty()){
			return new UserGeoInfo();
		}else{
			//最大注册量
			Long maxRegist = this.selectMaxRegistNumber();
			//最小注册量
			Long minRegist = this.selectMinRegistNumber();
			//最大注册量的几个城市
			List<UserGeo> noMaxCity = this.selectRegistNoMaxCityInfo();
			//封装信息类
			UserGeoInfo userGeoInfo = new UserGeoInfo();
			//城市信息集合
			List<CityInfo> li_ci = new ArrayList<CityInfo>();
			for(UserGeo userGeo : li_userGeo){
				CityInfo ci = new CityInfo();
				ci.setName(userGeo.getCity());
				ci.setValue(userGeo.getRegistNumber());
				li_ci.add(ci);
			}
			//注册量前几名的城市集合
			List<CityInfo> li_maxCi = new ArrayList<CityInfo>();
			for(UserGeo max :noMaxCity){
				CityInfo ci = new CityInfo();
				ci.setName(max.getCity());
				ci.setValue(max.getRegistNumber());
				li_maxCi.add(ci);
			}
			userGeoInfo.setMaxCityInfo(li_maxCi);
			userGeoInfo.setCityInfo(li_ci);
			userGeoInfo.setMaxRegistNumber(maxRegist);
			userGeoInfo.setMinRegistNumber(minRegist);
			
			return userGeoInfo;
		}
	}

	/* 月统计
	 * @see com.wode.tongji.service.UserStatisticalService#month(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserMonthStatistical> month(String monthStartTime,
			String monthEndTime) throws ParseException {
		// TODO Auto-generated method stub
		List<UserMonthStatistical> li_userMonth = null;
		//开始时间是否为空
		Boolean start = StringUtils.isNullOrEmpty(monthStartTime);
		//结束时间是否为空
		Boolean end = StringUtils.isNullOrEmpty(monthEndTime);
		/**
		 * 如果time字段为空，查询的是最新的一年  12个月的数据
		 * */
		if (start&&end) {
			map.put("startTime",new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()));
			map.put("endTime",null);
			li_userMonth = this.userMonthStatisticalMapper.selectByTime(map);
		}else if(start||end){
			map.put("startTime", StringUtils.isEmpty(monthStartTime)?monthEndTime:monthStartTime);
			map.put("endTime",null);
			li_userMonth = this.userMonthStatisticalMapper.selectByTime(map);
		}else if(!start&&!end){
			SimpleDateFormat si = new SimpleDateFormat("yyyy-MM");
			Calendar date = Calendar.getInstance();
			date.setTime(si.parse(monthEndTime));
			date.set(Calendar.MONTH, date.get(Calendar.MONTH)+1);
			map.put("startTime", monthStartTime);
			map.put("endTime", si.format(date.getTime()));
			li_userMonth = this.userMonthStatisticalMapper.selectByTime(map);
		}else{
			return new ArrayList<UserMonthStatistical>();
		}
		if(!li_userMonth.isEmpty()){
			Collections.sort(li_userMonth);
		}
		return li_userMonth;
	}
	
	/**
	 * 功能说明：最大注册量
	 * 日期:	2015年5月21日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	private Long selectMaxRegistNumber(){
		return this.userGeoMapper.selectMaxRegistNumber();
	}
	/**
	 * 功能说明：查询注册量排前几的城市信息
	 * 日期:	2015年5月22日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	private List<UserGeo> selectRegistNoMaxCityInfo(){
		return this.userGeoMapper.selectRegistNumberMaxCity();
	}
	/**
	 * 功能说明：最小注册量
	 * 日期:	2015年5月21日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	private Long selectMinRegistNumber(){
		return this.userGeoMapper.selectMinRegistNumber();
	}
}
