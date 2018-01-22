package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SupplierAddressQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** user_id */
	private java.lang.Long supplierId;
	/** aid */
	private java.lang.String aid;
	/** provinceName */
	private java.lang.String provinceName;
	/** cityName */
	private java.lang.String cityName;
	/** areaName */
	private java.lang.String areaName;
	/** address */
	private java.lang.String address;
	/** name */
	private java.lang.String name;
	/** phone */
	private java.lang.String phone;
	/** order_no */
	private java.lang.Long orderNo;
	/** companyname */
	private java.lang.String companyname;
	/** tel */
	private java.lang.String tel;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	
	
	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getAid() {
		return aid;
	}

	public void setAid(java.lang.String aid) {
		this.aid = aid;
	}

	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	
	public void setProvinceName(java.lang.String value) {
		this.provinceName = value;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(java.lang.String value) {
		this.cityName = value;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	
	public void setAreaName(java.lang.String value) {
		this.areaName = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.Long getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(java.lang.Long value) {
		this.orderNo = value;
	}
	
	public java.lang.String getCompanyname() {
		return this.companyname;
	}
	
	public void setCompanyname(java.lang.String value) {
		this.companyname = value;
	}
	
	public java.lang.String getTel() {
		return this.tel;
	}
	
	public void setTel(java.lang.String value) {
		this.tel = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}