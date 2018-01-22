/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_balance")
public class UserBalance extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserBalance";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_USER_ID = "用户id";
	
	public static final String ALIAS_CURRENCY_ID = "币货id";
	
	public static final String ALIAS_BALANCE = "货币余额";
	
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
     * 用户id       db_column: user_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("user_id")
	private java.lang.Long userId;
    /**
     * 币货id       db_column: currency_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("currency_id")
	private java.lang.Long currencyId;
    /**
     * 货币余额       db_column: balance  
     * 
     * 
     * 
     */	
	@Column("balance")
	private BigDecimal balance;
	
    /**
     * 全部货比数量，一直累加，使用后该值不更改       db_column: balance_total  
     * 
     * 
     * 
     */	
	@Column("balance_total")
	private BigDecimal balanceTotal;
	
	private Integer state;//状态： 默认null或0：正常   1：禁用
	//columns END
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setCurrencyId(java.lang.Long value) {
		this.currencyId = value;
	}
	
	public java.lang.Long getCurrencyId() {
		return this.currencyId;
	}
	public void setBalance(BigDecimal value) {
		this.balance = value;
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}

	public BigDecimal getBalanceTotal() {
		return balanceTotal;
	}
	public void setBalanceTotal(BigDecimal balanceTotal) {
		this.balanceTotal = balanceTotal;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("CurrencyId",getCurrencyId())
			.append("Balance",getBalance())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserBalance == false) return false;
		if(this == obj) return true;
		UserBalance other = (UserBalance)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

