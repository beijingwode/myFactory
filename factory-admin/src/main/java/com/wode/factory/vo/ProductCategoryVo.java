/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.ProductCategory;



public class ProductCategoryVo extends ProductCategory implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7059364328155986541L;
	/**
	 * 上级菜单
	 */
	private String parentName;
	/**
	 * 菜单图片(该字段不能修改)
	 */
	private String icon;
	
	
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Long getParentId() {
		return this.getPid();
	}
}

