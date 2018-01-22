package com.wode.tongji.model;

import java.util.Date;

public class AppDayStatistic implements Comparable {
    private Long id;

    private String appCode;

    private Date day;

    private Long activeAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Long getActiveAmount() {
        return activeAmount;
    }

    public void setActiveAmount(Long activeAmount) {
        this.activeAmount = activeAmount;
    }

	@Override
	public String toString() {
		return "AppDayStatistic [id=" + id + ", appCode=" + appCode + ", day="
				+ day + ", activeAmount=" + activeAmount + "]";
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		AppDayStatistic appDay = (AppDayStatistic)arg0;
		if(this.day.after(appDay.getDay())){
			return 1;
		}else if(this.day.before(appDay.getDay())){
			return -1;
		}else{
			return 0;
		}
	}
    
}