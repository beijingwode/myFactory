/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


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

@Table("t_page_channel_hot_category")
public class PageChannelHotCategory extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "PageChannelHotCategory";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_CHANNEL_ID = "频道id";
	
	public static final String ALIAS_HOT_CATEGORY_ID = "热销分类id";
	
	public static final String ALIAS_ORDERS = "序号";
	
	//date formats
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	@Column("id")
	private java.lang.Long id;
    /**
     * 频道id       db_column: channel_id  
     * 
     * 
     * @NotNull 
     */
	@Column("channel_id")
	private java.lang.Long channelId;
    /**
     * 热销分类id       db_column: hot_category_id  
     * 
     * 
     * @NotNull 
     */	
	@Column("hot_category_id")
	private java.lang.Long hotCategoryId;
    /**
     * 序号       db_column: orders  
     * 
     * 
     * 
     */	
	@Column("orders")
	private java.lang.Integer orders;
	//columns END
	
	@One(target = ProductCategory.class, field = "hotCategoryId")
	private ProductCategory productCategory;
	
	@One(target = ProductCategory.class, field = "channelId")
	private ProductCategory channelCategory;

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductCategory getChannelCategory() {
		return channelCategory;
	}

	public void setChannelCategory(ProductCategory channelCategory) {
		this.channelCategory = channelCategory;
	}

	public PageChannelHotCategory(){
	}

	public PageChannelHotCategory(
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
	public void setChannelId(java.lang.Long value) {
		this.channelId = value;
	}
	
	public java.lang.Long getChannelId() {
		return this.channelId;
	}
	public void setHotCategoryId(java.lang.Long value) {
		this.hotCategoryId = value;
	}
	
	public java.lang.Long getHotCategoryId() {
		return this.hotCategoryId;
	}
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ChannelId",getChannelId())
			.append("HotCategoryId",getHotCategoryId())
			.append("Orders",getOrders())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PageChannelHotCategory == false) return false;
		if(this == obj) return true;
		PageChannelHotCategory other = (PageChannelHotCategory)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

