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


public class WithdrawalLog extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "WithdrawalLog";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_WITHDRAWAL_ID = "提现ID";
	
	public static final String ALIAS_TYPE = "审核类型";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_OPERATOR_ID = "审核者ID";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 提现ID       db_column: withdrawalId  
     * 
     * 
     * 
     */	
	private java.lang.Long withdrawalId;
    /**
     * 审核类型       db_column: type  
     * 
     * 
     * @Max(127)
     */	
	private Integer type;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */	
	private java.util.Date createDate;
    /**
     * 审核者ID       db_column: operatorID  
     * 
     * 
     * 
     */	
	private java.lang.Long operatorId;
	//columns END

	public WithdrawalLog(){
	}

	public WithdrawalLog(
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
	public void setWithdrawalId(java.lang.Long value) {
		this.withdrawalId = value;
	}
	
	public java.lang.Long getWithdrawalId() {
		return this.withdrawalId;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public String getCreateDateString() {
		return DateConvertUtils.format(getCreateDate(), FORMAT_CREATE_DATE);
	}
	public void setCreateDateString(String value) {
		setCreateDate(DateConvertUtils.parse(value, FORMAT_CREATE_DATE,java.util.Date.class));
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setOperatorId(java.lang.Long value) {
		this.operatorId = value;
	}
	
	public java.lang.Long getOperatorId() {
		return this.operatorId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("WithdrawalId",getWithdrawalId())
			.append("Type",getType())
			.append("CreateDate",getCreateDate())
			.append("OperatorId",getOperatorId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof WithdrawalLog == false) return false;
		if(this == obj) return true;
		WithdrawalLog other = (WithdrawalLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

