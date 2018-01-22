/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import java.math.BigDecimal;
import java.util.List;

import com.wode.factory.model.SaleBill;



/**
 * 发票信息
 * @author user
 *
 */
public class ReceiptVo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1213123426402521920L;
	/**
	 * 商家名称
	 */
	private String supplier;
	/**
	 * 发票金额
	 */
	private List<SaleBill> price;
	/**
	 * 发票总金额
	 */
	private Double sumPrice;
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	 
	public List<SaleBill> getPrice() {
		return price;
	}
	public void setPrice(List<SaleBill> price) {
		this.price = price;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	 
}

