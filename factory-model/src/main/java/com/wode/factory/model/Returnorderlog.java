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


public class Returnorderlog extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Returnorderlog";
	
	public static final String ALIAS_RETURN_ORDER_LOG_ID = "退货单日志ID";
	
	public static final String ALIAS_RETURN_ORDER_ID = "退货单号";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_CREATE_BY = "创建者";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 退货单日志ID       db_column: returnOrderLogId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long returnOrderLogId;
    /**
     * 退货单号       db_column: returnOrderId  
     * 
     * 
     * 
     */	
	private java.lang.Long returnOrderId;
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

	public Returnorderlog(){
	}

	public Returnorderlog(
		java.lang.Long returnOrderLogId
	){
		this.returnOrderLogId = returnOrderLogId;
	}

	public void setReturnOrderLogId(java.lang.Long value) {
		this.returnOrderLogId = value;
	}
	
	public java.lang.Long getReturnOrderLogId() {
		return this.returnOrderLogId;
	}
	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
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
			.append("ReturnOrderLogId",getReturnOrderLogId())
			.append("ReturnOrderId",getReturnOrderId())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("CreateBy",getCreateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getReturnOrderLogId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Returnorderlog == false) return false;
		if(this == obj) return true;
		Returnorderlog other = (Returnorderlog)obj;
		return new EqualsBuilder()
			.append(getReturnOrderLogId(),other.getReturnOrderLogId())
			.isEquals();
	}
}

