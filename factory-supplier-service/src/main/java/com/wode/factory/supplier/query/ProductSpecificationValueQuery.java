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


public class ProductSpecificationValueQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** ID */
	private java.lang.Long id;
	/** 规格_规格ID */
	private java.lang.Long specificationId;
	/** 规格值 */
	private java.lang.String specificationValue;
	/** skuid,商品规格集id */
	private java.lang.Long specificationsId;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getSpecificationId() {
		return this.specificationId;
	}
	
	public void setSpecificationId(java.lang.Long value) {
		this.specificationId = value;
	}
	
	public java.lang.String getSpecificationValue() {
		return this.specificationValue;
	}
	
	public void setSpecificationValue(java.lang.String value) {
		this.specificationValue = value;
	}
	
	public java.lang.Long getSpecificationsId() {
		return this.specificationsId;
	}
	
	public void setSpecificationsId(java.lang.Long value) {
		this.specificationsId = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

