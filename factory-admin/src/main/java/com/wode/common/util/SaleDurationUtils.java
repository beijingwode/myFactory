package com.wode.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author gaoyj
 *
 */
public class SaleDurationUtils {
	
	
	/**
	 * 计算首个账期
	 * 
	 * @param execDate
	 *            首个结算日
	 * @return [账期开始时间，账期结束时间，账期结算时间]
	 */
	public static Date[] getFirstDuration(Date execDate) {
		Date[] rtn = new Date[3];
		Calendar c = Calendar.getInstance();
		//c.set(2015, 11, 10, 0, 0, 0);
		
		//账期开始日期 = 2015-11-10 00:00:00
		rtn[0] = c.getTime();
		
		//输入日期时间清零
		c = Calendar.getInstance();
		c.setTime(execDate);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		
		//账期计算日期
		rtn[2] = c.getTime();
		
		//账期结束日期 = 账期计算日期前一天 23:59:59
		c.add(Calendar.SECOND, -1);
		rtn[1] = c.getTime();
		
		return rtn;
	}

	/**
	 * 计算首个账期
	 * 
	 * @param execDate
	 *            结算日
	 * @return [账期开始时间，账期结束时间，账期结算时间]
	 */
	public static Date[] getDuration(String key,Date execDate) {
		Date[] rtn = new Date[3];

		//输入日期时间清零
		Calendar c = Calendar.getInstance();
		c.setTime(execDate);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		
		//账期开始日期 = 当前结算日
		rtn[0] = c.getTime();
		
		//当前日
		int day = c.get(Calendar.DATE);
		int lastday = getLastDayOfMonth(c.getTime());

		switch (key) {
		case "105":
			//半月结
			if(day < 16) {
				if(day+15 > lastday) {
					c.set(Calendar.DATE, lastday);
				} else {
					c.set(Calendar.DATE, day+15);
				}
			} else {
				c.add(Calendar.MONTH, 1);	//月份+1
				c.add(Calendar.DATE, -15);	//日-15
				//账期计算日期
				rtn[2] = c.getTime();				
			}

			//账期结束日期 = 账期计算日期前一天 23:59:59
			c.add(Calendar.SECOND, -1);
			rtn[1] = c.getTime();
			break;

		case "106":
			//月结
			c.add(Calendar.MONTH, 1);		//月份+1
			lastday = getLastDayOfMonth(c.getTime()); //下月最后一天
			if(day <= lastday) {
				c.set(Calendar.DATE, day);	//日相同
				//账期计算日期
				rtn[2] = c.getTime();	
			} else {
				c.set(Calendar.DATE, lastday);	//下月月末
				//账期计算日期
				rtn[2] = c.getTime();				
			}

			//账期结束日期 = 账期计算日期前一天 23:59:59
			c.add(Calendar.SECOND, -1);
			rtn[1] = c.getTime();
			break;
			
		case "107":
			//每月5,15,25日结算
			if(day >= 25) {
				c.add(Calendar.MONTH, 1);	//月份+1
				c.set(Calendar.DATE, 5);	//下月5日
				//账期计算日期
				rtn[2] = c.getTime();	
			} else {
				c.set(Calendar.DATE, day+10);	//日+10
				//账期计算日期
				rtn[2] = c.getTime();				
			}

			//账期结束日期 = 账期计算日期前一天 23:59:59
			c.add(Calendar.SECOND, -1);
			rtn[1] = c.getTime();
			break;
			
		default:
			//按固定天数结算
			c.add(Calendar.DATE, getAmount(key));
			//账期计算日期
			rtn[2] = c.getTime();

			//账期结束日期 = 账期计算日期前一天 23:59:59
			c.add(Calendar.SECOND, -1);
			rtn[1] = c.getTime();
			break;
		}
		
		
		return rtn;
	}
	
	/**
	 * 按日数结时，计算偏移量
	 * @param key
	 * @return
	 */
	public static int getAmount(String key) {
		int rtn = 1;

		switch (key) {
		case "101":
			//每天结算
			rtn = 1;
			break;
		case "102":
			//每五天结算
			rtn = 5;
			break;

		case "103":
			//每七天结算
			rtn = 7;
			break;

		case "104":
			//每10天结算
			rtn = 10;
			break;
		case "201":
		case "211":
		case "221":
			//每天结算
			rtn = 1;
			break;

		default:
			rtn = 0;
			break;
		}
		//如果是3系列账期都为一天
		if(key.startsWith("3")){
			rtn = 1;
		}
		return rtn;
	}
	
	/**
	 * 获取指定日期的本月最后一天
	 * @param c
	 * @return
	 */
	public static int getLastDayOfMonth(Date execDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(execDate);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		
		return c.get(Calendar.DATE);
	}
	/**
	 * 返回账期对应天数（仅限于3系列账期形式）
	 * @param key
	 * @return
	 */
	public static int getDayBySaleDurationKey(String key){
		int rtn = 1000;

		//截取3xx系列后面xx转换为账期天数
		if(null != key && key.startsWith("3")){
			key = key.substring(1,key.length());
			rtn = Integer.parseInt(key);
		}
		
		return rtn;
		
	}

	public static void main(String[] args) {
		Date[] r = getFirstDuration(new Date());
		//System.out.println(getLastDayOfMonth(Calendar.getInstance()));
	}

}
