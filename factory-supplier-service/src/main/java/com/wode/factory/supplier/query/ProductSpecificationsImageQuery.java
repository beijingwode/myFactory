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


public class ProductSpecificationsImageQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 图片ID */
	private java.lang.Long id;
	/** 商品规格集ID */
	private java.lang.Long specificationsId;
	/** 排序 */
	private Integer orders;
	/** 路径 */
	private java.lang.String source;
	/** 创建时间 */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** 更新时间 */
	private java.util.Date updateDateBegin;
	private java.util.Date updateDateEnd;
	/** 商户 */
	private java.lang.Long supplyId;
	/** 大小 */
	private java.lang.Long size;
	/** height */
	private java.lang.Integer height;
	/** width */
	private java.lang.Integer width;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getSpecificationsId() {
		return this.specificationsId;
	}
	
	public void setSpecificationsId(java.lang.Long value) {
		this.specificationsId = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public java.lang.String getSource() {
		return this.source;
	}
	
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	
	public java.util.Date getCreateDateBegin() {
		return this.createDateBegin;
	}
	
	public void setCreateDateBegin(java.util.Date value) {
		this.createDateBegin = value;
	}	
	
	public java.util.Date getCreateDateEnd() {
		return this.createDateEnd;
	}
	
	public void setCreateDateEnd(java.util.Date value) {
		this.createDateEnd = value;
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
	
	public java.lang.Long getSupplyId() {
		return this.supplyId;
	}
	
	public void setSupplyId(java.lang.Long value) {
		this.supplyId = value;
	}
	
	public java.lang.Long getSize() {
		return this.size;
	}
	
	public void setSize(java.lang.Long value) {
		this.size = value;
	}
	
	public java.lang.Integer getHeight() {
		return this.height;
	}
	
	public void setHeight(java.lang.Integer value) {
		this.height = value;
	}
	
	public java.lang.Integer getWidth() {
		return this.width;
	}
	
	public void setWidth(java.lang.Integer value) {
		this.width = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

