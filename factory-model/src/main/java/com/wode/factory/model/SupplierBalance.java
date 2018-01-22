/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;


public class SupplierBalance extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "SupplierBalance";

	public static final String ALIAS_ID = "ID";

	public static final String ALIAS_SUPPLIER_ID = "供应商ID";

	public static final String ALIAS_SUPPLIER_NAME = "供应商名称";

	public static final String ALIAS_BALANCE = "余额";

	//date formats

	//columns START
	/**
	 * ID       db_column: id
	 */
	@PrimaryKey
	private java.lang.Long id;
	/**
	 * 供应商ID       db_column: supplierId
	 */
	private java.lang.Long supplierId;
	/**
	 * 供应商名称       db_column: supplierName
	 *
	 * @Length(max=100)
	 */
	private java.lang.String supplierName;
	/**
	 * 余额       db_column: balance
	 */
	private Long balance;
	//columns END

	public SupplierBalance() {
	}

	public SupplierBalance(
			java.lang.Long id
	) {
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}

	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierName(java.lang.String value) {
		this.supplierName = value;
	}

	public java.lang.String getSupplierName() {
		return this.supplierName;
	}

	public void setBalance(Long value) {
		this.balance = value;
	}

	public Long getBalance() {
		return this.balance;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId())
				.append("SupplierId", getSupplierId())
				.append("SupplierName", getSupplierName())
				.append("Balance", getBalance())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof SupplierBalance == false) return false;
		if (this == obj) return true;
		SupplierBalance other = (SupplierBalance) obj;
		return new EqualsBuilder()
				.append(getId(), other.getId())
				.isEquals();
	}
}

