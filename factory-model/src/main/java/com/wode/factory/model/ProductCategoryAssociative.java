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
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_category_associative")
public class ProductCategoryAssociative extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductCategoryAssociative";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_PRO_ID = "商品_商品ID";
	
	public static final String ALIAS_CATEGORY_ID = "分类id";
	
	//date formats
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 商品_商品ID       db_column: pro_id  
     * 
     * 
     * 
     */	
	@Column("pro_id")
	private java.lang.Long proId;
    /**
     * 分类id       db_column: category_id  
     * 
     * 
     * 
     */	
	@Column("category_id")
	private java.lang.Long categoryId;
	//columns END

	public ProductCategoryAssociative(){
	}

	public ProductCategoryAssociative(
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
	public void setProId(java.lang.Long value) {
		this.proId = value;
	}
	
	public java.lang.Long getProId() {
		return this.proId;
	}
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProId",getProId())
			.append("CategoryId",getCategoryId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductCategoryAssociative == false) return false;
		if(this == obj) return true;
		ProductCategoryAssociative other = (ProductCategoryAssociative)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

