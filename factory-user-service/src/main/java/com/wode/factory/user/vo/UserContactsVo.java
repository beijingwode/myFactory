package com.wode.factory.user.vo;

import com.wode.factory.user.model.UserContacts;

public class UserContactsVo extends UserContacts {
	/**
	 * 
	 */
	private static final long serialVersionUID = 152776626984113118L;
	private Integer totalBalance;
	private Integer balance;
	private String phoneNumber;
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Integer totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	

	public void setUserid(java.lang.Long value) {
		this.setContactsId(value);
	}
	
	public java.lang.Long getUserid() {
		return this.getContactsId();
	}
	
	public void setNickname(java.lang.String value) {
		this.setContactsMemo(value);
	}
	
	public java.lang.String getNickname() {
		return this.getContactsMemo();
	}
	public void setEmployeeid(java.lang.Long value) {
		this.setContactsId(value);
	}
	
	public java.lang.Long getEmployeeid() {
		return this.getContactsId();
	}
	
	public void setState(java.lang.Integer value) {
	}
	
	public java.lang.Integer getState() {
		return 1;
	}
	public void setMemo(java.lang.String value) {
		this.setContactsMemo(value);
	}
	
	public java.lang.String getMemo() {
		return this.getContactsMemo();
	}

	public java.lang.Long getApplyUser() {
		return this.getUserId();
	}

	public void setApplyUser(java.lang.Long applyUser) {
		this.setUserId(applyUser);
	}
}
