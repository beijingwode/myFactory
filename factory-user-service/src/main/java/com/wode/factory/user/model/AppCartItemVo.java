package com.wode.factory.user.model;

import java.io.Serializable;
import java.util.List;

public class AppCartItemVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6219751259372590313L;
	private Long supplierId;//供应商ID
	private Long shopId;//供应商ID
	private String supplierName;//供应商名称
	private List<CartItem> cartItemList;//购物车商品list
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public List<CartItem> getCartItemList() {
		return cartItemList;
	}
	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
}
