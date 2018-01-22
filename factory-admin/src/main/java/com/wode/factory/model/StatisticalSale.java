package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_statistical_sale")
public class StatisticalSale extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8040267356366338795L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @Column("id")
    private java.lang.Long id;
    /**
     * 对象年月 yyyy-mm       db_column: month
     * 
     * 
     * 
     */ 
    @Column("month")
    private java.lang.String month;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 商家名称       db_column: supplier_name
     * 
     * 
     * 
     */ 
    @Column("supplier_name")
    private java.lang.String supplierName;
    /**
     * 商家招商经理       db_column: supplier_manager
     * 
     * 
     * 
     */ 
    private java.lang.String managerName;
    
    @Column("supplier_manager")
    private java.lang.Long supplierManager;
    /**
     * 订单数       db_column: order_cnt
     * 
     * 
     * 
     */ 
    @Column("order_cnt")
    private java.lang.Integer orderCnt;
    /**
     * 运费       db_column: shipping
     * 
     * 
     * 
     */ 
    @Column("shipping")
    private java.math.BigDecimal shipping;
    /**
     * 优惠金额       db_column: adjustment
     * 
     * 
     * 
     */ 
    @Column("adjustment")
    private java.math.BigDecimal adjustment;
    /**
     * 实付金额（含运费）       db_column: realPrice
     * 
     * 
     * 
     */ 
    @Column("realPrice")
    private java.math.BigDecimal realPrice;
    /**
     * 内购券使用量       db_column: companyTicket
     * 
     * 
     * 
     */ 
    @Column("companyTicket")
    private java.math.BigDecimal companyTicket;
    /**
     * 优惠金额       db_column: benefit_amount
     * 
     * 
     * 
     */ 
    @Column("benefit_amount")
    private java.math.BigDecimal benefitAmount;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Month",getMonth())
            .append("CreateTime",getCreateTime())
            .append("SupplierId",getSupplierId())
            .append("SupplierName",getSupplierName())
            .append("SupplierManager",getSupplierManager())
            .append("OrderCnt",getOrderCnt())
            .append("Shipping",getShipping())
            .append("Adjustment",getAdjustment())
            .append("RealPrice",getRealPrice())
            .append("CompanyTicket",getCompanyTicket())
            .append("BenefitAmount",getBenefitAmount())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getMonth() {
		return month;
	}

	public void setMonth(java.lang.String month) {
		this.month = month;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

	public java.lang.Long getSupplierManager() {
		return supplierManager;
	}

	public void setSupplierManager(java.lang.Long supplierManager) {
		this.supplierManager = supplierManager;
	}

	public java.lang.Integer getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(java.lang.Integer orderCnt) {
		this.orderCnt = orderCnt;
	}

	public java.math.BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(java.math.BigDecimal shipping) {
		this.shipping = shipping;
	}

	public java.math.BigDecimal getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(java.math.BigDecimal adjustment) {
		this.adjustment = adjustment;
	}

	public java.math.BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(java.math.BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public java.math.BigDecimal getCompanyTicket() {
		return companyTicket;
	}

	public void setCompanyTicket(java.math.BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
	}

	public java.math.BigDecimal getBenefitAmount() {
		return benefitAmount;
	}

	public void setBenefitAmount(java.math.BigDecimal benefitAmount) {
		this.benefitAmount = benefitAmount;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

}

