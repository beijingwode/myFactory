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


public class UserQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Long id;
	/** username */
	private java.lang.String username;
	/** email */
	private java.lang.String email;
	/** phone */
	private java.lang.String phone;
	/** 1:买家 2:卖家 */
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
	/** 户用头像地址 */
	private java.lang.String avatar;
	/** 生日 */
	private java.util.Date birthdayBegin;
	private java.util.Date birthdayEnd;
	/** 称昵 */
	private java.lang.String nickName;
	/** 真实姓名 */
	private java.lang.String realName;
	/** 禁用标识：0禁用；1可用 */
	private java.lang.Boolean usable;
	/** 户用签名 */
	private java.lang.String userSignature;
	/** 户用等级 */
	private java.lang.Integer userLevel;
	/** 是否激活：0未激活；1已激活 */
	private java.lang.Integer enabled;
	/** 省 */
	private java.lang.String province;
	/** 市 */
	private java.lang.String city;
	/** 区 */
	private java.lang.String district;
	/** 性别  'm','f','n'   m：男   f：女  n：保密 */
	private java.lang.String gender;

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
	
	public java.lang.String getAvatar() {
		return this.avatar;
	}
	
	public void setAvatar(java.lang.String value) {
		this.avatar = value;
	}
	
	public java.util.Date getBirthdayBegin() {
		return this.birthdayBegin;
	}
	
	public void setBirthdayBegin(java.util.Date value) {
		this.birthdayBegin = value;
	}	
	
	public java.util.Date getBirthdayEnd() {
		return this.birthdayEnd;
	}
	
	public void setBirthdayEnd(java.util.Date value) {
		this.birthdayEnd = value;
	}
	
	public java.lang.String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(java.lang.String value) {
		this.nickName = value;
	}
	
	public java.lang.String getRealName() {
		return this.realName;
	}
	
	public void setRealName(java.lang.String value) {
		this.realName = value;
	}
	
	public java.lang.Boolean getUsable() {
		return this.usable;
	}
	
	public void setUsable(java.lang.Boolean value) {
		this.usable = value;
	}
	
	public java.lang.String getUserSignature() {
		return this.userSignature;
	}
	
	public void setUserSignature(java.lang.String value) {
		this.userSignature = value;
	}
	
	public java.lang.Integer getUserLevel() {
		return this.userLevel;
	}
	
	public void setUserLevel(java.lang.Integer value) {
		this.userLevel = value;
	}
	
	public java.lang.Integer getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(java.lang.Integer value) {
		this.enabled = value;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	
	public void setProvince(java.lang.String value) {
		this.province = value;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	
	public void setCity(java.lang.String value) {
		this.city = value;
	}
	
	public java.lang.String getDistrict() {
		return this.district;
	}
	
	public void setDistrict(java.lang.String value) {
		this.district = value;
	}
	
	public java.lang.String getGender() {
		return this.gender;
	}
	
	public void setGender(java.lang.String value) {
		this.gender = value;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

