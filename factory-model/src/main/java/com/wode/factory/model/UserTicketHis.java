package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_ticket_his")
public class UserTicketHis extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7691361074806225570L;
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
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
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
     * 券id       db_column: ticket_id
     * 
     * 
     * 
     */ 
    @Column("ticket_id")
    private java.lang.Long ticketId;
    /**
     * 额度       db_column: ticket
     * 
     * 
     * 
     */ 
    @Column("ticket")
    private java.math.BigDecimal ticket;
    /**
     * 操作后余额       db_column: ticket_balance
     * 
     * 
     * 
     */ 
    @Column("ticket_balance")
    private java.math.BigDecimal ticketBalance;
    /**
     * 说明       db_column: note
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
     * 操作员工姓名       db_column: user_name
     * 
     * 
     * 
     */ 
    @Column("user_name")
    private java.lang.String userName;
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("OpDate",getOpDate())
            .append("OpCode",getOpCode())
            .append("TicketId",getTicketId())
            .append("Ticket",getTicket())
            .append("TicketBalance",getTicketBalance())
            .append("Note",getNote())
            .append("KeyId",getKeyId())
            .append("UserName",getUserName())
            .append("SupplierId",getSupplierId())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
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

	public java.lang.Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(java.lang.Long ticketId) {
		this.ticketId = ticketId;
	}

	public java.math.BigDecimal getTicket() {
		return ticket;
	}

	public void setTicket(java.math.BigDecimal ticket) {
		this.ticket = ticket;
	}

	public java.math.BigDecimal getTicketBalance() {
		return ticketBalance;
	}

	public void setTicketBalance(java.math.BigDecimal ticketBalance) {
		this.ticketBalance = ticketBalance;
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

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

