/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_page_data")
public class PageData extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "PageSetting";
	
	public static final String ALIAS_ID = "ID";
	
	public static final String ALIAS_PAGE_TYPE_ID = "页面类型ID";
	
	public static final String ALIAS_TITLE = "标题";
	
	public static final String ALIAS_IMAGE_PATH = "图片路径";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_LINK = "链接";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 页面类型ID       db_column: pageTypeId  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Long pageTypeId;
    /**
     * 标题       db_column: title  
     * 
     * 
     * @Length(max=100)
     */	
	@Column
	private java.lang.String title;
    /**
     * 图片路径       db_column: imagePath  
     * 
     * 
     * @Length(max=255)
     */	
	@Column
	private java.lang.String imagePath;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer orders;
    /**
     * 链接       db_column: link  
     * 
     * 
     * @Length(max=255)
     */	
	@Column
	private java.lang.String link;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */	
	@Column
	private java.util.Date createDate;
    /**
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */	
	@Column
	private java.util.Date updateDate;
	
	
	@Column
	private java.lang.String page;

	private Long productId;

	private boolean isLinkProduct;
	
	public java.lang.String getPage() {
		return page;
	}

	public void setPage(java.lang.String page) {
		this.page = page;
	}

	@One(target = ProductCategory.class, field = "pid")
	private ProductCategory productCategory;
	
	@One(target = PageTypeSetting.class, field = "pageTypeId")
	private PageTypeSetting pageTypeSetting;
	//columns END

	private Integer locked;	//锁定 0:未锁定/1:锁定',
	private String lockReason; //0:不限购/1:仅vip购/2:仅员工购'
	private java.lang.Integer isMarketable;
	

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public PageTypeSetting getPageTypeSetting() {
		return pageTypeSetting;
	}

	public void setPageTypeSetting(PageTypeSetting pageTypeSetting) {
		this.pageTypeSetting = pageTypeSetting;
	}

	public PageData(){
	}

	public PageData(
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
	public void setPageTypeId(java.lang.Long value) {
		this.pageTypeId = value;
	}
	
	public java.lang.Long getPageTypeId() {
		return this.pageTypeId;
	}
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setImagePath(java.lang.String value) {
		this.imagePath = value;
	}
	
	public java.lang.String getImagePath() {
		return this.imagePath;
	}
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}

	public Long getProductId() {
		return productId;
	}

	public void setLink(java.lang.String value) {
		this.link = value;
		if(isLinkProduct(value)) {
			this.productId = Long.valueOf(value);
			this.isLinkProduct = true;
		}
	}
	
	public java.lang.String getLink() {
		return this.link;
	}
	
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	
	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public boolean isLinkProduct() {
		return this.isLinkProduct;
	}

	public static boolean isLinkProduct(String link) {
		if(StringUtils.isNotBlank(link)) {
			Pattern pattern = Pattern.compile("^[1-9][0-9]+");
			Matcher matcher = pattern.matcher(link);
			return matcher.matches();
		}
		return false;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PageTypeId",getPageTypeId())
			.append("Title",getTitle())
			.append("ImagePath",getImagePath())
			.append("Orders",getOrders())
			.append("Link",getLink())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PageData == false) return false;
		if(this == obj) return true;
		PageData other = (PageData)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public java.lang.Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(java.lang.Integer isMarketable) {
		this.isMarketable = isMarketable;
	}
	
	
}

