package com.wode.tongji.vo;

import java.io.Serializable;

/**
 * 
 * <pre>
 * 功能说明: 结算vo
 * 日期:	2015年3月10日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年3月10日
 * </pre>
 */
public class SubOrdersVo implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7904306292866808117L;
	/**
     * 月份       db_column: month  
     * 
     * 
     * 
     */
	private String month;
    /**
     * 销售总金额       db_column: total_sales_amount  
     * 
     * 
     * 
     */	
	private Long totalSalesAmount;
    /**
     * 佣金       db_column: commission  
     * 
     * 
     * 
     */	
	private Long commission;
    /**
     * 消费者保障金       db_column: buyer_security_amount  
     * 
     * 
     * 
     */
	private Long buyerSecurityAmount;
    /**
     * 运费       db_column: freight_price  
     * 
     * 
     * 
     */
	private Long freightPrice;
    /**
     * 交易总金额       db_column: total_deal_amount  
     * 
     * 
     * 
     */
	private Long totalDealAmount;
	
	private java.lang.Long cnt;


	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public void setTotalSalesAmount(Long totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public Long getCommission() {
		return commission;
	}

	public void setCommission(Long commission) {
		this.commission = commission;
	}

	public Long getBuyerSecurityAmount() {
		return buyerSecurityAmount;
	}

	public void setBuyerSecurityAmount(Long buyerSecurityAmount) {
		this.buyerSecurityAmount = buyerSecurityAmount;
	}

	public Long getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(Long freightPrice) {
		this.freightPrice = freightPrice;
	}

	public Long getTotalDealAmount() {
		return totalDealAmount;
	}

	public void setTotalDealAmount(Long totalDealAmount) {
		this.totalDealAmount = totalDealAmount;
	}

	public java.lang.Long getCnt() {
		return cnt;
	}

	public void setCnt(java.lang.Long cnt) {
		this.cnt = cnt;
	}

}
