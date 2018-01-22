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


public class SuborderitemQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 子单项ID */
	private java.lang.Long subOrderItemId;
	/** 子单号ID */
	private java.lang.String subOrderId;
	/** 商品编码 */
	private java.lang.String partNumber;
	/** 单价 */
	private Long price;
	/** 数量 */
	private java.lang.Integer number;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 更新时间 */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;
	/** 修改者 */
	private java.lang.String updateBy;
	/** 商品ID */
	private java.lang.Long productId;
	/** SKU主键 */
	private java.lang.Long skuId;
	/** 评论标识（0：未评论；1：已评论） */
	private java.lang.Boolean commentFlag;
	/** 活动商品id */
	private java.lang.Long promotionProductId;
	/** 活动id */
	private java.lang.Long promotionId;

	public java.lang.Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(java.lang.Long promotionId) {
		this.promotionId = promotionId;
	}

	public java.lang.Long getSubOrderItemId() {
		return this.subOrderItemId;
	}
	
	public void setSubOrderItemId(java.lang.Long value) {
		this.subOrderItemId = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	
	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public java.lang.String getPartNumber() {
		return this.partNumber;
	}
	
	public void setPartNumber(java.lang.String value) {
		this.partNumber = value;
	}
	
	public Long getPrice() {
		return this.price;
	}
	
	public void setPrice(Long value) {
		this.price = value;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}
	
	public void setNumber(java.lang.Integer value) {
		this.number = value;
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
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getSkuId() {
		return this.skuId;
	}
	
	public void setSkuId(java.lang.Long value) {
		this.skuId = value;
	}
	
	public java.lang.Boolean getCommentFlag() {
		return this.commentFlag;
	}
	
	public void setCommentFlag(java.lang.Boolean value) {
		this.commentFlag = value;
	}
	
	public java.lang.Long getPromotionProductId() {
		return this.promotionProductId;
	}
	
	public void setPromotionProductId(java.lang.Long value) {
		this.promotionProductId = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

