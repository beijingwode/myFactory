/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ShopPromotion extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ShopPromotion";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_PROMOTION = "促销id";
	
	public static final String ALIAS_SHOP_ID = "店铺id";
	
	public static final String ALIAS_STATUS = "状态";
	
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
     * 促销id       db_column: promotion  
     * 
     * 
     * 
     */	
	private java.lang.Long promotion;
    /**
     * 店铺id       db_column: shopId  
     * 
     * 
     * 
     */	
	private java.lang.Long shopId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * @Max(127)
     */	
	private Integer status;
	//columns END

	public ShopPromotion(){
	}

	public ShopPromotion(
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
	public void setPromotion(java.lang.Long value) {
		this.promotion = value;
	}
	
	public java.lang.Long getPromotion() {
		return this.promotion;
	}
	public void setShopId(java.lang.Long value) {
		this.shopId = value;
	}
	
	public java.lang.Long getShopId() {
		return this.shopId;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Promotion",getPromotion())
			.append("ShopId",getShopId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ShopPromotion == false) return false;
		if(this == obj) return true;
		ShopPromotion other = (ShopPromotion)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

