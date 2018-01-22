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


public class UserFriendQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** 亲友id */
	private java.lang.Long userid;
	/** 昵称(亲友昵称，userid 的昵称) */
	private java.lang.String nickname;
	/** 申请时间 */
	private java.util.Date applyTimeBegin;
	private java.util.Date applyTimeEnd;
	/** 员工id 对应  t_user表中的id(内部员工） */
	private java.lang.Long employeeid;
	/** 状态 默认是null  0：待审核  1：审核通过  -1:审核不同 */
	private java.lang.Integer state;
	/** 审核时间 */
	private java.util.Date checkTimeBegin;
	private java.util.Date checkTimeEnd;
	/** 备注：例如：我是***，请批准 */
	private java.lang.String memo;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.Long value) {
		this.userid = value;
	}
	
	public java.lang.String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(java.lang.String value) {
		this.nickname = value;
	}
	
	public java.util.Date getApplyTimeBegin() {
		return this.applyTimeBegin;
	}
	
	public void setApplyTimeBegin(java.util.Date value) {
		this.applyTimeBegin = value;
	}	
	
	public java.util.Date getApplyTimeEnd() {
		return this.applyTimeEnd;
	}
	
	public void setApplyTimeEnd(java.util.Date value) {
		this.applyTimeEnd = value;
	}
	
	public java.lang.Long getEmployeeid() {
		return this.employeeid;
	}
	
	public void setEmployeeid(java.lang.Long value) {
		this.employeeid = value;
	}
	
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
	public java.util.Date getCheckTimeBegin() {
		return this.checkTimeBegin;
	}
	
	public void setCheckTimeBegin(java.util.Date value) {
		this.checkTimeBegin = value;
	}	
	
	public java.util.Date getCheckTimeEnd() {
		return this.checkTimeEnd;
	}
	
	public void setCheckTimeEnd(java.util.Date value) {
		this.checkTimeEnd = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

