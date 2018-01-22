/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.ArrayList;
import java.util.List;

public class ProductCategory implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductCategory";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_ORDERS = "orders";
	
	public static final String ALIAS_GRADE = "grade";
	
	public static final String ALIAS_NAME = "name";
	
	public static final String ALIAS_CREATE_DATE = "createDate";
	
	public static final String ALIAS_UPDATE_DATE = "updateDate";
	
	public static final String ALIAS_DEEP = "deep";
	
	public static final String ALIAS_ROOT = "root";
	
	public static final String ALIAS_URL = "url";
	
	public static final String ALIAS_ROOT_ID = "每个类别的跟节点id";
	
	public static final String ALIAS_BROTHER_ORDER_ALL = "子节点总排行=父节点brotherOrderAll+父节点brotherOrder";
	
	public static final String ALIAS_BROTHER_ORDER = "子节点在兄弟节点中的排行";
	
	public static final String ALIAS_CHILD_COUNT = "父节点的子节点数量";
	
	public static final String ALIAS_PID = "pid";
	
	//date formats
//	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
//	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * id       db_column: id  
     * 
     * 
     * 
     */	
	private java.lang.Long id;
    /**
     * orders       db_column: orders  
     * 
     * 
     * @Max(127)
     */	
	private Integer orders;
    /**
     * name       db_column: name  
     * 
     * 
     * @Length(max=100)
     */
	private java.lang.String name;
	private java.lang.String image;
    /**
     * createDate       db_column: createDate  
     * 
     * 
     * 
     */
	private java.util.Date createDate;
    /**
     * updateDate       db_column: updateDate  
     * 
     * 
     * 
     */
	private java.util.Date updateDate;
    /**
     * deep       db_column: deep  
     * 
     * 
     * 
     */
	private java.lang.Integer deep;
   
    /**
     * url       db_column: url  
     * 
     * 
     * @Length(max=500)
     */
	private java.lang.String url;
    /**
     * 每个类别的跟节点id       db_column: rootId  
     * 
     * 
     * 
     */
	private java.lang.Long rootId;
    /**
     * 子节点总排行=父节点brotherOrderAll+父节点brotherOrder       db_column: brotherOrderAll  
     * 
     * 
     * @Length(max=100)
     */
	private java.lang.String brotherOrderAll;
    /**
     * 父节点的子节点数量       db_column: childCount  
     * 
     * 
     * 
     */
	private java.lang.Integer childCount;
    /**
     * pid       db_column: pid  
     * 
     * 
     * 
     */
	private java.lang.Long pid;
	
	private java.lang.Integer special;
	
	private Float commissionScale;
	 
//    private List<Attribute> attributes;
    
    public Float getCommissionScale() {
		return commissionScale;
	}

	public void setCommissionScale(Float commissionScale) {
		this.commissionScale = commissionScale;
	}

	private List<ProductCategory> childrens=new ArrayList();
    
	//columns END

	public ProductCategory(){
	}

	public ProductCategory(
		java.lang.Long id
	){
		this.id = id;
	}

//	public List<Attribute> getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(List<Attribute> attributes) {
//		this.attributes = attributes;
//	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.lang.Long getId() {
		return this.id;
	}
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
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
	public void setDeep(java.lang.Integer value) {
		this.deep = value;
	}
	
	public java.lang.Integer getDeep() {
		return this.deep;
	}


	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setRootId(java.lang.Long value) {
		this.rootId = value;
	}
	
	public java.lang.Long getRootId() {
		return this.rootId;
	}
	public void setBrotherOrderAll(java.lang.String value) {
		this.brotherOrderAll = value;
	}
	
	public java.lang.String getBrotherOrderAll() {
		return this.brotherOrderAll;
	}
	public void setChildCount(java.lang.Integer value) {
		this.childCount = value;
	}
	
	public java.lang.Integer getChildCount() {
		return this.childCount;
	}
	public void setPid(java.lang.Long value) {
		this.pid = value;
	}
	
	public java.lang.Long getPid() {
		return this.pid;
	}

	public java.lang.Integer getSpecial() {
		return special;
	}

	public void setSpecial(java.lang.Integer special) {
		this.special = special;
	}
	
	public List<ProductCategory> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<ProductCategory> childrens) {
		this.childrens = childrens;
	}

}

