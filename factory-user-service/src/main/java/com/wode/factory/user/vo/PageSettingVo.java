package com.wode.factory.user.vo;

import java.io.Serializable;

public class PageSettingVo implements Serializable{
	
	private String proName;
	private String proPrice;
	private String proSale;
	private String proDescription;
	
	private String name;
	private Long id;
	private long pageTypeId;
	private String pageTypeTitle;
	private String title;
	private String imagePath;
	private Integer saleKbn;	//销售区分 0：普通/1:特省',
	private String saleNote;	//让利原因
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String link;
	
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	private String regex;
	
	
	
	public String getPageTypeTitle() {
		return pageTypeTitle;
	}
	public void setPageTypeTitle(String pageTypeTitle) {
		this.pageTypeTitle = pageTypeTitle;
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
	public String getProDescription() {
		return proDescription;
	}
	public void setProDescription(String proDescription) {
		this.proDescription = proDescription;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPageTypeId() {
		return pageTypeId;
	}
	public void setPageTypeId(long pageTypeId) {
		this.pageTypeId = pageTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	
	
	public String getProSale() {
		return proSale;
	}
	public void setProSale(String proSale) {
		this.proSale = proSale;
	}
	public PageSettingVo(String name, long pageTypeId, String title,
			String imagePath, String link) {
		this.name = name;
		this.pageTypeId = pageTypeId;
		this.title = title;
		this.imagePath = imagePath;
		this.link = link;
	}
	public PageSettingVo() {
	}
	
}
