package com.wode.factory.model;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
@Table("t_balance_statistical")
public class BalanceMonthStatistical extends BaseModel implements java.io.Serializable{

	private static final long serialVersionUID = -8659938622344989167L;
	
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
     * 统计的那个月的数据
     */
    @Column("month")
    private java.lang.String month;
    /**
     * 统计每月数据的时间
     */
    @Column("creat_time")
    private java.util.Date creatTime;
    /**
     * 商家余额       db_column: supplier_balance
     * 
     */ 
    @Column("supplier_balance")
    private java.math.BigDecimal supplierBalance;
    /**
     * 用户余额       db_column: user_balance
     * 
     */ 
    private java.math.BigDecimal userBalance;
    /**
     * 扩展属性1
     */
    private java.math.BigDecimal exp1;
    /**
     * 扩展属性2
     */
    private java.math.BigDecimal exp2;
    /**
     * 扩展属性3
     */
    private java.math.BigDecimal exp3;
    /**
     * 扩展属性4
     */
    private java.math.BigDecimal exp4;
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
	public java.util.Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(java.util.Date creatTime) {
		this.creatTime = creatTime;
	}
	public java.math.BigDecimal getSupplierBalance() {
		return supplierBalance;
	}
	public void setSupplierBalance(java.math.BigDecimal supplierBalance) {
		this.supplierBalance = supplierBalance;
	}
	public java.math.BigDecimal getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(java.math.BigDecimal userBalance) {
		this.userBalance = userBalance;
	}
	public java.math.BigDecimal getExp1() {
		return exp1;
	}
	public void setExp1(java.math.BigDecimal exp1) {
		this.exp1 = exp1;
	}
	public java.math.BigDecimal getExp2() {
		return exp2;
	}
	public void setExp2(java.math.BigDecimal exp2) {
		this.exp2 = exp2;
	}
	public java.math.BigDecimal getExp3() {
		return exp3;
	}
	public void setExp3(java.math.BigDecimal exp3) {
		this.exp3 = exp3;
	}
	public java.math.BigDecimal getExp4() {
		return exp4;
	}
	public void setExp4(java.math.BigDecimal exp4) {
		this.exp4 = exp4;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("Id",getId())
				.append("Month",getMonth())
				.append("createTime",getCreatTime())
				.append("supplierBalance",getSupplierBalance())
				.append("userBalance",getUserBalance())
				.append("exp1",getExp1())
				.append("exp2",getExp2())
				.append("exp3",getExp3())
				.append("exp4",getExp4())
				.toString();
	}
    public int hashCode(){
    	return new HashCodeBuilder()
    			.append(getId())
    			.toHashCode();
    }
    
}
