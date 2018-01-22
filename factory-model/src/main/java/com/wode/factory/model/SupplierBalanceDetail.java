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


public class SupplierBalanceDetail extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SupplierBalanceDetail";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_ORDER_NO = "订单号";
	
	public static final String ALIAS_SUPPLIER_ID = "供应商ID";
	
	public static final String ALIAS_SUPPLIER_NAME = "供应商名称";
	
	public static final String ALIAS_RATE = "费率";
	
	public static final String ALIAS_REAL_AMOUNT = "供应商实收金额";
	
	public static final String ALIAS_DEDUCT_AMOUNT = "平台抽取金额";
	
	public static final String ALIAS_AMOUNT = "平台实收金额";
	
	public static final String ALIAS_TYPE = "款项类型";
	
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
     * 订单号       db_column: orderNo  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String orderNo;
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
     * 费率       db_column: rate  
     * 
     * 
     * 
     */	
	private Long rate;
    /**
     * 供应商实收金额       db_column: realAmount  
     * 
     * 
     * 
     */	
	private Long realAmount;
    /**
     * 平台抽取金额       db_column: deductAmount  
     * 
     * 
     * 
     */	
	private Long deductAmount;
    /**
     * 平台实收金额       db_column: amount  
     * 
     * 
     * 
     */	
	private Long amount;
    /**
     * 款项类型       db_column: type  
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
     * 操作者ID       db_column: operatorId  
     * 
     * 
     * 
     */	
	private java.lang.Long operatorId;
	//columns END

	public SupplierBalanceDetail(){
	}

	public SupplierBalanceDetail(
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
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
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
	public void setRate(Long value) {
		this.rate = value;
	}
	
	public Long getRate() {
		return this.rate;
	}
	public void setRealAmount(Long value) {
		this.realAmount = value;
	}
	
	public Long getRealAmount() {
		return this.realAmount;
	}
	public void setDeductAmount(Long value) {
		this.deductAmount = value;
	}
	
	public Long getDeductAmount() {
		return this.deductAmount;
	}
	public void setAmount(Long value) {
		this.amount = value;
	}
	
	public Long getAmount() {
		return this.amount;
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
			.append("OrderNo",getOrderNo())
			.append("SupplierId",getSupplierId())
			.append("SupplierName",getSupplierName())
			.append("Rate",getRate())
			.append("RealAmount",getRealAmount())
			.append("DeductAmount",getDeductAmount())
			.append("Amount",getAmount())
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
		if(obj instanceof SupplierBalanceDetail == false) return false;
		if(this == obj) return true;
		SupplierBalanceDetail other = (SupplierBalanceDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

