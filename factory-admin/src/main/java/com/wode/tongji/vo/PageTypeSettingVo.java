/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.tongji.vo;



public class PageTypeSettingVo  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
    /**
     * ID       db_column: pageTypeId  
     * 
     * 
     * channelId
     */	
	private java.lang.Long pageTypeId;
    /**
     * 标题       db_column: title  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String title;
    /**
     * 名称       db_column: name  
     * 
     * 
     * @Length(max=100)
     */	
	private java.lang.String name;
	
	private String categoryName;
    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
     * 父ID       db_column: pid  
     * 
     * 
     * 
     */	
	private java.lang.Integer pid;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * 
     */	
	private java.lang.Integer orders;
	
    /**
     * 创建日期       db_column: createDate  
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
	 * 所属平台
	 */
	private java.lang.Integer channelId;
	public java.lang.Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(java.lang.Integer channelId) {
		this.channelId = channelId;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	private int maxNum;

	public PageTypeSettingVo(){
	}

	public PageTypeSettingVo(
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
	public void setPid(java.lang.Integer value) {
		this.pid = value;
	}
	
	public java.lang.Integer getPid() {
		return this.pid;
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

}

