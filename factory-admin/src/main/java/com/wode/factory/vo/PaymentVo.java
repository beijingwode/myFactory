package com.wode.factory.vo;

import com.wode.factory.model.Payment;

public class PaymentVo extends Payment implements java.io.Serializable{
	private String supplierId;
	private String supplierName;
	
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}
