package com.wode.factory.company.query;

import java.math.BigDecimal;
import java.util.Date;

import com.wode.common.frame.base.BaseQuery;

public class EmpTradeFlowVo extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716156276447590610L;

	private Long id ;
	
	/**
	 * 企业ID
	 */
	private Long enterpriseId ;
	/**
	 * 员工id
	 */
	private Long empId ;
	
	/**
	 * 员工姓名
	 */
	private String empName ;
    private String exBenefitType;
	public String getExBenefitType() {
		return exBenefitType;
	}

	public void setExBenefitType(String exBenefitType) {
		this.exBenefitType = exBenefitType;
	}

	/**
	 * 
	 */
	private Date opDate ;
	/**
	 * 操作代码
	 */
	private String opCode;
	
	/**
	 * 操作名称
	 */
	private String opName ;
	
	/**
	 * 值：1 增加  -1 减少
	 */
	private String value ;
	
	/**
	 * 内购券
	 */
	private BigDecimal ticket ;
	
	/**
	 * 内购券余额
	 */
	private BigDecimal ticketBalance ;
	/**
	 * 订单总额
	 */
	private BigDecimal totalProduct;
	/**
	 * 现金
	 */
	private BigDecimal cash ;
	/**
	 * 现金余额
	 */
	private BigDecimal cashBalance ;
	/**
	 * 简介
	 */
	private String note ;
	/**
	 * 实付金额
	 */
	private BigDecimal realPrice ;
	
	/**
	 * 子订单号
	 */
	private String keyId ;
	
	private String userName;

    /**
     * 开始时间
     */
    private String startDate ;
    
    /**
     * 结束时间
     */
    private String endDate ;

	public Long getId() {
		return id;
	}

	public BigDecimal getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(BigDecimal totalProduct) {
		this.totalProduct = totalProduct;
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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BigDecimal getTicket() {
		return ticket;
	}

	public void setTicket(BigDecimal ticket) {
		this.ticket = ticket;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public String getSubOrderId() {
		return keyId;
	}

	public void setSubOrderId(String subOrderId) {
		this.keyId = subOrderId;
	}

	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		if("".equals(startDate)){
			this.startDate=null;
		}else{
			this.startDate = startDate;
		}
	}
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		if("".equals(endDate)){
			this.endDate=null;
		}else{
			this.endDate = endDate;
		}
	}
	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public BigDecimal getTicketBalance() {
		return ticketBalance;
	}

	public void setTicketBalance(BigDecimal ticketBalance) {
		this.ticketBalance = ticketBalance;
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
		this.note = note;
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
		this.userName = userName;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
}
