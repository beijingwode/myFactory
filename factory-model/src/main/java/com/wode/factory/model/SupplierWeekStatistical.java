package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_week_statistical")
public class SupplierWeekStatistical extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * 统计那一周的数据       db_column: day
     * 
     * 
     * 
     */ 
    @Column("day")
    private java.util.Date day;
    /**
     * 统计时间       db_column: creat_time
     * 
     * 
     * 
     */ 
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * 经营品牌数       db_column: supplier_brand_number
     * 
     * 
     * 
     */ 
    @Column("supplier_brand_number")
    private java.lang.Long supplierBrandNumber;
    /**
     * 新增经营品牌数       db_column: newsupplier_brand_number
     * 
     * 
     * 
     */ 
    @Column("newsupplier_brand_number")
    private java.lang.Long newsupplierBrandNumber;
    /**
     * 在售品牌数       db_column: sal_brand_number
     * 
     * 
     * 
     */ 
    @Column("sal_brand_number")
    private java.lang.Long salBrandNumber;
    /**
     * 新增在售品牌数       db_column: newsal_brand_number
     * 
     * 
     * 
     */ 
    @Column("newsal_brand_number")
    private java.lang.Long newsalBrandNumber;
    /**
     * 上架商品数       db_column: product_number
     * 
     * 
     * 
     */ 
    @Column("product_number")
    private java.lang.Long productNumber;
    /**
     * 新增上架商品数       db_column: newproduct_number
     * 
     * 
     * 
     */ 
    @Column("newproduct_number")
    private java.lang.Long newproductNumber;
    /**
     * 商品折扣率       db_column: product_discount
     * 
     * 
     * 
     */ 
    @Column("product_discount")
    private java.math.BigDecimal productDiscount;
    /**
     * 已注册员工数       db_column: people_number
     * 
     * 
     * 
     */ 
    @Column("people_number")
    private java.lang.Long peopleNumber;
    /**
     * 新增已注册员工数       db_column: newpeople_number
     * 
     * 
     * 
     */ 
    @Column("newpeople_number")
    private java.lang.Long newpeopleNumber;
    /**
     * 活跃员工数       db_column: active_peopleCnt
     * 
     * 
     * 
     */ 
    @Column("active_peopleCnt")
    private java.lang.Long activePeopleCnt;
    /**
     * 新增活跃员工数       db_column: newactive_peopleCnt
     * 
     * 
     * 
     */ 
    @Column("newactive_peopleCnt")
    private java.lang.Long newactivePeopleCnt;
    /**
     * 使用标志 0：表示停用1：表示使用       db_column: use_flg
     * 
     * 
     * 
     */ 
    @Column("use_flg")
    private java.lang.Integer useFlg;
    /**
     * 扩展属性       db_column: exp1
     * 
     * 
     * 
     */ 
    @Column("exp1")
    private java.lang.Long exp1;
    /**
     * 扩展属性2       db_column: exp2
     * 
     * 
     * 
     */ 
    @Column("exp2")
    private java.lang.Long exp2;
    /**
     * 扩展属性3       db_column: exp3
     * 
     * 
     * 
     */ 
    @Column("exp3")
    private java.lang.Long exp3;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("Day",getDay())
            .append("CreatTime",getCreatTime())
            .append("SupplierBrandNumber",getSupplierBrandNumber())
            .append("NewsupplierBrandNumber",getNewsupplierBrandNumber())
            .append("SalBrandNumber",getSalBrandNumber())
            .append("NewsalBrandNumber",getNewsalBrandNumber())
            .append("ProductNumber",getProductNumber())
            .append("NewproductNumber",getNewproductNumber())
            .append("ProductDiscount",getProductDiscount())
            .append("PeopleNumber",getPeopleNumber())
            .append("NewpeopleNumber",getNewpeopleNumber())
            .append("ActivePeopleCnt",getActivePeopleCnt())
            .append("NewactivePeopleCnt",getNewactivePeopleCnt())
            .append("UseFlg",getUseFlg())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
            .append("Exp3",getExp3())
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

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.util.Date getDay() {
		return day;
	}

	public void setDay(java.util.Date day) {
		this.day = day;
	}

	public java.util.Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}

	public java.lang.Long getSupplierBrandNumber() {
		return supplierBrandNumber;
	}

	public void setSupplierBrandNumber(java.lang.Long supplierBrandNumber) {
		this.supplierBrandNumber = supplierBrandNumber;
	}

	public java.lang.Long getNewsupplierBrandNumber() {
		return newsupplierBrandNumber;
	}

	public void setNewsupplierBrandNumber(java.lang.Long newsupplierBrandNumber) {
		this.newsupplierBrandNumber = newsupplierBrandNumber;
	}

	public java.lang.Long getSalBrandNumber() {
		return salBrandNumber;
	}

	public void setSalBrandNumber(java.lang.Long salBrandNumber) {
		this.salBrandNumber = salBrandNumber;
	}

	public java.lang.Long getNewsalBrandNumber() {
		return newsalBrandNumber;
	}

	public void setNewsalBrandNumber(java.lang.Long newsalBrandNumber) {
		this.newsalBrandNumber = newsalBrandNumber;
	}

	public java.lang.Long getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(java.lang.Long productNumber) {
		this.productNumber = productNumber;
	}

	public java.lang.Long getNewproductNumber() {
		return newproductNumber;
	}

	public void setNewproductNumber(java.lang.Long newproductNumber) {
		this.newproductNumber = newproductNumber;
	}

	public java.math.BigDecimal getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(java.math.BigDecimal productDiscount) {
		this.productDiscount = productDiscount;
	}

	public java.lang.Long getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(java.lang.Long peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public java.lang.Long getNewpeopleNumber() {
		return newpeopleNumber;
	}

	public void setNewpeopleNumber(java.lang.Long newpeopleNumber) {
		this.newpeopleNumber = newpeopleNumber;
	}

	public java.lang.Long getActivePeopleCnt() {
		return activePeopleCnt;
	}

	public void setActivePeopleCnt(java.lang.Long activePeopleCnt) {
		this.activePeopleCnt = activePeopleCnt;
	}

	public java.lang.Long getNewactivePeopleCnt() {
		return newactivePeopleCnt;
	}

	public void setNewactivePeopleCnt(java.lang.Long newactivePeopleCnt) {
		this.newactivePeopleCnt = newactivePeopleCnt;
	}

	public java.lang.Integer getUseFlg() {
		return useFlg;
	}

	public void setUseFlg(java.lang.Integer useFlg) {
		this.useFlg = useFlg;
	}

	public java.lang.Long getExp1() {
		return exp1;
	}

	public void setExp1(java.lang.Long exp1) {
		this.exp1 = exp1;
	}

	public java.lang.Long getExp2() {
		return exp2;
	}

	public void setExp2(java.lang.Long exp2) {
		this.exp2 = exp2;
	}

	public java.lang.Long getExp3() {
		return exp3;
	}

	public void setExp3(java.lang.Long exp3) {
		this.exp3 = exp3;
	}

}

