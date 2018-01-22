package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_transfer")
public class SupplierTransfer extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6211328710266695752L;
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
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 财务代码       db_column: finance_code
     * 
     * 
     * 
     */ 
    @Column("finance_code")
    private java.lang.String financeCode;
    /**
     * 金额       db_column: amount
     * 
     * 
     * 
     */ 
    @Column("amount")
    private java.math.BigDecimal amount;
    /**
     * 状态 1:已申请/2:已确认/3:已转账/-1已拒绝       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 申请日期       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 申请人       db_column: create_user
     * 
     * 
     * 
     */ 
    @Column("create_user")
    private java.lang.Long createUser;
    /**
     * 转账日期       db_column: pay_date
     * 
     * 
     * 
     */ 
    @Column("pay_date")
    private java.util.Date payDate;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 更新者       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 系统关联key       db_column: system_key
     * 
     * 
     * 
     */ 
    @Column("system_key")
    private java.lang.String systemKey;
    /**
     * 拒绝理由       db_column: reject_note
     * 
     * 
     * 
     */ 
    @Column("reject_note")
    private java.lang.String rejectNote;
    /**
     * 支付流水号       db_column: pay_flow_code
     * 
     * 
     * 
     */ 
    @Column("pay_flow_code")
    private java.lang.String payFlowCode;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("FinanceCode",getFinanceCode())
            .append("Amount",getAmount())
            .append("Status",getStatus())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .append("PayDate",getPayDate())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateUser",getUpdateUser())
            .append("SystemKey",getSystemKey())
            .append("RejectNote",getRejectNote())
            .append("PayFlowCode",getPayFlowCode())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getFinanceCode() {
		return financeCode;
	}

	public void setFinanceCode(java.lang.String financeCode) {
		this.financeCode = financeCode;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(java.lang.Long createUser) {
		this.createUser = createUser;
	}

	public java.util.Date getPayDate() {
		return payDate;
	}

	public void setPayDate(java.util.Date payDate) {
		this.payDate = payDate;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.Long updateUser) {
		this.updateUser = updateUser;
	}

	public java.lang.String getSystemKey() {
		return systemKey;
	}

	public void setSystemKey(java.lang.String systemKey) {
		this.systemKey = systemKey;
	}

	public java.lang.String getRejectNote() {
		return rejectNote;
	}

	public void setRejectNote(java.lang.String rejectNote) {
		this.rejectNote = rejectNote;
	}

	public java.lang.String getPayFlowCode() {
		return payFlowCode;
	}

	public void setPayFlowCode(java.lang.String payFlowCode) {
		this.payFlowCode = payFlowCode;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
}

