package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SupplierSaleCodeManageQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 商家id */
	private java.lang.Long supplierId;
	/** 年月数字yyyyMM */
	private java.lang.Integer yearMonth;
	/** 代码后四位每月从1开始自动累计 */
	private java.lang.Integer code;

	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Integer getYearMonth() {
		return this.yearMonth;
	}
	
	public void setYearMonth(java.lang.Integer value) {
		this.yearMonth = value;
	}
	
	public java.lang.Integer getCode() {
		return this.code;
	}
	
	public void setCode(java.lang.Integer value) {
		this.code = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

