/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import com.wode.factory.annotation.DefaultValue;

@Table("t_specification")
public class Specification extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Specification";
	
	public static final String ALIAS_ID = "规格ID";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_MEMO = "备注";
	
	public static final String ALIAS_TYPE = "类型";
	
	public static final String ALIAS_CATEGORY_ID = "供应商ID";
	
	public static final String ALIAS_CREATED_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	//date formats
	public static final String FORMAT_CREATED_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 规格ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */	
	@Column
	private java.lang.String name;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	@Column
	private Integer orders;
    /**
     * 备注       db_column: memo  
     * 
     * 
     * @Length(max=100)
     */	
	@Column
	private java.lang.String memo;
    /**
     * 类型       db_column: type  
     * 
     * 
     * @Max(127)
     */	
	@Column
	private Integer type;
	
	/**
     * 供应商ID       db_column: category_id  
     * 
     * 
     * 
     */	
	@Column("category_id")
	private java.lang.Long categoryId;
    /**
     * 创建时间       db_column: createdDate  
     * 
     * 
     * 
     */	
	@Column
	@DefaultValue(dateValue="now()")
	private java.util.Date createdDate;
    /**
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */	
	@Column
	private java.util.Date updateDate;
	
	@One(target = ProductCategory.class, field = "categoryId")
	private ProductCategory productCategory;
	
	//属性值列表
	@Many(target = SpecificationValue.class, field = "specificationId")
	private List<SpecificationValue> valuelist = new ArrayList<SpecificationValue>();
	//columns END 

	
	/*********以下字段只供显示*********/
	private String selectedValue;//已经选择的那个值，只供显示:适用于product修改
	
	private List<ProductSpecificationValue> productSpecificationValuelist;
	
	public List<ProductSpecificationValue> getProductSpecificationValuelist() {
		return productSpecificationValuelist;
	}

	public void setProductSpecificationValuelist(
			List<ProductSpecificationValue> productSpecificationValuelist) {
		this.productSpecificationValuelist = productSpecificationValuelist;
	}

	public String getSelectedValue() {
		return selectedValue;
	}



	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}



	public Specification(){
	}

	

	public List<SpecificationValue> getValuelist() {
		return valuelist;
	}



	public void setValuelist(List<SpecificationValue> valuelist) {
		this.valuelist = valuelist;
	}



	public Specification(
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public java.lang.Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(java.lang.Long categoryId) {
		this.categoryId = categoryId;
	}


	
	public void setCreatedDate(java.util.Date value) {
		this.createdDate = value;
	}
	
	public java.util.Date getCreatedDate() {
		return this.createdDate;
	}
	
	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}



	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Orders",getOrders())
			.append("Memo",getMemo())
			.append("Type",getType())
			.append("categoryId",getCategoryId())
			.append("CreatedDate",getCreatedDate())
			.append("UpdateDate",getUpdateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Specification == false) return false;
		if(this == obj) return true;
		Specification other = (Specification)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

