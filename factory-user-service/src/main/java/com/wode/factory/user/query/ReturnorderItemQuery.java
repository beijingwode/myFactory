/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class ReturnorderItemQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 退货单项ID */
	private java.lang.Long returnOrderItemId;
	/** 退货单 */
	private java.lang.Long returnOrderId;
	/** 商品编码 */
	private java.lang.String partNumber;
	/** 数量 */
	private Integer number;
	/** 单价 */
	private Long price;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 更新时间 */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;
	/** 修改者 */
	private java.lang.String updateBy;
	/** 子单项 */
	private java.lang.Long subOrderItemId;
	
	public java.lang.Long getReturnOrderItemId() {
		return this.returnOrderItemId;
	}
	
	public void setReturnOrderItemId(java.lang.Long value) {
		this.returnOrderItemId = value;
	}
	
	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}
	
	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.String getPartNumber() {
		return this.partNumber;
	}
	
	public void setPartNumber(java.lang.String value) {
		this.partNumber = value;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	
	public void setNumber(Integer value) {
		this.number = value;
	}
	
	public Long getPrice() {
		return this.price;
	}
	
	public void setPrice(Long value) {
		this.price = value;
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
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}
	
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.Long getSubOrderItemId() {
		return subOrderItemId;
	}

	public void setSubOrderItemId(java.lang.Long subOrderItemId) {
		this.subOrderItemId = subOrderItemId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

