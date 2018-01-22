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


public class Replaceorder extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Replaceorder";
	
	public static final String ALIAS_REPLACE_ORDER_ID = "换货单ID";
	
	public static final String ALIAS_SUB_ORDER_ID = "子单号";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	public static final String ALIAS_UPDATE_BY = "修改者";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 换货单ID       db_column: replaceOrderId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long replaceOrderId;
    /**
     * 子单号       db_column: subOrderId  
     * 
     * 
     * 
     */	
	private java.lang.Long subOrderId;
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
     * 更新时间       db_column: updateTime  
     * 
     * 
     * 
     */	
	private java.util.Date updateTime;
    /**
     * 修改者       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String updateBy;
	//columns END

	public Replaceorder(){
	}

	public Replaceorder(
		java.lang.Long replaceOrderId
	){
		this.replaceOrderId = replaceOrderId;
	}

	public void setReplaceOrderId(java.lang.Long value) {
		this.replaceOrderId = value;
	}
	
	public java.lang.Long getReplaceOrderId() {
		return this.replaceOrderId;
	}
	public void setSubOrderId(java.lang.Long value) {
		this.subOrderId = value;
	}
	
	public java.lang.Long getSubOrderId() {
		return this.subOrderId;
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
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ReplaceOrderId",getReplaceOrderId())
			.append("SubOrderId",getSubOrderId())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateBy",getUpdateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getReplaceOrderId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Replaceorder == false) return false;
		if(this == obj) return true;
		Replaceorder other = (Replaceorder)obj;
		return new EqualsBuilder()
			.append(getReplaceOrderId(),other.getReplaceOrderId())
			.isEquals();
	}
}

