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


public class FreightSetting extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "FreightSetting";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_TYPE_ID = "type_id";
	
	public static final String ALIAS_SUPPLY_ID = "supply_id";
	
	public static final String ALIAS_PEICE_JSON = "peice_json";
	
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
     * type_id       db_column: type_id  
     * 
     * 
     * 
     */	
	private java.lang.Long typeId;
    /**
     * supply_id       db_column: supply_id  
     * 
     * 
     * 
     */	
	private java.lang.Long supplyId;
    /**
     * peice_json       db_column: peice_json  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String peiceJson;
	//columns END

	public FreightSetting(){
	}

	public FreightSetting(
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
	public void setTypeId(java.lang.Long value) {
		this.typeId = value;
	}
	
	public java.lang.Long getTypeId() {
		return this.typeId;
	}
	public void setSupplyId(java.lang.Long value) {
		this.supplyId = value;
	}
	
	public java.lang.Long getSupplyId() {
		return this.supplyId;
	}
	public void setPeiceJson(java.lang.String value) {
		this.peiceJson = value;
	}
	
	public java.lang.String getPeiceJson() {
		return this.peiceJson;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TypeId",getTypeId())
			.append("SupplyId",getSupplyId())
			.append("PeiceJson",getPeiceJson())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof FreightSetting == false) return false;
		if(this == obj) return true;
		FreightSetting other = (FreightSetting)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

