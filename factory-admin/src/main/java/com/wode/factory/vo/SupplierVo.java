/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;


import java.util.Date;

import com.wode.factory.model.Supplier;

public class SupplierVo extends Supplier implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107085139387066325L;
	
	//企业名称
	private String enterpriseName;
	//品牌商
	private String brandOwner;
	
	private Date exitTime;//退出时间
	
	

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getBrandOwner() {
		return brandOwner;
	}

	public void setBrandOwner(String brandOwner) {
		this.brandOwner = brandOwner;
	}
	
	
}

