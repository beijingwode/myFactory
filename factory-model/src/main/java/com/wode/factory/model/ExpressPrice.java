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


public class ExpressPrice extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ExpressPrice";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_COM_ID = "快递品牌";
	
	public static final String ALIAS_SUPPLIER_ID = "供应商";
	
	public static final String ALIAS_FIRST_PRICE = "首重价格";
	
	public static final String ALIAS_FIRST_UNIT = "首重单位";
	
	public static final String ALIAS_CONTINUE_PRICE = "续重价格";
	
	public static final String ALIAS_CONTINUE_UNIT = "续重单位";
	
	//date formats
	
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
     * 快递品牌       db_column: com_id  
     * 
     * 
     * 
     */	
	private java.lang.Long comId;
    /**
     * 供应商       db_column: supplier_id  
     * 
     * 
     * 
     */	
	private java.lang.Long supplierId;
    /**
     * 首重价格       db_column: first_price  
     * 
     * 
     * 
     */	
	private Long firstPrice;
    /**
     * 首重单位       db_column: first_unit  
     * 
     * 
     * @Length(max=10)
     */	
	private java.lang.String firstUnit;
    /**
     * 续重价格       db_column: continue_price  
     * 
     * 
     * 
     */	
	private Long continuePrice;
    /**
     * 续重单位       db_column: continue_unit  
     * 
     * 
     * @Length(max=10)
     */	
	private java.lang.String continueUnit;
	//columns END

	public ExpressPrice(){
	}

	public ExpressPrice(
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
	public void setComId(java.lang.Long value) {
		this.comId = value;
	}
	
	public java.lang.Long getComId() {
		return this.comId;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	public void setFirstPrice(Long value) {
		this.firstPrice = value;
	}
	
	public Long getFirstPrice() {
		return this.firstPrice;
	}
	public void setFirstUnit(java.lang.String value) {
		this.firstUnit = value;
	}
	
	public java.lang.String getFirstUnit() {
		return this.firstUnit;
	}
	public void setContinuePrice(Long value) {
		this.continuePrice = value;
	}
	
	public Long getContinuePrice() {
		return this.continuePrice;
	}
	public void setContinueUnit(java.lang.String value) {
		this.continueUnit = value;
	}
	
	public java.lang.String getContinueUnit() {
		return this.continueUnit;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ComId",getComId())
			.append("SupplierId",getSupplierId())
			.append("FirstPrice",getFirstPrice())
			.append("FirstUnit",getFirstUnit())
			.append("ContinuePrice",getContinuePrice())
			.append("ContinueUnit",getContinueUnit())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExpressPrice == false) return false;
		if(this == obj) return true;
		ExpressPrice other = (ExpressPrice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

