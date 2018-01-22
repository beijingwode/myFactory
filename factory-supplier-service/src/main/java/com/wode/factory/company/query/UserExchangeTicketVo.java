package com.wode.factory.company.query;

import java.math.BigDecimal;
import java.util.Date;

public class UserExchangeTicketVo{

    private String nickname;//姓名
    private String duty;//职务
    private BigDecimal empAvgCnt;//商品个数
    private BigDecimal empAvgAmount;//换领币总额
    private Date createDate;//创建时间
    private BigDecimal usedAmount;//已消费金额
    private String usedNote;//使用记录
    private String phone;//电话
    private String sectionName;//部门
    private BigDecimal activeAmount;//已激活金额
    private BigDecimal prepayAmount;//预付金额
    private BigDecimal leftCnt;//剩余商品数
    
    
	public BigDecimal getLeftCnt() {
		return leftCnt;
	}
	public void setLeftCnt(BigDecimal leftCnt) {
		this.leftCnt = leftCnt;
	}
	public BigDecimal getPrepayAmount() {
		return prepayAmount;
	}
	public void setPrepayAmount(BigDecimal prepayAmount) {
		this.prepayAmount = prepayAmount;
	}
	public BigDecimal getActiveAmount() {
		return activeAmount;
	}
	public void setActiveAmount(BigDecimal activeAmount) {
		this.activeAmount = activeAmount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public BigDecimal getEmpAvgCnt() {
		return empAvgCnt;
	}
	public void setEmpAvgCnt(BigDecimal empAvgCnt) {
		this.empAvgCnt = empAvgCnt;
	}
	public BigDecimal getEmpAvgAmount() {
		return empAvgAmount;
	}
	public void setEmpAvgAmount(BigDecimal empAvgAmount) {
		this.empAvgAmount = empAvgAmount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public BigDecimal getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}
	public String getUsedNote() {
		return usedNote;
	}
	public void setUsedNote(String usedNote) {
		this.usedNote = usedNote;
	}
    
    

}

