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


public class PlatformUser extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "PlatformUser";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USERNAME = "username";
	
	public static final String ALIAS_PASSWORD = "password";
	
	public static final String ALIAS_XM = "xm";
	
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
     * username       db_column: username  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String username;
    /**
     * password       db_column: password  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String password;
    /**
     * xm       db_column: xm  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String xm;
	//columns END

	public PlatformUser(){
	}

	public PlatformUser(
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
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setXm(java.lang.String value) {
		this.xm = value;
	}
	
	public java.lang.String getXm() {
		return this.xm;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Username",getUsername())
			.append("Password",getPassword())
			.append("Xm",getXm())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PlatformUser == false) return false;
		if(this == obj) return true;
		PlatformUser other = (PlatformUser)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

