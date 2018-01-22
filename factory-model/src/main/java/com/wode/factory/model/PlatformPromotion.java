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

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class PlatformPromotion extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "PlatformPromotion";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_PROMOTIONS_ID = "促销ID";
	
	public static final String ALIAS_STATUS = "状态";
	
	//date formats
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 促销ID       db_column: promotionsId  
     * 
     * 
     * 
     */	
	private java.lang.Long promotionsId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * 
     */	
	private java.lang.Boolean status;
	//columns END

	public PlatformPromotion(){
	}

	public PlatformPromotion(
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
	public void setPromotionsId(java.lang.Long value) {
		this.promotionsId = value;
	}
	
	public java.lang.Long getPromotionsId() {
		return this.promotionsId;
	}
	public void setStatus(java.lang.Boolean value) {
		this.status = value;
	}
	
	public java.lang.Boolean getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PromotionsId",getPromotionsId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PlatformPromotion == false) return false;
		if(this == obj) return true;
		PlatformPromotion other = (PlatformPromotion)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

