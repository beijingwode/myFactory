package com.wode.factory.model;

import java.math.BigDecimal;
import java.util.Date;

public class EmpBenefitFlow {
    private Long id;

    private Long empId;

    private Date opDate;

    private String opCode;

    private BigDecimal ticket;

    private BigDecimal ticketBalance;

    private BigDecimal cash;

    private BigDecimal cashBalance;

    private String note;

    private String keyId;

    private String userName;

    private Long enterpriseId;

    private String empName;
    private String exBenefitType;
    
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

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode == null ? null : opCode.trim();
    }

    public BigDecimal getTicket() {
        return ticket;
    }

    public void setTicket(BigDecimal ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getTicketBalance() {
        return ticketBalance;
    }

    public void setTicketBalance(BigDecimal ticketBalance) {
        this.ticketBalance = ticketBalance;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

	public String getExBenefitType() {
		return exBenefitType;
	}

	public void setExBenefitType(String exBenefitType) {
		this.exBenefitType = exBenefitType;
	}
    
}