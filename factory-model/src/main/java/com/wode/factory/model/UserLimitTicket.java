package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_limit_ticket")
public class UserLimitTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1851172521355772992L;
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
     * 姓名       db_column: user_nickname
     * 
     * 
     * 
     */ 
    @Column("user_nickname")
    private java.lang.String userNickname;
    /**
     * 券id       db_column: limit_ticket_id
     * 
     * 
     * 
     */ 
    @Column("limit_ticket_id")
    private java.lang.Long limitTicketId;
    /**
     * 券是否一次使用 ：1:是，2：否       db_column: onece_flag
     * 
     * 
     * 
     */ 
    @Column("onece_flag")
    private java.lang.Integer oneceFlag;
    /**
     * 券类型 ：1:内购抵扣券，2：体验券，3:现金券       db_column: ticket_type
     * 
     * 
     * 
     */ 
    @Column("ticket_type")
    private java.lang.Integer ticketType;
    /**
     * 限制信息：1：通用，2：商品 pid_商品id，3：品类 cate_品类id       db_column: limit_key
     * 
     * 
     * 
     */ 
    @Column("limit_key")
    private java.lang.String limitKey;
    /**
     * 内购券总额       db_column: ticket_total
     * 
     * 
     * 
     */ 
    @Column("ticket_total")
    private java.math.BigDecimal ticketTotal;
    /**
     * 内购券余额       db_column: ticket_balance
     * 
     * 
     * 
     */ 
    @Column("ticket_balance")
    private java.math.BigDecimal ticketBalance;
    /**
     * 现金券总额       db_column: cash_total
     * 
     * 
     * 
     */ 
    @Column("cash_total")
    private java.math.BigDecimal cashTotal;
    /**
     * 现金券余额       db_column: cash_balance
     * 
     * 
     * 
     */ 
    @Column("cash_balance")
    private java.math.BigDecimal cashBalance;
    /**
     * 使用期限开始       db_column: limit_start
     * 
     * 
     * 
     */ 
    @Column("limit_start")
    private java.util.Date limitStart;
    /**
     * 使用期限结束       db_column: limit_end
     * 
     * 
     * 
     */ 
    @Column("limit_end")
    private java.util.Date limitEnd;
    /**
     * 券使用说明       db_column: ticket_note
     * 
     * 
     * 
     */ 
    @Column("ticket_note")
    private java.lang.String ticketNote;
    /**
     * 跳转路径       db_column: next_action
     * 
     * 
     * 
     */ 
    @Column("next_action")
    private java.lang.String nextAction;
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
     * 扩展属性3       db_column: exp3
     * 
     * 
     * 
     */ 
    @Column("exp3")
    private java.lang.String exp3;
    /**
     * 状态 0:未使用/1:部分使用 /2:已用完 /3:已过期/       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;
    /**
     * 更新者       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 限制类型：1：全场通用，2：商品，3：品类 ,
     */
    @Column("limit_type")
    private java.lang.Integer limitType;
    
    //columns END
    private List<SupplierLimitTicketSku> supplierLimitTicketSkuList;
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("UserNickname",getUserNickname())
            .append("LimitTicketId",getLimitTicketId())
            .append("OneceFlag",getOneceFlag())
            .append("TicketType",getTicketType())
            .append("LimitKey",getLimitKey())
            .append("TicketTotal",getTicketTotal())
            .append("TicketBalance",getTicketBalance())
            .append("CashTotal",getCashTotal())
            .append("CashBalance",getCashBalance())
            .append("LimitStart",getLimitStart())
            .append("LimitEnd",getLimitEnd())
            .append("TicketNote",getTicketNote())
            .append("NextAction",getNextAction())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
            .append("Exp3",getExp3())
            .append("Status",getStatus())
            .append("CreateDate",getCreateDate())
            .append("UpdateDate",getUpdateDate())
            .append("UpdateUser",getUpdateUser())
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

	public java.lang.String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(java.lang.String userNickname) {
		this.userNickname = userNickname;
	}

	public java.lang.Long getLimitTicketId() {
		return limitTicketId;
	}

	public void setLimitTicketId(java.lang.Long limitTicketId) {
		this.limitTicketId = limitTicketId;
	}

	public java.lang.Integer getOneceFlag() {
		return oneceFlag;
	}

	public void setOneceFlag(java.lang.Integer oneceFlag) {
		this.oneceFlag = oneceFlag;
	}

	public java.lang.Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(java.lang.Integer ticketType) {
		this.ticketType = ticketType;
	}

	public java.lang.String getLimitKey() {
		return limitKey;
	}

	public void setLimitKey(java.lang.String limitKey) {
		this.limitKey = limitKey;
	}

	public java.math.BigDecimal getTicketTotal() {
		return ticketTotal;
	}

	public void setTicketTotal(java.math.BigDecimal ticketTotal) {
		this.ticketTotal = ticketTotal;
	}

	public java.math.BigDecimal getTicketBalance() {
		return ticketBalance;
	}

	public void setTicketBalance(java.math.BigDecimal ticketBalance) {
		this.ticketBalance = ticketBalance;
	}

	public java.math.BigDecimal getCashTotal() {
		return cashTotal;
	}

	public void setCashTotal(java.math.BigDecimal cashTotal) {
		this.cashTotal = cashTotal;
	}

	public java.math.BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(java.math.BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public java.util.Date getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(java.util.Date limitStart) {
		this.limitStart = limitStart;
	}

	public java.util.Date getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(java.util.Date limitEnd) {
		this.limitEnd = limitEnd;
	}

	public java.lang.String getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(java.lang.String ticketNote) {
		this.ticketNote = ticketNote;
	}

	public java.lang.String getNextAction() {
		return nextAction;
	}

	public void setNextAction(java.lang.String nextAction) {
		this.nextAction = nextAction;
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

	public java.lang.String getExp3() {
		return exp3;
	}

	public void setExp3(java.lang.String exp3) {
		this.exp3 = exp3;
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

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.Long updateUser) {
		this.updateUser = updateUser;
	}
	
	
	public java.lang.Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(java.lang.Integer limitType) {
		this.limitType = limitType;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public List<SupplierLimitTicketSku> getSupplierLimitTicketSkuList() {
		return supplierLimitTicketSkuList;
	}

	public void setSupplierLimitTicketSkuList(List<SupplierLimitTicketSku> supplierLimitTicketSkuList) {
		this.supplierLimitTicketSkuList = supplierLimitTicketSkuList;
	}

}

