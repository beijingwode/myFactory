package com.wode.factory.user.vo;

import java.math.BigDecimal;
import java.util.List;

import com.wode.factory.model.UserShareItem;

public class UserShareProductItemVo  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 678603528230615812L;
	private String productName;
	private BigDecimal price;
	private BigDecimal realPrice;
	private BigDecimal welFare;
	private Double sale;
	private String detailLink;
	private String image;
	private List<String> banners;
	private UserShareItem item;
	private Integer saleKbn;	//销售区分 0：普通/1:特省',
	private Integer buyProductNum;//已购商品数量
	
	
	
	public Integer getBuyProductNum() {
		return buyProductNum;
	}
	public void setBuyProductNum(Integer buyProductNum) {
		this.buyProductNum = buyProductNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	public BigDecimal getWelFare() {
		return welFare;
	}
	public void setWelFare(BigDecimal welFare) {
		this.welFare = welFare;
	}
	public Double getSale() {
		return sale;
	}
	public void setSale(Double sale) {
		this.sale = sale;
	}
	public String getDetailLink() {
		return detailLink;
	}
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<String> getBanners() {
		return banners;
	}
	public void setBanners(List<String> banners) {
		this.banners = banners;
	}
	public UserShareItem getItem() {
		return item;
	}
	public void setItem(UserShareItem item) {
		this.item = item;
	}
	public Integer getSaleKbn() {
		return saleKbn;
	}
	public void setSaleKbn(Integer saleKbn) {
		this.saleKbn = saleKbn;
	}
	
}
