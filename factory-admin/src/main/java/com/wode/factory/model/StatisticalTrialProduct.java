package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_statistical_trial_product")
public class StatisticalTrialProduct extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5373056972971089149L;
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
    //招商经理名称
    private java.lang.String managerName;
    
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
     * 商品种类       db_column: product_type_cnt
     * 
     * 
     * 
     */ 
    @Column("product_type_cnt")
    private java.lang.Integer productTypeCnt;
    /**
     * 商品总数       db_column: product_cnt
     * 
     * 
     * 
     */ 
    @Column("product_cnt")
    private java.lang.Integer productCnt;
    /**
     * 平均电商价       db_column: avg_online_price
     * 
     * 
     * 
     */ 
    @Column("avg_online_price")
    private java.math.BigDecimal avgOnlinePrice;
    /**
     * 平均内购价       db_column: avg_real_price
     * 
     * 
     * 
     */ 
    @Column("avg_real_price")
    private java.math.BigDecimal avgRealPrice;
    /**
     * 平均运费       db_column: avg_shipping
     * 
     * 
     * 
     */ 
    @Column("avg_shipping")
    private java.math.BigDecimal avgShipping;
    /**
     * 电商价总和       db_column: online_amoung
     * 
     * 
     * 
     */ 
    @Column("online_amoung")
    private java.math.BigDecimal onlineAmoung;
    /**
     * 内购价总和       db_column: real_amount
     * 
     * 
     * 
     */ 
    @Column("real_amount")
    private java.math.BigDecimal realAmount;
    /**
     * 优惠总金额       db_column: break_amount
     * 
     * 
     * 
     */ 
    @Column("break_amount")
    private java.math.BigDecimal breakAmount;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Month",getMonth())
            .append("CreateTime",getCreateTime())
            .append("SupplierId",getSupplierId())
            .append("SupplierName",getSupplierName())
            .append("SupplierManager",getSupplierManager())
            .append("ProductTypeCnt",getProductTypeCnt())
            .append("ProductCnt",getProductCnt())
            .append("AvgOnlinePrice",getAvgOnlinePrice())
            .append("AvgRealPrice",getAvgRealPrice())
            .append("AvgShipping",getAvgShipping())
            .append("OnlineAmoung",getOnlineAmoung())
            .append("RealAmount",getRealAmount())
            .append("BreakAmount",getBreakAmount())
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

	public java.lang.Integer getProductTypeCnt() {
		return productTypeCnt;
	}

	public void setProductTypeCnt(java.lang.Integer productTypeCnt) {
		this.productTypeCnt = productTypeCnt;
	}

	public java.lang.Integer getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(java.lang.Integer productCnt) {
		this.productCnt = productCnt;
	}

	public java.math.BigDecimal getAvgOnlinePrice() {
		return avgOnlinePrice;
	}

	public void setAvgOnlinePrice(java.math.BigDecimal avgOnlinePrice) {
		this.avgOnlinePrice = avgOnlinePrice;
	}

	public java.math.BigDecimal getAvgRealPrice() {
		return avgRealPrice;
	}

	public void setAvgRealPrice(java.math.BigDecimal avgRealPrice) {
		this.avgRealPrice = avgRealPrice;
	}

	public java.math.BigDecimal getAvgShipping() {
		return avgShipping;
	}

	public void setAvgShipping(java.math.BigDecimal avgShipping) {
		this.avgShipping = avgShipping;
	}

	public java.math.BigDecimal getOnlineAmoung() {
		return onlineAmoung;
	}

	public void setOnlineAmoung(java.math.BigDecimal onlineAmoung) {
		this.onlineAmoung = onlineAmoung;
	}

	public java.math.BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(java.math.BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public java.math.BigDecimal getBreakAmount() {
		return breakAmount;
	}

	public void setBreakAmount(java.math.BigDecimal breakAmount) {
		this.breakAmount = breakAmount;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}
	

}

