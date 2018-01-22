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


public class SuborderQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 子单号ID */
	private java.lang.String subOrderId;
	/** 用户ID */
	private java.lang.Long userId;
	/** 母单号 */
	private java.lang.Long orderId;
	/** 供应商ID */
	private java.lang.Long supplierId;
	/** 总价 */
	private Long totalProduct;
	/** 运费 */
	private Long totalShipping;
	/** 折扣 */
	private Long totalAdjustment;
	/** 实付金额 */
	private Long realPrice;
	/** 状态 */
	private java.lang.Integer status;
	/** 物流单号 */
	private java.lang.String expressNo;
	/** 物流类型 */
	private java.lang.String expressType;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 更新时间 */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;
	/** 修改者 */
	private java.lang.String updateBy;
	/** 用户（非商家）延长收货的次数（默认为0次，即没有延长过收货） */
	private java.lang.Integer userExetendCount;
	/** 仅退款单ID */
	private java.lang.Long refundOrderId;
	/** 评论状态 **/
	private Integer commentStatus;
	/** 批处理id **/
	private java.lang.Long relationId;

	public Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	public java.lang.Integer getUserExetendCount() {
		return userExetendCount;
	}

	public void setUserExetendCount(java.lang.Integer userExetendCount) {
		this.userExetendCount = userExetendCount;
	}

	public java.lang.Long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(java.lang.Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Long getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(java.lang.Long value) {
		this.orderId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getTotalProduct() {
		return this.totalProduct;
	}
	
	public void setTotalProduct(Long value) {
		this.totalProduct = value;
	}
	
	public Long getTotalShipping() {
		return this.totalShipping;
	}
	
	public void setTotalShipping(Long value) {
		this.totalShipping = value;
	}
	
	public Long getTotalAdjustment() {
		return this.totalAdjustment;
	}
	
	public void setTotalAdjustment(Long value) {
		this.totalAdjustment = value;
	}
	
	public Long getRealPrice() {
		return this.realPrice;
	}
	
	public void setRealPrice(Long value) {
		this.realPrice = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.String getExpressNo() {
		return this.expressNo;
	}
	
	public void setExpressNo(java.lang.String value) {
		this.expressNo = value;
	}
	
	public java.lang.String getExpressType() {
		return this.expressType;
	}
	
	public void setExpressType(java.lang.String value) {
		this.expressType = value;
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


	public java.lang.Long getRelationId() {
		return relationId;
	}

	public void setRelationId(java.lang.Long relationId) {
		this.relationId = relationId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

