package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

/**
 * @author user
 *
 */
@Table("t_statistical_benefit")
public class StatisticalBenefit extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7554475103739658747L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */
    @Column("id")
    private java.lang.Long id;
    /**
     * 对象年月 yyyy-mm       db_column: month
     * 
     * 
     * 
     */ 
    @Column("month")
    private java.lang.String month;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 企业id       db_column: enterprise_id
     * 
     * 
     * 
     */ 
    @Column("enterprise_id")
    private java.lang.Long enterpriseId;
    /**
     * 企业名称       db_column: enterprise_name
     * 
     * 
     * 
     */ 
    @Column("enterprise_name")
    private java.lang.String enterpriseName;
    /**
     * 企业招商经理       db_column: enterprise_manager
     * 
     * 
     * 
     */ 
    	//设置一个企业名称
    private java.lang.String enterpriseManagerNamne;
    
    @Column("enterprise_manager")
    private java.lang.Long enterpriseManager;
    /**
     * 福利科目 1:生日礼金/2:过节费/空:其他       db_column: ex_benefit_type
     * 
     * 
     * 
     */ 
    @Column("ex_benefit_type")
    private java.lang.String exBenefitType;
    /**
     * 发放人次       db_column: emp_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_cnt")
    private java.lang.Integer empCnt;
    /**
     * 内购券总额       db_column: ticket_amount
     * 
     * 
     * 
     */ 
    @Column("ticket_amount")
    private java.math.BigDecimal ticketAmount;
    /**
     * 现金总额       db_column: cash_amount
     * 
     * 
     * 
     */ 
    @Column("cash_amount")
    private java.math.BigDecimal cashAmount;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Month",getMonth())
            .append("CreateTime",getCreateTime())
            .append("EnterpriseId",getEnterpriseId())
            .append("EnterpriseName",getEnterpriseName())
            .append("EnterpriseManager",getEnterpriseManager())
            .append("ExBenefitType",getExBenefitType())
            .append("EmpCnt",getEmpCnt())
            .append("TicketAmount",getTicketAmount())
            .append("CashAmount",getCashAmount())
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

	public java.lang.String getMonth() {
		return month;
	}

	public void setMonth(java.lang.String month) {
		this.month = month;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public java.lang.String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(java.lang.String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public java.lang.Long getEnterpriseManager() {
		return enterpriseManager;
	}

	public void setEnterpriseManager(java.lang.Long enterpriseManager) {
		this.enterpriseManager = enterpriseManager;
	}

	public java.lang.String getExBenefitType() {
		return exBenefitType;
	}

	public void setExBenefitType(java.lang.String exBenefitType) {
		this.exBenefitType = exBenefitType;
	}

	public java.lang.Integer getEmpCnt() {
		return empCnt;
	}

	public void setEmpCnt(java.lang.Integer empCnt) {
		this.empCnt = empCnt;
	}

	public java.math.BigDecimal getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(java.math.BigDecimal ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public java.math.BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(java.math.BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public java.lang.String getEnterpriseManagerNamne() {
		return enterpriseManagerNamne;
	}

	public void setEnterpriseManagerNamne(java.lang.String enterpriseManagerNamne) {
		this.enterpriseManagerNamne = enterpriseManagerNamne;
	}

}

