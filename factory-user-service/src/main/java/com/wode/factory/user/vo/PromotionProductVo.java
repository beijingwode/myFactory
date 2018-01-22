package com.wode.factory.user.vo;

import java.math.BigDecimal;

import com.wode.factory.model.PromotionProduct;

public class PromotionProductVo extends PromotionProduct{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8270286258629651316L;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品原价
	 */
	private BigDecimal oldPrice;
	/**
	 * 附加货币
	 */
	private String currencyName;
	/**
	 * 附加货币量
	 */
	private Integer currencyTotal;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 是否关注
	 */
	private Boolean isAttention = false;
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Integer getCurrencyTotal() {
		return currencyTotal;
	}
	public void setCurrencyTotal(Integer currencyTotal) {
		this.currencyTotal = currencyTotal;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Boolean getIsAttention() {
		return isAttention;
	}
	public void setIsAttention(Boolean isAttention) {
		this.isAttention = isAttention;
	}
	
}
