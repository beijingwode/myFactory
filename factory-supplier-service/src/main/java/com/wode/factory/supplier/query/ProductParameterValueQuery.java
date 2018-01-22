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


public class ProductParameterValueQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** ID */
	private java.lang.Long id;
	/** 商品ID */
	private java.lang.Long productId;
	/** 参数值 */
	private java.lang.String parameterValue;
	/** 参数ID */
	private java.lang.Long parameterValueKey;

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
	
	public java.lang.String getParameterValue() {
		return this.parameterValue;
	}
	
	public void setParameterValue(java.lang.String value) {
		this.parameterValue = value;
	}
	
	public java.lang.Long getParameterValueKey() {
		return this.parameterValueKey;
	}
	
	public void setParameterValueKey(java.lang.Long value) {
		this.parameterValueKey = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

