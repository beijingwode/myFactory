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


public class Invoice extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Invoice";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_USER_ID = "用户ID";
	
	public static final String ALIAS_TITLE = "发票抬头";
	
	public static final String ALIAS_TYPE = "发票类型";
	
	public static final String ALIAS_IS_DEFAULT = "是否默认";
	
	//date formats
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 用户ID       db_column: userId  
     * 
     * 
     * 
     */	
	private java.lang.Long userId;
    /**
     * 发票抬头       db_column: title  
     * 
     * 
     * @Length(max=255)
     */	
	private java.lang.String title;
    /**
     * 发票类型       db_column: type  
     * 
     * 
     * @Max(127)
     */	
	private Integer type;
    /**
     * 是否默认       db_column: is_default  
     * 
     * 
     * @Max(127)
     */	
	private Integer isDefault;
	
	private Integer billType;//发票类型
    private String taxpayerNumber;//纳税人识别号
    private String registerAddress;//注册地址
    private String registerPhone;//注册电话
    private String openingBan;//开户行
    private String openingBanNumber;//开户行账号
	    
	//columns END

    
    
	public Invoice(){
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}

	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getOpeningBan() {
		return openingBan;
	}

	public void setOpeningBan(String openingBan) {
		this.openingBan = openingBan;
	}

	public String getOpeningBanNumber() {
		return openingBanNumber;
	}

	public void setOpeningBanNumber(String openingBanNumber) {
		this.openingBanNumber = openingBanNumber;
	}

	public Invoice(
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
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setIsDefault(Integer value) {
		this.isDefault = value;
	}
	
	public Integer getIsDefault() {
		return this.isDefault;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Title",getTitle())
			.append("Type",getType())
			.append("IsDefault",getIsDefault())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Invoice == false) return false;
		if(this == obj) return true;
		Invoice other = (Invoice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

