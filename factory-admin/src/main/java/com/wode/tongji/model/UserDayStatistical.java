package com.wode.tongji.model;
import java.util.Date;

public class UserDayStatistical implements Comparable<Object>{
	/**
     * 
     */
    private Integer id;
    /**
     * 统计的那一天
     */
    private Date day;
    /**
     * 什么时候进行统计的  统计时间
     */
    private Date creatTime;
    /**
     * 日活量
     */
    private Long activeNumber;
    /**
     * 日注量
     */
    private Long registerNumber;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Long getActiveNumber() {
		return activeNumber;
	}

	public void setActiveNumber(Long activeNumber) {
		this.activeNumber = activeNumber;
	}

	public Long getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(Long registerNumber) {
		this.registerNumber = registerNumber;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		UserDayStatistical dayDate = (UserDayStatistical)o;
		if(this.day.after(dayDate.getDay())){
			return 1;
		}else if(this.day.before(dayDate.getDay())){
			return -1;
		}else{
			return 0;
		}
	}

}