package com.wode.tongji.model;

import java.util.Date;

public class UserMonthStatistical implements Comparable<Object>{
	private Integer id;
    /**
     * 统计那个月的数据时间
     */
    private Date month;
    /**
     * 统计时间  什么时候统计的
     */
    private Date creatTime;
    /**
     * 月活量
     */
    private Long activeNumber;
    /**
     * 月注量
     */
    private Long registerNumber;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
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
    	UserMonthStatistical monthDate = (UserMonthStatistical)o;
		if(this.month.after(monthDate.getMonth())){
			return 1;
		}else if(this.month.before(monthDate.getMonth())){
			return -1;
		}else{
			return 0;
		}
	}
}