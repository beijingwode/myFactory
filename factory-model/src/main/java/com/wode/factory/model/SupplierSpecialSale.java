package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_special_sale")
public class SupplierSpecialSale extends BaseModel implements java.io.Serializable{

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
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 商品名称       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 员工级别       db_column: emp_level
     * 
     * 
     * 
     */ 
    @Column("emp_level")
    private java.lang.Integer empLevel;
    /**
     * 员工数量       db_column: emp_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_cnt")
    private java.lang.Integer empCnt;
    /**
     * 销售数量       db_column: sale_cnt
     * 
     * 
     * 
     */ 
    @Column("sale_cnt")
    private java.lang.Integer saleCnt;
    /**
     * 员工购买数量       db_column: emp_buy_cnt
     * 
     * 
     * 
     */ 
    @Column("emp_buy_cnt")
    private java.lang.Integer empBuyCnt;
    /**
     * 员工特价       db_column: emp_price
     * 
     * 
     * 
     */ 
    @Column("emp_price")
    private java.math.BigDecimal empPrice;
    /**
     * 差价：最低价格sku去除佣金及特价部分       db_column: div_price
     * 
     * 
     * 
     */ 
    @Column("div_price")
    private java.math.BigDecimal divPrice;
    /**
     * 累计可分配金额差价累计（此金额不能提现）       db_column: div_amount
     * 
     * 
     * 
     */ 
    @Column("div_amount")
    private java.math.BigDecimal divAmount;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;

    /**
     * 停用标识 1:停用/0:使用       db_column: stop_flg
     * 
     * 
     * 
     */ 
    @Column("stop_flg")
    private java.lang.Integer stopFlg;
    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("ProductId",getProductId())
            .append("ProductName",getProductName())
            .append("EmpLevel",getEmpLevel())
            .append("EmpCnt",getEmpCnt())
            .append("SaleCnt",getSaleCnt())
            .append("EmpBuyCnt",getEmpBuyCnt())
            .append("EmpPrice",getEmpPrice())
            .append("DivPrice",getDivPrice())
            .append("DivAmount",getDivAmount())
            .append("CreateDate",getCreateDate())
            .append("UpdateDate",getUpdateDate())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.Integer getEmpLevel() {
		return empLevel;
	}

	public void setEmpLevel(java.lang.Integer empLevel) {
		this.empLevel = empLevel;
	}

	public java.lang.Integer getEmpCnt() {
		return empCnt;
	}

	public void setEmpCnt(java.lang.Integer empCnt) {
		this.empCnt = empCnt;
	}

	public java.lang.Integer getSaleCnt() {
		return saleCnt;
	}

	public void setSaleCnt(java.lang.Integer saleCnt) {
		this.saleCnt = saleCnt;
	}

	public java.lang.Integer getEmpBuyCnt() {
		return empBuyCnt;
	}

	public void setEmpBuyCnt(java.lang.Integer empBuyCnt) {
		this.empBuyCnt = empBuyCnt;
	}

	public java.math.BigDecimal getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(java.math.BigDecimal empPrice) {
		this.empPrice = empPrice;
	}

	public java.math.BigDecimal getDivPrice() {
		return divPrice;
	}

	public void setDivPrice(java.math.BigDecimal divPrice) {
		this.divPrice = divPrice;
	}

	public java.math.BigDecimal getDivAmount() {
		return divAmount;
	}

	public void setDivAmount(java.math.BigDecimal divAmount) {
		this.divAmount = divAmount;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.Integer getStopFlg() {
		return stopFlg;
	}

	public void setStopFlg(java.lang.Integer stopFlg) {
		this.stopFlg = stopFlg;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}