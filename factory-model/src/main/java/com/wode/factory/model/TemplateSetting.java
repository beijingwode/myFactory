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


public class TemplateSetting extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TemplateSetting";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_TITLE = "标题";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_CONTENT = "生成文件路径";
	
	public static final String ALIAS_FILE_PATH = "生成文件路径";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 标题       db_column: title  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String title;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String name;
    /**
     * 生成文件路径       db_column: content  
     * 
     * 
     * @Length(max=65535)
     */	
	private java.lang.String content;
    /**
     * 生成文件路径       db_column: filePath  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String filePath;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */	
	private java.util.Date createDate;
    /**
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */	
	private java.util.Date updateDate;
	//columns END

	public TemplateSetting(){
	}

	public TemplateSetting(
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
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setFilePath(java.lang.String value) {
		this.filePath = value;
	}
	
	public java.lang.String getFilePath() {
		return this.filePath;
	}
	public String getCreateDateString() {
		return DateConvertUtils.format(getCreateDate(), FORMAT_CREATE_DATE);
	}
	public void setCreateDateString(String value) {
		setCreateDate(DateConvertUtils.parse(value, FORMAT_CREATE_DATE,java.util.Date.class));
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public String getUpdateDateString() {
		return DateConvertUtils.format(getUpdateDate(), FORMAT_UPDATE_DATE);
	}
	public void setUpdateDateString(String value) {
		setUpdateDate(DateConvertUtils.parse(value, FORMAT_UPDATE_DATE,java.util.Date.class));
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
			.append("Title",getTitle())
			.append("Name",getName())
			.append("Content",getContent())
			.append("FilePath",getFilePath())
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
		if(obj instanceof TemplateSetting == false) return false;
		if(this == obj) return true;
		TemplateSetting other = (TemplateSetting)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

