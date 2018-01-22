package com.wode.factory.model;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
/**
 * 开具的发票
 * @author user
 *
 */
@Table("t_issued_invoice")
public class IssuedInvoice extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -468050071301259330L;
	
	@PrimaryKey
    @Column("id")
    private java.lang.Long id;
	/**
	 * 抬头
	 */
	@Column("title")
	private String title;
	/**
	 * 发票号
	 */
	@Column("invoice")
	private String invoice;
	/**
	 * 销售方
	 */
	@Column("seller")
	private String seller;
	/**
	 * 订单id
	 */
	@Column("suborderid")
	private String suborderid;
	/**
	 * 电子发票
	 */
	@Column("electronic_invoice")
	private String electronicInvoice;
	/**
	 * 纸质发票
	 */
	@Column("paper_invoice")
	private String paperInvoice;
	/**
	 * 价税合计
	 */
	@Column("price")
	private BigDecimal price;
	/**
	 * 创建时间
	 */
	@Column("createtime")
	private java.util.Date createtime;
	/**
	 * 发票类型 发票类型 0.电子增值税普通发票 1.电子增值税专用发票 2.纸质发票
	 */
	@Column("type")
	private Integer type;
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getSuborderid() {
		return suborderid;
	}
	public void setSuborderid(String suborderid) {
		this.suborderid = suborderid;
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
	public java.util.Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "IssuedInvoice [id=" + id + ", title=" + title + ", invoice=" + invoice + ", seller=" + seller
				+ ", suborderid=" + suborderid + ", electronicInvoice=" + electronicInvoice + ", paperInvoice="
				+ paperInvoice + ", price=" + price + ", createtime=" + createtime + "]";
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
