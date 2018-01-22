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


public class SpecificationQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 规格ID */
	private java.lang.Long id;
	/** 名称 */
	private java.lang.String name;
	/** 排序 */
	private Integer orders;
	/** 备注 */
	private java.lang.String memo;
	/** 类型 */
	private Integer type;
	/** 供应商ID */
	private java.lang.Long supplierId;
	/** 创建时间 */
	private java.util.Date createdDateBegin;
	private java.util.Date createdDateEnd;
	/** 更新时间 */
	private java.util.Date updateDateBegin;
	private java.util.Date updateDateEnd;

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
	
	public Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.util.Date getCreatedDateBegin() {
		return this.createdDateBegin;
	}
	
	public void setCreatedDateBegin(java.util.Date value) {
		this.createdDateBegin = value;
	}	
	
	public java.util.Date getCreatedDateEnd() {
		return this.createdDateEnd;
	}
	
	public void setCreatedDateEnd(java.util.Date value) {
		this.createdDateEnd = value;
	}
	
	public java.util.Date getUpdateDateBegin() {
		return this.updateDateBegin;
	}
	
	public void setUpdateDateBegin(java.util.Date value) {
		this.updateDateBegin = value;
	}	
	
	public java.util.Date getUpdateDateEnd() {
		return this.updateDateEnd;
	}
	
	public void setUpdateDateEnd(java.util.Date value) {
		this.updateDateEnd = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

