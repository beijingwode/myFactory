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


public class ReturnorderlogQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 退货单日志ID */
	private java.lang.Long returnOrderLogId;
	/** 退货单号 */
	private java.lang.Long returnOrderId;
	/** 状态 */
	private java.lang.String status;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 创建者 */
	private java.lang.String createBy;

	public java.lang.Long getReturnOrderLogId() {
		return this.returnOrderLogId;
	}
	
	public void setReturnOrderLogId(java.lang.Long value) {
		this.returnOrderLogId = value;
	}
	
	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}
	
	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
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
	
	public java.lang.String getCreateBy() {
		return this.createBy;
	}
	
	public void setCreateBy(java.lang.String value) {
		this.createBy = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

