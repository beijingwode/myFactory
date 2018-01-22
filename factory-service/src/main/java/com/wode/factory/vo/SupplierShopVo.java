package com.wode.factory.vo;

import java.io.Serializable;

import com.wode.factory.model.Shop;

public class SupplierShopVo extends Shop implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyState;
	private String companyCity;
	private String companyAddress;
	private String companyName;
	private String shopId;
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getCompanyState() {
		return companyState;
	}
	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}
	public String getCompanyCity() {
		return companyCity;
	}
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
