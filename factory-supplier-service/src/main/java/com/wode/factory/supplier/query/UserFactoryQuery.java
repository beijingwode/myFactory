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


public class UserFactoryQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** username */
	private java.lang.String username;
	/** password */
	private java.lang.String password;
	/** email */
	private java.lang.String email;
	/** phone */
	private java.lang.String phone;
	/** type */
	private java.lang.Integer type;
	/** address */
	private java.lang.String address;
	/** login_time */
	private java.util.Date loginTimeBegin;
	private java.util.Date loginTimeEnd;
	/** creat_time */
	private java.util.Date creatTimeBegin;
	private java.util.Date creatTimeEnd;
	/** status */
	private java.lang.Integer status;
	/** 积分 */
	private java.lang.Integer coin;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.util.Date getLoginTimeBegin() {
		return this.loginTimeBegin;
	}
	
	public void setLoginTimeBegin(java.util.Date value) {
		this.loginTimeBegin = value;
	}	
	
	public java.util.Date getLoginTimeEnd() {
		return this.loginTimeEnd;
	}
	
	public void setLoginTimeEnd(java.util.Date value) {
		this.loginTimeEnd = value;
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
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getCoin() {
		return this.coin;
	}
	
	public void setCoin(java.lang.Integer value) {
		this.coin = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

