/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.Suborder;

public class SuborderOrderVo extends Suborder implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -896657518741437051L;
	
	private String managerName;
	private String supplierName;
	private Long userId;
	private Long enterpriseId;
	private Long enterpriseName;
	
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
	public Long getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(Long enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
}

