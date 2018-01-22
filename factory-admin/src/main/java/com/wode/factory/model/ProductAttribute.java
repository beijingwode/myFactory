/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;



import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

public class ProductAttribute extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductAttribute";
	
	public static final String ALIAS_ID = "aid";
	
	public static final String ALIAS_PRODUCT_ID = "商品_商品ID";
	
	public static final String ALIAS_ATTRIBUTE_ID = "属性id";
	
	public static final String ALIAS_VALUE = "value";
	
	public static final String ALIAS_MUST_INPUT = "0:非必须，1：必须要";
	
	//date formats
	
	//columns START
    /**
     * aid       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 商品_商品ID       db_column: product_id
     * 
     * 
     * 
     */	
	private java.lang.Long productId;
    /**
     * 属性id       db_column: attribute_id  
     * 
     * 
     * 
     */	
	private java.lang.Long attributeId;
    /**
     * 属性Name       
     */	
	private java.lang.String attributeName;
    /**
     * value       db_column: value  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String value;
	
	public java.lang.String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(java.lang.String attributeName) {
		this.attributeName = attributeName;
	}

	public ProductAttribute(){
	}

	public ProductAttribute(
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
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	public void setAttributeId(java.lang.Long value) {
		this.attributeId = value;
	}
	
	public java.lang.Long getAttributeId() {
		return this.attributeId;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	
	
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductId",getProductId())
			.append("AttributeId",getAttributeId())
			.append("Value",getValue())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductAttribute == false) return false;
		if(this == obj) return true;
		ProductAttribute other = (ProductAttribute)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

