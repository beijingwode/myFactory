package com.wode.user.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.mapper.BalanceMonthStatisticalDao;
import com.wode.factory.mapper.EntSeasonActDao;
import com.wode.factory.mapper.UserBalanceMapper;


@Service
public class BalanceMonthStatistic {
	@Autowired
	BalanceMonthStatisticalDao balanceMothStatisticalMapper;
	@Autowired
	EntSeasonActDao entSeasonActMapper;
	@Autowired
	UserBalanceMapper userBalanceMapper;
	
	/**
	 * 添加月统计信息
	 */
	public void run() {
		com.wode.factory.model.BalanceMonthStatistical balanceMonth = new com.wode.factory.model.BalanceMonthStatistical();
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM");
		//获取当前系统时间
		Calendar date = Calendar.getInstance();
		//设置创建时间
		balanceMonth.setCreatTime(date.getTime());
		//将月份减一
		date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
		String lastMonth = si.format(date.getTime());
		//查询上个月的商家现金券余额
		 BigDecimal supplierBalance = entSeasonActMapper.selectBalanceByDate(lastMonth);
		//查询上个月的用户现金券余额
		 BigDecimal userBalance = userBalanceMapper.selectBalanceByDate(lastMonth);
		 balanceMonth.setSupplierBalance(supplierBalance);
		 balanceMonth.setUserBalance(userBalance);
		 //设置月份
		balanceMonth.setMonth(lastMonth);
		this.balanceMothStatisticalMapper.insertSelective(balanceMonth);
	}
}
