/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_suborder")
public class Suborder extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Suborder";
	
	public static final String ALIAS_SUB_ORDER_ID = "子单号ID";
	
	public static final String ALIAS_ORDER_ID = "母单号";
	
	public static final String ALIAS_SUPPLIER_ID = "supplierId";
	
	public static final String ALIAS_TOTAL_PRODUCT = "总价";
	
	public static final String ALIAS_TOTAL_SHIPPING = "运费";
	
	public static final String ALIAS_TOTAL_ADJUSTMENT = "折扣";
	
	public static final String ALIAS_REAL_PRICE = "实付金额";
	
	public static final String ALIAS_STATUS = "状态";//0未支付，1已支付，2已发货  3退单申请中，4已收货  10买家已评价  11已退货完毕 -1已取消
	
	public static final String ALIAS_EXPRESS_NO = "物流单号";
	
	public static final String ALIAS_EXPRESS_TYPE = "物流类型";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	public static final String ALIAS_UPDATE_BY = "修改者";
	
	public static final String ALIAS_URGE_NUMBER = "催促发货次数";
	
	public static final String ALIAS_URGE_TIME = "催促发货时间";
	
	public static final String ALIAS_DELETE_FLAG = "删除标识";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 子单号ID       db_column: subOrderId  
     * 
     * 
     * @Length(max=30)
     */	
	@PrimaryKey
	@Column("subOrderId")
	@Name
	private java.lang.String subOrderId;
    /**
     * 母单号       db_column: orderId  
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
     * supplierId       db_column: supplierId  
     * 
     * 
     * 
     */	
	@Column("shop_id")
	private java.lang.Long shopId;
    /**
     * 总价       db_column: totalProduct  
     * 
     * 
     * 
     */	
	@Column("totalProduct")
	private BigDecimal totalProduct;
    /**
     * 运费       db_column: totalShipping  
     * 
     * 
     * 
     */	
	@Column("totalShipping")
	private BigDecimal totalShipping;
    /**
     * 折扣       db_column: totalAdjustment  
     * 
     * 
     * 
     */	
	@Column("totalAdjustment")
	private BigDecimal totalAdjustment;
    /**
     * 实付金额       db_column: realPrice  
     * 
     * 
     * 
     */	
	@Column("realPrice")
	private BigDecimal realPrice;
    /**
     * 状态       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
    /**
     * 物流单号       db_column: expressNo  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("expressNo")
	private java.lang.String expressNo;
    /**
     * 物流类型       db_column: expressType  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("expressType")
	private java.lang.String expressType;
    /**
     * 创建时间       db_column: createTime  
     * 
     * 
     * 
     */	
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: updateTime  
     * 
     * 
     * 
     */	
	@Column("updateTime")
	private java.util.Date updateTime;
    /**
     * 修改者       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("updateBy")
	private java.lang.String updateBy;
	
	@Column("refuseNote")
	private String refuseNote;//商家 拒绝退货理由

	@Column("close_flg")
	private String closeFlg;	//结算状态 0:未结算/2:已结算
	@Column("sale_bill_id")
	private String saleBillId;	//结算单ID
	

	@Column("cashPay")
	private BigDecimal cashPay;  // 现金券支付订单额度
	@Column("cashNo")
	private String cashNo; 	 // 现金券支付订单额度
	@Column("thirdPay")
	private BigDecimal thirdPay; // 现金券支付订单额度
	@Column("thirdType")
	private String thirdType; 	 // 现金券支付订单额度
	@Column("thirdNo")
	private String thirdNo; 	 // 现金券支付订单额度
	@Column("pay_confirm")
	private java.lang.Integer payConfirm;	//财务确认到账
	@Column("pay_confirm_date")
	private Date payConfirmDate;	//财务确认到账时间
	@Column("cancelTime")
	public java.util.Date cancelTime;//取消时间
	@Column("stock_up")
	private java.lang.Integer stockUp;	//备货中标识 1:备货中
	@Column("companyTicket")
	private BigDecimal companyTicket;  // 内购券使用量
	@Column("benefit_type")
	private java.lang.Integer benefitType;	//优惠类型 3:换领币
	@Column("benefit_amount")
	private BigDecimal benefitAmount;  // 优惠金额
	@Column("noto")
	private String noto;	
	@Column("invoice_status")
	private Integer invoiceStatus;//发票状态 0.未开 1.用户申请 2.已开票
    /**
     * 订单类型 0:普通订单 1:团订单      db_column: order_type
     */
    @Column("order_type")
    private String orderType;
    /**
     * 关联id 团id      db_column: relation_id
     */
    @Column("relation_id")
    private Long relationId;
	/**
	 * 收货地址
	 */
	@Column("returnedAddress")
	private String returnedAddress;
	
	/**
	 * 发货地址
	 */
	@Column("sendAddress")
	private String sendAddress;
	/**
	 * 催促发货次数
	 */
	private int urgeNumber;
	/**
	 * 催促发货时间
	 */
	private Date urgeTime;
	/**
	 * 删除标识
	 */
	private boolean deleteFlag;
	/**
	 * 发货时间
	 */
	private java.util.Date takeTime;
	/**
	 * 最后确认发货时间
	 */
	private java.util.Date lasttakeTime;
	/**
	 * 发货时间
	 */
	private java.util.Date sendTime;
	
	private String closeReason;//关闭理由
	
	private Integer afterserviceStatus;
	private Integer commentStatus;
	private Integer monthcount;
	private BigDecimal monthtotal;
	private java.lang.Long returnOrderId;//退货单号
	private Long refundOrderId;// 仅退款单号
	private Integer userExetendCount;// 用户（非商家）延长收货的次数（默认为0次，即没有延长过收货）
	
	private Returnorder returnorder;
	private Integer subOrderCount; // 订单总数（非数据库字段）
	private BigDecimal sumPrice; // 销售总额（非数据库字段）
	@Column("product_name")
	private String productName;
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getNoto() {
		return noto;
	}

	public void setNoto(String noto) {
		this.noto = noto;
	}

	public Integer getUserExetendCount() {
		return userExetendCount;
	}

	public Returnorder getReturnorder() {
		return returnorder;
	}

	public void setReturnorder(Returnorder returnorder) {
		this.returnorder = returnorder;
	}

	public void setUserExetendCount(Integer userExetendCount) {
		this.userExetendCount = userExetendCount;
	}

	public Long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}
	@Column("payTime")
	public java.util.Date payTime;//支付时间
	
	/**
	 * 发货地址
	 */
	@Column("e_card_info")
	private String e_cardInfo;
	
	/**
	 * 优惠券使用数量 X张
	 */
	@Column("limit_ticket_cnt")
	private Integer limitTicketCnt;
	/**
	 * 用户id
	 */
	@Column("user_id")
	private Long userId;
	//columns END

	private String billId;// 仅退款单号
	
	private Orders orders;//父单对象
	private List<Suborderitem> suborderitemlist;//子订单购买的商品列表

	private List<HashMap> suborderItems;

	public List<HashMap> getSuborderItems() {
		return suborderItems;
	}

	public void setSuborderItems(List<HashMap> suborderItems) {
		this.suborderItems = suborderItems;
	}

	/************** 以下字段仅供显示***********************/
	private Integer itemnum;//购买的订单项的个数
	
	public String getRefuseNote() {
		return refuseNote;
	}

	public void setRefuseNote(String refuseNote) {
		this.refuseNote = refuseNote;
	}

	public Integer getItemnum() {
		return itemnum;
	}

	public void setItemnum(Integer itemnum) {
		this.itemnum = itemnum;
	}

	public Suborder(){
	}

	public Integer getAfterserviceStatus() {
		return afterserviceStatus;
	}

	public void setAfterserviceStatus(Integer afterserviceStatus) {
		this.afterserviceStatus = afterserviceStatus;
	}

	public Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getReturnedAddress() {
		return returnedAddress;
	}

	public void setReturnedAddress(String returnedAddress) {
		this.returnedAddress = returnedAddress;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public List<Suborderitem> getSuborderitemlist() {
		return suborderitemlist;
	}

	public void setSuborderitemlist(List<Suborderitem> suborderitemlist) {
		this.suborderitemlist = suborderitemlist;
	}

	public Suborder(
		java.lang.String subOrderId
	){
		this.subOrderId = subOrderId;
	}

	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	public void setOrderId(java.lang.Long value) {
		this.orderId = value;
	}
	
	public java.lang.Long getOrderId() {
		return this.orderId;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}

	public BigDecimal getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(BigDecimal totalProduct) {
		this.totalProduct = totalProduct;
	}

	public BigDecimal getTotalShipping() {
		return totalShipping;
	}

	public void setTotalShipping(BigDecimal totalShipping) {
		this.totalShipping = totalShipping;
	}

	public BigDecimal getTotalAdjustment() {
		return totalAdjustment;
	}

	public void setTotalAdjustment(BigDecimal totalAdjustment) {
		this.totalAdjustment = totalAdjustment;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setExpressNo(java.lang.String value) {
		this.expressNo = value;
	}
	
	public java.lang.String getExpressNo() {
		return this.expressNo;
	}
	public void setExpressType(java.lang.String value) {
		this.expressType = value;
	}
	
	public java.lang.String getExpressType() {
		return this.expressType;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		if(null != value) {
			setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
		}
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		if(null !=value) {
			setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
		}
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	public int getUrgeNumber() {
		return urgeNumber;
	}

	public void setUrgeNumber(int urgeNumber) {
		this.urgeNumber = urgeNumber;
	}

	public Date getUrgeTime() {
		return urgeTime;
	}

	public void setUrgeTime(Date urgeTime) {
		this.urgeTime = urgeTime;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public java.util.Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(java.util.Date takeTime) {
		this.takeTime = takeTime;
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
	
	public Integer getMonthcount() {
		return monthcount;
	}

	public void setMonthcount(Integer monthcount) {
		this.monthcount = monthcount;
	}

	public BigDecimal getMonthtotal() {
		return monthtotal;
	}

	public void setMonthtotal(BigDecimal monthtotal) {
		this.monthtotal = monthtotal;
	}

	public java.lang.Long getReturnOrderId() {
		return returnOrderId;
	}

	public void setReturnOrderId(java.lang.Long returnOrderId) {
		this.returnOrderId = returnOrderId;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public String getCloseFlg() {
		return closeFlg;
	}

	public void setCloseFlg(String closeFlg) {
		this.closeFlg = closeFlg;
	}

	public String getSaleBillId() {
		return saleBillId;
	}

	public void setSaleBillId(String saleBillId) {
		this.saleBillId = saleBillId;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public BigDecimal getCashPay() {
		return cashPay;
	}

	public void setCashPay(BigDecimal cashPay) {
		this.cashPay = cashPay;
	}

	public String getCashNo() {
		return cashNo;
	}

	public void setCashNo(String cashNo) {
		this.cashNo = cashNo;
	}

	public BigDecimal getThirdPay() {
		return thirdPay;
	}

	public void setThirdPay(BigDecimal thirdPay) {
		this.thirdPay = thirdPay;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getThirdNo() {
		return thirdNo;
	}

	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}

	public java.lang.Integer getPayConfirm() {
		return payConfirm;
	}

	public void setPayConfirm(java.lang.Integer payConfirm) {
		this.payConfirm = payConfirm;
	}

	public Date getPayConfirmDate() {
		return payConfirmDate;
	}

	public void setPayConfirmDate(Date payConfirmDate) {
		this.payConfirmDate = payConfirmDate;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("SubOrderId",getSubOrderId())
			.append("OrderId",getOrderId())
			.append("SupplierId",getSupplierId())
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
			.append("noto",getNoto())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSubOrderId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Suborder == false) return false;
		if(this == obj) return true;
		Suborder other = (Suborder)obj;
		return new EqualsBuilder()
			.append(getSubOrderId(),other.getSubOrderId())
			.isEquals();
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public Integer getSubOrderCount() {
		return subOrderCount;
	}

	public void setSubOrderCount(Integer subOrderCount) {
		this.subOrderCount = subOrderCount;
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

	public BigDecimal getBenefitAmount() {
		return benefitAmount;
	}

	public void setBenefitAmount(BigDecimal benefitAmount) {
		this.benefitAmount = benefitAmount;
	}

	public BigDecimal getCompanyTicket() {
		return companyTicket;
	}

	public void setCompanyTicket(BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
	}

	public Integer getInvoiceStatus() {
		return null != invoiceStatus ?invoiceStatus:0;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getE_cardInfo() {
		return e_cardInfo;
	}

	public void setE_cardInfo(String e_cardInfo) {
		this.e_cardInfo = e_cardInfo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Integer getLimitTicketCnt() {
		return limitTicketCnt;
	}

	public void setLimitTicketCnt(Integer limitTicketCnt) {
		this.limitTicketCnt = limitTicketCnt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}

