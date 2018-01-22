package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("m_supplier_sale_code_manage")
public class SupplierSaleCodeManage  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	
	
	
	//columns START
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * 年月数字yyyyMM       db_column: yearMonth  
     * 
     * 
     * @NotNull 
     */	
	@Column("yearMonth")
	private java.lang.Integer yearMonth;
    /**
     * 代码后四位每月从1开始自动累计       db_column: code  
     * 
     * 
     * @NotNull 
     */	
	@Column("code")
	private java.lang.Integer code;
	//columns END

	public SupplierSaleCodeManage(){
	}

	public SupplierSaleCodeManage(
		java.lang.Long supplierId
	){
		this.supplierId = supplierId;
	}

		
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
		
	public void setYearMonth(java.lang.Integer value) {
		this.yearMonth = value;
	}
	
	public java.lang.Integer getYearMonth() {
		return this.yearMonth;
	}
		
	public void setCode(java.lang.Integer value) {
		this.code = value;
	}
	
	public java.lang.Integer getCode() {
		return this.code;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("SupplierId",getSupplierId())
			.append("YearMonth",getYearMonth())
			.append("Code",getCode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getSupplierId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierSaleCodeManage == false) return false;
		if(this == obj) return true;
		SupplierSaleCodeManage other = (SupplierSaleCodeManage)obj;
		return new EqualsBuilder()
			.append(getSupplierId(),other.getSupplierId())
			.isEquals();
	}
}

