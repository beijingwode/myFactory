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


public class Paymentjournal extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Paymentjournal";
	
	public static final String ALIAS_ID = "主键";
	
	public static final String ALIAS_OUT_TRADE_NO = "对外交易号";
	
	public static final String ALIAS_ORDER_NO = "订单号";
	
	public static final String ALIAS_TYPE = "数据类型";
	
	public static final String ALIAS_TREADENO = "流水号";
	
	public static final String ALIAS_CREATED_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATED_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 主键       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 对外交易号       db_column: outTradeNo  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String outTradeNo;
    /**
     * 订单号       db_column: orderId  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String orderId;
    /**
     * 数据类型       db_column: type  
     * 
     * 
     * @Max(127)
     */	
	private Integer type;
    /**
     * 流水号       db_column: tradeNo  
     * 
     * 
     * @Length(max=3000)
     */	
	private java.lang.String tradeNo;
    /**
     * 创建时间       db_column: createdTime  
     * 
     * 
     * 
     */	
	private java.util.Date createdTime;
	//columns END

	public Paymentjournal(){
	}

	public Paymentjournal(
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
	public void setOutTradeNo(java.lang.String value) {
		this.outTradeNo = value;
	}
	
	public java.lang.String getOutTradeNo() {
		return this.outTradeNo;
	}
	public void setOrderId(java.lang.String value) {
		this.orderId = value;
	}
	
	public java.lang.String getOrderId() {
		return this.orderId;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setTradeNo(java.lang.String value) {
		this.tradeNo = value;
	}
	
	public java.lang.String getTradeNo() {
		return this.tradeNo;
	}
	public String getCreatedTimeString() {
		return DateConvertUtils.format(getCreatedTime(), FORMAT_CREATED_TIME);
	}
	public void setCreatedTimeString(String value) {
		setCreatedTime(DateConvertUtils.parse(value, FORMAT_CREATED_TIME,java.util.Date.class));
	}
	
	public void setCreatedTime(java.util.Date value) {
		this.createdTime = value;
	}
	
	public java.util.Date getCreatedTime() {
		return this.createdTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OutTradeNo",getOutTradeNo())
			.append("OrderId",getOrderId())
			.append("Type",getType())
			.append("TradeNo",getTradeNo())
			.append("CreatedTime",getCreatedTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Paymentjournal == false) return false;
		if(this == obj) return true;
		Paymentjournal other = (Paymentjournal)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

