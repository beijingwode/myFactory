package com.wode.factory.exception;

public class BenefitLessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1568264087315285127L;
	private String ticketType;	//优惠券类型 exchange:换领券，limit:优惠券
	public BenefitLessException(String name,String key) {
		super("该用户余额不足，无法完成当前操作，请确认后重试。用户信息："
				+ name + "（" + key + "）。");
	}
	
	public BenefitLessException(String name,String key,String ticketType) {
		super("该用户余额不足，无法完成当前操作，请确认后重试。用户信息："
				+ name + "（" + key + "）。");
		this.ticketType=ticketType;
	}
	
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	
	
}
