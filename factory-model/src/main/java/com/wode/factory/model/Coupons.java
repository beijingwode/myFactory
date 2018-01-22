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


public class Coupons extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Coupons";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "user_id";
	
	public static final String ALIAS_TICKET_ID = "券号码";
	
	public static final String ALIAS_DIE_TIME = "die_time";
	
	public static final String ALIAS_USED = "used";
	
	//date formats
	public static final String FORMAT_DIE_TIME = DATE_TIME_FORMAT;
	
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
     * 券号码       db_column: ticket_id  
     * 
     * 
     * 
     */	
	private java.lang.Long ticketId;
    /**
     * die_time       db_column: die_time  
     * 
     * 
     * 
     */	
	private java.util.Date dieTime;
    /**
     * used       db_column: used  
     * 
     * 
     * 
     */	
	private java.lang.Integer used;
	//columns END

	public Coupons(){
	}

	public Coupons(
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
	public void setTicketId(java.lang.Long value) {
		this.ticketId = value;
	}
	
	public java.lang.Long getTicketId() {
		return this.ticketId;
	}
	public String getDieTimeString() {
		return DateConvertUtils.format(getDieTime(), FORMAT_DIE_TIME);
	}
	public void setDieTimeString(String value) {
		setDieTime(DateConvertUtils.parse(value, FORMAT_DIE_TIME,java.util.Date.class));
	}
	
	public void setDieTime(java.util.Date value) {
		this.dieTime = value;
	}
	
	public java.util.Date getDieTime() {
		return this.dieTime;
	}
	public void setUsed(java.lang.Integer value) {
		this.used = value;
	}
	
	public java.lang.Integer getUsed() {
		return this.used;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("TicketId",getTicketId())
			.append("DieTime",getDieTime())
			.append("Used",getUsed())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Coupons == false) return false;
		if(this == obj) return true;
		Coupons other = (Coupons)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

