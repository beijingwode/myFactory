package com.wode.factory.user.vo;

import com.wode.factory.model.PageSectionData;

public class PageSectionDataVo extends PageSectionData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5984453962145108549L;

	private String sectionName; 
    private java.lang.String ex1Name;
    private java.lang.String ex2Name;
    private java.lang.String ex3Name;
    private java.lang.String ex4Name;
    private java.lang.String ex5Name;
    private java.lang.String ex6Name;
	
	private String proPrice;
	private String proSalePrice;
	private String proDiscount;
	private String proName;
	private String proBrand;
	private String tagFlg;
	private Integer saleKbn;	//销售区分 0：普通/1:特省',
	private String saleNote;	//让利原因
	private Integer quantity;	//库存',
	private String maxFucoin;	//内购券',
	
	
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getProPrice() {
		return proPrice;
	}
	public void setProPrice(String proPrice) {
		this.proPrice = proPrice;
	}
	public String getProSalePrice() {
		return proSalePrice;
	}
	public void setProSalePrice(String proSalePrice) {
		this.proSalePrice = proSalePrice;
	}
	public String getProDiscount() {
		return proDiscount;
	}
	public void setProDiscount(String proDiscount) {
		this.proDiscount = proDiscount;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getTagFlg() {
		return tagFlg;
	}
	public void setTagFlg(String tagFlg) {
		this.tagFlg = tagFlg;
	}
	public Integer getSaleKbn() {
		return saleKbn;
	}
	public void setSaleKbn(Integer saleKbn) {
		this.saleKbn = saleKbn;
	}
	public String getSaleNote() {
		return saleNote;
	}
	public void setSaleNote(String saleNote) {
		this.saleNote = saleNote;
	}
	public String getProBrand() {
		return proBrand;
	}
	public void setProBrand(String proBrand) {
		this.proBrand = proBrand;
	}
	public java.lang.String getEx1Name() {
		return ex1Name;
	}
	public void setEx1Name(java.lang.String ex1Name) {
		this.ex1Name = ex1Name;
	}
	public java.lang.String getEx2Name() {
		return ex2Name;
	}
	public void setEx2Name(java.lang.String ex2Name) {
		this.ex2Name = ex2Name;
	}
	public java.lang.String getEx3Name() {
		return ex3Name;
	}
	public void setEx3Name(java.lang.String ex3Name) {
		this.ex3Name = ex3Name;
	}
	public java.lang.String getEx4Name() {
		return ex4Name;
	}
	public void setEx4Name(java.lang.String ex4Name) {
		this.ex4Name = ex4Name;
	}
	public java.lang.String getEx5Name() {
		return ex5Name;
	}
	public void setEx5Name(java.lang.String ex5Name) {
		this.ex5Name = ex5Name;
	}
	public java.lang.String getEx6Name() {
		return ex6Name;
	}
	public void setEx6Name(java.lang.String ex6Name) {
		this.ex6Name = ex6Name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getMaxFucoin() {
		return maxFucoin;
	}
	public void setMaxFucoin(String maxFucoin) {
		this.maxFucoin = maxFucoin;
	}
	
	
}
