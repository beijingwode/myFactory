/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SettleAccounts  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SettleAccounts";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_MONTH = "月份";
	
	public static final String ALIAS_TOTAL_SALES_AMOUNT = "销售总金额";
	
	public static final String ALIAS_COMMISSION = "佣金";
	
	public static final String ALIAS_BUYER_SECURITY_AMOUNT = "消费者保障金";
	
	public static final String ALIAS_FREIGHT_PRICE = "运费";
	
	public static final String ALIAS_TOTAL_DEAL_AMOUNT = "交易总金额";
	
	public static final String ALIAS_STATUS = "状态：1正常，0不正常";
	
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * @NotNull 
     */	
	private Long id;
    /**
     * 月份       db_column: month  
     * 
     * 
     * 
     */
	private java.util.Date month;
    /**
     * 销售总金额       db_column: total_sales_amount  
     * 
     * 
     * 
     */	
	private BigDecimal totalSalesAmount;
    /**
     * 佣金       db_column: commission  
     * 
     * 
     * 
     */	
	private BigDecimal commission;
    /**
     * 消费者保障金       db_column: buyer_security_amount  
     * 
     * 
     * 
     */
	private BigDecimal buyerSecurityAmount;
    /**
     * 运费       db_column: freight_price  
     * 
     * 
     * 
     */
	private BigDecimal freightPrice;
    /**
     * 交易总金额       db_column: total_deal_amount  
     * 
     * 
     * 
     */
	private BigDecimal totalDealAmount;
    /**
     * 状态：1正常，0不正常       db_column: status  
     * 
     * 
     * 
     */
	private java.lang.Integer status;
	
	private java.lang.Long supplierId;
	//columns END

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public SettleAccounts(){
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public BigDecimal getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public void setTotalSalesAmount(BigDecimal totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getBuyerSecurityAmount() {
		return buyerSecurityAmount;
	}

	public void setBuyerSecurityAmount(BigDecimal buyerSecurityAmount) {
		this.buyerSecurityAmount = buyerSecurityAmount;
	}

	public BigDecimal getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}

	public BigDecimal getTotalDealAmount() {
		return totalDealAmount;
	}

	public void setTotalDealAmount(BigDecimal totalDealAmount) {
		this.totalDealAmount = totalDealAmount;
	}

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setMonth(java.util.Date value) {
		this.month = value;
	}
	
	public java.util.Date getMonth() {
		return this.month;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Month",getMonth())
			.append("TotalSalesAmount",getTotalSalesAmount())
			.append("Commission",getCommission())
			.append("BuyerSecurityAmount",getBuyerSecurityAmount())
			.append("FreightPrice",getFreightPrice())
			.append("TotalDealAmount",getTotalDealAmount())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SettleAccounts == false) return false;
		if(this == obj) return true;
		SettleAccounts other = (SettleAccounts)obj;
		return new EqualsBuilder()
			.isEquals();
	}
}

