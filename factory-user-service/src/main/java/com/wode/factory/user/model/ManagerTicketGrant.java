package com.wode.factory.user.model;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_manager_ticket_grant")
public class ManagerTicketGrant extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4861623583598379938L;
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
     * 招商经理id       db_column: manager_id
     * 
     * 
     * 
     */ 
    @Column("manager_id")
    private java.lang.Long managerId;
    /**
     * 招商经理姓名       db_column: manager_name
     * 
     * 
     * 
     */ 
    @Column("manager_name")
    private java.lang.String managerName;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 使用货币id 0 现金券，1内购券       db_column: currency_id
     * 
     * 
     * 
     */ 
    @Column("currency_id")
    private java.lang.Integer currencyId;
    /**
     * 金额       db_column: amount
     * 
     * 
     * 
     */ 
    @Column("amount")
    private java.math.BigDecimal amount;
    /**
     * 状态 0 未领用 1已领用       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 用户昵称       db_column: user_name
     * 
     * 
     * 
     */ 
    @Column("user_name")
    private java.lang.String userName;
    /**
     * 所属企业id       db_column: user_company
     * 
     * 
     * 
     */ 
    @Column("user_company")
    private java.lang.Long userCompany;
    /**
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ManagerId",getManagerId())
            .append("ManagerName",getManagerName())
            .append("CreateDate",getCreateDate())
            .append("CurrencyId",getCurrencyId())
            .append("Amount",getAmount())
            .append("Status",getStatus())
            .append("UserId",getUserId())
            .append("UserName",getUserName())
            .append("UserCompany",getUserCompany())
            .append("UpdateDate",getUpdateDate())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getManagerId() {
		return managerId;
	}

	public void setManagerId(java.lang.Long managerId) {
		this.managerId = managerId;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(java.lang.Integer currencyId) {
		this.currencyId = currencyId;
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

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.Long getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(java.lang.Long userCompany) {
		this.userCompany = userCompany;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
