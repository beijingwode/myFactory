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
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_currency")
public class Currency extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Currency";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_NAME = "货币名称";
	
	public static final String ALIAS_UNIT = "币货计数单位";
	
	public static final String ALIAS_DESCRIPTION = "描述";
	
	//date formats
	
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
     * 货币名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("name")
	private java.lang.String name;
    /**
     * 币货计数单位       db_column: unit  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("unit")
	private java.lang.String unit;
    /**
     * 描述       db_column: description  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("description")
	private java.lang.String description;
	/**
     * 兑换比例       db_column: percentage  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("percentage")
	private double percentage;
	//columns END

	public Currency(){
	}

	public Currency(
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
	public void setUnit(java.lang.String value) {
		this.unit = value;
	}
	
	public java.lang.String getUnit() {
		return this.unit;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Unit",getUnit())
			.append("Description",getDescription())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Currency == false) return false;
		if(this == obj) return true;
		Currency other = (Currency)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

