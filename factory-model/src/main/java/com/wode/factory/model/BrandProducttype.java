/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_brand_producttype")
public class BrandProducttype extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BrandProducttype";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_CATEGORY_ID = "商品类型id";
	
	public static final String ALIAS_BRAND_ID = "品牌id";
	
	public static final String ALIAS_SUPPLIER_ID = "供应商id";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
    /**
     * 商品类型id       db_column: category_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("category_id")
	private java.lang.Long categoryId;
    /**
     * 品牌id       db_column: brand_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("brand_id")
	private java.lang.Long brandId;
    /**
     * 供应商id       db_column: supplier_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
	//columns END

	public BrandProducttype(){
	}

	public BrandProducttype(
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
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	public void setBrandId(java.lang.Long value) {
		this.brandId = value;
	}
	
	public java.lang.Long getBrandId() {
		return this.brandId;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CategoryId",getCategoryId())
			.append("BrandId",getBrandId())
			.append("SupplierId",getSupplierId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BrandProducttype == false) return false;
		if(this == obj) return true;
		BrandProducttype other = (BrandProducttype)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

