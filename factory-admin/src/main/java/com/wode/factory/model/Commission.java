/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

import cn.org.rapid_framework.util.DateConvertUtils;

public class Commission extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Commission";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_SUPPLIER_ID = "商家ID";
	
	public static final String ALIAS_SUPPLIER_NAME = "商家名称";
	
	public static final String ALIAS_SUB_ORDER_ID = "子单ID";
	
	public static final String ALIAS_SUB_ORDER_USER_ID = "下单用户ID";
	
	public static final String ALIAS_SUB_ORDER_USER_NAME = "下单用户账户";
	
	public static final String ALIAS_PRODUCT_IDS = "子单中所包含的商品ID（多个以英文逗号分割）";
	
	public static final String ALIAS_COMMISSION = "应收佣金";
	
	public static final String ALIAS_CREAT_TIME = "建创时间";
	
	//date formats
	public static final String FORMAT_CREAT_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 商家ID       db_column: supplier_id  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long supplierId;
    /**
     * 商家名称       db_column: supplier_name  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String supplierName;
    /**
     * 子单ID       db_column: sub_order_id  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.String subOrderId;
    /**
     * 下单用户ID       db_column: sub_order_user_id  
     * 
     * 
     * @NotNull 
     */	
	private java.lang.Long subOrderUserId;
    /**
     * 下单用户账户       db_column: sub_order_user_name  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String subOrderUserName;
    /**
     * 子单中所包含的商品ID（多个以英文逗号分割）       db_column: product_ids  
     * 
     * 
     * @NotBlank @Length(max=250)
     */	
	private java.lang.String productIds;
    /**
     * 应收佣金       db_column: commission  
     * 
     * 
     * @NotNull 
     */	
	private BigDecimal commission;
    /**
     * 建创时间       db_column: creat_time  
     * 
     * 
     * @NotNull 
     */	
	private java.util.Date creatTime;
	//columns END

	public Commission(){
	}

	public Commission(
		java.lang.Long id
	){
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
	public void setSubOrderId(java.lang.String value) {
		this.subOrderId = value;
	}
	
	public java.lang.String getSubOrderId() {
		return this.subOrderId;
	}
	public void setSubOrderUserId(java.lang.Long value) {
		this.subOrderUserId = value;
	}
	
	public java.lang.Long getSubOrderUserId() {
		return this.subOrderUserId;
	}
	public void setSubOrderUserName(java.lang.String value) {
		this.subOrderUserName = value;
	}
	
	public java.lang.String getSubOrderUserName() {
		return this.subOrderUserName;
	}
	public void setProductIds(java.lang.String value) {
		this.productIds = value;
	}
	
	public java.lang.String getProductIds() {
		return this.productIds;
	}
	public void setCommission(BigDecimal value) {
		this.commission = value;
	}
	
	public BigDecimal getCommission() {
		return this.commission;
	}
	public String getCreatTimeString() {
		return DateConvertUtils.format(getCreatTime(), FORMAT_CREAT_TIME);
	}
	public void setCreatTimeString(String value) {
		setCreatTime(DateConvertUtils.parse(value, FORMAT_CREAT_TIME,java.util.Date.class));
	}
	
	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}
	
	public java.util.Date getCreatTime() {
		return this.creatTime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SupplierId",getSupplierId())
			.append("SupplierName",getSupplierName())
			.append("SubOrderId",getSubOrderId())
			.append("SubOrderUserId",getSubOrderUserId())
			.append("SubOrderUserName",getSubOrderUserName())
			.append("ProductIds",getProductIds())
			.append("Commission",getCommission())
			.append("CreatTime",getCreatTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Commission == false) return false;
		if(this == obj) return true;
		Commission other = (Commission)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

