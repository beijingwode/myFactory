/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class CashbackRecord extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "CashbackRecord";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_USER_ID = "用户ID";
	
	public static final String ALIAS_SUB_ORDER_ID = "子单ID";
	
	public static final String ALIAS_AMOUNT = "返现金额";
	
	//date formats
	
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
     * 用户ID       db_column: userId  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
    /**
     * 子单ID       db_column: subOrderId  
     * 
     * 
     * 
     */	
	private java.lang.Long subOrderId;
    /**
     * 返现金额       db_column: amount  
     * 
     * 
     * 
     */	
	private Long amount;
	//columns END

	public CashbackRecord(){
	}

	public CashbackRecord(
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
	public void setSubOrderId(java.lang.Long value) {
		this.subOrderId = value;
	}
	
	public java.lang.Long getSubOrderId() {
		return this.subOrderId;
	}
	public void setAmount(Long value) {
		this.amount = value;
	}
	
	public Long getAmount() {
		return this.amount;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("SubOrderId",getSubOrderId())
			.append("Amount",getAmount())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CashbackRecord == false) return false;
		if(this == obj) return true;
		CashbackRecord other = (CashbackRecord)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

