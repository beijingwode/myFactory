/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class SupplierCategory extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SupplierCategory";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_SUPPLIER_ID = "供应商id";
	
	public static final String ALIAS_CATEGORY_ID = "类型id";
	
	public static final String ALIAS_CATEGORY_PARENTID = "第二级类型id";
	
	public static final String ALIAS_CATEGORY_PARENT_PARENTID = "第一级类型id";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 供应商id       db_column: supplier_id  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long supplierId;
    /**
     * 供应商id       db_column: shopId  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long shopId;
    /**
     * 类型id       db_column: category_id  
     * 
     * 
     * 
     */	
	private java.lang.Long categoryId;
    /**
     * 第二级类型id       db_column: category_parentid  
     * 
     * 
     * 
     */	
	private java.lang.Long categoryParentid;
    /**
     * 第一级类型id       db_column: category_parent_parentid  
     * 
     * 
     * 
     */	
	private java.lang.Long categoryParentParentid;
	
	private String categoryName;//类别名称
	
	private List<ProductBrand> productBrandList;
	
	private Float commissionRatio;//佣金比例  X%  本字段值为  X
	
	
	//columns END

	public SupplierCategory(){
	}

	public List<ProductBrand> getProductBrandList() {
		return productBrandList;
	}

	public void setProductBrandList(List<ProductBrand> productBrandList) {
		this.productBrandList = productBrandList;
	}

	public SupplierCategory(
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
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryParentid(java.lang.Long value) {
		this.categoryParentid = value;
	}
	
	public java.lang.Long getCategoryParentid() {
		return this.categoryParentid;
	}
	public void setCategoryParentParentid(java.lang.Long value) {
		this.categoryParentParentid = value;
	}
	
	public java.lang.Long getCategoryParentParentid() {
		return this.categoryParentParentid;
	}

	public Float getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(Float commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SupplierId",getSupplierId())
			.append("shopId",getShopId())
			.append("CategoryId",getCategoryId())
			.append("CategoryParentid",getCategoryParentid())
			.append("CategoryParentParentid",getCategoryParentParentid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierCategory == false) return false;
		if(this == obj) return true;
		SupplierCategory other = (SupplierCategory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

