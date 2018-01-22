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

@Table("t_product_store_category")
public class ProductStoreCategory extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductStoreCategory";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_PRODUCT_ID = "productId";
	
	public static final String ALIAS_STORE_CATEGORY_ID = "storeCategoryId";
	
	public static final String ALIAS_SUPPLIER_ID = "supplierId";
	
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
     * productId       db_column: product_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("product_id")
	private java.lang.Long productId;
    /**
     * storeCategoryId       db_column: store_category_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("store_category_id")
	private java.lang.Long storeCategoryId;
    /**
     * supplierId       db_column: supplier_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
	//columns END

	public ProductStoreCategory(){
	}

	public ProductStoreCategory(
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
	public void setStoreCategoryId(java.lang.Long value) {
		this.storeCategoryId = value;
	}
	
	public java.lang.Long getStoreCategoryId() {
		return this.storeCategoryId;
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
			.append("ProductId",getProductId())
			.append("StoreCategoryId",getStoreCategoryId())
			.append("SupplierId",getSupplierId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductStoreCategory == false) return false;
		if(this == obj) return true;
		ProductStoreCategory other = (ProductStoreCategory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

