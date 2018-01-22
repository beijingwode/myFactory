/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wode.common.frame.base.BaseQuery;


public class SupplierLogQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** user_id */
	private java.lang.Long userId;
	/** username */
	private java.lang.String username;
	/** 操作 */
	private java.lang.String act;
	/** 操作时间 */
	private java.util.Date timeBegin;
	private java.util.Date timeEnd;
	/** 结果 */
	private java.lang.String result;

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
	
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getAct() {
		return this.act;
	}
	
	public void setAct(java.lang.String value) {
		this.act = value;
	}
	
	public java.util.Date getTimeBegin() {
		return this.timeBegin;
	}
	
	public void setTimeBegin(java.util.Date value) {
		this.timeBegin = value;
	}	
	
	public java.util.Date getTimeEnd() {
		return this.timeEnd;
	}
	
	public void setTimeEnd(java.util.Date value) {
		this.timeEnd = value;
	}
	
	public java.lang.String getResult() {
		return this.result;
	}
	
	public void setResult(java.lang.String value) {
		this.result = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

