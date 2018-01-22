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


public class Withdrawal extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Withdrawal";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_AMOUNT = "提现金额";
	
	public static final String ALIAS_ACCOUNT = "提现帐号卡号";
	
	public static final String ALIAS_SUPPLIER_ID = "供应商ID";
	
	public static final String ALIAS_SUPPLIER_NAME = "供应商名称";
	
	public static final String ALIAS_TYPE = "审核类型";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_OPERATOR_ID = "操作者ID";
	
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
     * 提现金额       db_column: amount  
     * 
     * 
     * 
     */	
	private Long amount;
    /**
     * 提现帐号卡号       db_column: account  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String account;
    /**
     * 供应商ID       db_column: supplierId  
     * 
     * 
     * 
     */	
	private java.lang.Long supplierId;
    /**
     * 供应商名称       db_column: supplierName  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String supplierName;
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
     * 操作者ID       db_column: operatorID  
     * 
     * 
     * 
     */	
	private java.lang.Long operatorId;
	//columns END

	public Withdrawal(){
	}

	public Withdrawal(
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
	public void setAmount(Long value) {
		this.amount = value;
	}
	
	public Long getAmount() {
		return this.amount;
	}
	public void setAccount(java.lang.String value) {
		this.account = value;
	}
	
	public java.lang.String getAccount() {
		return this.account;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	public void setSupplierName(java.lang.String value) {
		this.supplierName = value;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
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
			.append("Amount",getAmount())
			.append("Account",getAccount())
			.append("SupplierId",getSupplierId())
			.append("SupplierName",getSupplierName())
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
		if(obj instanceof Withdrawal == false) return false;
		if(this == obj) return true;
		Withdrawal other = (Withdrawal)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

