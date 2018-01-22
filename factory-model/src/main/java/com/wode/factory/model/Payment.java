/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_payment")
public class Payment extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Payment";
	
	public static final String ALIAS_PAYMENT_ID = "outTradeNo";
	
	public static final String ALIAS_TOTAL_FEE = "totalFee";
	
	public static final String ALIAS_ORDER_ID = "orderId";
	
	public static final String ALIAS_SUB_ORDER_ID = "subOrderId";
	
	public static final String ALIAS_STATUS = "0待支付，1支付成功";
	
	public static final String ALIAS_CREATE_TIME = "createTime";
	
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * paymentId       db_column: paymentId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("out_trade_no")
	private java.lang.String outTradeNo;
    /**
     * totalFee       db_column: totalFee  
     * 
     * 
     * 
     */	
	@Column("totalFee")
	private BigDecimal totalFee;
    /**
     * orderId       db_column: orderId  
     * 
     * 
     * 
     */	
	@Column("orderId")
	private java.lang.Long orderId;
    /**
     * subOrderId       db_column: subOrderId  
     * 
     * 
     * @Length(max=30)
     */	
	@Column("subOrderId")
	private java.lang.String subOrderId;
    /**
     * 0待支付，1支付成功       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
    /**
     * createTime       db_column: createTime  
     * 
     * 
     * 
     */	
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * updateTime       db_column: updateTime  
     * 
     * 
     * 
     */	
	@Column("updateTime")
	private java.util.Date updateTime;

	@Column("trade_no")
	private java.lang.String tradeNo;
	
	@Column("way")
	private java.lang.String way;
	@Column("pay_type")
	private  java.lang.Integer payType;
	@Column("pay_confirm")
	private java.lang.Integer payConfirm;	//财务确认到账
	@Column("pay_confirm_date")
	private Date payConfirmDate;	//财务确认到账时间
	@Column("note")
	private java.lang.String note;
	@Column("upd_user")
	private java.lang.String updUser;
	@Column("exp1")
	private java.lang.String exp1;;
	@Column("app_id")
	private java.lang.String appId;
	@Column("order_type")
	private Integer orderType;
	//columns END
	
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public java.lang.String getWay() {
		return way;
	}

	public void setWay(java.lang.String way) {
		this.way = way;
	}

	public Payment(){
	}

	public Payment(java.lang.String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setOutTradeNo(java.lang.String value) {
		this.outTradeNo = value;
	}
	
	public java.lang.String getOutTradeNo() {
		return this.outTradeNo;
	}
	public void setTotalFee(BigDecimal value) {
		this.totalFee = value;
	}
	
	public BigDecimal getTotalFee() {
		return this.totalFee;
	}
	public void setOrderId(java.lang.Long value) {
		this.orderId = value;
	}
	
	public java.lang.Long getOrderId() {
		return this.orderId;
	}
	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
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

	
	public java.lang.String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(java.lang.String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public java.lang.Integer getPayType() {
		return payType;
	}

	public void setPayType(java.lang.Integer payType) {
		this.payType = payType;
	}

	public java.lang.Integer getPayConfirm() {
		return payConfirm;
	}

	public void setPayConfirm(java.lang.Integer payConfirm) {
		this.payConfirm = payConfirm;
	}

	public Date getPayConfirmDate() {
		return payConfirmDate;
	}

	public void setPayConfirmDate(Date payConfirmDate) {
		this.payConfirmDate = payConfirmDate;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(java.lang.String updUser) {
		this.updUser = updUser;
	}

	public java.lang.String getExp1() {
		return exp1;
	}

	public void setExp1(java.lang.String exp1) {
		this.exp1 = exp1;
	}

	public java.lang.String getAppId() {
		return appId;
	}

	public void setAppId(java.lang.String appId) {
		this.appId = appId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("OutTradeNo",getOutTradeNo())
			.append("TotalFee",getTotalFee())
			.append("OrderId",getOrderId())
			.append("SubOrderId",getSubOrderId())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOutTradeNo())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Payment == false) return false;
		if(this == obj) return true;
		Payment other = (Payment)obj;
		return new EqualsBuilder()
			.append(getOutTradeNo(),other.getOutTradeNo())
			.isEquals();
	}
	
}

