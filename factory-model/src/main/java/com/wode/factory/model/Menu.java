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


public class Menu extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Menu";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_NAME = "name";
	
	public static final String ALIAS_URL = "url";
	
	public static final String ALIAS_PID = "pid";
	
	public static final String ALIAS_ORDER_NO = "order_no";
	
	public static final String ALIAS_ICON = "icon";
	
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
     * name       db_column: name  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String name;
    /**
     * url       db_column: url  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String url;
    /**
     * pid       db_column: pid  
     * 
     * 
     * 
     */	
	private java.lang.Long pid;
    /**
     * order_no       db_column: order_no  
     * 
     * 
     * 
     */	
	private java.lang.Integer orderNo;
    /**
     * icon       db_column: icon  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String icon;
	//columns END

	public Menu(){
	}

	public Menu(
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
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setPid(java.lang.Long value) {
		this.pid = value;
	}
	
	public java.lang.Long getPid() {
		return this.pid;
	}
	public void setOrderNo(java.lang.Integer value) {
		this.orderNo = value;
	}
	
	public java.lang.Integer getOrderNo() {
		return this.orderNo;
	}
	public void setIcon(java.lang.String value) {
		this.icon = value;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Url",getUrl())
			.append("Pid",getPid())
			.append("OrderNo",getOrderNo())
			.append("Icon",getIcon())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Menu == false) return false;
		if(this == obj) return true;
		Menu other = (Menu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

