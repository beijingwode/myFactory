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
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_returnorder_item")
public class Returnorderitem extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Returnorderitem";
	
	public static final String ALIAS_RETURN_ORDER_ITEM_ID = "退货单项ID";
	
	public static final String ALIAS_RETURN_ORDER_ID = "退货单";
	
	public static final String ALIAS_PART_NUMBER = "商品编码";
	
	public static final String ALIAS_NUMBER = "数量";
	
	public static final String ALIAS_PRICE = "单价";
	
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	public static final String ALIAS_UPDATE_BY = "修改者";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 退货单项ID       db_column: returnOrderItemId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	@Column("returnOrderItemId")
	private java.lang.Long returnOrderItemId;
    /**
     * 退货单       db_column: returnOrderId  
     * 
     * 
     * 
     */	
	@Column("returnOrderId")
	private java.lang.Long returnOrderId;
    /**
     * 商品编码       db_column: partNumber  
     * 
     * 
     * @Length(max=100)
     */
	@Column("partNumber")
	private java.lang.String partNumber;
    /**
     * 数量       db_column: number  
     * 
     * 
     * @Max(127)
     */
	@Column("number")
	private Integer number;
    /**
     * 单价       db_column: price  
     * 
     * 
     * 
     */
	@Column("price")
	private BigDecimal price;
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
     * 子单项       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */
	@Column("sub_order_item_id")
	private java.lang.Long subOrderItemId;
	//columns END
	
	public Returnorderitem(){
	}

	public Returnorderitem(
		java.lang.Long returnOrderItemId
	){
		this.returnOrderItemId = returnOrderItemId;
	}

	public void setReturnOrderItemId(java.lang.Long value) {
		this.returnOrderItemId = value;
	}
	
	public java.lang.Long getReturnOrderItemId() {
		return this.returnOrderItemId;
	}
	public void setReturnOrderId(java.lang.Long value) {
		this.returnOrderId = value;
	}
	
	public java.lang.Long getReturnOrderId() {
		return this.returnOrderId;
	}
	public void setPartNumber(java.lang.String value) {
		this.partNumber = value;
	}
	
	public java.lang.String getPartNumber() {
		return this.partNumber;
	}
	public void setNumber(Integer value) {
		this.number = value;
	}
	
	public Integer getNumber() {
		return this.number;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_UPDATE_TIME);
	}
	public void setUpdateTimeString(String value) {
		setUpdateTime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
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

	public java.lang.Long getSubOrderItemId() {
		return subOrderItemId;
	}

	public void setSubOrderItemId(java.lang.Long subOrderItemId) {
		this.subOrderItemId = subOrderItemId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ReturnOrderItemId",getReturnOrderItemId())
			.append("ReturnOrderId",getReturnOrderId())
			.append("PartNumber",getPartNumber())
			.append("Number",getNumber())
			.append("Price",getPrice())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateBy",getUpdateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getReturnOrderItemId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Returnorderitem == false) return false;
		if(this == obj) return true;
		Returnorderitem other = (Returnorderitem)obj;
		return new EqualsBuilder()
			.append(getReturnOrderItemId(),other.getReturnOrderItemId())
			.isEquals();
	}
}

