package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_ticket")
public class UserTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6967277899229924496L;
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
     * 券id       db_column: ticket_id
     * 
     * 
     * 
     */ 
    @Column("ticket_id")
    private java.lang.Long ticketId;
    /**
     * 余额       db_column: balance
     * 
     * 
     * 
     */ 
    @Column("balance")
    private java.math.BigDecimal balance;
    /**
     * 券使用期限       db_column: ticket_limit_date
     * 
     * 
     * 
     */ 
    @Column("ticket_limit_date")
    private java.util.Date ticketLimitDate;
    /**
     * 券类型 3:换领币       db_column: ticket_type
     * 
     * 
     * 
     */ 
    @Column("ticket_type")
    private java.lang.Integer ticketType;
    /**
     * 券使用说明       db_column: ticket_note
     * 
     * 
     * 
     */ 
    @Column("ticket_note")
    private java.lang.String ticketNote;
    /**
     * 扩展属性1       db_column: exp1
     * 
     * 
     * 
     */ 
    @Column("exp1")
    private java.lang.String exp1;
    /**
     * 扩展属性2       db_column: exp2
     * 
     * 
     * 
     */ 
    @Column("exp2")
    private java.lang.String exp2;
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
            .append("TicketId",getTicketId())
            .append("Balance",getBalance())
            .append("TicketLimitDate",getTicketLimitDate())
            .append("TicketType",getTicketType())
            .append("TicketNote",getTicketNote())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
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

	public java.lang.Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(java.lang.Long ticketId) {
		this.ticketId = ticketId;
	}

	public java.math.BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(java.math.BigDecimal balance) {
		this.balance = balance;
	}

	public java.util.Date getTicketLimitDate() {
		return ticketLimitDate;
	}

	public void setTicketLimitDate(java.util.Date ticketLimitDate) {
		this.ticketLimitDate = ticketLimitDate;
	}

	public java.lang.Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(java.lang.Integer ticketType) {
		this.ticketType = ticketType;
	}

	public java.lang.String getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(java.lang.String ticketNote) {
		this.ticketNote = ticketNote;
	}

	public java.lang.String getExp1() {
		return exp1;
	}

	public void setExp1(java.lang.String exp1) {
		this.exp1 = exp1;
	}

	public java.lang.String getExp2() {
		return exp2;
	}

	public void setExp2(java.lang.String exp2) {
		this.exp2 = exp2;
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

