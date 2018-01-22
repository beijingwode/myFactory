package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_ticket_flow")
public class SupplierTicketFlow extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8804925064755931891L;
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
     * 商家券id       db_column: ticket_id
     * 
     * 
     * 
     */ 
    @Column("ticket_id")
    private java.lang.Long ticketId;
    /**
     * 券类型 3:换领       db_column: ticket_type
     * 
     * 
     * 
     */ 
    @Column("ticket_type")
    private java.lang.Integer ticketType;
    /**
     * 操作时间       db_column: op_date
     * 
     * 
     * 
     */ 
    @Column("op_date")
    private java.util.Date opDate;
    /**
     * 操作代码       db_column: op_code
     * 
     * 
     * 
     */ 
    @Column("op_code")
    private java.lang.String opCode;
    /**
     * 额度       db_column: amount
     * 
     * 
     * 
     */ 
    @Column("amount")
    private java.math.BigDecimal amount;
    /**
     * 操作后余额       db_column: balance
     * 
     * 
     * 
     */ 
    @Column("balance")
    private java.math.BigDecimal balance;
    /**
     * 备注       db_column: note
     * 
     * 
     * 
     */ 
    @Column("note")
    private java.lang.String note;
    /**
     * 关键字       db_column: key_id
     * 
     * 
     * 
     */ 
    @Column("key_id")
    private java.lang.Long keyId;
    /**
     * 更新者       db_column: upd_name
     * 
     * 
     * 
     */ 
    @Column("upd_name")
    private java.lang.String updName;
    /**
     * 关键字2       db_column: key2
     * 
     * 
     * 
     */ 
    @Column("key2")
    private java.lang.String key2;
    
    @Column("cash_amount")
    private java.math.BigDecimal cashAmount;
    
    @Column("cash_balance")
    private java.math.BigDecimal cashBalance;
    
    

    //columns END

    public java.math.BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(java.math.BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public java.math.BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(java.math.BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("TicketId",getTicketId())
            .append("TicketType",getTicketType())
            .append("OpDate",getOpDate())
            .append("OpCode",getOpCode())
            .append("Amount",getAmount())
            .append("Balance",getBalance())
            .append("Note",getNote())
            .append("KeyId",getKeyId())
            .append("UpdName",getUpdName())
            .append("Key2",getKey2())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
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

	public java.lang.Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(java.lang.Long ticketId) {
		this.ticketId = ticketId;
	}

	public java.lang.Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(java.lang.Integer ticketType) {
		this.ticketType = ticketType;
	}

	public java.util.Date getOpDate() {
		return opDate;
	}

	public void setOpDate(java.util.Date opDate) {
		this.opDate = opDate;
	}

	public java.lang.String getOpCode() {
		return opCode;
	}

	public void setOpCode(java.lang.String opCode) {
		this.opCode = opCode;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	public java.math.BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(java.math.BigDecimal balance) {
		this.balance = balance;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.Long getKeyId() {
		return keyId;
	}

	public void setKeyId(java.lang.Long keyId) {
		this.keyId = keyId;
	}

	public java.lang.String getUpdName() {
		return updName;
	}

	public void setUpdName(java.lang.String updName) {
		this.updName = updName;
	}

	public java.lang.String getKey2() {
		return key2;
	}

	public void setKey2(java.lang.String key2) {
		this.key2 = key2;
	}

}
