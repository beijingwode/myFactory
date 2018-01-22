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


public class Suborderstatuslog extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Suborderstatuslog";
	
	public static final String ALIAS_ORDER_STATUS_LOG_ID = "日志ID";
	
	public static final String ALIAS_SUB_ORDER_ID = "子单号";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_CREATE_BY = "创建者";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 日志ID       db_column: orderStatusLogId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long orderStatusLogId;
    /**
     * 子单号       db_column: subOrderId  
     * 
     * 
     * 
     */	
	private java.lang.String subOrderId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * 
     */	
	private int status;
    /**
     * 创建时间       db_column: createTime  
     * 
     * 
     * 
     */	
	private java.util.Date createTime;
    /**
     * 创建者       db_column: createBy  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String createBy;
	//columns END

	public Suborderstatuslog(){
	}

	public Suborderstatuslog(
		java.lang.Long orderStatusLogId
	){
		this.orderStatusLogId = orderStatusLogId;
	}

	public void setOrderStatusLogId(java.lang.Long value) {
		this.orderStatusLogId = value;
	}
	
	public java.lang.Long getOrderStatusLogId() {
		return this.orderStatusLogId;
	}
	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return this.status;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateBy(java.lang.String value) {
		this.createBy = value;
	}
	
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("OrderStatusLogId",getOrderStatusLogId())
			.append("SubOrderId",getSubOrderId())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("CreateBy",getCreateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOrderStatusLogId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Suborderstatuslog == false) return false;
		if(this == obj) return true;
		Suborderstatuslog other = (Suborderstatuslog)obj;
		return new EqualsBuilder()
			.append(getOrderStatusLogId(),other.getOrderStatusLogId())
			.isEquals();
	}
}

