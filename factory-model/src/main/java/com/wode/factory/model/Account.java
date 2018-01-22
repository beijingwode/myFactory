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

@Table("t_account")
public class Account extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Account";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "user_id";
	
	public static final String ALIAS_BALANCE = "余额";
	
	public static final String ALIAS_TYPE = "类型";
	
	public static final String ALIAS_UPDATE_TIME = "update_time";
	
	//date formats
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id(auto=false)
	@Column("id")
	private java.lang.Long id;
    /**
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */
	@Column("user_id")
	private java.lang.Long userId;
    /**
     * 余额       db_column: balance  
     * 
     * 
     * 
     */
	@Column("balance")
	private java.lang.Double balance;
    /**
     * 类型       db_column: type  
     * 
     * 
     * 
     */
	@Column("type")
	private java.lang.Integer type;
    /**
     * update_time       db_column: update_time  
     * 
     * 
     * 
     */
	@Column("update_time")
	private java.util.Date updateTime;
	//columns END

	public Account(){
	}

	public Account(
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
	public void setBalance(java.lang.Double value) {
		this.balance = value;
	}
	
	public java.lang.Double getBalance() {
		return this.balance;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Balance",getBalance())
			.append("Type",getType())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Account == false) return false;
		if(this == obj) return true;
		Account other = (Account)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

