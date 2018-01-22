/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.model;


import java.sql.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OrderDayStatistic implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "OrderDayStatistic";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_DAY = "day";
	
	public static final String ALIAS_DEAL_AMOUNT = "日交易额";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	private java.lang.Long id;
    /**
     * day       db_column: day  
     * 
     * 
     * @NotNull 
     */	
	private String day;
    /**
     * 日交易额       db_column: deal_amount  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long dealAmount;
	//columns END

	public OrderDayStatistic(){
	}

	public OrderDayStatistic(
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
	
	public void setDay(String value) {
		this.day = value;
	}
	
	public String getDay() {
		return this.day;
	}
	public void setDealAmount(java.lang.Long value) {
		this.dealAmount = value;
	}
	
	public java.lang.Long getDealAmount() {
		return this.dealAmount;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Day",getDay())
			.append("DealAmount",getDealAmount())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OrderDayStatistic == false) return false;
		if(this == obj) return true;
		OrderDayStatistic other = (OrderDayStatistic)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

