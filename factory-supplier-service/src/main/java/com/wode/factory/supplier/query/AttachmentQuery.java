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


public class AttachmentQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 附件ID */
	private java.lang.Long id;
	/** 附件名称 */
	private java.lang.String name;
	/** 附件关联id */
	private java.lang.Long relatedId;
	/** 附件所属供应商id */
	private java.lang.Long supplierId;
	/** 附件类型:图片类型、文档类型、txt类型 */
	private java.lang.Integer type;
	/** 附件上传人id */
	private java.lang.Long userId;
	/** 创建时间 */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** 附件截止有效期 */
	private java.util.Date endDate;
	/** 附件关联类型： */
	private java.lang.Integer relatedType;
	/** 附件大小 */
	private java.lang.Double size;

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
	
	public java.lang.Long getRelatedId() {
		return this.relatedId;
	}
	
	public void setRelatedId(java.lang.Long value) {
		this.relatedId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
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
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.lang.Integer getRelatedType() {
		return this.relatedType;
	}
	
	public void setRelatedType(java.lang.Integer value) {
		this.relatedType = value;
	}
	
	public java.lang.Double getSize() {
		return this.size;
	}
	
	public void setSize(java.lang.Double value) {
		this.size = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

