package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_sale_detail")
public class SaleDetail  implements java.io.Serializable, Cloneable {
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
     * 子订单id       db_column: subOrderId  
     * 
     * 
     * 
     */	
	@Column("subOrderId")
	private java.lang.String subOrderId;
    /**
     * 子订单项id       db_column: subOrderId  
     * 
     * 
     * 
     */	
	@Column("subOrderItemId")
	private java.lang.Long subOrderItemId;
	
    /**
     * 对账单总单id
     * 
     * 
     * 
     */	
	@Column("saleBillId")
	private java.lang.Long saleBillId;
    /**
     * 付款时间       db_column: payTime  
     * 
     * 
     * 
     */	
	@Column("payTime")
	private java.util.Date payTime;
    /**
     * 收货时间       db_column: takeTime  
     * 
     * 
     * 
     */	
	@Column("takeTime")
	private java.util.Date takeTime;
    /**
     * 退货日期       db_column: returnTime  
     * 
     * 
     * 
     */	
	@Column("returnTime")
	private java.util.Date returnTime;
    /**
     * 货单类型：  0：本企业  1：其他企业       db_column: own  
     * 
     * 
     * 
     */	
	@Column("own")
	private java.lang.Integer own;
    /**
     * productId       db_column: productId  
     * 
     * 
     * 
     */	
	@Column("productId")
	private java.lang.Long productId;
    /**
     * 商品名称       db_column: productName  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("productName")
	private java.lang.String productName;
    /**
     * skuId       db_column: skuId  
     * 
     * 
     * 
     */	
	@Column("skuId")
	private java.lang.Long skuId;
    /**
     * categoryId       db_column: categoryId  
     * 
     * 
     * 
     */	
	@Column("categoryId")
	private java.lang.Long categoryId;
    /**
     * 类目名称       db_column: categoryName  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("categoryName")
	private java.lang.String categoryName;
	
    /**
     * price       db_column: price  
     * 
     * 
     * 
     */	
	@Column("price")
	private BigDecimal price;
    /**
     * number       db_column: number  
     * 
     * 
     * 
     */	
	@Column("number")
	private java.lang.Integer number;
    /**
     * 总货款       db_column: allPrice  
     * 
     * 
     * 
     */	
	@Column("allPrice")
	private BigDecimal allPrice;
    /**
     * 佣金比例       db_column: commissionRatio  
     * 
     * 
     * 
     */	
	@Column("commissionRatio")
	private java.lang.Float commissionRatio;
    /**
     * commission       db_column: commission  
     * 
     * 
     * 
     */	
	@Column("commission")
	private BigDecimal commission;
    /**
     * 实付金额       db_column: payPrice  
     * 
     * 
     * 
     */	
	@Column("realPrice")
	private BigDecimal realPrice;
	@Column("payPrice")
	private BigDecimal payPrice;
	@Column("carriagePrice")
	private BigDecimal carriagePrice;//运费
	
    /**
     * 状态：1:确认收货   -1:已退货       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
    /**
     * 有优惠  0：无优惠  1：有优惠       db_column: haveCheap  
     * 
     * 
     * 
     */	
	@Column("haveCheap")
	private java.lang.Integer haveCheap;
    /**
     * 福利币　：如果为退货　则为负数       db_column: fuCoin  
     * 
     * 
     * 
     */	
	@Column("fuCoin")
	private BigDecimal fuCoin;
    /**
     * createTime       db_column: createTime  
     * 
     * 
     * 
     */	
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * createUserid       db_column: createUserid  
     * 
     * 
     * 
     */	
	@Column("createUserid")
	private java.lang.Long createUserid;
    /**
     * 是否删除：  0：默认正常   1：已删除       db_column: isDelete  
     * 
     * 
     * 
     */	
	@Column("isDelete")
	private java.lang.Integer isDelete;
	
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
	@Column("benefit_type")
	private java.lang.Integer benefitType;	//优惠类型 3:换领币
	@Column("benefit_amount")
	private BigDecimal benefitAmount;  // 优惠金额
	@Column("sale_kbn")
	private java.lang.Integer saleKbn;  // 优惠金额
	@Column("benefit_ticket")
	private BigDecimal benefitTicket;  		// 优惠券
	@Column("benefit_self")
	private BigDecimal benefitSelf;  		// benefit_self
	@Column("sale_price")
	private BigDecimal salePrice;  		// 下单时 内购价（单价）
	//columns END

	public SaleDetail(){
	}

	public SaleDetail(
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
		
	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}
	
