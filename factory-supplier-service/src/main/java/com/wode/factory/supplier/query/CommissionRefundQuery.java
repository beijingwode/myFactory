package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class CommissionRefundQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 实付总金额 */
	private Long amount;
	/** 总运费 */
	private Long freight;
	/** 实际返还佣金 */
	private Long commissionAmount;
	/** 佣金比例 */
	private Long commissionScale;
	/** 内购券 */
	private java.lang.Long fuli;
	/** createTime */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** updateTime */
	private java.util.Date updateTimeBegin;
	private java.util.Date updateTimeEnd;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public Long getAmount() {
		return this.amount;
	}
	
	public void setAmount(Long value) {
		this.amount = value;
	}
	
	public Long getFreight() {
		return this.freight;
	}
	
	public void setFreight(Long value) {
		this.freight = value;
	}
	
	public Long getCommissionAmount() {
		return this.commissionAmount;
	}
	
	public void setCommissionAmount(Long value) {
		this.commissionAmount = value;
	}
	
	public Long getCommissionScale() {
		return this.commissionScale;
	}
	
	public void setCommissionScale(Long value) {
		this.commissionScale = value;
	}
	
	public java.lang.Long getFuli() {
		return this.fuli;
	}
	
	public void setFuli(java.lang.Long value) {
		this.fuli = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	public void setCreateTimeEnd(java.util.Date value) {
		this.createTimeEnd = value;
	}
	
	public java.util.Date getUpdateTimeBegin() {
		return this.updateTimeBegin;
	}
	
	public void setUpdateTimeBegin(java.util.Date value) {
		this.updateTimeBegin = value;
	}	
	
	public java.util.Date getUpdateTimeEnd() {
		return this.updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(java.util.Date value) {
		this.updateTimeEnd = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

