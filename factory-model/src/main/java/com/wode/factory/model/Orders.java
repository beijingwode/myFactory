/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_orders")
public class Orders extends BaseModel implements java.io.Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6920243507061863495L;
	//columns START
    /**
     * 订单号ID       db_column: orderId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long orderId;
    /**
     * 用户ID       db_column: userId  
     * 
     * 
     * 
     */
	@Column("userId")
	private java.lang.Long userId;
    /**
     * 收货人       db_column: name  
     * 
     * 
     * @Length(max=50)
     */
	@Column("name")
	private java.lang.String name;
    /**
     * 座机       db_column: phone  
     * 
     * 
     * @Length(max=50)
     */
	@Column("phone")
	private java.lang.String phone;
    /**
     * 手机       db_column: mobile  
     * 
     * 
     * @Length(max=50)
     */
	@Column("mobile")
	private java.lang.String mobile;
    /**
     * 收获地址       db_column: address  
     * 
     * 
     * @Length(max=100)
     */
	@Column("address")
	private java.lang.String address;
    /**
     * 总价       db_column: totalProduct  
     * 
     * 
     * 
     */
	@Column("totalProduct")
	private BigDecimal totalProduct;
    /**
     * 总运费       db_column: totalShipping  
     * 
     * 
     * 
     */
	@Column("totalShipping")
	private BigDecimal totalShipping;
    /**
     * 总折扣       db_column: totalAdjustment  
     * 
     * 
     * 
     */
	@Column("totalAdjustment")
	private BigDecimal totalAdjustment;
    /**
     * 实付金额       db_column: realPrice  
     * 
     * 
     * 
     */
	@Column("realPrice")
	private BigDecimal realPrice;
    /**
     * 状态       db_column: status  
     * 
     * 
     * @Length(max=2)
     */	
	@Column("status")
	private  int status;
    /**
     * 发票抬头       db_column: invoiceTitle  
     * 
     * 
     * @Length(max=50)
     */
	@Column("invoiceTitle")
	private java.lang.String invoiceTitle;
    /**
     * 备注       db_column: note  
     * 
     * 
     * @Length(max=100)
     */
	@Column("note")
	private java.lang.String note;
    /**
     * 创建时间       db_column: createTime  
     * 
     * 
     * 
     */
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: updateTime  
     * 
     * 
     * 
     */
	@Column("updateTime")
	private java.util.Date updateTime;
    /**
     * 修改者       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("updateBy")
	private java.lang.String updateBy;
    /**
     * 自提 1:自提订单       db_column: self_delivery  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("self_delivery")
	private java.lang.String selfDelivery;
	
	private Long invoiceId;
	
	
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	//columns END
	private List<Suborder> items;
	

	public List<Suborder> getItems() {
		return items;
	}

	public void setItems(List<Suborder> items) {
		this.items = items;
	}

	public Orders(){
	}

	public Orders(
		java.lang.Long orderId
	){
		this.orderId = orderId;
	}

	public void setOrderId(java.lang.Long value) {
		this.orderId = value;
	}
	
	public java.lang.Long getOrderId() {
		return this.orderId;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	
	public java.lang.String getMobile() {
		if(this.mobile==null||this.mobile.trim().equals("")){
			return this.phone;
		}else{
			return this.mobile;
		}
	}
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setTotalProduct(BigDecimal value) {
		this.totalProduct = value;
	}
	
	public BigDecimal getTotalProduct() {
		return this.totalProduct;
	}
	public void setTotalShipping(BigDecimal value) {
		this.totalShipping = value;
	}
	
	public BigDecimal getTotalShipping() {
		return this.totalShipping;
	}
	public void setTotalAdjustment(BigDecimal value) {
		this.totalAdjustment = value;
	}
	
	public BigDecimal getTotalAdjustment() {
		return this.totalAdjustment;
	}
	public void setRealPrice(BigDecimal value) {
		this.realPrice = value;
	}
	
	public BigDecimal getRealPrice() {
		return this.realPrice;
	}
	public void setStatus(int value) {
		this.status = value;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setInvoiceTitle(java.lang.String value) {
		this.invoiceTitle = value;
	}
	
	public java.lang.String getInvoiceTitle() {
		return this.invoiceTitle;
	}
	public void setNote(java.lang.String value) {
		this.note = value;
	}
	
	public java.lang.String getNote() {
		return this.note;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	public java.lang.String getSelfDelivery() {
		return selfDelivery;
	}

	public void setSelfDelivery(java.lang.String selfDelivery) {
		this.selfDelivery = selfDelivery;
	}

	public boolean isSelf() {
		return "1".equals(selfDelivery);
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("OrderId",getOrderId())
			.append("UserId",getUserId())
			.append("Name",getName())
			.append("Phone",getPhone())
			.append("Mobile",getMobile())
			.append("Address",getAddress())
			.append("TotalProduct",getTotalProduct())
			.append("TotalShipping",getTotalShipping())
			.append("TotalAdjustment",getTotalAdjustment())
			.append("RealPrice",getRealPrice())
			.append("Status",getStatus())
			.append("InvoiceTitle",getInvoiceTitle())
			.append("Note",getNote())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateBy",getUpdateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOrderId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Orders == false) return false;
		if(this == obj) return true;
		Orders other = (Orders)obj;
		return new EqualsBuilder()
			.append(getOrderId(),other.getOrderId())
			.isEquals();
	}
}

