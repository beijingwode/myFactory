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


public class Replaceorderlog extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Replaceorderlog";
	
	public static final String ALIAS_REPLACE_ORDER_LOG_ID = "换货单日志ID";
	
	public static final String ALIAS_REPLACE_ORDER_ID = "换货单号";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_CREATE_BY = "创建者";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 换货单日志ID       db_column: replaceOrderLogId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long replaceOrderLogId;
    /**
     * 换货单号       db_column: replaceOrderId  
     * 
     * 
     * 
     */	
	private java.lang.Long replaceOrderId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * @Length(max=2)
     */	
	private java.lang.String status;
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

	public Replaceorderlog(){
	}

	public Replaceorderlog(
		java.lang.Long replaceOrderLogId
	){
		this.replaceOrderLogId = replaceOrderLogId;
	}

	public void setReplaceOrderLogId(java.lang.Long value) {
		this.replaceOrderLogId = value;
	}
	
	public java.lang.Long getReplaceOrderLogId() {
		return this.replaceOrderLogId;
	}
	public void setReplaceOrderId(java.lang.Long value) {
		this.replaceOrderId = value;
	}
	
	public java.lang.Long getReplaceOrderId() {
		return this.replaceOrderId;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
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
			.append("ReplaceOrderLogId",getReplaceOrderLogId())
			.append("ReplaceOrderId",getReplaceOrderId())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("CreateBy",getCreateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getReplaceOrderLogId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Replaceorderlog == false) return false;
		if(this == obj) return true;
		Replaceorderlog other = (Replaceorderlog)obj;
		return new EqualsBuilder()
			.append(getReplaceOrderLogId(),other.getReplaceOrderLogId())
			.isEquals();
	}
}

