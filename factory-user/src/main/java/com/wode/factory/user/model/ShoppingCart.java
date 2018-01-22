package com.wode.factory.user.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.base.BaseModel;

/**
 * 购物车实体类
 */
public class ShoppingCart extends BaseModel implements java.io.Serializable {
	/*@Resource
	private DBUtils db;*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * SKUID
	 */
	private Long SKUId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 购买数量
	 */
	private int buyCount;
	/**
	 * 购买价格
	 *//*
	private double price;*/
	
	
	/*public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}*/
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getSKUId() {
		return SKUId;
	}
	public void setSKUId(Long sKUId) {
		SKUId = sKUId;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("SKUId",getSKUId())
			.append("UserId",getUserId())
			.append("BuyCount",getBuyCount())
			//.append("Price",getPrice())
			.toString();
	}
	
	
}
