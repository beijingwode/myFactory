package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_trial_limit_item")
public class ProductTrialLimitItem extends BaseModel implements java.io.Serializable{

  
	/**
	 * 
	 */
	private static final long serialVersionUID = -6997001535283081659L;
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
     * 商品id      db_column: productid
     * 
     * 
     * 
     */ 
    @Column("productid")
    private java.lang.Long productId;
    /**
     * 限购组id       db_column: groupid
     * 
     * 
     * 
     */ 
    @Column("groupid")
    private java.lang.Long groupId;
    

	@Override
	public String toString() {
		return "ProductTrialLimitItem [id=" + id + ", productId=" + productId + ", groupid=" + groupId + "]";
	}

	
	
	public java.lang.Long getId() {
		return id;
	}



	public void setId(java.lang.Long id) {
		this.id = id;
	}


	public java.lang.Long getProductId() {
		return productId;
	}



	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}



	public java.lang.Long getGroupId() {
		return groupId;
	}



	public void setGroupId(java.lang.Long groupId) {
		this.groupId = groupId;
	}



	@Override
	public int hashCode() {
		return new HashCodeBuilder()
	            .append(getId())
	            .toHashCode();
	}

}