	public java.util.Date getPayTime() {
		return this.payTime;
	}
		
	public void setTakeTime(java.util.Date value) {
		this.takeTime = value;
	}
	
	public java.util.Date getTakeTime() {
		return this.takeTime;
	}
		
	public void setReturnTime(java.util.Date value) {
		this.returnTime = value;
	}
	
	public java.util.Date getReturnTime() {
		return this.returnTime;
	}
		
	public void setOwn(java.lang.Integer value) {
		this.own = value;
	}
	
	public java.lang.Integer getOwn() {
		return this.own;
	}
		
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getSubOrderItemId() {
		return subOrderItemId;
	}

	public void setSubOrderItemId(java.lang.Long subOrderItemId) {
		this.subOrderItemId = subOrderItemId;
	}

	public java.lang.Long getProductId() {
		return this.productId;
	}
		
	public void setProductName(java.lang.String value) {
		this.productName = value;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
	}
		
	public void setSkuId(java.lang.Long value) {
		this.skuId = value;
	}
	
	public java.lang.Long getSkuId() {
		return this.skuId;
	}
		
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
		
	public void setCategoryName(java.lang.String value) {
		this.categoryName = value;
	}
	
	public java.lang.String getCategoryName() {
		return this.categoryName;
	}
		
	public void setPrice(BigDecimal value) {
		this.price = value;
	}
	
	public BigDecimal getPrice() {
		return this.price;
	}
		
	public void setNumber(java.lang.Integer value) {
		this.number = value;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}
		
	public java.lang.Long getSaleBillId() {
		return saleBillId;
	}

	public void setSaleBillId(java.lang.Long saleBillId) {
		this.saleBillId = saleBillId;
	}

	public void setAllPrice(BigDecimal value) {
		this.allPrice = value;
	}
	
	public BigDecimal getAllPrice() {
		return this.allPrice;
	}
		
	public void setCommissionRatio(java.lang.Float value) {
		this.commissionRatio = value;
	}
	
	public java.lang.Float getCommissionRatio() {
		return this.commissionRatio;
	}
		
	public void setCommission(BigDecimal value) {
		this.commission = value;
	}
	
	public BigDecimal getCommission() {
		return this.commission;
	}
		
	public void setPayPrice(BigDecimal value) {
		this.payPrice = value;
	}
	
	public BigDecimal getPayPrice() {
		return this.payPrice;
	}
		
	public BigDecimal getCarriagePrice() {
		return carriagePrice;
	}

	public void setCarriagePrice(BigDecimal carriagePrice) {
		this.carriagePrice = carriagePrice;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
		
	public void setHaveCheap(java.lang.Integer value) {
		this.haveCheap = value;
	}
	
	public java.lang.Integer getHaveCheap() {
		return this.haveCheap;
	}
		
	public void setFuCoin(BigDecimal value) {
		this.fuCoin = value;
	}
	
	public BigDecimal getFuCoin() {
		return this.fuCoin;
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
		
	public void setIsDelete(java.lang.Integer value) {
		this.isDelete = value;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
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

	public java.lang.Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(java.lang.Integer saleKbn) {
		this.saleKbn = saleKbn;
	}

	public BigDecimal getBenefitTicket() {
		return benefitTicket;
	}

	public void setBenefitTicket(BigDecimal benefitTicket) {
		this.benefitTicket = benefitTicket;
	}

	public BigDecimal getBenefitSelf() {
		return benefitSelf;
	}

	public void setBenefitSelf(BigDecimal benefitSelf) {
		this.benefitSelf = benefitSelf;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SubOrderId",getSubOrderId())
			.append("PayTime",getPayTime())
			.append("TakeTime",getTakeTime())
			.append("ReturnTime",getReturnTime())
			.append("Own",getOwn())
			.append("ProductId",getProductId())
			.append("ProductName",getProductName())
			.append("SkuId",getSkuId())
			.append("CategoryId",getCategoryId())
			.append("CategoryName",getCategoryName())
			.append("Price",getPrice())
			.append("Number",getNumber())
			.append("AllPrice",getAllPrice())
			.append("CommissionRatio",getCommissionRatio())
			.append("Commission",getCommission())
			.append("PayPrice",getPayPrice())
			.append("Status",getStatus())
			.append("HaveCheap",getHaveCheap())
			.append("FuCoin",getFuCoin())
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
		if(obj instanceof SaleDetail == false) return false;
		if(this == obj) return true;
		SaleDetail other = (SaleDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return (SaleDetail)super.clone();  
    }  
	
	
}

