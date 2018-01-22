package com.wode.factory.model;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wode.factory.model.ExpressCompany;


public class CompInfo {
	/**
	 * 状态码（与标准web一致）
	 */
	private Integer status=0;

	/**
	 * 说明
	 */
	private String errmsg="";
	private String page;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public ExpressCompany getBody() {
		return body;
	}

	public void setBody(ExpressCompany body) {
		this.body = body;
	}

	/**
	 * 数据
	 */
	private ExpressCompany body;

	/**
	 * 状态码（与标准web一致）
	 */
	private String time_stamp="";
	/**  
	 * @Fields counts  发件记录 受理状态
	 */ 
	private String counts;
	public String getTime_stamp() {
		if("".equals(time_stamp))
		{
			time_stamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		return time_stamp;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	
}
