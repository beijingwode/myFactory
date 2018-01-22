package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_sale_bill")
public class SaleBill  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	
	
	
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
     * billId  对账单id
     * 
     * 
     * 
     */	
	@Column("billId")
	private java.lang.String billId;
	
    /**
     * 商户id       db_column: supplierId  
     * 
     * 
     * 
     */	
	@Column("supplierId")
	private java.lang.Long supplierId;
    /**
     * 对账单标题：如： 2015-11-01至2015-11-31对账单       db_column: title  
     * 
     * 
     * @Length(max=200)
     */	
	@Column("title")
	private java.lang.String title;
    /**
     * 商户名称（商户的公司名称）       db_column: name  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("name")
	private java.lang.String name;
    /**
     * 账单开始日期       db_column: startTime  
     * 
     * 
     * 
     */	
	@Column("startTime")
	private java.util.Date startTime;
    /**
     * 账单结束日期       db_column: endTime  
     * 
     * 
     * 
     */	
	@Column("endTime")
	private java.util.Date endTime;
    /**
     * 代收总货款       db_column: receivePrice  
     * 
     * 
     * 
     */	
	@Column("receivePrice")
	private BigDecimal receivePrice;
    /**
     * 运费       db_column: carriagePrice  
     * 
     * 
     * 
     */	
	@Column("carriagePrice")
	private BigDecimal carriagePrice;
    /**
     * 佣金费用       db_column: commissionPrice  
     * 
     * 
     * 
     */	
	@Column("commissionPrice")
	private BigDecimal commissionPrice;
    /**
     * 扣除费用       db_column: deductPrice  
     * 
     * 
     * 
     */	
	@Column("deductPrice")
	private BigDecimal deductPrice;
    /**
     * 支付结算状态 0：未支付  1：已支付       db_column: payStatus  
     * 
     * 
     * 
     */	
	@Column("payStatus")
	private java.lang.Integer payStatus;
    /**
     * 本期需支付的总金额       db_column: payPrice  
     * 
     * 
     * 
     */	
	@Column("payPrice")
	private BigDecimal payPrice;
    /**
     * 付款日期       db_column: payTime  
     * 
     * 
     * 
     */	
	@Column("payTime")
	private java.util.Date payTime;
    /**
     * 福利币使用数量       db_column: fuCoin  
     * 
     * 
     * 
     */	
	@Column("fuCoin")
	private BigDecimal fuCoin;
    /**
     * 商家确认状态 0：未确认  1：已确认       db_column: confirmStatus  
     * 
     * 
     * 
     */	
	@Column("confirmStatus")
	private java.lang.Integer confirmStatus;
    /**
     * 商家确认时间       db_column: confirmTime  
     * 
     * 
     * 
     */	
	@Column("confirmTime")
	private java.util.Date confirmTime;
    /**
     * 发票开具状态 0：未开发票  1：已开发票       db_column: receiptStatus  
     * 
     * 
     * 
     */	
	@Column("receiptStatus")
	private java.lang.Integer receiptStatus;
    /**
     * 开票时间       db_column: receiptTime  
     * 
     * 
     * 
     */	
	@Column("receiptTime")
	private java.util.Date receiptTime;
    /**
     * 打印状态：0 :未打印   1：已打印       db_column: printStatus  
     * 
     * 
     * 
     */	
	@Column("printStatus")
	private java.lang.Integer printStatus;
    /**
     * 开票时间       db_column: printTime  
     * 
     * 
     * 
     */	
	@Column("printTime")
	private java.util.Date printTime;
    /**
     * 创建时间       db_column: createTime  
     * 
     * 
     * 
     */	
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * 创建人id       db_column: createUserid  
     * 
     * 
     * 
     */	
	@Column("createUserid")
	private java.lang.Long createUserid;
    /**
     * 是否删除  0：未删除   1：已删除       db_column: isDelete  
     * 
     * 
     * 
     */	
	@Column("isDelete")
	private java.lang.Long isDelete;
    /**
     * 发票号码       db_column: receipt_code  
     * 
     * 
     * 
     */	
	@Column("receipt_code")
	private java.lang.String receiptCode;
    /**
     * 发票号码       db_column: receipt_code  
     * 
     * 
     * 
     */	
	@Column("receipt_id")
	private java.lang.Long receiptId;
    /**
     * 发票号码       db_column: receipt_code  
     * 
     * 
     * 
     */	
	@Column("refund_id")
	private java.lang.Long refundId;
    /**
     * 发票号码       db_column: receipt_code  
     * 
     * 
     * 
     */	
	@Column("refund_amount")
	private BigDecimal refundAmount;
	@Column("close_type")
	private Integer closeType;					//结算内容 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金
	@Column("close_note")
	private String closeNote;					//备注、结算内容
	@Column("saleDurationKey")
	private java.lang.String saleDurationKey;	//对账单生成类别key
	@Column("relation_type")
	private Integer relationType;				//关联内容0:未关联 7:货款+运费+佣金 6:货款+运费 5:货款+佣金 4:货款 3:运费+佣金 2:运费 1:佣金
	@Column("relation_key")
	private java.lang.Long relationKey;			//关联key
	@Column("relation_date")
	private java.util.Date relationDate;		//关联日期
	@Column("relation_user")
	private java.lang.String relationUser;		//关联用户
	@Column("pay_type")
	private Integer payType;					//1:现金账户 2:其他方式
	@Column("pay_note")
	private String payNote;						//支付信息
	@Column("last_update_time1")
	private java.util.Date lastUpdateTime1;		//对账审核通过时间
	@Column("last_update_time2")
	private java.util.Date lastUpdateTime2;		//运营审核通过时间
	@Column("last_update_time3")
	private java.util.Date lastUpdateTime3;		//财务审核通过时间
	@Column("last_update_time4")
	private java.util.Date lastUpdateTime4;		//付款时间
	
	//columns END
	private BigDecimal cashPay;
	private String bankName;
	private String bankNo;


	private java.lang.String financeCode;
	
	public SaleBill(){
	}

	public SaleBill(
		java.lang.Long id
	){
		this.id = id;
	}

		
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
		
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
		
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
		
	public java.lang.String getBillId() {
		return billId;
	}

	public void setBillId(java.lang.String billId) {
		this.billId = billId;
	}

	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
		
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
		
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
		
	
	public BigDecimal getReceivePrice() {
		return receivePrice;
	}

	public void setReceivePrice(BigDecimal receivePrice) {
		this.receivePrice = receivePrice;
	}

	public BigDecimal getCarriagePrice() {
		return carriagePrice;
	}

	public void setCarriagePrice(BigDecimal carriagePrice) {
		this.carriagePrice = carriagePrice;
	}

	public BigDecimal getCommissionPrice() {
		return commissionPrice;
	}

	public void setCommissionPrice(BigDecimal commissionPrice) {
		this.commissionPrice = commissionPrice;
	}

	public BigDecimal getDeductPrice() {
		return deductPrice;
	}

	public void setDeductPrice(BigDecimal deductPrice) {
		this.deductPrice = deductPrice;
	}

	public java.lang.Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(java.lang.Integer payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public BigDecimal getFuCoin() {
		return fuCoin;
	}

	public void setFuCoin(BigDecimal fuCoin) {
		this.fuCoin = fuCoin;
	}

	public void setConfirmStatus(java.lang.Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public java.lang.Integer getConfirmStatus() {
		return this.confirmStatus;
	}
		
	public void setConfirmTime(java.util.Date value) {
		this.confirmTime = value;
	}
	
	public java.util.Date getConfirmTime() {
		return this.confirmTime;
	}
		
	public void setReceiptStatus(java.lang.Integer value) {
		this.receiptStatus = value;
	}
	
	public java.lang.Integer getReceiptStatus() {
		return this.receiptStatus;
	}
		
	public void setReceiptTime(java.util.Date value) {
		this.receiptTime = value;
	}
	
	public java.util.Date getReceiptTime() {
		return this.receiptTime;
	}
		
	public void setPrintStatus(java.lang.Integer value) {
		this.printStatus = value;
	}
	
	public java.lang.Integer getPrintStatus() {
		return this.printStatus;
	}
		
	public void setPrintTime(java.util.Date value) {
		this.printTime = value;
	}
	
	public java.util.Date getPrintTime() {
		return this.printTime;
	}
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
	public void setCreateUserid(java.lang.Long value) {
		this.createUserid = value;
	}
	
	public java.lang.Long getCreateUserid() {
		return this.createUserid;
	}
		
	public void setIsDelete(java.lang.Long value) {
		this.isDelete = value;
	}
	
	public java.lang.Long getIsDelete() {
		return this.isDelete;
	}

	public java.lang.String getReceiptCode() {
		return receiptCode;
	}

	public void setReceiptCode(java.lang.String receiptCode) {
		this.receiptCode = receiptCode;
	}

	public java.lang.Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(java.lang.Long receiptId) {
		this.receiptId = receiptId;
	}

	public java.lang.Long getRefundId() {
		return refundId;
	}

	public void setRefundId(java.lang.Long refundId) {
		this.refundId = refundId;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public java.lang.String getFinanceCode() {
		return financeCode;
	}

	public void setFinanceCode(java.lang.String financeCode) {
		this.financeCode = financeCode;
	}

	public BigDecimal getCashPay() {
		return cashPay;
	}

	public void setCashPay(BigDecimal cashPay) {
		this.cashPay = cashPay;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	public Integer getCloseType() {
		return closeType;
	}

	public void setCloseType(Integer closeType) {
		this.closeType = closeType;
	}

	public String getCloseNote() {
		return closeNote;
	}

	public void setCloseNote(String closeNote) {
		this.closeNote = closeNote;
	}

	public java.lang.String getSaleDurationKey() {
		return saleDurationKey;
	}

	public void setSaleDurationKey(java.lang.String saleDurationKey) {
		this.saleDurationKey = saleDurationKey;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public java.lang.Long getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(java.lang.Long relationKey) {
		this.relationKey = relationKey;
	}

	public java.util.Date getRelationDate() {
		return relationDate;
	}

	public void setRelationDate(java.util.Date relationDate) {
		this.relationDate = relationDate;
	}

	public java.lang.String getRelationUser() {
		return relationUser;
	}

	public void setRelationUser(java.lang.String relationUser) {
		this.relationUser = relationUser;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPayNote() {
		return payNote;
	}

	public void setPayNote(String payNote) {
		this.payNote = payNote;
	}

	public java.util.Date getLastUpdateTime1() {
		return lastUpdateTime1;
	}

	public void setLastUpdateTime1(java.util.Date lastUpdateTime1) {
		this.lastUpdateTime1 = lastUpdateTime1;
	}

	public java.util.Date getLastUpdateTime2() {
		return lastUpdateTime2;
	}

	public void setLastUpdateTime2(java.util.Date lastUpdateTime2) {
		this.lastUpdateTime2 = lastUpdateTime2;
	}

	public java.util.Date getLastUpdateTime3() {
		return lastUpdateTime3;
	}

	public void setLastUpdateTime3(java.util.Date lastUpdateTime3) {
		this.lastUpdateTime3 = lastUpdateTime3;
	}

	public java.util.Date getLastUpdateTime4() {
		return lastUpdateTime4;
	}

	public void setLastUpdateTime4(java.util.Date lastUpdateTime4) {
		this.lastUpdateTime4 = lastUpdateTime4;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SupplierId",getSupplierId())
			.append("Title",getTitle())
			.append("Name",getName())
			.append("StartTime",getStartTime())
			.append("EndTime",getEndTime())
			.append("ReceivePrice",getReceivePrice())
			.append("CarriagePrice",getCarriagePrice())
			.append("CommissionPrice",getCommissionPrice())
			.append("DeductPrice",getDeductPrice())
			.append("PayStatus",getPayStatus())
			.append("PayPrice",getPayPrice())
			.append("PayTime",getPayTime())
			.append("FuCoin",getFuCoin())
			.append("ConfirmStatus",getConfirmStatus())
			.append("ConfirmTime",getConfirmTime())
			.append("ReceiptStatus",getReceiptStatus())
			.append("ReceiptTime",getReceiptTime())
			.append("PrintStatus",getPrintStatus())
			.append("PrintTime",getPrintTime())
			.append("CreateTime",getCreateTime())
			.append("CreateUserid",getCreateUserid())
			.append("IsDelete",getIsDelete())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SaleBill == false) return false;
		if(this == obj) return true;
		SaleBill other = (SaleBill)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

