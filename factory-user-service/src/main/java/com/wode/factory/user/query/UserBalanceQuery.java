/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class UserBalanceQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 用户id */
	private java.lang.Long userId;
	/** 币货id */
	private java.lang.Long currencyId;
	/** 货币余额 */
	private java.lang.Long balance;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getCurrencyId() {
		return this.currencyId;
	}
	
	public void setCurrencyId(java.lang.Long value) {
		this.currencyId = value;
	}
	
	public java.lang.Long getBalance() {
		return this.balance;
	}
	
	public void setBalance(java.lang.Long value) {
		this.balance = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

