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


public class Transations extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Transations";
	
	public static final String ALIAS_ID = "主键";
	
	public static final String ALIAS_OUT_TRADE_NO = "对外交易号";
	
	public static final String ALIAS_ORDER_NO = "订单号";
	
	public static final String ALIAS_PAY_CHANNEL = "支付方式";
	
	public static final String ALIAS_ORDER_AMOUNT = "订单金额";
	
	public static final String ALIAS_SELLER = "卖家注册账号";
	
	public static final String ALIAS_TO_ACCOUNT = "收款账号";
	
	public static final String ALIAS_BUYER = "买家注册账号";
	
	public static final String ALIAS_FROM_ACCOUNT = "付款账号";
	
	public static final String ALIAS_TRANSACTION_NO = "返回的交易号";
	
	public static final String ALIAS_REAL_AMOUNT = "平台实收金额";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_STATUS_TEXT = "状态文本";
	
	public static final String ALIAS_SUPPLIER_ID = "供应商ID";
	
	public static final String ALIAS_RATE = "当前订单税率";
	
	public static final String ALIAS_COMPLETED_TIME = "交易完成时间";
	
	public static final String ALIAS_CREATED_TIME = "创建时间";
	
	public static final String ALIAS_UPDATED_TIME = "修改时间";
	
	//date formats
	public static final String FORMAT_COMPLETED_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_CREATED_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATED_TIME = DATE_TIME_FORMAT;
	
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
     * 订单号       db_column: orderNo  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String orderNo;
    /**
     * 支付方式       db_column: payChannel  
     * 
     * 
     * @Length(max=30)
     */	
	private java.lang.String payChannel;
    /**
     * 订单金额       db_column: orderAmount  
     * 
     * 
     * 
     */	
	private Long orderAmount;
    /**
     * 卖家注册账号       db_column: seller  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String seller;
    /**
     * 收款账号       db_column: toAccount  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String toAccount;
    /**
     * 买家注册账号       db_column: buyer  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String buyer;
    /**
     * 付款账号       db_column: fromAccount  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String fromAccount;
    /**
     * 返回的交易号       db_column: transactionNo  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String transactionNo;
    /**
     * 平台实收金额       db_column: realAmount  
     * 
     * 
     * 
     */	
	private Long realAmount;
    /**
     * 状态       db_column: status  
     * 
     * 
     * @Length(max=20)
     */	
	private java.lang.String status;
    /**
     * 状态文本       db_column: statusText  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String statusText;
    /**
     * 供应商ID       db_column: supplierId  
     * 
     * 
     * 
     */	
	private java.lang.Long supplierId;
    /**
     * 当前订单税率       db_column: rate  
     * 
     * 
     * 
     */	
	private Long rate;
    /**
     * 交易完成时间       db_column: completedTime  
     * 
     * 
     * 
     */	
	private java.util.Date completedTime;
    /**
     * 创建时间       db_column: createdTime  
     * 
     * 
     * 
     */	
	private java.util.Date createdTime;
    /**
     * 修改时间       db_column: updatedTime  
     * 
     * 
     * 
     */	
	private java.util.Date updatedTime;
	//columns END

	public Transations(){
	}

	public Transations(
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
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	public void setPayChannel(java.lang.String value) {
		this.payChannel = value;
	}
	
	public java.lang.String getPayChannel() {
		return this.payChannel;
	}
	public void setOrderAmount(Long value) {
		this.orderAmount = value;
	}
	
	public Long getOrderAmount() {
		return this.orderAmount;
	}
	public void setSeller(java.lang.String value) {
		this.seller = value;
	}
	
	public java.lang.String getSeller() {
		return this.seller;
	}
	public void setToAccount(java.lang.String value) {
		this.toAccount = value;
	}
	
	public java.lang.String getToAccount() {
		return this.toAccount;
	}
	public void setBuyer(java.lang.String value) {
		this.buyer = value;
	}
	
	public java.lang.String getBuyer() {
		return this.buyer;
	}
	public void setFromAccount(java.lang.String value) {
		this.fromAccount = value;
	}
	
	public java.lang.String getFromAccount() {
		return this.fromAccount;
	}
	public void setTransactionNo(java.lang.String value) {
		this.transactionNo = value;
	}
	
	public java.lang.String getTransactionNo() {
		return this.transactionNo;
	}
	public void setRealAmount(Long value) {
		this.realAmount = value;
	}
	
	public Long getRealAmount() {
		return this.realAmount;
	}
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setStatusText(java.lang.String value) {
		this.statusText = value;
	}
	
	public java.lang.String getStatusText() {
		return this.statusText;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	public void setRate(Long value) {
		this.rate = value;
	}
	
	public Long getRate() {
		return this.rate;
	}
	public String getCompletedTimeString() {
		return DateConvertUtils.format(getCompletedTime(), FORMAT_COMPLETED_TIME);
	}
	public void setCompletedTimeString(String value) {
		setCompletedTime(DateConvertUtils.parse(value, FORMAT_COMPLETED_TIME,java.util.Date.class));
	}
	
	public void setCompletedTime(java.util.Date value) {
		this.completedTime = value;
	}
	
	public java.util.Date getCompletedTime() {
		return this.completedTime;
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
	public String getUpdatedTimeString() {
		return DateConvertUtils.format(getUpdatedTime(), FORMAT_UPDATED_TIME);
	}
	public void setUpdatedTimeString(String value) {
		setUpdatedTime(DateConvertUtils.parse(value, FORMAT_UPDATED_TIME,java.util.Date.class));
	}
	
	public void setUpdatedTime(java.util.Date value) {
		this.updatedTime = value;
	}
	
	public java.util.Date getUpdatedTime() {
		return this.updatedTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("OutTradeNo",getOutTradeNo())
			.append("OrderNo",getOrderNo())
			.append("PayChannel",getPayChannel())
			.append("OrderAmount",getOrderAmount())
			.append("Seller",getSeller())
			.append("ToAccount",getToAccount())
			.append("Buyer",getBuyer())
			.append("FromAccount",getFromAccount())
			.append("TransactionNo",getTransactionNo())
			.append("RealAmount",getRealAmount())
			.append("Status",getStatus())
			.append("StatusText",getStatusText())
			.append("SupplierId",getSupplierId())
			.append("Rate",getRate())
			.append("CompletedTime",getCompletedTime())
			.append("CreatedTime",getCreatedTime())
			.append("UpdatedTime",getUpdatedTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Transations == false) return false;
		if(this == obj) return true;
		Transations other = (Transations)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

