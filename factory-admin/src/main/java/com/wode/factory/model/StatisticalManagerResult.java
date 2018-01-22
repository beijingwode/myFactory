package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;

@Table("t_statistical_manager_result")
public class StatisticalManagerResult extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6200590101761589014L;
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
     * 招商经理       db_column: manager_id
     * 
     * 
     * 
     */ 
    @Column("manager_id")
    private java.lang.Long managerId;
    /**
     * 员工首单（单）       db_column: emp_order_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_order_cnt")
    private java.lang.Integer empOrderCnt;
    /**
     * 员工首单（金额）       db_column: emp_order_amount
     * 
     * 
     * 
     */ 
    @Column("emp_order_amount")
    private java.math.BigDecimal empOrderAmount;
    /**
     * 换领商品总金额       db_column: exchange_amount
     * 
     * 
     * 
     */ 
    @Column("exchange_amount")
    private java.math.BigDecimal exchangeAmount;
    /**
     * 试用商品总金额       db_column: trail_amount
     * 
     * 
     * 
     */ 
    @Column("trail_amount")
    private java.math.BigDecimal trailAmount;
    /**
     * 生日礼金（人次）       db_column: birth_day_cnt
     * 
     * 
     * 
     */ 
    //商家名称
    private java.lang.String supplierName; 
    
    @Column("birth_day_cnt")
    private java.lang.Integer birthDayCnt;
    /**
     * 生日礼金（总额）       db_column: birth_day_amount
     * 
     * 
     * 
     */ 
    @Column("birth_day_amount")
    private java.math.BigDecimal birthDayAmount;
    /**
     * 过节费（人次）       db_column: festival_cnt
     * 
     * 
     * 
     */ 
    @Column("festival_cnt")
    private java.lang.Integer festivalCnt;
    /**
     * 过节费（总额）       db_column: festival_amount
     * 
     * 
     * 
     */ 
    @Column("festival_amount")
    private java.math.BigDecimal festivalAmount;
    /**
     * 订单（总数）       db_column: order_cnt
     * 
     * 
     * 
     */ 
    @Column("order_cnt")
    private java.lang.Integer orderCnt;
    /**
     * 订单（总额）       db_column: order_amount
     * 
     * 
     * 
     */ 
    @Column("order_amount")
    private java.math.BigDecimal orderAmount;
    /**
     * 订单运费（总额）       db_column: order_shipping
     * 
     * 
     * 
     */ 
    @Column("order_shipping")
    private java.math.BigDecimal orderShipping;
    /**
     * 商家首单（单）       db_column: supplier_order_cnt
     * 
     * 
     * 
     */ 
    @Column("supplier_order_cnt")
    private java.lang.Integer supplierOrderCnt;
    /**
     * 商家首单（金额）       db_column: supplier_order_amount
     * 
     * 
     * 
     */ 
    @Column("supplier_order_amount")
    private java.math.BigDecimal supplierOrderAmount;

    //columns END
    private java.lang.String managerName;

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Month",getMonth())
            .append("CreateTime",getCreateTime())
            .append("ManagerId",getManagerId())
            .append("EmpOrderCnt",getEmpOrderCnt())
            .append("EmpOrderAmount",getEmpOrderAmount())
            .append("ExchangeAmount",getExchangeAmount())
            .append("TrailAmount",getTrailAmount())
            .append("BirthDayCnt",getBirthDayCnt())
            .append("BirthDayAmount",getBirthDayAmount())
            .append("FestivalCnt",getFestivalCnt())
            .append("FestivalAmount",getFestivalAmount())
            .append("OrderCnt",getOrderCnt())
            .append("OrderAmount",getOrderAmount())
            .append("OrderShipping",getOrderShipping())
            .append("SupplierOrderCnt",getSupplierOrderCnt())
            .append("SupplierOrderAmount",getSupplierOrderAmount())
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

	public java.lang.Long getManagerId() {
		return managerId;
	}

	public void setManagerId(java.lang.Long managerId) {
		this.managerId = managerId;
	}

	public java.lang.Integer getEmpOrderCnt() {
		return empOrderCnt;
	}

	public void setEmpOrderCnt(java.lang.Integer empOrderCnt) {
		this.empOrderCnt = empOrderCnt;
	}

	public java.math.BigDecimal getEmpOrderAmount() {
		return empOrderAmount;
	}

	public void setEmpOrderAmount(java.math.BigDecimal empOrderAmount) {
		this.empOrderAmount = empOrderAmount;
	}

	public java.math.BigDecimal getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(java.math.BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public java.math.BigDecimal getTrailAmount() {
		return trailAmount;
	}

	public void setTrailAmount(java.math.BigDecimal trailAmount) {
		this.trailAmount = trailAmount;
	}

	public java.lang.Integer getBirthDayCnt() {
		return birthDayCnt;
	}

	public void setBirthDayCnt(java.lang.Integer birthDayCnt) {
		this.birthDayCnt = birthDayCnt;
	}

	public java.math.BigDecimal getBirthDayAmount() {
		return birthDayAmount;
	}

	public void setBirthDayAmount(java.math.BigDecimal birthDayAmount) {
		this.birthDayAmount = birthDayAmount;
	}

	public java.lang.Integer getFestivalCnt() {
		return festivalCnt;
	}

	public void setFestivalCnt(java.lang.Integer festivalCnt) {
		this.festivalCnt = festivalCnt;
	}

	public java.math.BigDecimal getFestivalAmount() {
		return festivalAmount;
	}

	public void setFestivalAmount(java.math.BigDecimal festivalAmount) {
		this.festivalAmount = festivalAmount;
	}

	public java.lang.Integer getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(java.lang.Integer orderCnt) {
		this.orderCnt = orderCnt;
	}

	public java.math.BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(java.math.BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public java.math.BigDecimal getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(java.math.BigDecimal orderShipping) {
		this.orderShipping = orderShipping;
	}

	public java.lang.Integer getSupplierOrderCnt() {
		return supplierOrderCnt;
	}

	public void setSupplierOrderCnt(java.lang.Integer supplierOrderCnt) {
		this.supplierOrderCnt = supplierOrderCnt;
	}

	public java.math.BigDecimal getSupplierOrderAmount() {
		return supplierOrderAmount;
	}

	public void setSupplierOrderAmount(java.math.BigDecimal supplierOrderAmount) {
		this.supplierOrderAmount = supplierOrderAmount;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

}

