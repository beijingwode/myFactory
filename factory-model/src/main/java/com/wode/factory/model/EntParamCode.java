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
import com.wode.common.util.TimeUtil;


public class EntParamCode extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "EntParamCode";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_GROUP_CD = "分组";
	
	public static final String ALIAS_CODE = "代码";
	
	public static final String ALIAS_VALUE = "值";
	
	public static final String ALIAS_NAME = "简称";
	
	public static final String ALIAS_DESCR = "说明";
	
	public static final String ALIAS_STOP_FLG = "停用标识";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
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
     * 分组       db_column: group_cd  
     * 
     * 
     * @NotBlank @Length(max=3)
     */	
	private java.lang.String groupCd;
    /**
     * 代码       db_column: code  
     * 
     * 
     * @NotBlank @Length(max=10)
     */	
	private java.lang.String code;
    /**
     * 值       db_column: value  
     * 
     * 
     * @NotBlank @Length(max=50)
     */	
	private java.lang.String value;
    /**
     * 简称       db_column: name  
     * 
     * 
     * @NotBlank @Length(max=64)
     */	
	private java.lang.String name;
    /**
     * 说明       db_column: descr  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String descr;
    /**
     * 停用标识       db_column: stop_flg  
     * 
     * 
     * @NotBlank @Length(max=1)
     */	
	private java.lang.String stopFlg;
    /**
     * 创建时间       db_column: create_date  
     * 
     * 
     * 
     */	
	private java.util.Date createDate;
    /**
     * 更新时间       db_column: update_date  
     * 
     * 
     * 
     */	
	private java.util.Date updateDate;
	//columns END

	public EntParamCode(){
	}

	public EntParamCode(
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
	public void setGroupCd(java.lang.String value) {
		this.groupCd = value;
	}
	
	public java.lang.String getGroupCd() {
		return this.groupCd;
	}
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	public java.lang.String getValue() {
		return this.value;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setDescr(java.lang.String value) {
		this.descr = value;
	}
	
	public java.lang.String getDescr() {
		return this.descr;
	}
	public void setStopFlg(java.lang.String value) {
		this.stopFlg = value;
	}
	
	public java.lang.String getStopFlg() {
		return this.stopFlg;
	}
	public String getCreateDateString() {
		return TimeUtil.dateToStr(getCreateDate());
	}
	public void setCreateDateString(String value) {
		setCreateDate(TimeUtil.strToDate(value));
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public String getUpdateDateString() {
		return TimeUtil.dateToStr(getUpdateDate());
	}
	public void setUpdateDateString(String value) {
		setUpdateDate(TimeUtil.strToDate(value));
	}
	
	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("GroupCd",getGroupCd())
			.append("Code",getCode())
			.append("Value",getValue())
			.append("Name",getName())
			.append("Descr",getDescr())
			.append("StopFlg",getStopFlg())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof EntParamCode == false) return false;
		if(this == obj) return true;
		EntParamCode other = (EntParamCode)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

