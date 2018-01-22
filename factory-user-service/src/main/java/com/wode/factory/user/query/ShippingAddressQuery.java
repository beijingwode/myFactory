/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;

import com.wode.common.frame.base.BaseQuery;

public class ShippingAddressQuery extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 3148176768559230877L;

	/** id */
	private java.lang.Long id;
	/** user_id */
	private java.lang.Long userId;
	/** aid */
	private java.lang.Long aid;
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

	private String companyname;

	private String tel;
	private java.lang.Integer returned;
	private java.lang.Integer send;
	private java.lang.String comments;

	public java.lang.Long getId() {
		return this.id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getUserId() {
		return this.userId;
	}

	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}

	public java.lang.Long getAid() {
		return this.aid;
	}

	public void setAid(java.lang.Long value) {
		this.aid = value;
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

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public java.lang.Integer getReturned() {
		return returned;
	}

	public void setReturned(java.lang.Integer returned) {
		this.returned = returned;
	}

	public java.lang.Integer getSend() {
		return send;
	}

	public void setSend(java.lang.Integer send) {
		this.send = send;
	}

	public java.lang.String getComments() {
		return comments;
	}

	public void setComments(java.lang.String comments) {
		this.comments = comments;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
