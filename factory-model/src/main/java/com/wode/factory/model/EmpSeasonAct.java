package com.wode.factory.model;

import java.math.BigDecimal;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;

import com.wode.common.stereotype.PrimaryKey;

public class EmpSeasonAct {

	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;

    /**
     * 员工id      db_column: emp_id  
     * 
     */	
	@Column("emp_id")
	private Long empId;

    /**
     * 已发放内购券总额      db_column: give_ticket_sum  
     * 
     */	
	@Column("give_ticket_sum")
	private BigDecimal giveTicketSum;

    /**
     * 已发放现金总额      db_column: give_cash_sum  
     * 
     */	
	@Column("give_cash_sum")
	private BigDecimal giveCashSum;

    /**
     * 年度     db_column: cur_year  
     * 
     */	
	@Column("cur_year")
	private String curYear;

    /**
     * 季度     db_column: cur_season  
     * 
     */	
	@Column("cur_season")
	private String curSeason;

    /**
     * 创建时间     db_column: create_date  
     * 
     */	
	@Column("create_date")
	private Date createDate;

    /**
     * 创建时间     db_column: update_date  
     * 
     */	
	@Column("update_date")
	private Date updateDate;

    /**
     * 更新者姓名    db_column: update_user  
     * 
     */	
	@Column("update_user")
	private String updateUser;

    /**
     * 企业id  db_column: enterprise_id  
     * 
     */	
	@Column("enterprise_id")
	private Long enterpriseId;
	
	//columns END
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public BigDecimal getGiveTicketSum() {
        return giveTicketSum;
    }

    public void setGiveTicketSum(BigDecimal giveTicketSum) {
        this.giveTicketSum = giveTicketSum;
    }

    public BigDecimal getGiveCashSum() {
        return giveCashSum;
    }

    public void setGiveCashSum(BigDecimal giveCashSum) {
        this.giveCashSum = giveCashSum;
    }

    public String getCurYear() {
        return curYear;
    }

    public void setCurYear(String curYear) {
        this.curYear = curYear == null ? null : curYear.trim();
    }

    public String getCurSeason() {
        return curSeason;
    }

    public void setCurSeason(String curSeason) {
        this.curSeason = curSeason == null ? null : curSeason.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}