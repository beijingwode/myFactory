package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SaleBillQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 商户id */
	private java.lang.Long supplierId;
	/** 对账单标题：如： 2015-11-01至2015-11-31对账单 */
	private java.lang.String title;
	/** 商户名称（商户的公司名称） */
	private java.lang.String name;
	/** 账单开始日期 */
	private java.util.Date startTime;
	/** 账单结束日期 */
	private java.util.Date endTime;
	/** 代收总货款 */
	private Long receivePrice;
	/** 运费 */
	private Long carriagePrice;
	/** 佣金费用 */
	private Long commissionPrice;
	/** 扣除费用 */
	private Long deductPrice;
	/** 支付结算状态 0：未支付  1：已支付 */
	private java.lang.Integer payStatus;
	/** 本期需支付的总金额 */
	private Long payPrice;
	/** 付款日期 */
	private java.util.Date payTimeBegin;
	private java.util.Date payTimeEnd;
	/** 福利币使用数量 */
	private java.lang.Long fuCoin;
	/** 商家确认状态 0：未确认  1：已确认 */
	private java.lang.Integer confirmStatus;
	/** 商家确认时间 */
	private java.util.Date confirmTimeBegin;
	private java.util.Date confirmTimeEnd;
	/** 发票开具状态 0：未开发票  1：已开发票 */
	private java.lang.Integer receiptStatus;
	/** 开票时间 */
	private java.util.Date receiptTimeBegin;
	private java.util.Date receiptTimeEnd;
	/** 打印状态：0 :未打印   1：已打印 */
	private java.lang.Integer printStatus;
	/** 开票时间 */
	private java.util.Date printTimeBegin;
	private java.util.Date printTimeEnd;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 创建人id */
	private java.lang.Long createUserid;
	/** 是否删除  0：未删除   1：已删除 */
	private java.lang.Long isDelete;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public Long getReceivePrice() {
		return this.receivePrice;
	}
	
	public void setReceivePrice(Long value) {
		this.receivePrice = value;
	}
	
	public Long getCarriagePrice() {
		return this.carriagePrice;
	}
	
	public void setCarriagePrice(Long value) {
		this.carriagePrice = value;
	}
	
	public Long getCommissionPrice() {
		return this.commissionPrice;
	}
	
	public void setCommissionPrice(Long value) {
		this.commissionPrice = value;
	}
	
	public Long getDeductPrice() {
		return this.deductPrice;
	}
	
	public void setDeductPrice(Long value) {
		this.deductPrice = value;
	}
	
	public java.lang.Integer getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(java.lang.Integer value) {
		this.payStatus = value;
	}
	
	public Long getPayPrice() {
		return this.payPrice;
	}
	
	public void setPayPrice(Long value) {
		this.payPrice = value;
	}
	
	public java.util.Date getPayTimeBegin() {
		return this.payTimeBegin;
	}
	
	public void setPayTimeBegin(java.util.Date value) {
		this.payTimeBegin = value;
	}	
	
	public java.util.Date getPayTimeEnd() {
		return this.payTimeEnd;
	}
	
	public void setPayTimeEnd(java.util.Date value) {
		this.payTimeEnd = value;
	}
	
	public java.lang.Long getFuCoin() {
		return this.fuCoin;
	}
	
	public void setFuCoin(java.lang.Long value) {
		this.fuCoin = value;
	}
	
	public java.lang.Integer getConfirmStatus() {
		return this.confirmStatus;
	}
	
	public void setConfirmStatus(java.lang.Integer value) {
		this.confirmStatus = value;
	}
	
	public java.util.Date getConfirmTimeBegin() {
		return this.confirmTimeBegin;
	}
	
	public void setConfirmTimeBegin(java.util.Date value) {
		this.confirmTimeBegin = value;
	}	
	
	public java.util.Date getConfirmTimeEnd() {
		return this.confirmTimeEnd;
	}
	
	public void setConfirmTimeEnd(java.util.Date value) {
		this.confirmTimeEnd = value;
	}
	
	public java.lang.Integer getReceiptStatus() {
		return this.receiptStatus;
	}
	
	public void setReceiptStatus(java.lang.Integer value) {
		this.receiptStatus = value;
	}
	
	public java.util.Date getReceiptTimeBegin() {
		return this.receiptTimeBegin;
	}
	
	public void setReceiptTimeBegin(java.util.Date value) {
		this.receiptTimeBegin = value;
	}	
	
	public java.util.Date getReceiptTimeEnd() {
		return this.receiptTimeEnd;
	}
	
	public void setReceiptTimeEnd(java.util.Date value) {
		this.receiptTimeEnd = value;
	}
	
	public java.lang.Integer getPrintStatus() {
		return this.printStatus;
	}
	
	public void setPrintStatus(java.lang.Integer value) {
		this.printStatus = value;
	}
	
	public java.util.Date getPrintTimeBegin() {
		return this.printTimeBegin;
	}
	
	public void setPrintTimeBegin(java.util.Date value) {
		this.printTimeBegin = value;
	}	
	
	public java.util.Date getPrintTimeEnd() {
		return this.printTimeEnd;
	}
	
	public void setPrintTimeEnd(java.util.Date value) {
		this.printTimeEnd = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	public void setCreateTimeEnd(java.util.Date value) {
		this.createTimeEnd = value;
	}
	
	public java.lang.Long getCreateUserid() {
		return this.createUserid;
	}
	
	public void setCreateUserid(java.lang.Long value) {
		this.createUserid = value;
	}
	
	public java.lang.Long getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsDelete(java.lang.Long value) {
		this.isDelete = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

