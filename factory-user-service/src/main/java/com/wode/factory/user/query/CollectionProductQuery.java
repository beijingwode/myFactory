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


public class CollectionProductQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** user_id */
	private java.lang.Long userId;
	/** 产品id */
	private java.lang.Long productId;
	/** creat_time */
	private java.util.Date creatTimeBegin;
	private java.util.Date creatTimeEnd;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.util.Date getCreatTimeBegin() {
		return this.creatTimeBegin;
	}
	
	public void setCreatTimeBegin(java.util.Date value) {
		this.creatTimeBegin = value;
	}	
	
	public java.util.Date getCreatTimeEnd() {
		return this.creatTimeEnd;
	}
	
	public void setCreatTimeEnd(java.util.Date value) {
		this.creatTimeEnd = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

