/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class GroupPromotion extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "GroupPromotion";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_PRODUCT_ID = "产品id";
	
	public static final String ALIAS_PROMOTION_ID = "促销id";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_COUNT = "数量";
	
	public static final String ALIAS_DISCOUNT_AMOUNT = "优惠金额";
	
	public static final String ALIAS_ORDERS = "次序";
	
	public static final String ALIAS_IS_SHOW = "显示套餐";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 产品id       db_column: productId  
     * 
     * 
     * 
     */	
	private java.lang.Long productId;
    /**
     * 促销id       db_column: promotionId  
     * 
     * 
     * 
     */	
	private java.lang.Long promotionId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * @Max(127)
     */	
	private Integer status;
    /**
     * 数量       db_column: count  
     * 
     * 
     * 
     */	
	private java.lang.Integer count;
    /**
     * 优惠金额       db_column: discountAmount  
     * 
     * 
     * 
     */	
	private java.lang.Double discountAmount;
    /**
     * 次序       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	private Integer orders;
    /**
     * 显示套餐       db_column: isShow  
     * 
     * 
     * @Max(127)
     */	
	private Integer isShow;
	//columns END

	public GroupPromotion(){
	}

	public GroupPromotion(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	public void setPromotionId(java.lang.Long value) {
		this.promotionId = value;
	}
	
	public java.lang.Long getPromotionId() {
		return this.promotionId;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setCount(java.lang.Integer value) {
		this.count = value;
	}
	
	public java.lang.Integer getCount() {
		return this.count;
	}
	public void setDiscountAmount(java.lang.Double value) {
		this.discountAmount = value;
	}
	
	public java.lang.Double getDiscountAmount() {
		return this.discountAmount;
	}
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	public void setIsShow(Integer value) {
		this.isShow = value;
	}
	
	public Integer getIsShow() {
		return this.isShow;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductId",getProductId())
			.append("PromotionId",getPromotionId())
			.append("Status",getStatus())
			.append("Count",getCount())
			.append("DiscountAmount",getDiscountAmount())
			.append("Orders",getOrders())
			.append("IsShow",getIsShow())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GroupPromotion == false) return false;
		if(this == obj) return true;
		GroupPromotion other = (GroupPromotion)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

