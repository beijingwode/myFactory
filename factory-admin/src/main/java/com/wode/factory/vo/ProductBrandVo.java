/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;

public class ProductBrandVo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String brandNo;
	private String logo;
	private String name;
	private String nameEN;
	private String natural;
	private String brandType;
	private String importFlg;
	private Date brandcreatedTM;
	private Date begintimeR;
	private Date endtimeR;
	private java.lang.Long categoryId;
	private Date createDate;
	private Long supId;
	private String supplierName;
	private Long managerId;
	private String managerName;
	private String shopName;
	private Long proCnt;
	private Date firstCreate;
	private String categorys;
	private java.util.Date begintimeAuth;
	private java.util.Date endtimeAuth;

	private Long checkId;
    private java.lang.String checkMemo;
    private java.util.Date checkAlarmDate;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBrandNo() {
		return brandNo;
	}
	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameEN() {
		return nameEN;
	}
	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}
	public String getNatural() {
		return natural;
	}
	public void setNatural(String natural) {
		this.natural = natural;
	}
	public String getBrandType() {
		return brandType;
	}
	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}
	public String getImportFlg() {
		return importFlg;
	}
	public void setImportFlg(String importFlg) {
		this.importFlg = importFlg;
	}
	public Date getBrandcreatedTM() {
		return brandcreatedTM;
	}
	public void setBrandcreatedTM(Date brandcreatedTM) {
		this.brandcreatedTM = brandcreatedTM;
	}
	public Date getBegintimeR() {
		return begintimeR;
	}
	public void setBegintimeR(Date begintimeR) {
		this.begintimeR = begintimeR;
	}
	public Date getEndtimeR() {
		return endtimeR;
	}
	public void setEndtimeR(Date endtimeR) {
		this.endtimeR = endtimeR;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getSupId() {
		return supId;
	}
	public void setSupId(Long supId) {
		this.supId = supId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getProCnt() {
		return proCnt;
	}
	public void setProCnt(Long proCnt) {
		this.proCnt = proCnt;
	}
	public String getCategorys() {
		return categorys;
	}
	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}
	public Date getFirstCreate() {
		return firstCreate;
	}
	public void setFirstCreate(Date firstCreate) {
		this.firstCreate = firstCreate;
	}
	public java.lang.Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(java.lang.Long categoryId) {
		this.categoryId = categoryId;
	}
	public Long getCheckId() {
		return checkId;
	}
	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}
	public java.lang.String getCheckMemo() {
		return checkMemo;
	}
	public void setCheckMemo(java.lang.String checkMemo) {
		this.checkMemo = checkMemo;
	}
	public java.util.Date getCheckAlarmDate() {
		return checkAlarmDate;
	}
	public void setCheckAlarmDate(java.util.Date checkAlarmDate) {
		this.checkAlarmDate = checkAlarmDate;
	}
	public java.util.Date getBegintimeAuth() {
		return begintimeAuth;
	}
	public void setBegintimeAuth(java.util.Date begintimeAuth) {
		this.begintimeAuth = begintimeAuth;
	}
	public java.util.Date getEndtimeAuth() {
		return endtimeAuth;
	}
	public void setEndtimeAuth(java.util.Date endtimeAuth) {
		this.endtimeAuth = endtimeAuth;
	}
	
}

