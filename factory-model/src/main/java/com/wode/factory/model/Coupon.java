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


public class Coupon extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Coupon";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_COUPONS = "优惠券";
	
	public static final String ALIAS_PROMOTION_ID = "促销ID";
	
	public static final String ALIAS_BATCH_NO = "批次号";
	
	public static final String ALIAS_VALIDITY_START_DATE = "有效期开始时间";
	
	public static final String ALIAS_EXPIRATION_TIME = "过期时间";
	
	public static final String ALIAS_COUPON_TYPE = "优惠券类型";
	
	public static final String ALIAS_SHOP_ID = "店铺id";
	
	public static final String ALIAS_STATUS = "状态";
	
	public static final String ALIAS_COUNT = "数量";
	
	public static final String ALIAS_GIVE_TYPE = "发券方式";
	
	public static final String ALIAS_GET_TYPE = "领券方式";
	
	public static final String ALIAS_MEMBER_LEVEL = "会员级别";
	
	public static final String ALIAS_GET_START_TIME = "领券开始时间";
	
	public static final String ALIAS_GET_END_TIME = "领券结束时间";
	
	public static final String ALIAS_FULL_PRICE = "使用条件(订单满)";
	
	//date formats
	public static final String FORMAT_VALIDITY_START_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_EXPIRATION_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_GET_START_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_GET_END_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 优惠券       db_column: coupons  
     * 
     * 
     * 
     */	
	private java.lang.Integer coupons;
    /**
     * 促销ID       db_column: promotionId  
     * 
     * 
     * 
     */	
	private java.lang.Long promotionId;
    /**
     * 批次号       db_column: batchNo  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String batchNo;
    /**
     * 有效期开始时间       db_column: validityStartDate  
     * 
     * 
     * 
     */	
	private java.util.Date validityStartDate;
    /**
     * 过期时间       db_column: expirationTime  
     * 
     * 
     * 
     */	
	private java.util.Date expirationTime;
    /**
     * 优惠券类型       db_column: couponType  
     * 
     * 
     * @Max(127)
     */	
	private Integer couponType;
    /**
     * 店铺id       db_column: shopId  
     * 
     * 
     * 
     */	
	private java.lang.Long shopId;
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
     * 发券方式       db_column: giveType  
     * 
     * 
     * @Max(127)
     */	
	private Integer giveType;
    /**
     * 领券方式       db_column: getType  
     * 
     * 
     * @Max(127)
     */	
	private Integer getType;
    /**
     * 会员级别       db_column: memberLevel  
     * 
     * 
     * 
     */	
	private java.lang.Integer memberLevel;
    /**
     * 领券开始时间       db_column: getStartTime  
     * 
     * 
     * 
     */	
	private java.util.Date getStartTime;
    /**
     * 领券结束时间       db_column: getEndTime  
     * 
     * 
     * 
     */	
	private java.util.Date getEndTime;
    /**
     * 使用条件(订单满)       db_column: fullPrice  
     * 
     * 
     * 
     */	
	private java.lang.Integer fullPrice;
	//columns END

	public Coupon(){
	}

	public Coupon(
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
	public void setCoupons(java.lang.Integer value) {
		this.coupons = value;
	}
	
	public java.lang.Integer getCoupons() {
		return this.coupons;
	}
	public void setPromotionId(java.lang.Long value) {
		this.promotionId = value;
	}
	
	public java.lang.Long getPromotionId() {
		return this.promotionId;
	}
	public void setBatchNo(java.lang.String value) {
		this.batchNo = value;
	}
	
	public java.lang.String getBatchNo() {
		return this.batchNo;
	}
	public String getValidityStartDateString() {
		return DateConvertUtils.format(getValidityStartDate(), FORMAT_VALIDITY_START_DATE);
	}
	public void setValidityStartDateString(String value) {
		setValidityStartDate(DateConvertUtils.parse(value, FORMAT_VALIDITY_START_DATE,java.util.Date.class));
	}
	
	public void setValidityStartDate(java.util.Date value) {
		this.validityStartDate = value;
	}
	
	public java.util.Date getValidityStartDate() {
		return this.validityStartDate;
	}
	public String getExpirationTimeString() {
		return DateConvertUtils.format(getExpirationTime(), FORMAT_EXPIRATION_TIME);
	}
	public void setExpirationTimeString(String value) {
		setExpirationTime(DateConvertUtils.parse(value, FORMAT_EXPIRATION_TIME,java.util.Date.class));
	}
	
	public void setExpirationTime(java.util.Date value) {
		this.expirationTime = value;
	}
	
	public java.util.Date getExpirationTime() {
		return this.expirationTime;
	}
	public void setCouponType(Integer value) {
		this.couponType = value;
	}
	
	public Integer getCouponType() {
		return this.couponType;
	}
	public void setShopId(java.lang.Long value) {
		this.shopId = value;
	}
	
	public java.lang.Long getShopId() {
		return this.shopId;
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
	public void setGiveType(Integer value) {
		this.giveType = value;
	}
	
	public Integer getGiveType() {
		return this.giveType;
	}
	public void setGetType(Integer value) {
		this.getType = value;
	}
	
	public Integer getGetType() {
		return this.getType;
	}
	public void setMemberLevel(java.lang.Integer value) {
		this.memberLevel = value;
	}
	
	public java.lang.Integer getMemberLevel() {
		return this.memberLevel;
	}
	public String getGetStartTimeString() {
		return DateConvertUtils.format(getGetStartTime(), FORMAT_GET_START_TIME);
	}
	public void setGetStartTimeString(String value) {
		setGetStartTime(DateConvertUtils.parse(value, FORMAT_GET_START_TIME,java.util.Date.class));
	}
	
	public void setGetStartTime(java.util.Date value) {
		this.getStartTime = value;
	}
	
	public java.util.Date getGetStartTime() {
		return this.getStartTime;
	}
	public String getGetEndTimeString() {
		return DateConvertUtils.format(getGetEndTime(), FORMAT_GET_END_TIME);
	}
	public void setGetEndTimeString(String value) {
		setGetEndTime(DateConvertUtils.parse(value, FORMAT_GET_END_TIME,java.util.Date.class));
	}
	
	public void setGetEndTime(java.util.Date value) {
		this.getEndTime = value;
	}
	
	public java.util.Date getGetEndTime() {
		return this.getEndTime;
	}
	public void setFullPrice(java.lang.Integer value) {
		this.fullPrice = value;
	}
	
	public java.lang.Integer getFullPrice() {
		return this.fullPrice;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Coupons",getCoupons())
			.append("PromotionId",getPromotionId())
			.append("BatchNo",getBatchNo())
			.append("ValidityStartDate",getValidityStartDate())
			.append("ExpirationTime",getExpirationTime())
			.append("CouponType",getCouponType())
			.append("ShopId",getShopId())
			.append("Status",getStatus())
			.append("Count",getCount())
			.append("GiveType",getGiveType())
			.append("GetType",getGetType())
			.append("MemberLevel",getMemberLevel())
			.append("GetStartTime",getGetStartTime())
			.append("GetEndTime",getGetEndTime())
			.append("FullPrice",getFullPrice())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Coupon == false) return false;
		if(this == obj) return true;
		Coupon other = (Coupon)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

