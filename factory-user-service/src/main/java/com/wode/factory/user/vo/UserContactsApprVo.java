package com.wode.factory.user.vo;

import com.wode.factory.user.model.UserContactsAppr;

public class UserContactsApprVo extends UserContactsAppr {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -696091670269276935L;

	public String getPhoneNumber() {
		return this.getUserKey();
	}
	public void setPhoneNumber(String phoneNumber) {
		this.setUserKey(phoneNumber);
	}
	
	public void setUserid(java.lang.Long value) {
		this.setUserId(value);
	}
	
	public java.lang.Long getUserid() {
		return this.getUserId();
	}
	
	public void setNickname(java.lang.String value) {
		this.setUserNickname(value);
	}
	
	public java.lang.String getNickname() {
		return this.getUserNickname();
	}
	public void setEmployeeid(java.lang.Long value) {
		this.setFirendId(value);
	}
	
	public java.lang.Long getEmployeeid() {
		return this.getUserId();
	}
	
	public void setState(java.lang.Integer value) {
	}
	
	public java.lang.Integer getState() {
		return 0;
	}
	public void setMemo(java.lang.String value) {
		this.setUserNickname(value);
	}
	
	public java.lang.String getMemo() {
		return this.getUserNickname();
	}
}
