package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class PromotionProductQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 活动ID */
	private java.lang.Long promotionId;
	/** 商品ID */
	private java.lang.Long productId;
	/** SKU id */
	private java.lang.Long skuId;
	/** pc端图片 */
	private java.lang.String bigImage;
	/** 移动端图片 */
	private java.lang.String smallImage;
	/** 参与活动数量 */
	private java.lang.Integer joinQuantity;
	/** 参与活动开始时间 */
	private java.util.Date joinStart;
	/** 参与活动结束时间 */
	private java.util.Date joinEnd;
	/** 每个用户最大购买数量 */
	private java.lang.Integer maxQuantity;
	/** 活动价 */
	private Long price;
	/** 货币 */
	private java.lang.Long currency;
	/** 附加货币额 */
	private Long plusPrice;
	/** 0待审核 1审核中 2通过 -1不通过 -2 已退出 */
	private Integer status;
	/** 正在审核中的用户ID */
	private java.lang.Long reviewingUserId;
	/** 正在审核中的开始时间 */
	private java.util.Date reviewingDateBegin;
	private java.util.Date reviewingDateEnd;
	/** shopId */
	private java.lang.Long shopId;
	/** 是否能退货 1能 -1不能 */
	private Integer canReturn;
	/** 否是可换货 1是0否 */
	private Integer canReplace;
	/** 是否保修 1是0否 */
	private Integer canRepair;
	/** 促销商品创建时间 */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** 促销商品修改时间 */
	private java.util.Date modifyDateBegin;
	private java.util.Date modifyDateEnd;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getPromotionId() {
		return this.promotionId;
	}
	
	public void setPromotionId(java.lang.Long value) {
		this.promotionId = value;
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
	
	public java.lang.String getBigImage() {
		return this.bigImage;
	}
	
	public void setBigImage(java.lang.String value) {
		this.bigImage = value;
	}
	
	public java.lang.String getSmallImage() {
		return this.smallImage;
	}
	
	public void setSmallImage(java.lang.String value) {
		this.smallImage = value;
	}
	
	public java.lang.Integer getJoinQuantity() {
		return this.joinQuantity;
	}
	
	public void setJoinQuantity(java.lang.Integer value) {
		this.joinQuantity = value;
	}
	
	public java.util.Date getJoinStart() {
		return this.joinStart;
	}
	
	public void setJoinStart(java.util.Date value) {
		this.joinStart = value;
	}
	
	public java.util.Date getJoinEnd() {
		return this.joinEnd;
	}
	
	public void setJoinEnd(java.util.Date value) {
		this.joinEnd = value;
	}
	
	public java.lang.Integer getMaxQuantity() {
		return this.maxQuantity;
	}
	
	public void setMaxQuantity(java.lang.Integer value) {
		this.maxQuantity = value;
	}
	
	public Long getPrice() {
		return this.price;
	}
	
	public void setPrice(Long value) {
		this.price = value;
	}
	
	public java.lang.Long getCurrency() {
		return this.currency;
	}
	
	public void setCurrency(java.lang.Long value) {
		this.currency = value;
	}
	
	public Long getPlusPrice() {
		return this.plusPrice;
	}
	
	public void setPlusPrice(Long value) {
		this.plusPrice = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public java.lang.Long getReviewingUserId() {
		return this.reviewingUserId;
	}
	
	public void setReviewingUserId(java.lang.Long value) {
		this.reviewingUserId = value;
	}
	
	public java.util.Date getReviewingDateBegin() {
		return this.reviewingDateBegin;
	}
	
	public void setReviewingDateBegin(java.util.Date value) {
		this.reviewingDateBegin = value;
	}	
	
	public java.util.Date getReviewingDateEnd() {
		return this.reviewingDateEnd;
	}
	
	public void setReviewingDateEnd(java.util.Date value) {
		this.reviewingDateEnd = value;
	}
	
	public java.lang.Long getShopId() {
		return this.shopId;
	}
	
	public void setShopId(java.lang.Long value) {
		this.shopId = value;
	}
	
	public Integer getCanReturn() {
		return this.canReturn;
	}
	
	public void setCanReturn(Integer value) {
		this.canReturn = value;
	}
	
	public Integer getCanReplace() {
		return this.canReplace;
	}
	
	public void setCanReplace(Integer value) {
		this.canReplace = value;
	}
	
	public Integer getCanRepair() {
		return this.canRepair;
	}
	
	public void setCanRepair(Integer value) {
		this.canRepair = value;
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
	
	public java.util.Date getModifyDateBegin() {
		return this.modifyDateBegin;
	}
	
	public void setModifyDateBegin(java.util.Date value) {
		this.modifyDateBegin = value;
	}	
	
	public java.util.Date getModifyDateEnd() {
		return this.modifyDateEnd;
	}
	
	public void setModifyDateEnd(java.util.Date value) {
		this.modifyDateEnd = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

