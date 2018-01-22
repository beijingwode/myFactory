package com.wode.tongji.vo;

import java.io.Serializable;

/**
 * 
 * <pre>
 * 功能说明: 结算vo
 * 日期:	2015年3月10日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年3月10日
 * </pre>
 */
public class OrderStatisticsSelectVo implements Serializable {
	
    private String dayBegin;
    
    private String dayEnd;

	public String getDayBegin() {
		return dayBegin;
	}

	public void setDayBegin(String dayBegin) {
		this.dayBegin = dayBegin;
	}

	public String getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(String dayEnd) {
		this.dayEnd = dayEnd;
	}

	
}
