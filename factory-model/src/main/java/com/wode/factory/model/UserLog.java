/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class UserLog extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserLog";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "user_id";
	
	public static final String ALIAS_USERNAME = "username";
	
	public static final String ALIAS_ACT = "act";
	
	public static final String ALIAS_TIME = "time";
	
	public static final String ALIAS_RESULT = "result";
	
	//date formats
	public static final String FORMAT_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
    /**
     * username       db_column: username  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String username;
    /**
     * act       db_column: act  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String act;
    /**
     * time       db_column: time  
     * 
     * 
     * 
     */	
	private java.util.Date time;
    /**
     * result       db_column: result  
     * 
     * 
     * @Length(max=500)
     */	
	private java.lang.String result;
	//columns END

	public UserLog(){
	}

	public UserLog(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setAct(java.lang.String value) {
		this.act = value;
	}
	
	public java.lang.String getAct() {
		return this.act;
	}
	public String getTimeString() {
		return DateConvertUtils.format(getTime(), FORMAT_TIME);
	}
	public void setTimeString(String value) {
		setTime(DateConvertUtils.parse(value, FORMAT_TIME,java.util.Date.class));
	}
	
	public void setTime(java.util.Date value) {
		this.time = value;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}
	public void setResult(java.lang.String value) {
		this.result = value;
	}
	
	public java.lang.String getResult() {
		return this.result;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Username",getUsername())
			.append("Act",getAct())
			.append("Time",getTime())
			.append("Result",getResult())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserLog == false) return false;
		if(this == obj) return true;
		UserLog other = (UserLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

