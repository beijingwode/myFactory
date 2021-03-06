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


public class AttributeOptionQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 属性选项ID */
	private java.lang.Long id;
	/** 属性ID */
	private java.lang.Long attributeId;
	/** 名称 */
	private java.lang.String name;
	/** orders */
	private java.lang.Integer orders;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getAttributeId() {
		return this.attributeId;
	}
	
	public void setAttributeId(java.lang.Long value) {
		this.attributeId = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

