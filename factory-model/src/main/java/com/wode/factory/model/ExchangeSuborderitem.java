package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_exchange_suborderitem")
public class ExchangeSuborderitem extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8245344661999857181L;
	//columns START
    /**
     * 子单项ID       db_column: subOrderItemId
     * 
     * 
     * 
     */ 
    @Column("subOrderItemId")
    private java.lang.Long subOrderItemId;
    /**
     * 子单号ID       db_column: subOrderId
     * 
     * 
     * 
     */ 
    @Column("subOrderId")
    private java.lang.String subOrderId;
    /**
     * 商品编码       db_column: partNumber
     * 
     * 
     * 
     */ 
    @Column("partNumber")
    private java.lang.String partNumber;
    /**
     * 单价       db_column: price
     * 
     * 
     * 
     */ 
    @Column("price")
    private java.math.BigDecimal price;
    /**
     * 数量       db_column: number
     * 
     * 
     * 
     */ 
    @Column("number")
    private java.lang.Integer number;
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
     * 
     */ 
    @Column("updateBy")
    private java.lang.String updateBy;
    /**
     * 商品ID       db_column: productId
     * 
     * 
     * 
     */ 
    @Column("productId")
    private java.lang.Long productId;
    /**
     * SKU主键       db_column: skuId
     * 
     * 
     * 
     */ 
    @Column("skuId")
    private java.lang.Long skuId;
    /**
     * commentFlag       db_column: commentFlag
     * 
     * 
     * 
     */ 
    @Column("commentFlag")
    private java.lang.Integer commentFlag;
    /**
     * promotion_id       db_column: promotion_id
     * 
     * 
     * 
     */ 
    @Column("promotion_id")
    private java.lang.Long promotionId;
    /**
     * promotion_product_id       db_column: promotion_product_id
     * 
     * 
     * 
     */ 
    @Column("promotion_product_id")
    private java.lang.Long promotionProductId;
    /**
     * 单项实付总计（排除运费）       db_column: real_pay
     * 
     * 
     * 
     */ 
    @Column("real_pay")
    private java.math.BigDecimal realPay;
    /**
     * 单项运费       db_column: shipping
     * 
     * 
     * 
     */ 
    @Column("shipping")
    private java.math.BigDecimal shipping;
    /**
     * 订单ID       db_column: order_id
     * 
     * 
     * 
     */ 
    @Column("order_id")
    private java.lang.Long orderId;
    /**
     * 结算单ID       db_column: sale_bill_id
     * 
     * 
     * 
     */ 
    @Column("sale_bill_id")
    private java.lang.Long saleBillId;
    /**
     * 佣金比例       db_column: commissionRatio
     * 
     * 
     * 
     */ 
    @Column("commissionRatio")
    private java.lang.Float commissionRatio;
    /**
     * 内购券使用量       db_column: companyTicket
     * 
     * 
     * 
     */ 
    @Column("companyTicket")
    private java.math.BigDecimal companyTicket;
    /**
     * 优惠类型 3:换购券       db_column: benefit_type
     * 
     * 
     * 
     */ 
    @Column("benefit_type")
    private java.lang.Integer benefitType;
    /**
     * 优惠金额       db_column: benefit_amount
     * 
     * 
     * 
     */ 
    @Column("benefit_amount")
    private java.math.BigDecimal benefitAmount;
    /**
     * 销售区分 0：普通/1:特省/2:换购       db_column: sale_kbn
     * 
     * 
     * 
     */ 
    @Column("sale_kbn")
    private java.lang.Integer saleKbn;
    /**
     * 商品名称       db_column: productName
     * 
     * 
     * 
     */ 
    @Column("productName")
    private java.lang.String productName;
    /**
     * 员工特价       db_column: emp_price
     * 
     * 
     * 
     */ 
    @Column("emp_price")
    private java.math.BigDecimal empPrice;
    /**
     * 品类id       db_column: categoryId
     * 
     * 
     * 
     */ 
    @Column("categoryId")
    private java.lang.Long categoryId;
    /**
     * 品类名称       db_column: categoryName
     * 
     * 
     * 
     */ 
    @Column("categoryName")
    private java.lang.String categoryName;
    /**
     * 商品条形码       db_column: product_code
     * 
     * 
     * 
     */ 
    @Column("product_code")
    private java.lang.String productCode;
    /**
     * 商品规格       db_column: itemValues
     * 
     * 
     * 
     */ 
    @Column("itemValues")
    private java.lang.String itemValues;
    /**
     * 图片路径       db_column: image
     * 
     * 
     * 
     */ 
    @Column("image")
    private java.lang.String image;
    /**
     * 使用优惠券       db_column: benefit_ticket
     * 
     * 
     * 
     */ 
    @Column("benefit_ticket")
    private java.math.BigDecimal benefitTicket;
    /**
     * 员工资格福利       db_column: benefit_self
     * 
     * 
     * 
     */ 
    @Column("benefit_self")
    private java.math.BigDecimal benefitSelf;
    /**
     * 商品内购价（单价）       db_column: internal_purchase_price
     * 
     * 
     * 
     */ 
    @Column("internal_purchase_price")
    private java.math.BigDecimal internalPurchasePrice;
    /**
     * 来自页面       db_column: from_way
     * 
     */ 
    @Column("from_way")
    private java.lang.String fromWay;
    /**
     * 来自客户端（单价）       db_column: from_type
     * 
     */ 
    @Column("from_type")
    private java.lang.String fromType;
    
    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("SubOrderItemId",getSubOrderItemId())
            .append("SubOrderId",getSubOrderId())
            .append("PartNumber",getPartNumber())
            .append("Price",getPrice())
            .append("Number",getNumber())
            .append("CreateTime",getCreateTime())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateBy",getUpdateBy())
            .append("ProductId",getProductId())
            .append("SkuId",getSkuId())
            .append("CommentFlag",getCommentFlag())
            .append("PromotionId",getPromotionId())
            .append("PromotionProductId",getPromotionProductId())
            .append("RealPay",getRealPay())
            .append("Shipping",getShipping())
            .append("OrderId",getOrderId())
            .append("SaleBillId",getSaleBillId())
            .append("CommissionRatio",getCommissionRatio())
            .append("CompanyTicket",getCompanyTicket())
            .append("BenefitType",getBenefitType())
            .append("BenefitAmount",getBenefitAmount())
            .append("SaleKbn",getSaleKbn())
            .append("ProductName",getProductName())
            .append("EmpPrice",getEmpPrice())
            .append("CategoryId",getCategoryId())
            .append("CategoryName",getCategoryName())
            .append("ProductCode",getProductCode())
            .append("ItemValues",getItemValues())
            .append("Image",getImage())
            .append("BenefitTicket",getBenefitTicket())
            .append("BenefitSelf",getBenefitSelf())
            .append("InternalPurchasePrice",getInternalPurchasePrice())
            .toString();
    }

    public java.lang.Long getSubOrderItemId() {
		return subOrderItemId;
	}

	public void setSubOrderItemId(java.lang.Long subOrderItemId) {
		this.subOrderItemId = subOrderItemId;
	}

	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public java.lang.String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(java.lang.String partNumber) {
		this.partNumber = partNumber;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.lang.Integer getNumber() {
		return number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
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

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.Long getSkuId() {
		return skuId;
	}

	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}

	public java.lang.Integer getCommentFlag() {
		return commentFlag;
	}

	public void setCommentFlag(java.lang.Integer commentFlag) {
		this.commentFlag = commentFlag;
	}

	public java.lang.Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(java.lang.Long promotionId) {
		this.promotionId = promotionId;
	}

	public java.lang.Long getPromotionProductId() {
		return promotionProductId;
	}

	public void setPromotionProductId(java.lang.Long promotionProductId) {
		this.promotionProductId = promotionProductId;
	}

	public java.math.BigDecimal getRealPay() {
		return realPay;
	}

	public void setRealPay(java.math.BigDecimal realPay) {
		this.realPay = realPay;
	}

	public java.math.BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(java.math.BigDecimal shipping) {
		this.shipping = shipping;
	}

	public java.lang.Long getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}

	public java.lang.Long getSaleBillId() {
		return saleBillId;
	}

	public void setSaleBillId(java.lang.Long saleBillId) {
		this.saleBillId = saleBillId;
	}

	public java.lang.Float getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(java.lang.Float commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

	public java.math.BigDecimal getCompanyTicket() {
		return companyTicket;
	}

	public void setCompanyTicket(java.math.BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
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

	public java.lang.Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(java.lang.Integer saleKbn) {
		this.saleKbn = saleKbn;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.math.BigDecimal getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(java.math.BigDecimal empPrice) {
		this.empPrice = empPrice;
	}

	public java.lang.Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(java.lang.Long categoryId) {
		this.categoryId = categoryId;
	}

	public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	public java.lang.String getProductCode() {
		return productCode;
	}

	public void setProductCode(java.lang.String productCode) {
		this.productCode = productCode;
	}

	public java.lang.String getItemValues() {
		return itemValues;
	}

	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.math.BigDecimal getBenefitTicket() {
		return benefitTicket;
	}

	public void setBenefitTicket(java.math.BigDecimal benefitTicket) {
		this.benefitTicket = benefitTicket;
	}

	public java.math.BigDecimal getBenefitSelf() {
		return benefitSelf;
	}

	public void setBenefitSelf(java.math.BigDecimal benefitSelf) {
		this.benefitSelf = benefitSelf;
	}

	public java.math.BigDecimal getInternalPurchasePrice() {
		return internalPurchasePrice;
	}

	public void setInternalPurchasePrice(java.math.BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}
	
	public java.lang.String getFromWay() {
		return fromWay;
	}

	public void setFromWay(java.lang.String fromWay) {
		this.fromWay = fromWay;
	}

	public java.lang.String getFromType() {
		return fromType;
	}

	public void setFromType(java.lang.String fromType) {
		this.fromType = fromType;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getSubOrderItemId())
            .toHashCode();
    }

}

