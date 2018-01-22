package com.wode.factory.supplier.query;

import com.wode.common.frame.base.BaseQuery;

public class SupplierSkuImageQuery extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180686340633117191L;
	private java.lang.Long id;
	private java.lang.String image1;
	private java.lang.String image2;
	private java.lang.String image3;
	private java.lang.String image4;
	private java.lang.String image5;
	private java.lang.Long supplierId;
	private java.lang.String introduce;
	private java.util.Date createTime;
	private java.lang.Integer status;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getImage1() {
		return image1;
	}
	public void setImage1(java.lang.String image1) {
		this.image1 = image1;
	}
	public java.lang.String getImage2() {
		return image2;
	}
	public void setImage2(java.lang.String image2) {
		this.image2 = image2;
	}
	public java.lang.String getImage3() {
		return image3;
	}
	public void setImage3(java.lang.String image3) {
		this.image3 = image3;
	}
	public java.lang.String getImage4() {
		return image4;
	}
	public void setImage4(java.lang.String image4) {
		this.image4 = image4;
	}
	public java.lang.String getImage5() {
		return image5;
	}
	public void setImage5(java.lang.String image5) {
		this.image5 = image5;
	}
	public java.lang.Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}
	
	public java.lang.String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.Integer getStatus() {
		return status;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
}
