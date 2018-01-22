package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;

public class UserTicketQuery extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2697246335143672651L;
	
	/** id */
	private java.lang.Long id;
	/** 用户id */
	private java.lang.Long userId;
	/** 券id */
	private java.lang.Long ticketId;
	/** 余额 */
	private java.math.BigDecimal balance;
	/** 券使用期限 */
	private java.util.Date ticketLimitDate;
	/** 券类型 */
	private java.lang.Integer ticketType;
	/** 券说明 */
	private java.lang.String ticketNote;
	/** 商家id */
    private java.lang.Long supplierId;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public java.lang.Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(java.lang.Long ticketId) {
		this.ticketId = ticketId;
	}
	public java.math.BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(java.math.BigDecimal balance) {
		this.balance = balance;
	}
	public java.util.Date getTicketLimitDate() {
		return ticketLimitDate;
	}
	public void setTicketLimitDate(java.util.Date ticketLimitDate) {
		this.ticketLimitDate = ticketLimitDate;
	}
	public java.lang.Integer getTicketType() {
		return ticketType;
	}
	public void setTicketType(java.lang.Integer ticketType) {
		this.ticketType = ticketType;
	}
	public java.lang.String getTicketNote() {
		return ticketNote;
	}
	public void setTicketNote(java.lang.String ticketNote) {
		this.ticketNote = ticketNote;
	}
	public java.lang.Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}
    
    public String toString(){
    	return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
    }
}
