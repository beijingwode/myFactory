package com.wode.factory.model.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;

public class ShoppingCartQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long userId;
	/** text */
	private java.lang.Long SKUId;
	/** orders */
	private java.lang.Integer buyCount;
	
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public java.lang.Long getSKUId() {
		return SKUId;
	}
	public void setSKUId(java.lang.Long sKUId) {
		SKUId = sKUId;
	}
	public java.lang.Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(java.lang.Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
