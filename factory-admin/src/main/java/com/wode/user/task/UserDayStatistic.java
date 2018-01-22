package com.wode.user.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.constant.Constant;
import com.wode.common.util.ActResult;
import com.wode.factory.outside.service.CommUserService;
import com.wode.factory.outside.service.ServiceFactory;
import com.wode.tongji.mapper.UserDayStatisticalMapper;
import com.wode.tongji.model.UserDayStatistical;
@Service
public class UserDayStatistic {

	@Autowired
	UserDayStatisticalMapper userDayStatisticalMapper;


	public void run() {
		/**
		 * 统计时间为次日，需要计算昨天的用户信息
		 * 
		 * */
		UserDayStatistical userDay = new UserDayStatistical();
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = Calendar.getInstance();
		//统计每日数据的时间  统计时间为次日凌晨
		userDay.setCreatTime(date.getTime());
		//将统计数据的时间减一天
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		//根据时间查询用户的活动量
		String yesTerday = si.format(date.getTime());

		CommUserService us = ServiceFactory.getCommUserService(Constant.OUTSIDE_SERVICE_URL);
		
		//日活量
		ActResult da =  us.getDayActiveNumber(yesTerday);
		Long dayActive = 0l;
		if(da.isSuccess()) {
			dayActive = Long.valueOf(da.getData().toString());
		}
		//日注量
		Long dayRegister = 0l;
		da =  us.getDayRegisterNumber(yesTerday);
		if(da.isSuccess()) {
			dayRegister= Long.valueOf(da.getData().toString());
		}
		userDay.setActiveNumber(dayActive);
		userDay.setRegisterNumber(dayRegister);
		try {
			userDay.setDay(si.parse(yesTerday));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//添加查询信息
		this.userDayStatisticalMapper.insertSelective(userDay);
	}
	
	
	
	
}
