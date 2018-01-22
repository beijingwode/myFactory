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
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_specification_value")
public class ProductSpecificationValue extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductSpecificationValue";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_SPECIFICATION_ID = "规格_规格ID";
	
	public static final String ALIAS_SPECIFICATION_VALUE = "规格值";
	
	public static final String ALIAS_SPECIFICATIONS_ID = "skuid,商品规格集id";
	
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
     * 规格_规格ID       db_column: specification_id  
     * 
     * 
     * 
     */	
	@Column(value="specification_id")
	private java.lang.Long specificationId;
    /**
     * 规格值       db_column: specification_value  
     * 
     * 
     * @Length(max=100)
     */	
	@Column(value="specification_value")
	private java.lang.String specificationValue;
    /**
     * skuid,商品规格集id       db_column: specifications_id  
     * 
     * 
     * 
     */	
	@Column(value="product_id")
	private java.lang.Long productId;
	@Column(value="orders")
	private Integer orders;
	@Column("isDelete")
	private Integer isDelete;//是否删除  0：未删除   1：已删除
	private List<ProductSpecifications> productSpecificationslist = new ArrayList<ProductSpecifications>();//用于商品添加页面的修改
	
	private String imageStrs;
	@Column(value="image")
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageStrs() {
		return imageStrs;
	}

	public void setImageStrs(String imageStrs) {
		this.imageStrs = imageStrs;
	}

	public List<ProductSpecifications> getProductSpecificationslist() {
		return productSpecificationslist;
	}

	public void setProductSpecificationslist(
			List<ProductSpecifications> productSpecificationslist) {
		this.productSpecificationslist = productSpecificationslist;
	}

	/**
     * 规格_规格Name 
     * 
     */	
	private java.lang.String specificationName;
	
	public java.lang.String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(java.lang.String specificationName) {
		this.specificationName = specificationName;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public ProductSpecificationValue(){
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public ProductSpecificationValue(
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
	public void setSpecificationId(java.lang.Long value) {
		this.specificationId = value;
	}
	
	public java.lang.Long getSpecificationId() {
		return this.specificationId;
	}
	public void setSpecificationValue(java.lang.String value) {
		this.specificationValue = value;
	}
	
	public java.lang.String getSpecificationValue() {
		return this.specificationValue;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SpecificationId",getSpecificationId())
			.append("SpecificationValue",getSpecificationValue())
			.append("getProductId",getProductId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductSpecificationValue == false) return false;
		if(this == obj) return true;
		ProductSpecificationValue other = (ProductSpecificationValue)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

