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

@Table("t_service_receipt")
public class ServiceReceipt extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2838068174915306571L;
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
     * 类型       db_column: type
     * 
     * 
     * 
     */ 
    @Column("type")
    private java.lang.String type;
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 发票号码       db_column: code
     * 
     * 
     * 
     */ 
    @Column("code")
    private java.lang.String code;
    /**
     * 付款单位       db_column: title
     * 
     * 
     * 
     */ 
    @Column("title")
    private java.lang.String title;
    /**
     * 财务代码       db_column: finance_code
     * 
     * 
     * 
     */ 
    @Column("finance_code")
    private java.lang.String financeCode;
    /**
     * 金额合计       db_column: amount
     * 
     * 
     * 
     */ 
    @Column("amount")
    private java.math.BigDecimal amount;
    /**
     * 结算单id       db_column: sale_bill_ids
     * 
     * 
     * 
     */ 
    @Column("sale_bill_ids")
    private java.lang.String saleBillIds;
    /**
     * 开票日期       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 创建用户       db_column: create_user
     * 
     * 
     * 
     */ 
    @Column("create_user")
    private java.lang.Long createUser;
    /**
     * 状态 2:已开出/3:已寄出/4:申请退回/5:拒绝退回/6:同意退回/7:退回已寄出/8:确认退回       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.String status;
    /**
     * 邮寄日期       db_column: send_date
     * 
     * 
     * 
     */ 
    @Column("send_date")
    private java.util.Date sendDate;
    /**
     * 邮寄 快递公司       db_column: send_express_type
     * 
     * 
     * 
     */ 
    @Column("send_express_type")
    private java.lang.String sendExpressType;
    /**
     * 邮寄运单号       db_column: send_express_no
     * 
     * 
     * 
     */ 
    @Column("send_express_no")
    private java.lang.String sendExpressNo;
    /**
     * 回退日期       db_column: return_date
     * 
     * 
     * 
     */ 
    @Column("return_date")
    private java.util.Date returnDate;
    /**
     * 回退 快递公司       db_column: return_express_type
     * 
     * 
     * 
     */ 
    @Column("return_express_type")
    private java.lang.String returnExpressType;
    /**
     * 回退运单号       db_column: return_express_no
     * 
     * 
     * 
     */ 
    @Column("return_express_no")
    private java.lang.String returnExpressNo;
    /**
     * 回退说明       db_column: return_note
     * 
     * 
     * 
     */ 
    @Column("return_note")
    private java.lang.String returnNote;
    /**
     * 回退期限       db_column: return_limit
     * 
     * 
     * 
     */ 
    @Column("return_limit")
    private java.util.Date returnLimit;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 更新用户       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 拒绝理由       db_column: reject_note
     * 
     * 
     * 
     */ 
    @Column("reject_note")
    private java.lang.String rejectNote;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Type",getType())
            .append("SupplierId",getSupplierId())
            .append("Code",getCode())
            .append("Title",getTitle())
            .append("FinanceCode",getFinanceCode())
            .append("Amount",getAmount())
            .append("SaleBillIds",getSaleBillIds())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .append("Status",getStatus())
            .append("SendDate",getSendDate())
            .append("SendExpressType",getSendExpressType())
            .append("SendExpressNo",getSendExpressNo())
            .append("ReturnDate",getReturnDate())
            .append("ReturnExpressType",getReturnExpressType())
            .append("ReturnExpressNo",getReturnExpressNo())
            .append("ReturnNote",getReturnNote())
            .append("ReturnLimit",getReturnLimit())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateUser",getUpdateUser())
            .append("RejectNote",getRejectNote())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getFinanceCode() {
		return financeCode;
	}

	public void setFinanceCode(java.lang.String financeCode) {
		this.financeCode = financeCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public java.lang.String getSaleBillIds() {
		return saleBillIds;
	}

	public void setSaleBillIds(java.lang.String saleBillIds) {
		this.saleBillIds = saleBillIds;
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

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.util.Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(java.util.Date sendDate) {
		this.sendDate = sendDate;
	}

	public java.lang.String getSendExpressType() {
		return sendExpressType;
	}

	public void setSendExpressType(java.lang.String sendExpressType) {
		this.sendExpressType = sendExpressType;
	}

	public java.lang.String getSendExpressNo() {
		return sendExpressNo;
	}

	public void setSendExpressNo(java.lang.String sendExpressNo) {
		this.sendExpressNo = sendExpressNo;
	}

	public java.util.Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(java.util.Date returnDate) {
		this.returnDate = returnDate;
	}

	public java.lang.String getReturnExpressType() {
		return returnExpressType;
	}

	public void setReturnExpressType(java.lang.String returnExpressType) {
		this.returnExpressType = returnExpressType;
	}

	public java.lang.String getReturnExpressNo() {
		return returnExpressNo;
	}

	public void setReturnExpressNo(java.lang.String returnExpressNo) {
		this.returnExpressNo = returnExpressNo;
	}

	public java.lang.String getReturnNote() {
		return returnNote;
	}

	public void setReturnNote(java.lang.String returnNote) {
		this.returnNote = returnNote;
	}

	public java.util.Date getReturnLimit() {
		return returnLimit;
	}

	public void setReturnLimit(java.util.Date returnLimit) {
		this.returnLimit = returnLimit;
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

	public java.lang.String getRejectNote() {
		return rejectNote;
	}

	public void setRejectNote(java.lang.String rejectNote) {
		this.rejectNote = rejectNote;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
}
