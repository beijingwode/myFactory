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


public class ProductDetailListQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 清单id */
	private java.lang.Long id;
	/** 配件名称 */
	private java.lang.String name;
	/** 数量 */
	private java.lang.Integer num;
	/** 单位 */
	private java.lang.String unit;
	/** 商品id */
	private java.lang.Long productId;
	/** 是否删除：0、默认未删除   1、已删除 */
	private java.lang.Integer isdelete;
	/** 排序 */
	private java.lang.Integer orders;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.Integer getNum() {
		return this.num;
	}
	
	public void setNum(java.lang.Integer value) {
		this.num = value;
	}
	
	public java.lang.String getUnit() {
		return this.unit;
	}
	
	public void setUnit(java.lang.String value) {
		this.unit = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Integer getIsdelete() {
		return this.isdelete;
	}
	
	public void setIsdelete(java.lang.Integer value) {
		this.isdelete = value;
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

