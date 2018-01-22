package com.wode.factory.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GroupBuyProductsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4344611026153256749L;
	
	//商品id
	private Long productId;
	//规格id
	private Long skuId;
	//商品名称
	private String productName;
	//电商价
	private BigDecimal marketPrice;
	//主图
	private String  image;
	//内购券
	private BigDecimal maxFucoin;
	//内购价
	private BigDecimal internalPurchasePrice;
	//规格值
	private String itemValues;
	//起售数量
	private Integer minLimitNum;
	//团内已购数量
	private Integer groupBuyProductNum;
	//最低阶梯价
	private BigDecimal bestPrice;
	//最低价对应的数量
	private Integer bestPriceNum;
	
	//getter setter
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public BigDecimal getMaxFucoin() {
		return maxFucoin;
	}
	public void setMaxFucoin(BigDecimal maxFucoin) {
		this.maxFucoin = maxFucoin;
	}
	public BigDecimal getInternalPurchasePrice() {
		return internalPurchasePrice;
	}
	public void setInternalPurchasePrice(BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}
	public String getItemValues() {
		return itemValues;
	}
	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}
	public Integer getMinLimitNum() {
		return minLimitNum;
	}
	public void setMinLimitNum(Integer minLimitNum) {
		this.minLimitNum = minLimitNum;
	}
	public Integer getGroupBuyProductNum() {
		return groupBuyProductNum;
	}
	public void setGroupBuyProductNum(Integer groupBuyProductNum) {
		this.groupBuyProductNum = groupBuyProductNum;
	}
	public BigDecimal getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(BigDecimal bestPrice) {
		this.bestPrice = bestPrice;
	}
	public Integer getBestPriceNum() {
		return bestPriceNum;
	}
	public void setBestPriceNum(Integer bestPriceNum) {
		this.bestPriceNum = bestPriceNum;
	}
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSkuId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GroupBuyProductsVo == false) return false;
		if(this == obj) return true;
		GroupBuyProductsVo other = (GroupBuyProductsVo)obj;
		return new EqualsBuilder()
			.append(getSkuId(),other.getSkuId())
			.isEquals();
	}
}
