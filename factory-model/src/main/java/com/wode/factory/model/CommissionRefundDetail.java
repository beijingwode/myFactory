package com.wode.factory.model;


import com.wode.common.stereotype.PrimaryKey;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import java.math.BigDecimal;

@Table("t_commission_refund_detail")
public class CommissionRefundDetail implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;


	//columns START
	/**
	 * id       db_column: id
	 */
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
	/**
	 * userId       db_column: user_id
	 */
	@Column("user_id")
	private java.lang.Long userId;
	/**
	 * orderId       db_column: order_id
	 *
	 * @Length(max=225)
	 */
	@Column("order_id")
	private java.lang.String orderId;
	/**
	 * paymentAmount       db_column: payment_amount
	 */
	@Column("payment_amount")
	private BigDecimal paymentAmount;
	/**
	 * freight       db_column: freight
	 */
	@Column("freight")
	private BigDecimal freight;

	@Column("commissionRefundId")
	private Long commissionRefundId;
	@Column("commissionTotal")
	private BigDecimal commissionTotal;
	/**
	 * fuli       db_column: fuli
	 */
	@Column("fuli")
	private BigDecimal fuli;
	/**
	 * createTime       db_column: create_time
	 */
	@Column("create_time")
	private java.util.Date createTime;
	/**
	 * updateTime       db_column: update_time
	 */
	@Column("update_time")
	private java.util.Date updateTime;
	//columns END

	public CommissionRefundDetail() {
	}

	public CommissionRefundDetail(
			java.lang.Long id
	) {
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

	public void setOrderId(java.lang.String value) {
		this.orderId = value;
	}

	public java.lang.String getOrderId() {
		return this.orderId;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
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

	public BigDecimal getCommissionTotal() {
		return commissionTotal;
	}

	public void setCommissionTotal(BigDecimal commissionTotal) {
		this.commissionTotal = commissionTotal;
	}

	public Long getCommissionRefundId() {
		return commissionRefundId;
	}

	public void setCommissionRefundId(Long commissionRefundId) {
		this.commissionRefundId = commissionRefundId;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId())
				.append("UserId", getUserId())
				.append("OrderId", getOrderId())
				.append("PaymentAmount", getPaymentAmount())
				.append("Freight", getFreight())
				.append("Fuli", getFuli())
				.append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof CommissionRefundDetail == false) return false;
		if (this == obj) return true;
		CommissionRefundDetail other = (CommissionRefundDetail) obj;
		return new EqualsBuilder()
				.append(getId(), other.getId())
				.isEquals();
	}
}

