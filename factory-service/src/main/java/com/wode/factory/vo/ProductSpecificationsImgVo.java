package com.wode.factory.vo;

import java.io.Serializable;

public class ProductSpecificationsImgVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Long imageId;
	private String itemIds;
	private String imgUrl;
	private Long productId;
	private String rItemIds;
	private Long productSpecificationsId;
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	public String getrItemIds() {
		return rItemIds;
	}
	public void setrItemIds(String rItemIds) {
		this.rItemIds = rItemIds;
	}
	public Long getProductSpecificationsId() {
		return productSpecificationsId;
	}
	public void setProductSpecificationsId(Long productSpecificationsId) {
		this.productSpecificationsId = productSpecificationsId;
	}
	
}
