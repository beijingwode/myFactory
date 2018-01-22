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
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_attribute")
public class Attribute extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Attribute";
	
	public static final String ALIAS_ID = "属性ID";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_PRODUCT_CATEGORY = "绑定分类";
	
	public static final String ALIAS_INPUTTYPE = "值类型";
	
	public static final String ALIAS_ISMUST = "是否必填";
	
	public static final String ALIAS_DEFAULT_DISPLAY = "默认显示";
	
	public static final String ALIAS_DEFAULT_VAL = "默认值";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	public static final String ALIAS_FOR_SEARCH = "是否可搜索";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	
	//columns START
    /**
     * 属性ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */	
	@Column
	private java.lang.String name;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	@Column
	private Integer orders;
   
	
    /**
     * 值类型       db_column: inputtype  
     * 
     * 
     * @Length(max=50)
     */
	@Column
	private Integer inputtype;
    /**
     * 是否必填       db_column: ismust  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer ismust;
    /**
     * 默认显示       db_column: default_display  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("default_display")
	private java.lang.String defaultDisplay;
    /**
     * 默认值       db_column: default_val  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("default_val")
	private java.lang.String defaultVal;
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
    /**
     * 是否可搜索       db_column: for_search  
     * 
     * 
     * 
     */	
	@Column("for_search")
	private java.lang.Integer forSearch;
	 /**
     * 绑定分类       db_column: category_id  
     * 配置一对多的关系
     * 
     * 
     */	
	@Column("category_id")
	private Long categoryId;
	
	
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	@One(target = ProductCategory.class, field = "categoryId")
	private ProductCategory productCategory;
	
	@Many(target = AttributeOption.class, field = "attributeId")
	List<AttributeOption> optionlist = new ArrayList<AttributeOption>();

	//columns END

	
	/*********以下字段只供显示*********/
	private String selectedValue;//已经选择的那个值，只供显示:适用于product修改
	public Attribute(){
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public List<AttributeOption> getOptionlist() {
		return optionlist;
	}

	public void setOptionlist(List<AttributeOption> optionlist) {
		this.optionlist = optionlist;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Attribute(
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
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}

	
	public Integer getInputtype() {
		return inputtype;
	}

	public void setInputtype(Integer inputtype) {
		this.inputtype = inputtype;
	}

	public void setIsmust(java.lang.Integer value) {
		this.ismust = value;
	}
	
	public java.lang.Integer getIsmust() {
		return this.ismust;
	}
	public void setDefaultDisplay(java.lang.String value) {
		this.defaultDisplay = value;
	}
	
	public java.lang.String getDefaultDisplay() {
		return this.defaultDisplay;
	}
	public void setDefaultVal(java.lang.String value) {
		this.defaultVal = value;
	}
	
	public java.lang.String getDefaultVal() {
		return this.defaultVal;
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
	public void setForSearch(java.lang.Integer value) {
		this.forSearch = value;
	}
	
	public java.lang.Integer getForSearch() {
		return this.forSearch;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Orders",getOrders())
			.append("product",getProductCategory())
			.append("Inputtype",getInputtype())
			.append("Ismust",getIsmust())
			.append("DefaultDisplay",getDefaultDisplay())
			.append("DefaultVal",getDefaultVal())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.append("ForSearch",getForSearch())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Attribute == false) return false;
		if(this == obj) return true;
		Attribute other = (Attribute)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

