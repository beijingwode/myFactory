/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;


import java.math.BigDecimal;

import com.wode.factory.model.SupplierTransfer;

public class SupplierTransferVo extends SupplierTransfer implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107085139387066325L;
	private String comName;
	private BigDecimal balance;
	private BigDecimal returnCash;
	private String bankName;
	private String bankNo;
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getReturnCash() {
		return returnCash;
	}
	public void setReturnCash(BigDecimal returnCash) {
		this.returnCash = returnCash;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}	
	
}

