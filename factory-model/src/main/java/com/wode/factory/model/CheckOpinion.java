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

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_check_opinion")
public class CheckOpinion extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CheckOpinion";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "审核人id";
	
	public static final String ALIAS_USERNAME = "审核人姓名";
	
	public static final String ALIAS_OPINION = "审核意见";
	
	public static final String ALIAS_TIME = "审核时间";
	
	public static final String ALIAS_RESULT = "审核结果(-1:审核不通过、0：审核中、1：审核通过)";
	
	public static final String ALIAS_TYPE = "审核类型(0:商家信息审核，1:商品审核)";
	
	public static final String ALIAS_CHECK_ID = "审核记录id（商家id、商品id）";
	
	//date formats
	public static final String FORMAT_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id(auto=false)
	private java.lang.Long id;
    /**
     * 审核人id       db_column: user_id  
     * 
     * 
     * 
     */	
	@Column("user_id")
	private java.lang.Long userId;
    /**
     * 审核人姓名       db_column: username  
     * 
     * 
     * @Length(max=255)
     */	
	@Column
	private java.lang.String username;
    /**
     * 审核意见       db_column: opinion  
     * 
     * 
     * @Length(max=255)
     */	
	@Column
	private java.lang.String opinion;
    /**
     * 审核时间       db_column: time  
     * 
     * 
     * 
     */
	@Column
	private java.util.Date time;
    /**
     * 审核结果(-1:审核不通过、0：审核中、1：审核通过)       db_column: result  
     * 
     * 
     * @Max(127)
     */
	@Column
	private Integer result;
    /**
     * 审核类型(0:商家信息审核，1:商品审核)       db_column: type  
     * 
     * 
     * @Max(127)
     */
	@Column
	private Integer type;
    /**
     * 审核记录id（商家id、商品id）       db_column: checkId  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Long checkId;
	//columns END

	private java.lang.String checkType;
	
	public CheckOpinion(){
	}

	public CheckOpinion(
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
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setOpinion(java.lang.String value) {
		this.opinion = value;
	}
	
	public java.lang.String getOpinion() {
		return this.opinion;
	}
	public String getTimeString() {
		return DateConvertUtils.format(getTime(), FORMAT_TIME);
	}
	public void setTimeString(String value) {
		setTime(DateConvertUtils.parse(value, FORMAT_TIME,java.util.Date.class));
	}
	
	public void setTime(java.util.Date value) {
		this.time = value;
	}
	
	public java.util.Date getTime() {
		return this.time;
	}
	public void setResult(Integer value) {
		this.result = value;
	}
	
	public Integer getResult() {
		return this.result;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setCheckId(java.lang.Long value) {
		this.checkId = value;
	}
	
	public java.lang.Long getCheckId() {
		return this.checkId;
	}

	public java.lang.String getCheckType() {
		return checkType;
	}

	public void setCheckType(java.lang.String checkType) {
		this.checkType = checkType;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Username",getUsername())
			.append("Opinion",getOpinion())
			.append("Time",getTime())
			.append("Result",getResult())
			.append("Type",getType())
			.append("CheckId",getCheckId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CheckOpinion == false) return false;
		if(this == obj) return true;
		CheckOpinion other = (CheckOpinion)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

