/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class InventoryQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** ID */
	private java.lang.Long id;
	/** 商品规格ID */
	private java.lang.Long productSpecificationsId;
	/** 库存 */
	private java.lang.Integer quantity;
	/** 锁定库存 */
	private java.lang.Integer lockQuantity;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getProductSpecificationsId() {
		return this.productSpecificationsId;
	}
	
	public void setProductSpecificationsId(java.lang.Long value) {
		this.productSpecificationsId = value;
	}
	
	public java.lang.Integer getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(java.lang.Integer value) {
		this.quantity = value;
	}
	
	public java.lang.Integer getLockQuantity() {
		return this.lockQuantity;
	}
	
	public void setLockQuantity(java.lang.Integer value) {
		this.lockQuantity = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

