/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import java.math.BigDecimal;

import com.wode.factory.model.EmpBenefitFlow;

public class EmpBenefitFlowVo extends EmpBenefitFlow implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6788947399619715189L;
	private String itemValues;
	private String productName;
    private BigDecimal price;
    private Integer number;
	public String getItemValues() {
		return itemValues;
	}
	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
    
    
	
}

