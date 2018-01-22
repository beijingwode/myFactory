package com.wode.factory.model.open;

import java.math.BigDecimal;

import com.wode.factory.model.OpenRequestBase;

public class InvoiceGet extends OpenRequestBase {
	
	/**
	 * 订单id
	 */
	private String trade_id;
	
	/**
	 * 发票号
	 */
	private String invoice;
	/**
	 * 销售方
	 */
	private String seller;
	/**
	 * 电子发票
	 */
	private String electronicInvoice;
	/**
	 * 纸质发票
	 */
	private String paperInvoice;
	/**
	 * 价税合计
	 */
	private BigDecimal price;
	/**
	 * 发票类型 发票类型 0.电子增值税普通发票 1.电子增值税专用发票 2.纸质发票
	 */
	private Integer type;
	
	private String title;
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getElectronicInvoice() {
		return electronicInvoice;
	}
	public void setElectronicInvoice(String electronicInvoice) {
		this.electronicInvoice = electronicInvoice;
	}
	public String getPaperInvoice() {
		return paperInvoice;
	}
	public void setPaperInvoice(String paperInvoice) {
		this.paperInvoice = paperInvoice;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
