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


public class CommentsStatisticsQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 主键 */
	private java.lang.Long id;
	/** 好评数量 */
	private java.lang.Integer good;
	/** 中评数量 */
	private java.lang.Integer medium;
	/** 差评数量 */
	private java.lang.Integer bad;
	/** 商家id */
	private java.lang.Long supplierId;
	/** 统计时间 */
	private java.util.Date datetimeBegin;
	private java.util.Date datetimeEnd;
	/** 统计类型   0 周  1 月 */
	private java.lang.Integer type;
	/** 总数 */
	private java.lang.Integer tote;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Integer getGood() {
		return this.good;
	}
	
	public void setGood(java.lang.Integer value) {
		this.good = value;
	}
	
	public java.lang.Integer getMedium() {
		return this.medium;
	}
	
	public void setMedium(java.lang.Integer value) {
		this.medium = value;
	}
	
	public java.lang.Integer getBad() {
		return this.bad;
	}
	
	public void setBad(java.lang.Integer value) {
		this.bad = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.util.Date getDatetimeBegin() {
		return this.datetimeBegin;
	}
	
	public void setDatetimeBegin(java.util.Date value) {
		this.datetimeBegin = value;
	}	
	
	public java.util.Date getDatetimeEnd() {
		return this.datetimeEnd;
	}
	
	public void setDatetimeEnd(java.util.Date value) {
		this.datetimeEnd = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getTote() {
		return this.tote;
	}
	
	public void setTote(java.lang.Integer value) {
		this.tote = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

