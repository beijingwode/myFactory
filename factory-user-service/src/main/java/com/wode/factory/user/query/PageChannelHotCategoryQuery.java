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


public class PageChannelHotCategoryQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 频道id */
	private java.lang.Long channelId;
	/** 热销分类id */
	private java.lang.Long hotCategoryId;
	/** 序号 */
	private java.lang.Integer orders;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(java.lang.Long value) {
		this.channelId = value;
	}
	
	public java.lang.Long getHotCategoryId() {
		return this.hotCategoryId;
	}
	
	public void setHotCategoryId(java.lang.Long value) {
		this.hotCategoryId = value;
	}
	
	public java.lang.Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

