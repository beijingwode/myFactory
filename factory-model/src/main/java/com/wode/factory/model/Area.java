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
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_area")
public class Area extends BaseModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * name       db_column: name  
     * 
     * 
     * @Length(max=30)
     */	
	@Column("name")
	private java.lang.String name;
    /**
     * pid       db_column: father  
     * 
     * 
     * 
     */	
	@Column("father")
	private java.lang.Integer father;
    /**
     * grade       db_column: grade  
     * 
     * 
     * 
     */	
	@Column("grade")
	private java.lang.Integer grade;
    /**
     * orderno       db_column: code  
     * 
     * 
     * 
     */	
	@Column("code")
	private java.lang.Integer code;
   
	//columns END

	public Area(){
	}

	public Area(
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
	
	public void setGrade(java.lang.Integer value) {
		this.grade = value;
	}
	
	public java.lang.Integer getGrade() {
		return this.grade;
	}
	

	public java.lang.Integer getFather() {
		return father;
	}

	public void setFather(java.lang.Integer father) {
		this.father = father;
	}

	public java.lang.Integer getCode() {
		return code;
	}

	public void setCode(java.lang.Integer code) {
		this.code = code;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Code",getCode())
			.append("Name",getName())
			.append("father",getFather())
			.append("Grade",getGrade())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Area == false) return false;
		if(this == obj) return true;
		Area other = (Area)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

