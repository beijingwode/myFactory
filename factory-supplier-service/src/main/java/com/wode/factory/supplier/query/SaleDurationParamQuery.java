package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SaleDurationParamQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 关键字 */
	private java.lang.String key;
	/** 显示内容 */
	private java.lang.String caption;
	/** 表示顺序 */
	private java.lang.Integer dispOrder;
	/** 值 暂时为空 */
	private java.lang.String value;
	/** 说明 */
	private java.lang.String descr;
	/** 停用标识 1:停用/0:正常 */
	private java.lang.String stopFlg;
	/** 更新时间 */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getKey() {
		return this.key;
	}
	
	public void setKey(java.lang.String value) {
		this.key = value;
	}
	
	public java.lang.String getCaption() {
		return this.caption;
	}
	
	public void setCaption(java.lang.String value) {
		this.caption = value;
	}
	
	public java.lang.Integer getDispOrder() {
		return this.dispOrder;
	}
	
	public void setDispOrder(java.lang.Integer value) {
		this.dispOrder = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getDescr() {
		return this.descr;
	}
	
	public void setDescr(java.lang.String value) {
		this.descr = value;
	}
	
	public java.lang.String getStopFlg() {
		return this.stopFlg;
	}
	
	public void setStopFlg(java.lang.String value) {
		this.stopFlg = value;
	}
	
	public java.util.Date getUpdateTimeBegin() {
		return this.updateTimeBegin;
	}
	
	public void setUpdateTimeBegin(java.util.Date value) {
		this.updateTimeBegin = value;
	}	
	
	public java.util.Date getUpdateTimeEnd() {
		return this.updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(java.util.Date value) {
		this.updateTimeEnd = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

