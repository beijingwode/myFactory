/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import com.wode.factory.model.SupplierTicketFlow;

public class SupplierTicketFlowVo extends SupplierTicketFlow implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4108573040499307631L;
	private String supplierName;	//商家名称
	private String financeCode;
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getFinanceCode() {
		return financeCode;
	}
	public void setFinanceCode(String financeCode) {
		this.financeCode = financeCode;
	}
}

