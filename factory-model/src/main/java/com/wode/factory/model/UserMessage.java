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


public class UserMessage extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserMessage";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_MSG_ID = "msgid";
	
	public static final String ALIAS_IS_READ = "is_read";
	
	public static final String ALIAS_READ_TIME = "read_time";
	
	//date formats
	public static final String FORMAT_READ_TIME = DATE_TIME_FORMAT;
	
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
     * msgid       db_column: msg_id  
     * 
     * 
     * 
     */	
	private java.lang.Long msgId;
    /**
     * is_read       db_column: is_read  
     * 
     * 
     * 
     */	
	private java.lang.Integer isRead;
    /**
     * read_time       db_column: read_time  
     * 
     * 
     * 
     */	
	private java.util.Date readTime;
	//columns END

	public UserMessage(){
	}

	public UserMessage(
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
	public void setMsgId(java.lang.Long value) {
		this.msgId = value;
	}
	
	public java.lang.Long getMsgId() {
		return this.msgId;
	}
	public void setIsRead(java.lang.Integer value) {
		this.isRead = value;
	}
	
	public java.lang.Integer getIsRead() {
		return this.isRead;
	}
	public String getReadTimeString() {
		return DateConvertUtils.format(getReadTime(), FORMAT_READ_TIME);
	}
	public void setReadTimeString(String value) {
		setReadTime(DateConvertUtils.parse(value, FORMAT_READ_TIME,java.util.Date.class));
	}
	
	public void setReadTime(java.util.Date value) {
		this.readTime = value;
	}
	
	public java.util.Date getReadTime() {
		return this.readTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MsgId",getMsgId())
			.append("IsRead",getIsRead())
			.append("ReadTime",getReadTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserMessage == false) return false;
		if(this == obj) return true;
		UserMessage other = (UserMessage)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

