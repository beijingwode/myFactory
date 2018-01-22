/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_commission_setting")
public class CommissionSetting extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CommissionSetting";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_COMMISSION = "commission";
	
	public static final String ALIAS_CATEGORY_ID = "categoryId";
	
	public static final String ALIAS_CREAT_TIME = "creatTime";
	
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	//date formats
	public static final String FORMAT_CREAT_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	@Id
	private java.lang.Long id;
    /**
     * commission       db_column: commission  
     * 
     * 
     * @NotNull 
     */	
	@Column("commission")
	private BigDecimal commission;
    /**
     * categoryId       db_column: category_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("category_id")
	private java.lang.Long categoryId;
    /**
     * creatTime       db_column: creat_time  
     * 
     * 
     * 
     */	
	@Column("creat_time")
	private java.util.Date creatTime;
    /**
     * updateTime       db_column: update_time  
     * 
     * 
     * @NotNull 
     */	
	@Column("update_time")
	private java.util.Date updateTime;
	
	/**
     * preferential       db_column: preferential  
     * 
     * 
     * @NotNull 
     */	
	@Column("preferential")
	private java.lang.Integer preferential;
	//columns END
	
	@One(target = ProductCategory.class, field = "categoryId")
	private ProductCategory productCategory;
	
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

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
	public String getCreatTimeString() {
		return DateConvertUtils.format(getCreatTime(), FORMAT_CREAT_TIME);
	}
	public void setCreatTimeString(String value) {
		setCreatTime(DateConvertUtils.parse(value, FORMAT_CREAT_TIME,java.util.Date.class));
	}
	
	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}
	
	public java.util.Date getCreatTime() {
		return this.creatTime;
	}
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Commission",getCommission())
			.append("CategoryId",getCategoryId())
			.append("CreatTime",getCreatTime())
			.append("UpdateTime",getUpdateTime())
			.append("Preferential",getPreferential())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CommissionSetting == false) return false;
		if(this == obj) return true;
		CommissionSetting other = (CommissionSetting)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

