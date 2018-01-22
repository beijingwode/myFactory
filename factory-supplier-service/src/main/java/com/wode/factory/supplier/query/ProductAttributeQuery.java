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


public class ProductAttributeQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** aid */
	private java.lang.Long id;
	/** 商品_商品ID */
	private java.lang.Long productId;
	/** 属性id */
	private java.lang.Long attributeId;
	/** value */
	private java.lang.String value;
	/** displayvalue */
	private java.lang.String displayvalue;
	/** 0:非必须，1：必须要 */
	private java.lang.Boolean mustInput;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getAttributeId() {
		return this.attributeId;
	}
	
	public void setAttributeId(java.lang.Long value) {
		this.attributeId = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getDisplayvalue() {
		return this.displayvalue;
	}
	
	public void setDisplayvalue(java.lang.String value) {
		this.displayvalue = value;
	}
	
	public java.lang.Boolean getMustInput() {
		return this.mustInput;
	}
	
	public void setMustInput(java.lang.Boolean value) {
		this.mustInput = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

