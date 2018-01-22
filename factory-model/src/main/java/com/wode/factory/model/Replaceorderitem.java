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


public class Replaceorderitem extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Replaceorderitem";
	
	public static final String ALIAS_REPLACE_ORDER_ITEM_ID = "换货项ID";
	
	public static final String ALIAS_REPLACE_ORDER_ID = "换货单";
	
	public static final String ALIAS_PART_NUMBER = "商品编码";
	
	public static final String ALIAS_NUMBER = "数量";
	
	public static final String ALIAS_REPLACE_PART_NUMBER = "换货商品编码";
	
	public static final String ALIAS_REPLACE_NUMBER = "换货数量";
	
	public static final String ALIAS_CREATE_TIME = "修改时间";
	
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	public static final String ALIAS_UPDATE_BY = "修改者";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_TIME = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 换货项ID       db_column: replaceOrderItemId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long replaceOrderItemId;
    /**
     * 换货单       db_column: replaceOrderId  
     * 
     * 
     * 
     */	
	private java.lang.Long replaceOrderId;
    /**
     * 商品编码       db_column: partNumber  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String partNumber;
    /**
     * 数量       db_column: number  
     * 
     * 
     * @Max(127)
     */	
	private Integer number;
    /**
     * 换货商品编码       db_column: replacePartNumber  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String replacePartNumber;
    /**
     * 换货数量       db_column: replaceNumber  
     * 
     * 
     * @Max(127)
     */	
	private Integer replaceNumber;
    /**
     * 修改时间       db_column: createTime  
     * 
     * 
     * 
     */	
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: updateTime  
     * 
     * 
     * 
     */	
	private java.util.Date updateTime;
    /**
     * 修改者       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String updateBy;
	//columns END

	public Replaceorderitem(){
	}

	public Replaceorderitem(
		java.lang.Long replaceOrderItemId
	){
		this.replaceOrderItemId = replaceOrderItemId;
	}

	public void setReplaceOrderItemId(java.lang.Long value) {
		this.replaceOrderItemId = value;
	}
	
	public java.lang.Long getReplaceOrderItemId() {
		return this.replaceOrderItemId;
	}
	public void setReplaceOrderId(java.lang.Long value) {
		this.replaceOrderId = value;
	}
	
	public java.lang.Long getReplaceOrderId() {
		return this.replaceOrderId;
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
	public void setReplacePartNumber(java.lang.String value) {
		this.replacePartNumber = value;
	}
	
	public java.lang.String getReplacePartNumber() {
		return this.replacePartNumber;
	}
	public void setReplaceNumber(Integer value) {
		this.replaceNumber = value;
	}
	
	public Integer getReplaceNumber() {
		return this.replaceNumber;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ReplaceOrderItemId",getReplaceOrderItemId())
			.append("ReplaceOrderId",getReplaceOrderId())
			.append("PartNumber",getPartNumber())
			.append("Number",getNumber())
			.append("ReplacePartNumber",getReplacePartNumber())
			.append("ReplaceNumber",getReplaceNumber())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateBy",getUpdateBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getReplaceOrderItemId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Replaceorderitem == false) return false;
		if(this == obj) return true;
		Replaceorderitem other = (Replaceorderitem)obj;
		return new EqualsBuilder()
			.append(getReplaceOrderItemId(),other.getReplaceOrderItemId())
			.isEquals();
	}
}

