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


public class PermissionsFunction extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "PermissionsFunction";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_ROLE_ID = "role_id";
	
	public static final String ALIAS_MENU_ID = "menu_id";
	
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
     * role_id       db_column: role_id  
     * 
     * 
     * 
     */	
	private java.lang.Long roleId;
    /**
     * menu_id       db_column: menu_id  
     * 
     * 
     * 
     */	
	private java.lang.Long menuId;
	//columns END

	public PermissionsFunction(){
	}

	public PermissionsFunction(
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
	public void setRoleId(java.lang.Long value) {
		this.roleId = value;
	}
	
	public java.lang.Long getRoleId() {
		return this.roleId;
	}
	public void setMenuId(java.lang.Long value) {
		this.menuId = value;
	}
	
	public java.lang.Long getMenuId() {
		return this.menuId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RoleId",getRoleId())
			.append("MenuId",getMenuId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PermissionsFunction == false) return false;
		if(this == obj) return true;
		PermissionsFunction other = (PermissionsFunction)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

