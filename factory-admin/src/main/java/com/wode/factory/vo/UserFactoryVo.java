/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;


import com.wode.factory.model.UserFactory;

public class UserFactoryVo extends UserFactory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2116839969320081949L;

	// 商家名称
	private String supplierName;
	// 角色名称
	private String roleName;
	// 角色描述
	private String roleDescription;
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
		
	
}

