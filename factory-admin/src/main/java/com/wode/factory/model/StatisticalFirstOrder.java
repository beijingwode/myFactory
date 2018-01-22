package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_statistical_first_order")
public class StatisticalFirstOrder extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9060422946800781225L;
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
     * 员工id       db_column: emp_id
     * 
     * 
     * 
     */ 
    @Column("emp_id")
    private java.lang.Long empId;
    /**
     * 员工所属企业id       db_column: enterprise_id
     * 
     * 
     * 
     */ 
    @Column("enterprise_id")
    private java.lang.Long enterpriseId;
    /**
     * 员工所属企业名称       db_column: enterprise_name
     * 
     * 
     * 
     */ 
    private java.lang.String managerName;
    
    @Column("enterprise_name")
    private java.lang.String enterpriseName;
    /**
     * 企业招商经理       db_column: enterprise_manager
     * 
     * 
     * 
     */ 
    @Column("enterprise_manager")
    private java.lang.Long enterpriseManager;
    /**
     * 支付时间       db_column: pay_time
     * 
     * 
     * 
     */ 
    @Column("pay_time")
    private java.util.Date payTime;
    /**
     * 确认收货时间       db_column: take_time
     * 
     * 
     * 
     */ 
    @Column("take_time")
    private java.util.Date takeTime;
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
    @Column("supplier_manager")
    private java.lang.Long supplierManager;
    /**
     * 订单id(子单)       db_column: subOrderId
     * 
     * 
     * 
     */ 
    @Column("subOrderId")
    private java.lang.String subOrderId;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Month",getMonth())
            .append("CreateTime",getCreateTime())
            .append("EmpId",getEmpId())
            .append("EnterpriseId",getEnterpriseId())
            .append("EnterpriseName",getEnterpriseName())
            .append("EnterpriseManager",getEnterpriseManager())
            .append("PayTime",getPayTime())
            .append("TakeTime",getTakeTime())
            .append("Shipping",getShipping())
            .append("Adjustment",getAdjustment())
            .append("RealPrice",getRealPrice())
            .append("CompanyTicket",getCompanyTicket())
            .append("BenefitType",getBenefitType())
            .append("BenefitAmount",getBenefitAmount())
            .append("SupplierId",getSupplierId())
            .append("SupplierName",getSupplierName())
            .append("SupplierManager",getSupplierManager())
            .append("SubOrderId",getSubOrderId())
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

	public java.lang.Long getEmpId() {
		return empId;
	}

	public void setEmpId(java.lang.Long empId) {
		this.empId = empId;
	}

	public java.lang.Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public java.lang.String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(java.lang.String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public java.lang.Long getEnterpriseManager() {
		return enterpriseManager;
	}

	public void setEnterpriseManager(java.lang.Long enterpriseManager) {
		this.enterpriseManager = enterpriseManager;
	}

	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	public java.util.Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(java.util.Date takeTime) {
		this.takeTime = takeTime;
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

	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

}

