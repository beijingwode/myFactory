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


public class RefundorderQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 退款单ID */
	private java.lang.Long refundOrderId;
	/** 退货单id */
	private java.lang.Long returnOrderId;
	/** 退货快递公司 */
	private java.lang.String expressCompany;
	/** 退款金额 */
	private Long refundPrice;
	/** 退款帐号 */
	private java.lang.String refundAccount;
	/** 退货原因 */
	private java.lang.String reason;
	/** 状态 1提交申请 2:处理中  3;退款异常 10：退款完毕 */
	private java.lang.Integer status;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 更新时间 */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;
	/** 修改者 */
	private java.lang.String updateBy;
	/** 货物状态（0：未收到货物；1：已收到货物）*/
	private java.lang.Integer goodsStatus;
	
	public java.lang.Integer getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(java.lang.Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public java.lang.Long getRefundOrderId() {
		return this.refundOrderId;
	}
	
	public void setRefundOrderId(java.lang.Long value) {
		this.refundOrderId = value;
	}
	
	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}
	
	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.String getExpressCompany() {
		return this.expressCompany;
	}
	
	public void setExpressCompany(java.lang.String value) {
		this.expressCompany = value;
	}
	
	public Long getRefundPrice() {
		return this.refundPrice;
	}
	
	public void setRefundPrice(Long value) {
		this.refundPrice = value;
	}
	
	public java.lang.String getRefundAccount() {
		return this.refundAccount;
	}
	
	public void setRefundAccount(java.lang.String value) {
		this.refundAccount = value;
	}
	
	public java.lang.String getReason() {
		return this.reason;
	}
	
	public void setReason(java.lang.String value) {
		this.reason = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
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
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

