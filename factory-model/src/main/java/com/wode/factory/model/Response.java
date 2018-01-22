package com.wode.factory.model;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Response {
	/**
	 * 状态码（与标准web一致）
	 */
	private Integer status=0;

	/**
	 * 说明
	 */
	private String errmsg="";

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	/**
	 * 数据
	 */
	private Body body;

	/**
	 * 状态码（与标准web一致）
	 */
	private String time_stamp="";
	/**  
	 * @Fields counts  发件记录 受理状态
	 */ 
	private Integer[] counts;
	public String getTime_stamp() {
		if("".equals(time_stamp))
		{
			time_stamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		return time_stamp;
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

	public Integer[] getCounts() {
		return counts;
	}

	public void setCounts(Integer[] counts) {
		this.counts = counts;
	}

	
}
