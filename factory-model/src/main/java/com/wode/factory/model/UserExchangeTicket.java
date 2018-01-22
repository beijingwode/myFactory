package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_exchange_ticket")
public class UserExchangeTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8358558329413435585L;
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
     * 换领商品id       db_column: exchange_product_id
     * 
     * 
     * 
     */ 
    @Column("exchange_product_id")
    private java.lang.Long exchangeProductId;
    /**
     * 员工id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 姓名       db_column: nickname
     * 
     * 
     * 
     */ 
    @Column("nickname")
    private java.lang.String nickname;
    /**
     * 职务       db_column: duty
     * 
     * 
     * 
     */ 
    @Column("duty")
    private java.lang.String duty;
    /**
     * 分配换领商品个数       db_column: emp_avg_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_avg_cnt")
    private java.math.BigDecimal empAvgCnt;
    /**
     * 换领币总额       db_column: emp_avg_amount
     * 
     * 
     * 
     */ 
    @Column("emp_avg_amount")
    private java.math.BigDecimal empAvgAmount;
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
     * 状态 0:未使用/1:部分使用 /2:已用完 /3:已过期/4:已折现/6:线下换领       db_column: status
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
     * 已激活金额       db_column: active_amount
     * 
     * 
     * 
     */ 
    @Column("active_amount")
    private java.math.BigDecimal activeAmount;
    /**
     * 已使用金额       db_column: used_amount
     * 
     * 
     * 
     */ 
    @Column("used_amount")
    private java.math.BigDecimal usedAmount;
    /**
     * 已使用现金       db_column: used_active
     * 
     * 
     * 
     */ 
    @Column("used_active")
    private java.math.BigDecimal usedActive;
    /**
     * 使用记录       db_column: used_note
     * 
     * 
     * 
     */ 
    @Column("used_note")
    private java.lang.String usedNote;
    /**
     * 剩余商品数       db_column: left_cnt
     * 
     * 
     * 
     */ 
    @Column("left_cnt")
    private java.math.BigDecimal leftCnt;
    /**
     * 预付金额       db_column: prepay_amount
     * 
     * 
     * 
     */ 
    @Column("prepay_amount")
    private java.math.BigDecimal prepayAmount;
    /**
     * 券使用说明       db_column: ticket_note
     * 
     * 
     * 
     */ 
    @Column("ticket_note")
    private java.lang.String ticketNote;
    /**
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 商品标题       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;

    //columns END
    private String phone;
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ExchangeProductId",getExchangeProductId())
            .append("UserId",getUserId())
            .append("Nickname",getNickname())
            .append("Duty",getDuty())
            .append("EmpAvgCnt",getEmpAvgCnt())
            .append("EmpAvgAmount",getEmpAvgAmount())
            .append("LimitStart",getLimitStart())
            .append("LimitEnd",getLimitEnd())
            .append("Status",getStatus())
            .append("CreateDate",getCreateDate())
            .append("UpdateDate",getUpdateDate())
            .append("UpdateUser",getUpdateUser())
            .append("ActiveAmount",getActiveAmount())
            .append("UsedAmount",getUsedAmount())
            .append("UsedActive",getUsedActive())
            .append("UsedNote",getUsedNote())
            .append("LeftCnt",getLeftCnt())
            .append("PrepayAmount",getPrepayAmount())
            .append("TicketNote",getTicketNote())
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

	public java.lang.Long getExchangeProductId() {
		return exchangeProductId;
	}

	public void setExchangeProductId(java.lang.Long exchangeProductId) {
		this.exchangeProductId = exchangeProductId;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.String getNickname() {
		return nickname;
	}

	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}

	public java.lang.String getDuty() {
		return duty;
	}

	public void setDuty(java.lang.String duty) {
		this.duty = duty;
	}

	public java.math.BigDecimal getEmpAvgCnt() {
		return empAvgCnt;
	}

	public void setEmpAvgCnt(java.math.BigDecimal empAvgCnt) {
		this.empAvgCnt = empAvgCnt;
	}

	public java.math.BigDecimal getEmpAvgAmount() {
		return empAvgAmount;
	}

	public void setEmpAvgAmount(java.math.BigDecimal empAvgAmount) {
		this.empAvgAmount = empAvgAmount;
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

	public java.math.BigDecimal getActiveAmount() {
		return activeAmount;
	}

	public void setActiveAmount(java.math.BigDecimal activeAmount) {
		this.activeAmount = activeAmount;
	}

	public java.math.BigDecimal getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(java.math.BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	public java.math.BigDecimal getUsedActive() {
		return usedActive;
	}

	public void setUsedActive(java.math.BigDecimal usedActive) {
		this.usedActive = usedActive;
	}

	public java.lang.String getUsedNote() {
		return usedNote;
	}

	public void setUsedNote(java.lang.String usedNote) {
		this.usedNote = usedNote;
	}

	public java.math.BigDecimal getLeftCnt() {
		return leftCnt;
	}

	public void setLeftCnt(java.math.BigDecimal leftCnt) {
		this.leftCnt = leftCnt;
	}

	public java.math.BigDecimal getPrepayAmount() {
		return prepayAmount;
	}

	public void setPrepayAmount(java.math.BigDecimal prepayAmount) {
		this.prepayAmount = prepayAmount;
	}

	public java.lang.String getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(java.lang.String ticketNote) {
		this.ticketNote = ticketNote;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}

