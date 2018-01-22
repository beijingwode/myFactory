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


public class SupplierCategoryQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 供应商id */
	private java.lang.Long supplierId;
	/** 供应商id */
	private java.lang.Long shopId;
	/** 类型id */
	private java.lang.Long categoryId;
	/** 第二级类型id */
	private java.lang.Long categoryParentid;
	/** 第一级类型id */
	private java.lang.Long categoryParentParentid;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryParentid() {
		return this.categoryParentid;
	}
	
	public void setCategoryParentid(java.lang.Long value) {
		this.categoryParentid = value;
	}
	
	public java.lang.Long getCategoryParentParentid() {
		return this.categoryParentParentid;
	}
	
	public void setCategoryParentParentid(java.lang.Long value) {
		this.categoryParentParentid = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

