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


public class Message extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Message";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_TITLE = "title";
	
	public static final String ALIAS_TEXT = "text";
	
	
	public static final String ALIAS_START_TIME = "start_time";
	
	public static final String ALIAS_END_TIME = "end_time";
	
	public static final String ALIAS_TYPE = "类型";
	
	public static final String ALIAS_CREAT_TIME = "creat_time";
	
	public static final String ALIAS_TO_USER_TYPE = "接收的用户";
	
	public static final String ALIAS_USER_ID = "用户id";
	
	//date formats
	public static final String FORMAT_START_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_END_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_CREAT_TIME = DATE_TIME_FORMAT;
	
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
     * title       db_column: title  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String title;
    /**
     * text       db_column: text  
     * 
     * 
     * @Length(max=500)
     */	
	private java.lang.String text;
    /**
     * start_time       db_column: start_time  
     * 
     * 
     * 
     */	
	private java.util.Date startTime;
    /**
     * end_time       db_column: end_time  
     * 
     * 
     * 
     */	
	private java.util.Date endTime;
    /**
     * 类型       db_column: type  
     * 
     * 
     * 
     */	
	private java.lang.Integer type;
    /**
     * creat_time       db_column: creat_time  
     * 
     * 
     * 
     */	
	private java.util.Date creatTime;
    /**
     * 接收的用户       db_column: to_user_type  
     * 
     * 
     * 
     */	
	private java.lang.Integer toUserType;
    /**
     * 用户id       db_column: user_id  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
	//columns END

	public Message(){
	}

	public Message(
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
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setText(java.lang.String value) {
		this.text = value;
	}
	
	public java.lang.String getText() {
		return this.text;
	}
	public String getStartTimeString() {
		return DateConvertUtils.format(getStartTime(), FORMAT_START_TIME);
	}
	public void setStartTimeString(String value) {
		setStartTime(DateConvertUtils.parse(value, FORMAT_START_TIME,java.util.Date.class));
	}
	
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public String getEndTimeString() {
		return DateConvertUtils.format(getEndTime(), FORMAT_END_TIME);
	}
	public void setEndTimeString(String value) {
		setEndTime(DateConvertUtils.parse(value, FORMAT_END_TIME,java.util.Date.class));
	}
	
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public String getCreatTimeString() {
		return DateConvertUtils.format(getCreatTime(), FORMAT_CREAT_TIME);
	}
	public void setCreatTimeString(String value) {
		setCreatTime(DateConvertUtils.parse(value, FORMAT_CREAT_TIME,java.util.Date.class));
	}
	
	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}
	
	public java.util.Date getCreatTime() {
		return this.creatTime;
	}
	public void setToUserType(java.lang.Integer value) {
		this.toUserType = value;
	}
	
	public java.lang.Integer getToUserType() {
		return this.toUserType;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Title",getTitle())
			.append("Text",getText())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("Type",getType())
			.append("CreatTime",getCreatTime())
			.append("ToUserType",getToUserType())
			.append("UserId",getUserId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Message == false) return false;
		if(this == obj) return true;
		Message other = (Message)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

