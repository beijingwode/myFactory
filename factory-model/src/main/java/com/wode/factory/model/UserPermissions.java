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


public class UserPermissions extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserPermissions";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_PLA_ID = "平台用_id";
	
	public static final String ALIAS_USER_ID = "user_id";
	
	public static final String ALIAS_ROLE_ID = "role_id";
	
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
     * 平台用_id       db_column: pla_id  
     * 
     * 
     * 
     */	
	private java.lang.Long plaId;
    /**
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
    /**
     * role_id       db_column: role_id  
     * 
     * 
     * 
     */	
	private java.lang.Long roleId;
	//columns END

	public UserPermissions(){
	}

	public UserPermissions(
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
	public void setPlaId(java.lang.Long value) {
		this.plaId = value;
	}
	
	public java.lang.Long getPlaId() {
		return this.plaId;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setRoleId(java.lang.Long value) {
		this.roleId = value;
	}
	
	public java.lang.Long getRoleId() {
		return this.roleId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PlaId",getPlaId())
			.append("UserId",getUserId())
			.append("RoleId",getRoleId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserPermissions == false) return false;
		if(this == obj) return true;
		UserPermissions other = (UserPermissions)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

