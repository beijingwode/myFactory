/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;


import java.util.List;

import com.wode.factory.model.SupplierCategory;

/**商家审核
 * @author user
 *
 */
public class SupplierCheckVo implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商家id
	 */
	private java.lang.Long id;
	/**
	 * 状态
	 */
	private java.lang.Integer status;
	/**
	 * 审核内容
	 */
	private java.lang.String opinion;
	/**
	 * 账期
	 */
	private java.util.Date bill_date;
	/**
	 * 类型
	 */
	private java.lang.String bill_type;

	private List<SupplierCategory> commission;
	public java.util.Date getBill_date() {
		return bill_date;
	}

	public void setBill_date(java.util.Date bill_date) {
		this.bill_date = bill_date;
	}

	public java.lang.String getBill_type() {
		return bill_type;
	}

	public void setBill_type(java.lang.String bill_type) {
		this.bill_type = bill_type;
	}


	public List<SupplierCategory> getCommission() {
		return commission;
	}

	public void setCommission(List<SupplierCategory> commission) {
		this.commission = commission;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getOpinion() {
		return opinion;
	}

	public void setOpinion(java.lang.String opinion) {
		this.opinion = opinion;
	}
	
	
}

