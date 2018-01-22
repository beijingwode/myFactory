package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;

/**
 * @author user
 *
 */
public class UserTicketHisQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3407139248287432979L;
	
	/** id */
	private java.lang.Long id;
	/** 用户id  */
	private java.lang.Long userId;
	/** 操作代码    */
	private java.lang.String opCode;
	/** 券id    */
	private java.lang.Long ticketId;
	/** 额度    */
	private java.math.BigDecimal ticket;
	/** 操作后额度   */
    private java.math.BigDecimal ticketBalance;
	/** 说明    */
    private java.lang.String note;
	/** 关键字   */
    private java.lang.Long keyId;
	/** 操作员工姓名    */
    private java.lang.String userName;
	/** 商家id   */
    private java.lang.Long supplierId;
    /** 操作时间    */
	private java.util.Date opDate;
	
	
	
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



	public java.lang.String getOpCode() {
		return opCode;
	}



	public void setOpCode(java.lang.String opCode) {
		this.opCode = opCode;
	}



	public java.lang.Long getTicketId() {
		return ticketId;
	}



	public void setTicketId(java.lang.Long ticketId) {
		this.ticketId = ticketId;
	}



	public java.math.BigDecimal getTicket() {
		return ticket;
	}



	public void setTicket(java.math.BigDecimal ticket) {
		this.ticket = ticket;
	}



	public java.math.BigDecimal getTicketBalance() {
		return ticketBalance;
	}



	public void setTicketBalance(java.math.BigDecimal ticketBalance) {
		this.ticketBalance = ticketBalance;
	}



	public java.lang.String getNote() {
		return note;
	}



	public void setNote(java.lang.String note) {
		this.note = note;
	}



	public java.lang.Long getKeyId() {
		return keyId;
	}



	public void setKeyId(java.lang.Long keyId) {
		this.keyId = keyId;
	}



	public java.lang.String getUserName() {
		return userName;
	}



	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}



	public java.lang.Long getSupplierId() {
		return supplierId;
	}



	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}



	public java.util.Date getOpDate() {
		return opDate;
	}



	public void setOpDate(java.util.Date opDate) {
		this.opDate = opDate;
	}



	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
