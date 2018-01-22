package com.wode.factory.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AceUserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5199277125056965013L;
	private String ticket;
	private String nickName;
	private String phone;
	private BigDecimal account;
	private String userid;
	private String industory;
	private String company;
	
	
	public String getIndustory() {
		return industory;
	}
	public void setIndustory(String industory) {
		this.industory = industory;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "AceUserVo [ticket=" + ticket + ", nickName=" + nickName + ", phone=" + phone + ", account=" + account
				+ "]";
	}
	
	
}
