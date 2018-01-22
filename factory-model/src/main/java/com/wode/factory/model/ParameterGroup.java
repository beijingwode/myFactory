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

@Table("t_parameter_group")
public class ParameterGroup extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ParameterGroup";
	
	public static final String ALIAS_ID = "参数组ID";
	
	public static final String ALIAS_NAME = "名称";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_ISMUST = "ismust";
	
	public static final String ALIAS_INPUTTYPE = "inputtype";
	
	public static final String ALIAS_CATEGORY_ID = "绑定分类";
	
	public static final String ALIAS_SHOW_TYPE = "展示类型 1 表示文本展示，2 表示图片展示";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 参数组ID       db_column: id  
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
     * ismust       db_column: ismust  
     * 
     * 
     * 
     */
	@Column
	private java.lang.Integer ismust;
    /**
     * inputtype       db_column: inputtype  
     * 
     * 
     * @Length(max=100)
     */	
	@Column
	private java.lang.String inputtype;
    /**
     * 绑定分类       db_column: category_id  
     * 
     * 
     * 
     */	
	@Column("category_id")
	private java.lang.Long categoryId;
    /**
     * 展示类型 1 表示文本展示，2 表示图片展示       db_column: showType  
     * 
     * 
     * 
     */
	@Column
	private java.lang.Integer showType;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */
	@Column
	private java.util.Date createDate;
    /**Parameter
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */	
	@Column
	private java.util.Date updateDate;
	
	
	@One(target = ProductCategory.class, field = "categoryId")
	private ProductCategory productCategory;
	
	
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	@Many(target = Parameter.class, field = "parameterGroupId")
	private List<Parameter> parameterlist = new ArrayList<Parameter>();
	
	private String selectedValue;//已经选择的那个值，只供显示:适用于product修改
	//columns END

	public List<Parameter> getParameterlist() {
		return parameterlist;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public void setParameterlist(List<Parameter> parameterlist) {
		this.parameterlist = parameterlist;
	}

	public ParameterGroup(){
	}

	public ParameterGroup(
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

	public java.lang.Integer getIsmust() {
		return ismust;
	}

	public void setIsmust(java.lang.Integer ismust) {
		this.ismust = ismust;
	}

	public void setInputtype(java.lang.String value) {
		this.inputtype = value;
	}
	
	public java.lang.String getInputtype() {
		return this.inputtype;
	}
	public void setCategoryId(java.lang.Long value) {
		this.categoryId = value;
	}
	
	public java.lang.Long getCategoryId() {
		return this.categoryId;
	}
	public void setShowType(java.lang.Integer value) {
		this.showType = value;
	}
	
	public java.lang.Integer getShowType() {
		return this.showType;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Orders",getOrders())
			.append("Ismust",getIsmust())
			.append("Inputtype",getInputtype())
			.append("CategoryId",getCategoryId())
			.append("ShowType",getShowType())
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
		if(obj instanceof ParameterGroup == false) return false;
		if(this == obj) return true;
		ParameterGroup other = (ParameterGroup)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

