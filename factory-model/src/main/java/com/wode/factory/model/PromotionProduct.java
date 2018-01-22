/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.wode.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

public class PromotionProduct extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
    /**
     * 活动ID       db_column: promotion_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("promotion_id")
	private java.lang.Long promotionId;
    /**
     * 商品ID       db_column: product_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("product_id")
	private java.lang.Long productId;
    /**
     * SKU id       db_column: sku_id  
     * 
     * 
     * 
     */	
	@Column("sku_id")
	private java.lang.Long skuId;
    /**
     * pc端图片       db_column: big_image  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("big_image")
	private java.lang.String bigImage;
    /**
     * 移动端图片       db_column: small_image  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("small_image")
	private java.lang.String smallImage;
    /**
     * 参与活动数量       db_column: join_quantity  
     * 
     * 
     * @NotNull 
     */	
	@Column("join_quantity")
	private java.lang.Integer joinQuantity;
    /**
     * 参与活动开始时间       db_column: join_start  
     * 
     * 
     * 
     */	
	@Column("join_start")
	private java.util.Date joinStart;
    /**
     * 参与活动结束时间       db_column: join_end  
     * 
     * 
     * 
     */	
	@Column("join_end")
	private java.util.Date joinEnd;
    /**
     * 每个用户最大购买数量       db_column: max_quantity  
     * 
     * 
     * 
     */	
	@Column("max_quantity")
	private java.lang.Integer maxQuantity;
    /**
     * 活动价       db_column: price  
     * 
     * 
     * @NotNull 
     */	
	@Column("price")
	private BigDecimal price;
    /**
     * 货币       db_column: currency  
     * 
     * 
     * 
     */	
	@Column("currency")
	private java.lang.Long currency;
    /**
     * 附加货币额       db_column: plus_price  
     * 
     * 
     * 
     */	
	@Column("plus_price")
	private Long plusPrice;
    /**
     * 0待审核 1审核中 2通过 -1不通过       db_column: status  
     * 
     * 
     * @NotNull @Max(127)
     */	
	@Column("status")
	private Integer status;
    /**
     * 正在审核中的用户ID       db_column: reviewing_userId  
     * 
     * 
     * 
     */	
	@Column("reviewing_userId")
	private java.lang.Long reviewingUserId;
    /**
     * 正在审核中的开始时间       db_column: reviewing_date  
     * 
     * 
     * 
     */	
	@Column("reviewing_date")
	private java.util.Date reviewingDate;
    /**
     * shopId       db_column: shop_id  
     * 
     * 
     * 
     */	
	@Column("shop_id")
	private java.lang.Long shopId;
    /**
     * 是否能退货 1能 -1不能       db_column: can_return  
     * 
     * 
     * @Max(127)
     */	
	@Column("can_return")
	private Integer canReturn;
    /**
     * 否是可换货 1是0否       db_column: can_replace  
     * 
     * 
     * @Max(127)
     */	
	@Column("can_replace")
	private Integer canReplace;
    /**
     * 是否保修 1是0否       db_column: can_repair  
     * 
     * 
     * @Max(127)
     */	
	@Column("can_repair")
	private Integer canRepair;
    /**
     * 促销商品创建时间       db_column: create_date  
     * 
     * 
     * @NotNull 
     */	
	@Column("create_date")
	private java.util.Date createDate;
    /**
     * 促销商品修改时间       db_column: modify_date  
     * 
     * 
     * 
     */	
	@Column("modify_date")
	private java.util.Date modifyDate;
	/**
     * 促销商品修改时间       db_column: sell_num  
     * 
     * 
     * 
     */	
	@Column("sell_num")
	private int sellNum;
	//columns END
	@Column("preferential_num")
	private Double preferentialNum;//优惠数字
	@Column("preferential_type")
	private Integer preferentialType;//优惠类型
	// 审核通过或不通过人
	private Long reviewedUserId;
	// 审核通过或不通过时间
	private java.util.Date reviewedDate;
	
	/** 是否包邮 默认包邮为0 */
	private BigDecimal carriagePrice;
	
	private Promotion promotion;
	private String fullName;

	private String skuName;

	public PromotionProduct(){
	}

	public PromotionProduct(
		java.lang.Long id
	){
		this.id = id;
	}

	public BigDecimal getCarriagePrice() {
		return carriagePrice;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public void setCarriagePrice(BigDecimal carriagePrice) {
		this.carriagePrice = carriagePrice;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setPromotionId(java.lang.Long value) {
		this.promotionId = value;
	}
	
	public Double getPreferentialNum() {
		return preferentialNum;
	}

	public void setPreferentialNum(Double preferentialNum) {
		this.preferentialNum = preferentialNum;
	}

	public Integer getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(Integer preferentialType) {
		this.preferentialType = preferentialType;
	}

	public java.lang.Long getPromotionId() {
		return this.promotionId;
	}
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	public void setSkuId(java.lang.Long value) {
		this.skuId = value;
	}
	
	public java.lang.Long getSkuId() {
		return this.skuId;
	}
	public void setBigImage(java.lang.String value) {
		this.bigImage = value;
	}
	
	public java.lang.String getBigImage() {
		return this.bigImage;
	}
	public void setSmallImage(java.lang.String value) {
		this.smallImage = value;
	}
	
	public java.lang.String getSmallImage() {
		return this.smallImage;
	}
	public void setJoinQuantity(java.lang.Integer value) {
		this.joinQuantity = value;
	}
	
	public java.lang.Integer getJoinQuantity() {
		return this.joinQuantity;
	}
	
	
	public void setJoinStart(java.util.Date value) {
		this.joinStart = value;
	}
	
	public java.util.Date getJoinStart() {
		return this.joinStart;
	}
	
	
	public void setJoinEnd(java.util.Date value) {
		this.joinEnd = value;
	}
	
	public java.util.Date getJoinEnd() {
		return this.joinEnd;
	}
	public void setMaxQuantity(java.lang.Integer value) {
		this.maxQuantity = value;
	}
	
	public java.lang.Integer getMaxQuantity() {
		return this.maxQuantity;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setCurrency(java.lang.Long value) {
		this.currency = value;
	}
	
	public java.lang.Long getCurrency() {
		return this.currency;
	}
	public void setPlusPrice(Long value) {
		this.plusPrice = value;
	}
	
	public Long getPlusPrice() {
		return this.plusPrice;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setReviewingUserId(java.lang.Long value) {
		this.reviewingUserId = value;
	}
	
	public java.lang.Long getReviewingUserId() {
		return this.reviewingUserId;
	}
	
	
	public void setReviewingDate(java.util.Date value) {
		this.reviewingDate = value;
	}
	
	public java.util.Date getReviewingDate() {
		return this.reviewingDate;
	}
	public void setShopId(java.lang.Long value) {
		this.shopId = value;
	}
	
	public java.lang.Long getShopId() {
		return this.shopId;
	}
	public void setCanReturn(Integer value) {
		this.canReturn = value;
	}
	
	public Integer getCanReturn() {
		return this.canReturn;
	}
	public void setCanReplace(Integer value) {
		this.canReplace = value;
	}
	
	public Integer getCanReplace() {
		return this.canReplace;
	}
	public void setCanRepair(Integer value) {
		this.canRepair = value;
	}
	
	public Integer getCanRepair() {
		return this.canRepair;
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	
	
	public void setModifyDate(java.util.Date value) {
		this.modifyDate = value;
	}
	
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public int getSellNum() {
		return sellNum;
	}

	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}

	public String getFullName() {
		if(StringUtils.isNotBlank(skuName)) {
			StringBuilder sb = new StringBuilder(fullName==null?"":fullName);
			Map<String, String> map = JsonUtil.getMap4Json(skuName);
			Collection<String> vals = map.values();
			Iterator<String> iterator = vals.iterator();
			while(iterator.hasNext()) {
				sb.append(" ").append(iterator.next());
			}
			return sb.toString();
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PromotionId",getPromotionId())
			.append("ProductId",getProductId())
			.append("SkuId",getSkuId())
			.append("BigImage",getBigImage())
			.append("SmallImage",getSmallImage())
			.append("JoinQuantity",getJoinQuantity())
			.append("JoinStart",getJoinStart())
			.append("JoinEnd",getJoinEnd())
			.append("MaxQuantity",getMaxQuantity())
			.append("Price",getPrice())
			.append("Currency",getCurrency())
			.append("PlusPrice",getPlusPrice())
			.append("Status",getStatus())
			.append("ReviewingUserId",getReviewingUserId())
			.append("ReviewingDate",getReviewingDate())
			.append("ShopId",getShopId())
			.append("CanReturn",getCanReturn())
			.append("CanReplace",getCanReplace())
			.append("CanRepair",getCanRepair())
			.append("CreateDate",getCreateDate())
			.append("ModifyDate",getModifyDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PromotionProduct == false) return false;
		if(this == obj) return true;
		PromotionProduct other = (PromotionProduct)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public Long getReviewedUserId() {
		return reviewedUserId;
	}

	public void setReviewedUserId(Long reviewedUserId) {
		this.reviewedUserId = reviewedUserId;
	}

	public java.util.Date getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(java.util.Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}
}

