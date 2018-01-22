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

@Table("t_inventory")
public class Inventory extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Inventory";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_PRODUCT_SPECIFICATIONS_ID = "商品规格ID";
	
	public static final String ALIAS_QUANTITY = "库存";
	
	public static final String ALIAS_LOCK_QUANTITY = "锁定库存";

	//date formats
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	@Id
	private java.lang.Long id;
    /**
     * 商品规格ID       db_column: productSpecificationsId  
     * 
     * 
     * 
     */	
	@Column("productSpecificationsId")
	private java.lang.Long productSpecificationsId;
    /**
     * 库存       db_column: quantity  
     * 
     * 
     * 
     */	
	@Column("quantity")
	private java.lang.Integer quantity;
    /**
     * 锁定库存       db_column: lockQuantity  
     * 
     * 
     * 
     */	
	@Column("lockQuantity")
	private java.lang.Integer lockQuantity;
    /**
     * 库存预警值       db_column: warnQuantity  
     * 
     * 
     * 
     */	
	@Column("warnQuantity")
	private Integer warnQuantity;
	//columns END

	public Inventory(){
	}

	public Integer getWarnQuantity() {
		return warnQuantity;
	}

	public void setWarnQuantity(Integer warnQuantity) {
		this.warnQuantity = warnQuantity;
	}

	public Inventory(
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
	public void setProductSpecificationsId(java.lang.Long value) {
		this.productSpecificationsId = value;
	}
	
	public java.lang.Long getProductSpecificationsId() {
		return this.productSpecificationsId;
	}
	public void setQuantity(java.lang.Integer value) {
		this.quantity = value;
	}
	
	public java.lang.Integer getQuantity() {
		return this.quantity;
	}
	public void setLockQuantity(java.lang.Integer value) {
		this.lockQuantity = value;
	}
	
	public java.lang.Integer getLockQuantity() {
		return this.lockQuantity;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductSpecificationsId",getProductSpecificationsId())
			.append("Quantity",getQuantity())
			.append("LockQuantity",getLockQuantity())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Inventory == false) return false;
		if(this == obj) return true;
		Inventory other = (Inventory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

