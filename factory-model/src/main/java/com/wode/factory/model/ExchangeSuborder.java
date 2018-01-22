package com.wode.factory.model;


import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_exchange_suborder")
public class ExchangeSuborder extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7154956947541963329L;
	//columns START
    /**
     * '子单号ID',       db_column: subOrderId
     * 
     * 
     * 
     */ 
    @Column("subOrderId")
    private java.lang.String subOrderId;
    /**
     * '母单号',       db_column: orderId
     * 
     * 
     * 
     */ 
    @Column("orderId")
    private java.lang.Long orderId;
    /**
     * supplierId       db_column: supplierId
     * 
     * 
     * 
     */ 
    @Column("supplierId")
    private java.lang.Long supplierId;
    /**
     * '店铺id',       db_column: shop_id
     * 
     * 
     * 
     */ 
    @Column("shop_id")
    private java.lang.Long shopId;
    /**
     * '商品总价',       db_column: totalProduct
     * 
     * 
     * 
     */ 
    @Column("totalProduct")
    private java.math.BigDecimal totalProduct;
    /**
     * '运费',       db_column: totalShipping
     * 
     * 
     * 
     */ 
    @Column("totalShipping")
    private java.math.BigDecimal totalShipping;
    /**
     * '折扣',       db_column: totalAdjustment
     * 
     * 
     * 
     */ 
    @Column("totalAdjustment")
    private java.math.BigDecimal totalAdjustment;
    /**
     * '实付金额',       db_column: realPrice
     * 
     * 
     * 
     */ 
    @Column("realPrice")
    private java.math.BigDecimal realPrice;
    /**
     * '状态：0未支付，1已支付，2已发货，3退货退款申请中，4已收货，5仅退款申请中，11已退货退款完毕，12已仅退款完成，-1已关闭',       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * '物流单号',       db_column: expressNo
     * 
     * 
     * 
     */ 
    @Column("expressNo")
    private java.lang.String expressNo;
    /**
     * '物流类型',       db_column: expressType
     * 
     * 
     * 
     */ 
    @Column("expressType")
    private java.lang.String expressType;
    /**
     * '创建时间',       db_column: createTime
     * 
     * 
     * 
     */ 
    @Column("createTime")
    private java.util.Date createTime;
    /**
     * '更新时间',       db_column: updateTime
     * 
     * 
     * 
     */ 
    @Column("updateTime")
    private java.util.Date updateTime;
    /**
     * '修改者',       db_column: updateBy
     * 
     * 
     * 
     */ 
    @Column("updateBy")
    private java.lang.String updateBy;
    /**
     * '发货地址',       db_column: sendAddress
     * 
     * 
     * 
     */ 
    @Column("sendAddress")
    private java.lang.String sendAddress;
    /**
     * '退货地址',       db_column: returnedAddress
     * 
     * 
     * 
     */ 
    @Column("returnedAddress")
    private java.lang.String returnedAddress;
    /**
     * '售后服务状态  默认 null   ，1：退款申请中，  2：退款成功',       db_column: afterserviceStatus
     * 
     * 
     * 
     */ 
    @Column("afterserviceStatus")
    private java.lang.Integer afterserviceStatus;
    /**
     * '评价状态  ：默认null    0：未评价  1：已评价',       db_column: commentStatus
     * 
     * 
     * 
     */ 
    @Column("commentStatus")
    private java.lang.Integer commentStatus;
    /**
     * '收获时间',       db_column: takeTime
     * 
     * 
     * 
     */ 
    @Column("takeTime")
    private java.util.Date takeTime;
    /**
     * '用户（非商家）延长收货的次数（默认为0次，即没有延长过收货）',       db_column: userExetendCount
     * 
     * 
     * 
     */ 
    @Column("userExetendCount")
    private java.lang.Integer userExetendCount;
    /**
     * '最后确认时间',       db_column: lasttakeTime
     * 
     * 
     * 
     */ 
    @Column("lasttakeTime")
    private java.util.Date lasttakeTime;
    /**
     * '发货时间',       db_column: sendTime
     * 
     * 
     * 
     */ 
    @Column("sendTime")
    private java.util.Date sendTime;
    /**
     * payTime       db_column: payTime
     * 
     * 
     * 
     */ 
    @Column("payTime")
    private java.util.Date payTime;
    /**
     * '订单关闭理由',       db_column: closeReason
     * 
     * 
     * 
     */ 
    @Column("closeReason")
    private java.lang.String closeReason;
    /**
     * '催促次数',       db_column: urgeNumber
     * 
     * 
     * 
     */ 
    @Column("urgeNumber")
    private int urgeNumber;
    /**
     * '催促时间',       db_column: urgeTime
     * 
     * 
     * 
     */ 
    @Column("urgeTime")
    private java.util.Date urgeTime;
    /**
     * '删除标识',       db_column: deleteFlag
     * 
     * 
     * 
     */ 
    @Column("deleteFlag")
    private boolean deleteFlag;
    /**
     * '退货单号',       db_column: returnOrderId
     * 
     * 
     * 
     */ 
    @Column("returnOrderId")
    private java.lang.Long returnOrderId;
    /**
     * '仅退款单ID',       db_column: refundOrderId
     * 
     * 
     * 
     */ 
    @Column("refundOrderId")
    private java.lang.Long refundOrderId;
    /**
     * '商家拒绝退款的原因备注',       db_column: refuseNote
     * 
     * 
     * 
     */ 
    @Column("refuseNote")
    private java.lang.String refuseNote;
    /**
     * '结算状态 0:未结算/2:已结算',       db_column: close_flg
     * 
     * 
     * 
     */ 
    @Column("close_flg")
    private java.lang.String closeFlg;
    /**
     * '结算单ID',       db_column: sale_bill_id
     * 
     * 
     * 
     */ 
    @Column("sale_bill_id")
    private java.lang.Long saleBillId;
    /**
     * '现金券抵扣金额',       db_column: cashPay
     * 
     * 
     * 
     */ 
    @Column("cashPay")
    private java.math.BigDecimal cashPay;
    /**
     * '现金券抵扣流水号',       db_column: cashNo
     * 
     * 
     * 
     */ 
    @Column("cashNo")
    private java.lang.String cashNo;
    /**
     * '支付金额',       db_column: thirdPay
     * 
     * 
     * 
     */ 
    @Column("thirdPay")
    private java.math.BigDecimal thirdPay;
    /**
     * '支付方式',       db_column: thirdType
     * 
     * 
     * 
     */ 
    @Column("thirdType")
    private java.lang.String thirdType;
    /**
     * '支付流水号',       db_column: thirdNo
     * 
     * 
     * 
     */ 
    @Column("thirdNo")
    private java.lang.String thirdNo;
    /**
     * '财务到账确认',       db_column: pay_confirm
     * 
     * 
     * 
     */ 
    @Column("pay_confirm")
    private java.lang.Integer payConfirm;
    /**
     * '到账确认时间',       db_column: pay_confirm_date
     * 
     * 
     * 
     */ 
    @Column("pay_confirm_date")
    private java.util.Date payConfirmDate;
    /**
     * '订单取消时间',       db_column: cancelTime
     * 
     * 
     * 
     */ 
    @Column("cancelTime")
    private java.util.Date cancelTime;
    /**
     * '备货中标识 1:备货中',       db_column: stock_up
     * 
     * 
     * 
     */ 
    @Column("stock_up")
    private java.lang.Integer stockUp;
    /**
     * '优惠类型 3:换购券',       db_column: benefit_type
     * 
     * 
     * 
     */ 
    @Column("benefit_type")
    private java.lang.Integer benefitType;
    /**
     * '优惠金额',       db_column: benefit_amount
     * 
     * 
     * 
     */ 
    @Column("benefit_amount")
    private java.math.BigDecimal benefitAmount;
    /**
     * '内购券使用量',       db_column: companyTicket
     * 
     * 
     * 
     */ 
    @Column("companyTicket")
    private java.math.BigDecimal companyTicket;
    /**
     * '备注',       db_column: noto
     * 
     * 
     * 
     */ 
    @Column("noto")
    private java.lang.String noto;
    /**
     * '发票项目',       db_column: invoice_item
     * 
     * 
     * 
     */ 
    @Column("invoice_item")
    private java.lang.String invoiceItem;
    /**
     * '发票状态 0.未开 1.用户申请 2.已开票',       db_column: invoice_status
     * 
     * 
     * 
     */ 
    @Column("invoice_status")
    private java.lang.Integer invoiceStatus;
    /**
     * '电子卡券内容：{url:page,pw:卡券密码}',       db_column: e_card_info
     * 
     * 
     * 
     */ 
    @Column("e_card_info")
    private java.lang.String eCardInfo;
    /**
     * '用户ID 同订单',       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * '状态：1匹配中，2匹配成功，3匹配失败 已退款，4匹配失败 调剂成功，-1已关闭',       db_column: exchange_status
     * 
     * 
     * 
     */ 
    @Column("exchange_status")
    private java.lang.Integer exchangeStatus;
    /**
     * '关联id 子单id',       db_column: relation_id
     * 
     * 
     * 
     */ 
    @Column("relation_id")
    private java.lang.String relationId;
    /**
     * '关联id 子单id',       db_column: free_swap
     * 
     * 
     * 
     */ 
    @Column("free_swap")
    private java.lang.String freeSwap;
    /**
     * 批处理id,       db_column: batch_id
     * 
     * 
     * 
     */ 
    @Column("batch_id")
    private java.lang.Long batchId;
    /**
     * 批处理id,       db_column: batch_id
     * 
     * 
     * 
     */ 
    @Column("limit_ticket_cnt")
    private java.lang.Integer limitTicketCnt;
    //columns END
	private String supplierName;//供应商名称
	@Column("product_name")
	private String productName; //商品名称
	private String managerName; //招商经理
	private String enterpriseName;//企业名称
	private List<ExchangeSuborderitem> subOrderItems;
	public List<ExchangeSuborderitem> getSubOrderItems() {
		return subOrderItems;
	}

	public void setSubOrderItems(List<ExchangeSuborderitem> subOrderItems) {
		this.subOrderItems = subOrderItems;
	}
	
    public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("SubOrderId",getSubOrderId())
            .append("OrderId",getOrderId())
            .append("SupplierId",getSupplierId())
            .append("ShopId",getShopId())
            .append("TotalProduct",getTotalProduct())
            .append("TotalShipping",getTotalShipping())
            .append("TotalAdjustment",getTotalAdjustment())
            .append("RealPrice",getRealPrice())
            .append("Status",getStatus())
            .append("ExpressNo",getExpressNo())
            .append("ExpressType",getExpressType())
            .append("CreateTime",getCreateTime())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateBy",getUpdateBy())
            .append("SendAddress",getSendAddress())
            .append("ReturnedAddress",getReturnedAddress())
            .append("AfterserviceStatus",getAfterserviceStatus())
            .append("CommentStatus",getCommentStatus())
            .append("TakeTime",getTakeTime())
            .append("UserExetendCount",getUserExetendCount())
            .append("LasttakeTime",getLasttakeTime())
            .append("SendTime",getSendTime())
            .append("PayTime",getPayTime())
            .append("CloseReason",getCloseReason())
            .append("UrgeNumber",getUrgeNumber())
            .append("UrgeTime",getUrgeTime())
            .append("ReturnOrderId",getReturnOrderId())
            .append("RefundOrderId",getRefundOrderId())
            .append("RefuseNote",getRefuseNote())
            .append("CloseFlg",getCloseFlg())
            .append("SaleBillId",getSaleBillId())
            .append("CashPay",getCashPay())
            .append("CashNo",getCashNo())
            .append("ThirdPay",getThirdPay())
            .append("ThirdType",getThirdType())
            .append("ThirdNo",getThirdNo())
            .append("PayConfirm",getPayConfirm())
            .append("PayConfirmDate",getPayConfirmDate())
            .append("CancelTime",getCancelTime())
            .append("StockUp",getStockUp())
            .append("BenefitType",getBenefitType())
            .append("BenefitAmount",getBenefitAmount())
            .append("CompanyTicket",getCompanyTicket())
            .append("Noto",getNoto())
            .append("InvoiceItem",getInvoiceItem())
            .append("InvoiceStatus",getInvoiceStatus())
            .append("ECardInfo",getECardInfo())
            .append("UserId",getUserId())
            .append("ExchangeStatus",getExchangeStatus())
            .append("RelationId",getRelationId())
            .toString();
    }

    public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public java.lang.Long getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public java.math.BigDecimal getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(java.math.BigDecimal totalProduct) {
		this.totalProduct = totalProduct;
	}

	public java.math.BigDecimal getTotalShipping() {
		return totalShipping;
	}

	public void setTotalShipping(java.math.BigDecimal totalShipping) {
		this.totalShipping = totalShipping;
	}

	public java.math.BigDecimal getTotalAdjustment() {
		return totalAdjustment;
	}

	public void setTotalAdjustment(java.math.BigDecimal totalAdjustment) {
		this.totalAdjustment = totalAdjustment;
	}

	public java.math.BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(java.math.BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(java.lang.String expressNo) {
		this.expressNo = expressNo;
	}

	public java.lang.String getExpressType() {
		return expressType;
	}

	public void setExpressType(java.lang.String expressType) {
		this.expressType = expressType;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	public java.lang.String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(java.lang.String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public java.lang.String getReturnedAddress() {
		return returnedAddress;
	}

	public void setReturnedAddress(java.lang.String returnedAddress) {
		this.returnedAddress = returnedAddress;
	}

	public java.lang.Integer getAfterserviceStatus() {
		return afterserviceStatus;
	}

	public void setAfterserviceStatus(java.lang.Integer afterserviceStatus) {
		this.afterserviceStatus = afterserviceStatus;
	}

	public java.lang.Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(java.lang.Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	public java.util.Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(java.util.Date takeTime) {
		this.takeTime = takeTime;
	}

	public java.lang.Integer getUserExetendCount() {
		return userExetendCount;
	}

	public void setUserExetendCount(java.lang.Integer userExetendCount) {
		this.userExetendCount = userExetendCount;
	}

	public java.util.Date getLasttakeTime() {
		return lasttakeTime;
	}

	public void setLasttakeTime(java.util.Date lasttakeTime) {
		this.lasttakeTime = lasttakeTime;
	}

	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public java.lang.String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(java.lang.String closeReason) {
		this.closeReason = closeReason;
	}

	public int getUrgeNumber() {
		return urgeNumber;
	}

	public void setUrgeNumber(int urgeNumber) {
		this.urgeNumber = urgeNumber;
	}

	public java.util.Date getUrgeTime() {
		return urgeTime;
	}

	public void setUrgeTime(java.util.Date urgeTime) {
		this.urgeTime = urgeTime;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public java.lang.Long getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(java.lang.Long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public java.lang.Long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(java.lang.Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public java.lang.String getRefuseNote() {
		return refuseNote;
	}

	public void setRefuseNote(java.lang.String refuseNote) {
		this.refuseNote = refuseNote;
	}

	public java.lang.String getCloseFlg() {
		return closeFlg;
	}

	public void setCloseFlg(java.lang.String closeFlg) {
		this.closeFlg = closeFlg;
	}

	public java.lang.Long getSaleBillId() {
		return saleBillId;
	}

	public void setSaleBillId(java.lang.Long saleBillId) {
		this.saleBillId = saleBillId;
	}

	public java.math.BigDecimal getCashPay() {
		return cashPay;
	}

	public void setCashPay(java.math.BigDecimal cashPay) {
		this.cashPay = cashPay;
	}

	public java.lang.String getCashNo() {
		return cashNo;
	}

	public void setCashNo(java.lang.String cashNo) {
		this.cashNo = cashNo;
	}

	public java.math.BigDecimal getThirdPay() {
		return thirdPay;
	}

	public void setThirdPay(java.math.BigDecimal thirdPay) {
		this.thirdPay = thirdPay;
	}

	public java.lang.String getThirdType() {
		return thirdType;
	}

	public void setThirdType(java.lang.String thirdType) {
		this.thirdType = thirdType;
	}

	public java.lang.String getThirdNo() {
		return thirdNo;
	}

	public void setThirdNo(java.lang.String thirdNo) {
		this.thirdNo = thirdNo;
	}

	public java.lang.Integer getPayConfirm() {
		return payConfirm;
	}

	public void setPayConfirm(java.lang.Integer payConfirm) {
		this.payConfirm = payConfirm;
	}

	public java.util.Date getPayConfirmDate() {
		return payConfirmDate;
	}

	public void setPayConfirmDate(java.util.Date payConfirmDate) {
		this.payConfirmDate = payConfirmDate;
	}

	public java.util.Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(java.util.Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public java.lang.Integer getStockUp() {
		return stockUp;
	}

	public void setStockUp(java.lang.Integer stockUp) {
		this.stockUp = stockUp;
	}

	public java.lang.Integer getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(java.lang.Integer benefitType) {
		this.benefitType = benefitType;
	}

	public java.math.BigDecimal getBenefitAmount() {
		return benefitAmount;
	}

	public void setBenefitAmount(java.math.BigDecimal benefitAmount) {
		this.benefitAmount = benefitAmount;
	}

	public java.math.BigDecimal getCompanyTicket() {
		return companyTicket;
	}

	public void setCompanyTicket(java.math.BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
	}

	public java.lang.String getNoto() {
		return noto;
	}

	public void setNoto(java.lang.String noto) {
		this.noto = noto;
	}

	public java.lang.String getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(java.lang.String invoiceItem) {
		this.invoiceItem = invoiceItem;
	}

	public java.lang.Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(java.lang.Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public java.lang.String getECardInfo() {
		return eCardInfo;
	}

	public void setECardInfo(java.lang.String eCardInfo) {
		this.eCardInfo = eCardInfo;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Integer getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(java.lang.Integer exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public java.lang.String getRelationId() {
		return relationId;
	}

	public void setRelationId(java.lang.String relationId) {
		this.relationId = relationId;
	}

	public java.lang.String getFreeSwap() {
		return freeSwap;
	}

	public void setFreeSwap(java.lang.String freeSwap) {
		this.freeSwap = freeSwap;
	}

	public java.lang.Long getBatchId() {
		return batchId;
	}

	public void setBatchId(java.lang.Long batchId) {
		this.batchId = batchId;
	}
	
	public java.lang.Integer getLimitTicketCnt() {
		return limitTicketCnt;
	}

	public void setLimitTicketCnt(java.lang.Integer limitTicketCnt) {
		this.limitTicketCnt = limitTicketCnt;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getSubOrderId())
            .toHashCode();
    }

}

