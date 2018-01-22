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

@Table("t_product_parameter_value")
public class ProductParameterValue extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductParameterValue";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_PRODUCT_ID = "商品ID";
	
	public static final String ALIAS_PARAMETER_VALUE = "参数值";
	
	public static final String ALIAS_PARAMETER_VALUE_KEY = "参数ID";
	
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
     * 商品ID       db_column: productId  
     * 
     * 
     * 
     */
	@Column
	private java.lang.Long productId;
    /**
     * 参数值       db_column: parameter_value  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("parameter_value")
	private java.lang.String parameterValue;
    /**
     * 参数ID       db_column: parameter_group_id  
     * 
     * 
     * 
     */
	@Column("parameter_group_id")
	private java.lang.Long parameterGroupId;
	
    /**
     * 参数Name  
     * 
     */	
	private java.lang.String parameterGroupName;

	public ProductParameterValue(){
	}

	public java.lang.String getParameterGroupName() {
		return parameterGroupName;
	}

	public void setParameterGroupName(java.lang.String parameterGroupName) {
		this.parameterGroupName = parameterGroupName;
	}

	public ProductParameterValue(
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
	public void setParameterValue(java.lang.String value) {
		this.parameterValue = value;
	}
	
	public java.lang.String getParameterValue() {
		return this.parameterValue;
	}


	public java.lang.Long getParameterGroupId() {
		return parameterGroupId;
	}

	public void setParameterGroupId(java.lang.Long parameterGroupId) {
		this.parameterGroupId = parameterGroupId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductId",getProductId())
			.append("ParameterValue",getParameterValue())
			.append("getParameterGroupId",getParameterGroupId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductParameterValue == false) return false;
		if(this == obj) return true;
		ProductParameterValue other = (ProductParameterValue)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

