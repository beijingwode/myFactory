/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;


import java.util.List;

import com.wode.factory.model.SaleBill;
import com.wode.factory.model.SaleDetail;

public class SaleBillDetailVo implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 对账单详情
	 */
	private SaleBill saleBill;
	/**
	 * 对账单对应订单详情
	 */
	private List<SaleDetail> saleDetail;

	
	public SaleBill getSaleBill() {
		return saleBill;
	}

	public void setSaleBill(SaleBill saleBill) {
		this.saleBill = saleBill;
	}

	public List<SaleDetail> getSaleDetail() {
		return saleDetail;
	}

	public void setSaleDetail(List<SaleDetail> saleDetail) {
		this.saleDetail = saleDetail;
	}
	
	
	
}

