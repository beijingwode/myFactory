/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.model;


import java.math.BigDecimal;

public class CommissionSetting implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CommissionSetting";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_COMMISSION = "commission";
	
	public static final String ALIAS_CATEGORY_ID = "categoryId";
	
	public static final String ALIAS_CREAT_TIME = "creatTime";
	
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	private java.lang.Long id;
    /**
     * commission       db_column: commission  
     * 
     * 
     * @NotNull 
     */	
	private BigDecimal commission;
    /**
     * categoryId       db_column: category_id  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long categoryId;
    /**
     * creatTime       db_column: creat_time  
     * 
     * 
     * 
     */	
	private java.util.Date creatTime;
    /**
     * updateTime       db_column: update_time  
     * 
     * 
     * @NotNull 
     */	
	private java.util.Date updateTime;
	
	/**
     * preferential       db_column: preferential  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Integer preferential;
	//columns END

	private java.lang.String brandname;
	
	private java.lang.String categoryname;
	
	
	

	public CommissionSetting(){
	}

	public CommissionSetting(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setCommission(BigDecimal value) {
		this.commission = value;
	}
	
	public BigDecimal getCommission() {
		return this.commission;
	}
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	
	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}
	
	public java.util.Date getCreatTime() {
		return this.creatTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public java.lang.String getBrandname() {
		return brandname;
	}

	public void setBrandname(java.lang.String brandname) {
		this.brandname = brandname;
	}

	public java.lang.String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(java.lang.String categoryname) {
		this.categoryname = categoryname;
	}
	
	public java.lang.Integer getPreferential() {
		return preferential;
	}

	public void setPreferential(java.lang.Integer preferential) {
		this.preferential = preferential;
	}

}

