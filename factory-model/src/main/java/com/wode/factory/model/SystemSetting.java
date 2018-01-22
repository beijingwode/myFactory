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


public class SystemSetting extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SystemSetting";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_SYSTEM_KEY = "key";
	
	public static final String ALIAS_DESCIPT = "descipt";
	
	public static final String ALIAS_SYSTEM_VALUE = "value";
	
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
     * key       db_column: system_key  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String systemKey;
    /**
     * descipt       db_column: descipt  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String descipt;
    /**
     * value       db_column: system_value  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String systemValue;
	//columns END

	public SystemSetting(){
	}

	public SystemSetting(
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
	public void setSystemKey(java.lang.String value) {
		this.systemKey = value;
	}
	
	public java.lang.String getSystemKey() {
		return this.systemKey;
	}
	public void setDescipt(java.lang.String value) {
		this.descipt = value;
	}
	
	public java.lang.String getDescipt() {
		return this.descipt;
	}
	public void setSystemValue(java.lang.String value) {
		this.systemValue = value;
	}
	
	public java.lang.String getSystemValue() {
		return this.systemValue;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SystemKey",getSystemKey())
			.append("Descipt",getDescipt())
			.append("SystemValue",getSystemValue())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SystemSetting == false) return false;
		if(this == obj) return true;
		SystemSetting other = (SystemSetting)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

