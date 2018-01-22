package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_statistical_exchange_product")
public class StatisticalExchangeProduct extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5547273189186956629L;
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
    	/**
    	 * 招商经理
    	 */
    private java.lang.String managerName;
    
    
    @Column("product_cnt")
    private java.lang.Integer productCnt;
    /**
     * 参与员工数       db_column: emp_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_cnt")
    private java.lang.Integer empCnt;
    /**
     * 商品总额       db_column: product_amount
     * 
     * 
     * 
     */ 
    @Column("product_amount")
    private java.math.BigDecimal productAmount;

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
            .append("EmpCnt",getEmpCnt())
            .append("ProductAmount",getProductAmount())
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

	public java.lang.Integer getEmpCnt() {
		return empCnt;
	}

	public void setEmpCnt(java.lang.Integer empCnt) {
		this.empCnt = empCnt;
	}

	public java.math.BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(java.math.BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

}

