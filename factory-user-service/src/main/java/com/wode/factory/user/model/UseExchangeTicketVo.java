package com.wode.factory.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class UseExchangeTicketVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4978346501829527208L;
	
	private Long productId;
	private BigDecimal ticket;
	private BigDecimal cash;
	private BigDecimal self;
	private String note;
	private String subOrderId;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public BigDecimal getTicket() {
		return ticket;
	}
	public void setTicket(BigDecimal ticket) {
		this.ticket = ticket;
	}
	public BigDecimal getSelf() {
		return self;
	}
	public void setSelf(BigDecimal self) {
		this.self = self;
	}
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSubOrderId() {
		return subOrderId;
	}
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}
	
	
}
