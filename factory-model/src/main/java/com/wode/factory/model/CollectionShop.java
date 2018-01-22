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


public class CollectionShop extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CollectionShop";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "user_id";
	
	public static final String ALIAS_SHOP_ID = "shop_id";
	
	public static final String ALIAS_CREAT_TIME = "creat_time";
	
	//date formats
	public static final String FORMAT_CREAT_TIME = DATE_TIME_FORMAT;
	
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
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
    /**
     * shop_id       db_column: shop_id  
     * 
     * 
     * 
     */	
	private java.lang.Long shopId;
    /**
     * creat_time       db_column: creat_time  
     * 
     * 
     * 
     */	
	private java.util.Date creatTime;
	//columns END

	public CollectionShop(){
	}

	public CollectionShop(
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
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setShopId(java.lang.Long value) {
		this.shopId = value;
	}
	
	public java.lang.Long getShopId() {
		return this.shopId;
	}
	public String getCreatTimeString() {
		return DateConvertUtils.format(getCreatTime(), FORMAT_CREAT_TIME);
	}
	public void setCreatTimeString(String value) {
		setCreatTime(DateConvertUtils.parse(value, FORMAT_CREAT_TIME,java.util.Date.class));
	}
	
	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}
	
	public java.util.Date getCreatTime() {
		return this.creatTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("ShopId",getShopId())
			.append("CreatTime",getCreatTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CollectionShop == false) return false;
		if(this == obj) return true;
		CollectionShop other = (CollectionShop)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

