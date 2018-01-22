package com.wode.factory.user.model;

import java.math.BigDecimal;

/**
 * 购物车实体类
 */
public class ProductHot implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2860455722896025763L;
	
	private java.lang.Long id;
	private java.lang.String image;
	private java.lang.String name;
	private java.lang.String showPrice;
	private BigDecimal minprice;//sku的最小价格
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getImage() {
		return image;
	}
	public void setImage(java.lang.String image) {
		this.image = image;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(java.lang.String showPrice) {
		this.showPrice = showPrice;
	}
	public BigDecimal getMinprice() {
		return minprice;
	}
	public void setMinprice(BigDecimal minprice) {
		this.minprice = minprice;
	}
	
	
	
}
