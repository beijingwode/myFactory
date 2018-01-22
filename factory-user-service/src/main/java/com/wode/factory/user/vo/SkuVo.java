package com.wode.factory.user.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SkuVo implements Serializable {

	private String name;
	private Long productId;
	private Long skuId;
	private String image;
	private BigDecimal price;
	private BigDecimal companyTicket;//int

	private Integer saleKbn;	//销售区分 0：普通/1:特省',
	private String saleNote;	//让利原因
	
	private BigDecimal salePrice; //内购价
	
	private Integer stock; 	 //规格下库存
	private Integer allStock;//商品总库存
	
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getAllStock() {
		return allStock;
	}
	public void setAllStock(Integer allStock) {
		this.allStock = allStock;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getCompanyTicket() {
		return companyTicket;
	}
	public void setCompanyTicket(BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
	}
	
	public Integer getSaleKbn() {
		return saleKbn;
	}
	public void setSaleKbn(Integer saleKbn) {
		this.saleKbn = saleKbn;
	}
	public String getSaleNote() {
		return saleNote;
	}
	public void setSaleNote(String saleNote) {
		this.saleNote = saleNote;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((skuId == null) ? 0 : skuId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkuVo other = (SkuVo) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (skuId == null) {
			if (other.skuId != null)
				return false;
		} else if (!skuId.equals(other.skuId))
			return false;
		return true;
	}
	
	
}
