package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class PromotionQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 活动名称 */
	private java.lang.String name;
	/** 顺序 */
	private java.lang.Integer orders;
	/** 开始时间 */
	private java.util.Date beginDate;
	/** 结束时间 */
	private java.util.Date endDate;
	/** 活动介绍 */
	private java.lang.String introduction;
	/** 是否免运费1免运费 0不免 */
	private java.lang.Boolean freeShipping;
	/** 最小金额 */
	private Long minPrice;
	/** 最小数量 */
	private java.lang.Integer minQuantity;
	/** 是否与其他促销共存:0可以共存，1：不可共存 */
	private java.lang.Boolean exclusive;
	/** 店铺ID,为空则为平台活动 */
	private java.lang.Long shopId;
	/** 规则 */
	private java.lang.String rule;
	/** 成功参加活动的商品数量 */
	private java.lang.Long joinTotal;
	/** 状态 */
	private Integer status;
	/** 活动类型id */
	private java.lang.Integer type;
	/** 创建时间 */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** 修改时间 */
	private java.util.Date modifyDateBegin;
	private java.util.Date modifyDateEnd;
	/** 是否能退货 1能 -1不能 */
	private Integer canReturn;
	/** 是否能换货 1能 -1不能 */
	private Integer canReplace;
	/** 是否保修 1是 -1否 */
	private Integer canRepair;
	/** 活动需知 */
	private java.lang.String notice;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	
	public java.util.Date getBeginDate() {
		return this.beginDate;
	}
	
	public void setBeginDate(java.util.Date value) {
		this.beginDate = value;
	}
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.lang.String getIntroduction() {
		return this.introduction;
	}
	
	public void setIntroduction(java.lang.String value) {
		this.introduction = value;
	}
	
	public java.lang.Boolean getFreeShipping() {
		return this.freeShipping;
	}
	
	public void setFreeShipping(java.lang.Boolean value) {
		this.freeShipping = value;
	}
	
	public Long getMinPrice() {
		return this.minPrice;
	}
	
	public void setMinPrice(Long value) {
		this.minPrice = value;
	}
	
	public java.lang.Integer getMinQuantity() {
		return this.minQuantity;
	}
	
	public void setMinQuantity(java.lang.Integer value) {
		this.minQuantity = value;
	}
	
	public java.lang.Boolean getExclusive() {
		return this.exclusive;
	}
	
	public void setExclusive(java.lang.Boolean value) {
		this.exclusive = value;
	}
	
	public java.lang.Long getShopId() {
		return this.shopId;
	}
	
	public void setShopId(java.lang.Long value) {
		this.shopId = value;
	}
	
	public java.lang.String getRule() {
		return this.rule;
	}
	
	public void setRule(java.lang.String value) {
		this.rule = value;
	}
	
	public java.lang.Long getJoinTotal() {
		return this.joinTotal;
	}
	
	public void setJoinTotal(java.lang.Long value) {
		this.joinTotal = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
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
	
	public java.lang.String getNotice() {
		return this.notice;
	}
	
	public void setNotice(java.lang.String value) {
		this.notice = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

