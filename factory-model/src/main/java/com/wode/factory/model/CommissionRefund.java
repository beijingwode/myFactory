package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_commission_refund")
public class CommissionRefund  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
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
	@Column("supplierId")
	private java.lang.Long supplierId;
	
	@Column("refundId")
	private java.lang.String refundId;
	
    public java.lang.String getRefundId() {
		return refundId;
	}

	public void setRefundId(java.lang.String refundId) {
		this.refundId = refundId;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
     * 实付总金额       db_column: amount  
     * 
     * 
     * 
     */	
	@Column("amount")
	private BigDecimal amount;
    /**
     * 总运费       db_column: freight  
     * 
     * 
     * 
     */	
	@Column("freight")
	private BigDecimal freight;
    /**
     * 实际返还佣金       db_column: commission_amount  
     * 
     * 
     * 
     */	
	@Column("commission_amount")
	private BigDecimal commissionAmount;
    /**
     * 佣金比例       db_column: commission_scale  
     * 
     * 
     * 
     */	
	@Column("commissionTotal")
	private BigDecimal commissionTotal;

	@Column("currentCommission")
	private BigDecimal currentCommission;

	@Column("sale_amount")
	private BigDecimal saleAmount;

	@Column("ded_cash")
	private BigDecimal dedCash;

	@Column("give_cash_sum")
	private BigDecimal giveCashSum;

	@Column("ded_cash_sum")
	private BigDecimal dedCashSum;

	@Column("ded_cash_commission")
	private BigDecimal dedCashCommission;

	@Column("buy_commission")
	private BigDecimal buyCommission;
    /**
     * 内购券       db_column: fuli  
     * 
     * 
     * 
     */	
	@Column("fuli")
	private BigDecimal fuli;
    /**
     * createTime       db_column: create_time  
     * 
     * 
     * 
     */	
	@Column("create_time")
	private java.util.Date createTime;
    /**
     * updateTime       db_column: update_time  
     * 
     * 
     * 
     */	
	@Column("update_time")
	private java.util.Date updateTime;
	//columns END

	public CommissionRefund(){
	}

	public CommissionRefund(
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
		
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public BigDecimal getCommissionTotal() {
		return commissionTotal;
	}

	public void setCommissionTotal(BigDecimal commissionTotal) {
		this.commissionTotal = commissionTotal;
	}

	public BigDecimal getCurrentCommission() {
		return currentCommission;
	}

	public void setCurrentCommission(BigDecimal currentCommission) {
		this.currentCommission = currentCommission;
	}

	public void setFuli(BigDecimal value) {
		this.fuli = value;
	}
	
	public BigDecimal getFuli() {
		return this.fuli;
	}
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getDedCash() {
		return dedCash;
	}

	public void setDedCash(BigDecimal dedCash) {
		this.dedCash = dedCash;
	}

	public BigDecimal getGiveCashSum() {
		return giveCashSum;
	}

	public void setGiveCashSum(BigDecimal giveCashSum) {
		this.giveCashSum = giveCashSum;
	}

	public BigDecimal getDedCashSum() {
		return dedCashSum;
	}

	public void setDedCashSum(BigDecimal dedCashSum) {
		this.dedCashSum = dedCashSum;
	}

	public BigDecimal getDedCashCommission() {
		return dedCashCommission;
	}

	public void setDedCashCommission(BigDecimal dedCashCommission) {
		this.dedCashCommission = dedCashCommission;
	}

	public BigDecimal getBuyCommission() {
		return buyCommission;
	}

	public void setBuyCommission(BigDecimal buyCommission) {
		this.buyCommission = buyCommission;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Amount",getAmount())
			.append("Freight",getFreight())
			.append("CommissionAmount",getCommissionAmount())
			.append("Fuli",getFuli())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CommissionRefund == false) return false;
		if(this == obj) return true;
		CommissionRefund other = (CommissionRefund)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

