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

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_comments_statistics")
public class CommentsStatistics extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CommentsStatistics";
	
	public static final String ALIAS_ID = "主键";
	
	public static final String ALIAS_GOOD = "好评数量";
	
	public static final String ALIAS_MEDIUM = "中评数量";
	
	public static final String ALIAS_BAD = "差评数量";
	
	public static final String ALIAS_SUPPLIER_ID = "商家id";
	
	public static final String ALIAS_DATETIME = "统计时间";
	
	public static final String ALIAS_TYPE = "统计类型   0 周  1 月";
	
	public static final String ALIAS_TOTE = "总数";
	
	//date formats
	public static final String FORMAT_DATETIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 主键       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
    /**
     * 好评数量       db_column: good  
     * 
     * 
     * 
     */	
	@Column("good")
	private java.lang.Long good;
    /**
     * 中评数量       db_column: medium  
     * 
     * 
     * 
     */	
	@Column("medium")
	private java.lang.Long medium;
    /**
     * 差评数量       db_column: bad  
     * 
     * 
     * 
     */	
	@Column("bad")
	private java.lang.Long bad;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * 统计时间       db_column: datetime  
     * 
     * 
     * 
     */	
	@Column("datetime")
	private java.util.Date datetime;
    /**
     * 统计类型   0 周  1 月       db_column: type  
     * 
     * 
     * 
     */	
	@Column("type")
	private java.lang.Integer type;
    /**
     * 总数       db_column: tote  
     * 
     * 
     * 
     */	
	@Column("tote")
	private java.lang.Long tote;
	//columns END

	public CommentsStatistics(){
	}

	public CommentsStatistics(
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
	public void setGood(java.lang.Long value) {
		this.good = value;
	}
	
	public java.lang.Long getGood() {
		return this.good;
	}
	public void setMedium(java.lang.Long value) {
		this.medium = value;
	}
	
	public java.lang.Long getMedium() {
		return this.medium;
	}
	public void setBad(java.lang.Long value) {
		this.bad = value;
	}
	
	public java.lang.Long getBad() {
		return this.bad;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	public String getDatetimeString() {
		return DateConvertUtils.format(getDatetime(), FORMAT_DATETIME);
	}
	public void setDatetimeString(String value) {
		setDatetime(DateConvertUtils.parse(value, FORMAT_DATETIME,java.util.Date.class));
	}
	
	public void setDatetime(java.util.Date value) {
		this.datetime = value;
	}
	
	public java.util.Date getDatetime() {
		return this.datetime;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setTote(java.lang.Long value) {
		this.tote = value;
	}
	
	public java.lang.Long getTote() {
		return this.tote;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Good",getGood())
			.append("Medium",getMedium())
			.append("Bad",getBad())
			.append("SupplierId",getSupplierId())
			.append("Datetime",getDatetime())
			.append("Type",getType())
			.append("Tote",getTote())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CommentsStatistics == false) return false;
		if(this == obj) return true;
		CommentsStatistics other = (CommentsStatistics)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

