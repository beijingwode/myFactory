package com.wode.factory.company.query;

import java.io.Serializable;

import com.wode.common.frame.base.BaseQuery;

/**
 * 企业福利流水vo
 * @author liangkq
 *
 */
public class SupplierTransferVo extends BaseQuery implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7274038888436742423L;

	private Long id;

    /**
     * 开始时间
     */
    private String startDate ;
    
    /**
     * 结束时间
     */
    private String endDate ;

    private java.lang.Long supplierId;
    /**
     * 财务代码       db_column: finance_code
     * 
     * 
     * 
     */ 
    private java.lang.String financeCode;
    /**
     * 金额       db_column: amount
     * 
     * 
     * 
     */ 
    private java.math.BigDecimal amount;
    /**
     * 状态 1:已申请/2:已确认/3:已转账/-1已拒绝       db_column: status
     * 
     * 
     * 
     */ 
    private java.lang.Integer status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public java.lang.Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}
	public java.lang.String getFinanceCode() {
		return financeCode;
	}
	public void setFinanceCode(java.lang.String financeCode) {
		this.financeCode = financeCode;
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

}
