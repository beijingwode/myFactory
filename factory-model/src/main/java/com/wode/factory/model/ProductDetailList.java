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

@Table("t_product_detail_list")
public class ProductDetailList extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductDetailList";
	
	public static final String ALIAS_ID = "清单id";
	
	public static final String ALIAS_NAME = "配件名称";
	
	public static final String ALIAS_NUM = "数量";
	
	public static final String ALIAS_UNIT = "单位";
	
	public static final String ALIAS_PRODUCT_ID = "商品id";
	
	public static final String ALIAS_ISDELETE = "是否删除：0、默认未删除   1、已删除";
	
	public static final String ALIAS_ORDERS = "排序";
	
	//date formats
	
	//columns START
    /**
     * 清单id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 配件名称       db_column: name  
     * 
     * 
     * @NotBlank @Length(max=100)
     */	
	@Column
	private java.lang.String name;
    /**
     * 数量       db_column: num  
     * 
     * 
     * @NotNull 
     */	
	@Column
	private java.lang.Integer num;
    /**
     * 单位       db_column: unit  
     * 
     * 
     * @NotBlank @Length(max=10)
     */	
	@Column
	private java.lang.String unit;
    /**
     * 商品id       db_column: product_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("product_id")
	private java.lang.Long productId;
    /**
     * 是否删除：0、默认未删除   1、已删除       db_column: isdelete  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer isdelete;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer orders;
	//columns END

	public ProductDetailList(){
	}

	public ProductDetailList(
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
	public void setNum(java.lang.Integer value) {
		this.num = value;
	}
	
	public java.lang.Integer getNum() {
		return this.num;
	}
	public void setUnit(java.lang.String value) {
		this.unit = value;
	}
	
	public java.lang.String getUnit() {
		return this.unit;
	}
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	public void setIsdelete(java.lang.Integer value) {
		this.isdelete = value;
	}
	
	public java.lang.Integer getIsdelete() {
		return this.isdelete;
	}
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Num",getNum())
			.append("Unit",getUnit())
			.append("ProductId",getProductId())
			.append("Isdelete",getIsdelete())
			.append("Orders",getOrders())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductDetailList == false) return false;
		if(this == obj) return true;
		ProductDetailList other = (ProductDetailList)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

