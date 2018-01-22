/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;
import com.wode.common.util.StringUtils;

import cn.org.rapid_framework.util.DateConvertUtils;

@Table("t_product_brand")
public class ProductBrand extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductBrand";
	
	public static final String ALIAS_ID = "品牌ID";
	
	public static final String ALIAS_NAME = "品牌中文名称";
	
	public static final String ALIAS_LOGO = "logo";
	
	public static final String ALIAS_URL = "网址";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_CATEGORY_ID = "分类";
	
	public static final String ALIAS_INTRODUCTION = "介绍";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	public static final String ALIAS_UPDATE_BY = "修改者";
	
	public static final String ALIAS_NAME_EN = "品牌名称英文";
	
	public static final String ALIAS_NATURAL = "0 自有品牌";
	
	public static final String ALIAS_BRAND_TYPE = "0 TM标   1R标";
	
	public static final String ALIAS_BRAND_NO = "商标注册号";
	
	public static final String ALIAS_BRANDCREATED_TM = "商标注册日期";
	
	public static final String ALIAS_BEGINTIME_R = "r标有效期开始时间";
	
	public static final String ALIAS_ENDTIME_R = "r标有效期结束时间";
	
	public static final String ALIAS_BRANDURL = "品牌资质文件地址";
	
	public static final String ALIAS_SUPPLIER_ID = "商家id";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_BRANDCREATED_TM = DATE_TIME_FORMAT;
	public static final String FORMAT_BEGINTIME_R = DATE_TIME_FORMAT;
	public static final String FORMAT_ENDTIME_R = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 品牌ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Column
	@Id
	private java.lang.Long id;
    /**
     * 品牌中文名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("name")
	private java.lang.String name;
    /**
     * logo       db_column: logo  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("logo")
	private java.lang.String logo;
    /**
     * 网址       db_column: url  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("url")
	private java.lang.String url;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	@Column("orders")
	private Integer orders;
    /**
     * 分类       db_column: category_id  
     * 
     * 
     * 
     */	
	@Column("category_id")
	private java.lang.Long categoryId;
    /**
     * 介绍       db_column: introduction  
     * 
     * 
     * @Length(max=65535)
     */	
	@Column("introduction")
	private java.lang.String introduction;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */	
	@Column("createDate")
	private java.util.Date createDate;
    /**
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */	
	@Column("updateDate")
	private java.util.Date updateDate;
    /**
     * 修改者       db_column: updateBy  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("updateBy")
	private java.lang.String updateBy;
    /**
     * 品牌名称英文       db_column: nameEN  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("nameEN")
	private java.lang.String nameEn;
    /**
     * 0 自有品牌       db_column: natural  
     * 
     * 
     * 
     */	
	@Column("natural")
	private java.lang.Integer natural;

    /**
     * 进口标致       db_column: import_flg  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("import_flg")
	private java.lang.String importFlg;
	
    /**
     * 0 TM标   1R标       db_column: brand_type  
     * 
     * 
     * 
     */	
	@Column("brand_type")
	private java.lang.Integer brandType;
    /**
     * 商标注册号       db_column: brandNo  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("brandNo")
	private java.lang.String brandNo;
    /**
     * 商标注册日期       db_column: brandcreatedTM  
     * 
     * 
     * 
     */	
	@Column("brandcreatedTM")
	private java.util.Date brandcreatedTm;
    /**
     * r标有效期开始时间       db_column: begintimeR  
     * 
     * 
     * 
     */	
	@Column("begintimeR")
	private java.util.Date begintimeR;
    /**
     * r标有效期结束时间       db_column: endtimeR  
     * 
     * 
     * 
     */	
	@Column("endtimeR")
	private java.util.Date endtimeR;
    /**
     * 品牌资质文件地址       db_column: brandurl  
     * 
     * 
     * @Length(max=200)
     */	
	@Column("brandurl")
	private java.lang.String brandurl;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("shop_id")
	private java.lang.Long shopId;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("old_id")
	private java.lang.Long oldId;
    /**
     * 审核字段  备用       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
    /**
     * 0 正常启用  1删除停用       db_column: is_delete  
     * 
     * 
     * 
     */	
	@Column("is_delete")
	private java.lang.Integer isDelete;
    /**
     * r标有效期开始时间       db_column: begintimeR  
     * 
     * 
     * 
     */	
	@Column("begintimeAuth")
	private java.util.Date begintimeAuth;
    /**
     * r标有效期结束时间       db_column: endtimeR  
     * 
     * 
     * 
     */	
	@Column("endtimeAuth")
	private java.util.Date endtimeAuth;
	//columns END
	
    private java.lang.String checkMemo;
    private java.util.Date checkAlarmDate;
	
    private List<ProductBrandImage> imageList;
    private List<String> brandurlList;
	
	public ProductBrand(){
	}

	public ProductBrand(
		java.lang.Long id
	){
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setLogo(java.lang.String value) {
		this.logo = value;
	}
	
	public java.lang.String getLogo() {
		return this.logo;
	}
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	public void setIntroduction(java.lang.String value) {
		this.introduction = value;
	}
	
	public java.lang.String getIntroduction() {
		return this.introduction;
	}
	public String getCreateDateString() {
		return DateConvertUtils.format(getCreateDate(), FORMAT_CREATE_DATE);
	}
	public void setCreateDateString(String value) {
		setCreateDate(DateConvertUtils.parse(value, FORMAT_CREATE_DATE,java.util.Date.class));
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public String getUpdateDateString() {
		return DateConvertUtils.format(getUpdateDate(), FORMAT_UPDATE_DATE);
	}
	public void setUpdateDateString(String value) {
		setUpdateDate(DateConvertUtils.parse(value, FORMAT_UPDATE_DATE,java.util.Date.class));
	}
	
	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateBy(java.lang.String value) {
		this.updateBy = value;
	}
	
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}
	public void setNameEn(java.lang.String value) {
		this.nameEn = value;
	}
	
	public java.lang.String getNameEn() {
		return this.nameEn;
	}
	public void setNatural(java.lang.Integer value) {
		this.natural = value;
	}
	
	public java.lang.Integer getNatural() {
		return this.natural;
	}
	public void setBrandType(java.lang.Integer value) {
		this.brandType = value;
	}
	
	public java.lang.Integer getBrandType() {
		return this.brandType;
	}
	public void setBrandNo(java.lang.String value) {
		this.brandNo = value;
	}
	
	public java.lang.String getBrandNo() {
		return this.brandNo;
	}
	public String getBrandcreatedTmString() {
		return DateConvertUtils.format(getBrandcreatedTm(), FORMAT_BRANDCREATED_TM);
	}
	public void setBrandcreatedTmString(String value) {
		setBrandcreatedTm(DateConvertUtils.parse(value, FORMAT_BRANDCREATED_TM,java.util.Date.class));
	}
	
	public void setBrandcreatedTm(java.util.Date value) {
		this.brandcreatedTm = value;
	}
	
	public java.util.Date getBrandcreatedTm() {
		return this.brandcreatedTm;
	}
	public String getBegintimeRString() {
		return DateConvertUtils.format(getBegintimeR(), FORMAT_BEGINTIME_R);
	}
	public void setBegintimeRString(String value) {
		setBegintimeR(DateConvertUtils.parse(value, FORMAT_BEGINTIME_R,java.util.Date.class));
	}
	
	public void setBegintimeR(java.util.Date value) {
		this.begintimeR = value;
	}
	
	public java.util.Date getBegintimeR() {
		return this.begintimeR;
	}
	public String getEndtimeRString() {
		return DateConvertUtils.format(getEndtimeR(), FORMAT_ENDTIME_R);
	}
	public void setEndtimeRString(String value) {
		setEndtimeR(DateConvertUtils.parse(value, FORMAT_ENDTIME_R,java.util.Date.class));
	}
	
	public void setEndtimeR(java.util.Date value) {
		this.endtimeR = value;
	}
	
	public java.util.Date getEndtimeR() {
		return this.endtimeR;
	}
	public void setBrandurl(java.lang.String value) {
		this.brandurl = value;
	}
	
	public java.lang.String getBrandurl() {
		return this.brandurl;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}

	public List<ProductBrandImage> getImageList() {
		return imageList;
	}

	public void setImageList(List<ProductBrandImage> imageList) {
		this.imageList = imageList;
	}

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public java.lang.Long getOldId() {
		return oldId;
	}

	public void setOldId(java.lang.Long oldId) {
		this.oldId = oldId;
	}

	public java.lang.String getImportFlg() {
		return importFlg;
	}

	public void setImportFlg(java.lang.String importFlg) {
		this.importFlg = importFlg;
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

	public List<String> getBrandurlList() {
		if(brandurlList==null) {
			brandurlList=new ArrayList<String>();
			if(!StringUtils.isEmpty(this.brandurl)) {
				String[] urls=this.brandurl.split(",");
				for (String string : urls) {
					brandurlList.add(string.trim());
				}
			}
			
		}
		return brandurlList;
	}

	public void setBrandurlList(List<String> brandurlList) {
		this.brandurlList = brandurlList;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Logo",getLogo())
			.append("Url",getUrl())
			.append("Orders",getOrders())
			.append("CategoryId",getCategoryId())
			.append("Introduction",getIntroduction())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.append("UpdateBy",getUpdateBy())
			.append("NameEn",getNameEn())
			.append("Natural",getNatural())
			.append("BrandType",getBrandType())
			.append("BrandNo",getBrandNo())
			.append("BrandcreatedTm",getBrandcreatedTm())
			.append("BegintimeR",getBegintimeR())
			.append("EndtimeR",getEndtimeR())
			.append("Brandurl",getBrandurl())
			.append("SupplierId",getSupplierId())
			.append("ShopId",getShopId())
			.append("Status",getStatus())
			.append("IsDelete",getIsDelete())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductBrand == false) return false;
		if(this == obj) return true;
		ProductBrand other = (ProductBrand)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

