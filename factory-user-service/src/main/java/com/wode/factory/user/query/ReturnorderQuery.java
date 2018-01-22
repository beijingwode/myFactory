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


public class ReturnorderQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 退货单号ID */
	private java.lang.Long returnOrderId;
	/** 子单号 */
	private java.lang.String subOrderId;
	/** 退款金额 */
	private Long returnPrice;
	/** 状态 -1:退货失败 ；0：退货中；1：退货成功 */
	private java.lang.Integer status;
	/** 快递公司类型 */
	private java.lang.String expressType;
	/** 快递单号 */
	private java.lang.String expressNo;
	/** 退款原因 */
	private java.lang.String reason;
	/** 备注 */
	private java.lang.String note;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 更新时间 */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;
	/** 修改者 */
	private java.lang.String updateBy;
	/** 最后处理时间 */
	private java.util.Date lastTimeBegin;
	private java.util.Date lastTimeEnd;
	/** userId */
	private java.lang.Long userId;

	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}
	
	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	
	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public Long getReturnPrice() {
		return this.returnPrice;
	}
	
	public void setReturnPrice(Long value) {
		this.returnPrice = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.String getExpressType() {
		return this.expressType;
	}
	
	public void setExpressType(java.lang.String value) {
		this.expressType = value;
	}
	
	public java.lang.String getExpressNo() {
		return this.expressNo;
	}
	
	public void setExpressNo(java.lang.String value) {
		this.expressNo = value;
	}
	
	public java.lang.String getReason() {
		return this.reason;
	}
	
	public void setReason(java.lang.String value) {
		this.reason = value;
	}
	
	public java.lang.String getNote() {
		return this.note;
	}
	
	public void setNote(java.lang.String value) {
		this.note = value;
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
	
	public java.util.Date getLastTimeBegin() {
		return this.lastTimeBegin;
	}
	
	public void setLastTimeBegin(java.util.Date value) {
		this.lastTimeBegin = value;
	}	
	
	public java.util.Date getLastTimeEnd() {
		return this.lastTimeEnd;
	}
	
	public void setLastTimeEnd(java.util.Date value) {
		this.lastTimeEnd = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

