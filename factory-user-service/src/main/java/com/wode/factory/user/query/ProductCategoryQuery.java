/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class ProductCategoryQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 排序 */
	private Integer orders;
	/** name */
	private java.lang.String name;
	/** createDate */
	private java.util.Date createDateBegin;
	private java.util.Date createDateEnd;
	/** updateDate */
	private java.util.Date updateDateBegin;
	private java.util.Date updateDateEnd;
	/** 节点深度 */
	private java.lang.Integer deep;
	/** root */
	private java.lang.String root;
	/** url */
	private java.lang.String url;
	/** 每个类别的跟节点id */
	private java.lang.Long rootId;
	/** 子节点总排行=父节点brotherOrderAll+父节点brotherOrder */
	private java.lang.String brotherOrderAll;
	/** 父节点的子节点数量 */
	private java.lang.Integer childCount;
	/** pid */
	private java.lang.Long pid;
	/** 是否特殊行业 0否  1是 */
	private java.lang.Boolean special;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
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
	
	public java.lang.Integer getDeep() {
		return this.deep;
	}
	
	public void setDeep(java.lang.Integer value) {
		this.deep = value;
	}
	
	public java.lang.String getRoot() {
		return this.root;
	}
	
	public void setRoot(java.lang.String value) {
		this.root = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.Long getRootId() {
		return this.rootId;
	}
	
	public void setRootId(java.lang.Long value) {
		this.rootId = value;
	}
	
	public java.lang.String getBrotherOrderAll() {
		return this.brotherOrderAll;
	}
	
	public void setBrotherOrderAll(java.lang.String value) {
		this.brotherOrderAll = value;
	}
	
	public java.lang.Integer getChildCount() {
		return this.childCount;
	}
	
	public void setChildCount(java.lang.Integer value) {
		this.childCount = value;
	}
	
	public java.lang.Long getPid() {
		return this.pid;
	}
	
	public void setPid(java.lang.Long value) {
		this.pid = value;
	}
	
	public java.lang.Boolean getSpecial() {
		return this.special;
	}
	
	public void setSpecial(java.lang.Boolean value) {
		this.special = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

