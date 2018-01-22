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


public class ExchangeSuborderQuery extends BaseQuery implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -8980223662134218583L;
	/** 子单号ID */
	private java.lang.String subOrderId;
	/** 用户ID */
	private java.lang.Long userId;
	/** 母单号 */
	private java.lang.Long orderId;
	/** 供应商ID */
	private java.lang.Long supplierId;
	/** 状态 */
	private java.lang.Integer status;
	/** 状态 */
	private java.lang.Integer exchangeStatus;

	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Long getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(java.lang.Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

