/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.List;

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
import com.wode.factory.annotation.DefaultValue;

@Table("t_page_type_setting")
public class PageTypeSetting extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "PageTypeSetting";
	
	public static final String ALIAS_PAGE_TYPE_ID = "ID";
	
	public static final String ALIAS_TITLE = "标题";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_PID = "父ID";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_CREATE_DATE = "创建日期";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * ID       db_column: pageTypeId  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
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
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=100)
     */	
	@Column
	private java.lang.String name;
    /**
     * 父ID       db_column: page  
     * 
     * 
     * 
     */	
	@Column
	private String page;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer orders;
    /**
     * 创建日期       db_column: createDate  
     * 
     * 
     * 
     */	
	@Column
	@DefaultValue(dateValue="now")
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
	private List<PageData> pageDataList;
	public List<PageData> getPageDataList() {
		return pageDataList;
	}

	public void setPageDataList(List<PageData> pageDataList) {
		this.pageDataList = pageDataList;
	}

	/**
	 * 标示(1表示pc、2表示app)
	 */
	@Column
	private java.lang.Integer channelId;
	
	@One(target = ProductCategory.class, field = "page")
	private ProductCategory productCategory;
	
	public java.lang.Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(java.lang.Integer channelId) {
		this.channelId = channelId;
	}

	public static String getAliasCreateDate() {
		return ALIAS_CREATE_DATE;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	@Column("max_num")
	private int maxNum;
	//columns END

	public PageTypeSetting(){
	}

	public PageTypeSetting(
		java.lang.Long pageTypeId
	){
		this.pageTypeId = pageTypeId;
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setPage(String value) {
		this.page = value;
	}
	
	public String getPage() {
		return this.page;
	}
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("PageTypeId",getPageTypeId())
			.append("Title",getTitle())
			.append("Name",getName())
			.append("page",getPage())
			.append("Orders",getOrders())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getPageTypeId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PageTypeSetting == false) return false;
		if(this == obj) return true;
		PageTypeSetting other = (PageTypeSetting)obj;
		return new EqualsBuilder()
			.append(getPageTypeId(),other.getPageTypeId())
			.isEquals();
	}
}

