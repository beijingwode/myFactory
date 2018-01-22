/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class ProductBrandQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 品牌ID */
	private java.lang.Long id;
	/** 品牌中文名称 */
	private java.lang.String name;
	/** logo */
	private java.lang.String logo;
	/** 网址 */
	private java.lang.String url;
	/** 排序 */
	private Integer orders;
	/** 分类 */
	private java.lang.Long categoryId;
	/** 介绍 */
	private java.lang.String introduction;
	/** 创建时间 */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** 更新时间 */
	private java.util.Date updateDateBegin;
	private java.util.Date updateDateEnd;
	/** 修改者 */
	private java.lang.String updateBy;
	/** 品牌名称英文 */
	private java.lang.String nameEn;
	/** 0 自有品牌 */
	private java.lang.Integer natural;
	/** 0 TM标   1R标 */
	private java.lang.Integer brandType;
	/** 商标注册号 */
	private java.lang.String brandNo;
	/** 商标注册日期 */
	private java.util.Date brandcreatedTmBegin;
	private java.util.Date brandcreatedTmEnd;
	/** r标有效期开始时间 */
	private java.util.Date begintimeR;
	/** r标有效期结束时间 */
	private java.util.Date endtimeR;
	/** 品牌资质文件地址 */
	private java.lang.String brandurl;
	/** 商家id */
	private java.lang.Long supplierId;
	/** 商家id */
	private java.lang.Long shopId;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getLogo() {
		return this.logo;
	}
	
	public void setLogo(java.lang.String value) {
		this.logo = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.String getIntroduction() {
		return this.introduction;
	}
	
	public void setIntroduction(java.lang.String value) {
		this.introduction = value;
	}
	
	public java.util.Date getCreateDateBegin() {
		return this.createDateBegin;
	}
	
	public void setCreateDateBegin(java.util.Date value) {
		this.createDateBegin = value;
	}	
	
	public java.util.Date getCreateDateEnd() {
		return this.createDateEnd;
	}
	
	public void setCreateDateEnd(java.util.Date value) {
		this.createDateEnd = value;
	}
	
	public java.util.Date getUpdateDateBegin() {
		return this.updateDateBegin;
	}
	
	public void setUpdateDateBegin(java.util.Date value) {
		this.updateDateBegin = value;
	}	
	
	public java.util.Date getUpdateDateEnd() {
		return this.updateDateEnd;
	}
	
	public void setUpdateDateEnd(java.util.Date value) {
		this.updateDateEnd = value;
	}
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}
	
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.String getNameEn() {
		return this.nameEn;
	}
	
	public void setNameEn(java.lang.String value) {
		this.nameEn = value;
	}
	
	public java.lang.Integer getNatural() {
		return this.natural;
	}
	
	public void setNatural(java.lang.Integer value) {
		this.natural = value;
	}
	
	public java.lang.Integer getBrandType() {
		return this.brandType;
	}
	
	public void setBrandType(java.lang.Integer value) {
		this.brandType = value;
	}
	
	public java.lang.String getBrandNo() {
		return this.brandNo;
	}
	
	public void setBrandNo(java.lang.String value) {
		this.brandNo = value;
	}
	
	public java.util.Date getBrandcreatedTmBegin() {
		return this.brandcreatedTmBegin;
	}
	
	public void setBrandcreatedTmBegin(java.util.Date value) {
		this.brandcreatedTmBegin = value;
	}	
	
	public java.util.Date getBrandcreatedTmEnd() {
		return this.brandcreatedTmEnd;
	}
	
	public void setBrandcreatedTmEnd(java.util.Date value) {
		this.brandcreatedTmEnd = value;
	}
	
	public java.util.Date getBegintimeR() {
		return this.begintimeR;
	}
	
	public void setBegintimeR(java.util.Date value) {
		this.begintimeR = value;
	}
	
	public java.util.Date getEndtimeR() {
		return this.endtimeR;
	}
	
	public void setEndtimeR(java.util.Date value) {
		this.endtimeR = value;
	}
	
	public java.lang.String getBrandurl() {
		return this.brandurl;
	}
	
	public void setBrandurl(java.lang.String value) {
		this.brandurl = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

