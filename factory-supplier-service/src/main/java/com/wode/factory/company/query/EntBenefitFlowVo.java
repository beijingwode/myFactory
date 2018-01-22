package com.wode.factory.company.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.wode.common.frame.base.BaseQuery;

/**
 * 企业福利流水vo
 * @author liangkq
 *
 */
public class EntBenefitFlowVo extends BaseQuery implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7274038888436742423L;

	private Long id;

    /**
     * 企业id
     */
    private Long enterpriseId;
    
    /**
     * 操作时间
     */
    private Date opDate;

    /**
     * 操作代码
     */
    private String opCode;

    /**
     * 内购券
     */
    private BigDecimal ticket;

    /**
     * 内购券余额
     */
    private BigDecimal ticketBalance;

    /**
     * 现金
     */
    private BigDecimal cash;

    /**
     * 现金余额
     */
    private BigDecimal cashBalance;

    /**
     * 简要说明
     */
    private String note;

    /**
     * 关键字
     */
    private Long keyId;

    /**
     * 操作员姓名
     */
    private String userName;

    /**
     * 开始时间
     */
    private String startDate ;
    
    /**
     * 结束时间
     */
    private String endDate ;

    /**
     *  1：增加   -1 ：减少
     */
    private String value ;

    /**
     * 简称
     */
    private String name ;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
