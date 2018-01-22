/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.vo;


import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

public class AttributeVo extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	//columns START
    /**
     * 属性ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	private java.lang.Long id;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String name;
	
	
	private java.lang.String categoryName;
	
    public java.lang.String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}

	/**
     * 排序       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	private Integer orders;
   
	
    /**
     * 值类型       db_column: inputtype  
     * 
     * @Length(max=50)
     */
	private Integer inputtype;
    /**
     * 是否必填       db_column: ismust  
     * 
     * 
     * 
     */	
	private java.lang.Integer ismust;
    /**
     * 默认显示       db_column: default_display  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String defaultDisplay;
    /**
     * 默认值       db_column: default_val  
     * 
     * 
     * @Length(max=50)
     */	
	private java.lang.String defaultVal;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */	
	private java.util.Date createDate;
    /**
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */	
	private java.util.Date updateDate;
    /**
     * 是否可搜索       db_column: for_search  
     * 
     * 
     * 
     */	
	private java.lang.Integer forSearch;
	 /**
     * 绑定分类       db_column: category_id  
     * 配置一对多的关系
     * 
     * 
     */	
	private Long categoryId;
	
	
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	/*********以下字段只供显示*********/
	private String selectedValue;//已经选择的那个值，只供显示:适用于product修改
	public AttributeVo(){
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}


	public AttributeVo(
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
}

