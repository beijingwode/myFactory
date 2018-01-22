package com.wode.user.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.tongji.mapper.UserDayStatisticalMapper;
import com.wode.tongji.mapper.UserMonthStatisticalMapper;
import com.wode.tongji.model.UserDayStatistical;
import com.wode.tongji.model.UserMonthStatistical;

@Service
public class UserMonthStatistic  {
	@Autowired
	UserDayStatisticalMapper userDayStatisticalMapper;
	@Autowired
	UserMonthStatisticalMapper userMonthStatisticalMapper;

	/*
	 * 添加月统计信息
	 * 
	 */
	public void run() {
		// TODO Auto-generated method stub
		UserMonthStatistical userMonth = new UserMonthStatistical();
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM");

		Calendar date = Calendar.getInstance();
		// 统计时间
		userMonth.setCreatTime(date.getTime());
		// 将月份减一
		date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
		// 得到的是上个月的月份 并转成string类型的进行查询
		String lastMonth = si.format(date.getTime());
		List<UserDayStatistical> li_userDay = this.userDayStatisticalMapper.selectMonthRegisterNumber(lastMonth);
		long monthRegisterNumber = 0;
		long monthActiveNumber = 0;
		// 循环取注册量并进行统计
		for (UserDayStatistical userDayStatistical : li_userDay) {
			monthRegisterNumber = monthRegisterNumber
					+ userDayStatistical.getRegisterNumber();
			
			monthActiveNumber = monthActiveNumber+ userDayStatistical.getActiveNumber();
		}
		// 保存注册量
		userMonth.setRegisterNumber(monthRegisterNumber);
		//保存活跃量
		userMonth.setActiveNumber(monthActiveNumber);
		try {
			userMonth.setMonth(si.parse(lastMonth));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 添加月注量
		this.userMonthStatisticalMapper.insertSelective(userMonth);
	}

	
	

}
