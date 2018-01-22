package com.wode.factory.user.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;

public class PageDataVo implements Serializable {
    /**
     * 扩展1值       db_column: ex1_value
     * 
     * 
     * 
     */ 
    @Column("ex1_value")
    private java.lang.String ex1Value;
    /**
     * 扩展2值       db_column: ex2_value
     * 
     * 
     * 
     */ 
    @Column("ex2_value")
    private java.lang.String ex2Value;
    /**
     * 扩展3值       db_column: ex3_value
     * 
     * 
     * 
     */ 
    @Column("ex3_value")
    private java.lang.String ex3Value;
    /**
     * 扩展4值       db_column: ex3_value
     * 
     */ 
    @Column("ex4_value")
    private java.lang.String ex4Value;
    /**
     * 扩展5值       db_column: ex3_value
     * 
     */ 
    @Column("ex5_value")
    private java.lang.String ex5Value;
    /**
     * 扩展6值       db_column: ex3_value
     * 
     */ 
    @Column("ex6_value")
    private java.lang.String ex6Value;
    
	private String proName;
	private String proPrice;
	private String proSale;
	private String proDescription;
	private Integer saleKbn;	//销售区分 0：普通/1:特省',
	private String saleNote;	//让利原因
	private Integer stock;//库存
	private String maxFucoin;//最大可使用的福利币（联盟员工福利币）
	private Integer allStock;//总库存
	private String minSkuId;//规格id;
	private String marketPrice;//电商价;
	/**
	 * 标题
	 */
	private java.lang.String title;
	/**
	 * 标题1
	 */
	private java.lang.String title1;
	/**
	 * 标题2
	 */
	private java.lang.String title2;
	/**
	 * 图片路径
	 */
	private java.lang.String imagePath;
	/**
	 * 连接
	 */
	private java.lang.String link;

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getImagePath() {
		return imagePath;
	}

	public void setImagePath(java.lang.String imagePath) {
		this.imagePath = imagePath;
	}

	public java.lang.String getLink() {
		return link;
	}

	public void setLink(java.lang.String link) {
		this.link = link;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProPrice() {
		return proPrice;
	}

	public void setProPrice(String proPrice) {
		this.proPrice = proPrice;
	}

	public String getProSale() {
		return proSale;
	}

	public void setProSale(String proSale) {
		this.proSale = proSale;
	}

	public String getProDescription() {
		return proDescription;
	}

	public void setProDescription(String proDescription) {
		this.proDescription = proDescription;
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

	public java.lang.String getEx1Value() {
		return ex1Value;
	}

	public void setEx1Value(java.lang.String ex1Value) {
		this.ex1Value = ex1Value;
	}

	public java.lang.String getEx2Value() {
		return ex2Value;
	}

	public void setEx2Value(java.lang.String ex2Value) {
		this.ex2Value = ex2Value;
	}

	public java.lang.String getEx3Value() {
		return ex3Value;
	}

	public void setEx3Value(java.lang.String ex3Value) {
		this.ex3Value = ex3Value;
	}

	public java.lang.String getEx4Value() {
		return ex4Value;
	}

	public void setEx4Value(java.lang.String ex4Value) {
		this.ex4Value = ex4Value;
	}
	

	public java.lang.String getTitle1() {
		return title1;
	}

	public void setTitle1(java.lang.String title1) {
		this.title1 = title1;
	}

	public java.lang.String getTitle2() {
		return title2;
	}

	public void setTitle2(java.lang.String title2) {
		this.title2 = title2;
	}

	public java.lang.String getEx5Value() {
		return ex5Value;
	}

	public void setEx5Value(java.lang.String ex5Value) {
		this.ex5Value = ex5Value;
	}

	public java.lang.String getEx6Value() {
		return ex6Value;
	}

	public void setEx6Value(java.lang.String ex6Value) {
		this.ex6Value = ex6Value;
	}

	public java.lang.Integer getStock() {
		return stock;
	}

	public void setStock(java.lang.Integer stock) {
		this.stock = stock;
	}

	public String getMaxFucoin() {
		return maxFucoin;
	}

	public void setMaxFucoin(String maxFucoin) {
		this.maxFucoin = maxFucoin;
	}

	public java.lang.Integer getAllStock() {
		return allStock;
	}

	public void setAllStock(java.lang.Integer allStock) {
		this.allStock = allStock;
	}

	public String getMinSkuId() {
		return minSkuId;
	}

	public void setMinSkuId(String minSkuId) {
		this.minSkuId = minSkuId;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	
}