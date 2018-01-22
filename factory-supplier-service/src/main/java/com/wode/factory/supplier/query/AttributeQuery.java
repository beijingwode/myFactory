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


public class AttributeQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 属性ID */
	private java.lang.Long id;
	/** 名称 */
	private java.lang.String name;
	/** 是否必须输入 1:必填 */
	private java.lang.Boolean mustInput;
	/** 输入类型 */
	private java.lang.String inputType;
	/** 是否可搜索 1:可搜索 */
	private java.lang.Integer forSearch;
	/** 分类id */
	private java.lang.Long categoryId;
	/** 默认值 */
	private java.lang.String defaultVal;
	/** createTime */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
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
	
	public java.lang.Boolean getMustInput() {
		return this.mustInput;
	}
	
	public void setMustInput(java.lang.Boolean value) {
		this.mustInput = value;
	}
	
	public java.lang.String getInputType() {
		return this.inputType;
	}
	
	public void setInputType(java.lang.String value) {
		this.inputType = value;
	}
	
	public java.lang.Integer getForSearch() {
		return this.forSearch;
	}
	
	public void setForSearch(java.lang.Integer value) {
		this.forSearch = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.String getDefaultVal() {
		return this.defaultVal;
	}
	
	public void setDefaultVal(java.lang.String value) {
		this.defaultVal = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	public void setCreateTimeEnd(java.util.Date value) {
		this.createTimeEnd = value;
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

