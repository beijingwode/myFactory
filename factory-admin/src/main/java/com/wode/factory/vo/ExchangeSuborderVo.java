package com.wode.factory.vo;

import com.wode.factory.model.ExchangeSuborder;

public class ExchangeSuborderVo extends ExchangeSuborder implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2007499629071632451L;
	private String managerName;
	private String supplierName;
	private Long userId;
	private Long enterpriseId;
	
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
}
