/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user")
public class UserFactory extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;


	//columns START
	/**
	 * id       db_column: id
	 */
	@PrimaryKey
	@Column("id")
	private java.lang.Long id;
	/**
	 * username       db_column: username
	 *
	 * @Length(max=100)
	 */
	@Column("user_name")
	private java.lang.String userName;
	/**
	 * email       db_column: email
	 *
	 * @Email @Length(max=100)
	 */
	@Column("email")
	private java.lang.String email;
	/**
	 * phone       db_column: phone
	 *
	 * @Length(max=100)
	 */
	@Column("phone")
	private java.lang.String phone;
	/**
	 * 1:买家 2:卖家       db_column: type
	 */
	@Column("type")
	private java.lang.Integer type;
	/**
	 * address       db_column: address
	 *
	 * @Length(max=100)
	 */
	@Column("address")
	private java.lang.String address;
	/**
	 * login_time       db_column: login_time
	 */
	@Column("login_time")
	private java.util.Date loginTime;
	/**
	 * creat_time       db_column: creat_time
	 */
	@Column("creat_time")
	private java.util.Date creatTime;
	/**
	 * status       db_column: status
	 */
	@Column("status")
	private java.lang.Integer status;

	/**
	 * 户用头像地址       db_column: avatar
	 *
	 * @Length(max=200)
	 */
	@Column("avatar")
	private java.lang.String avatar;
	/**
	 * 生日       db_column: birthday
	 */
	@Column("birthday")
	private java.util.Date birthday;
	/**
	 * 称昵       db_column: nick_name
	 *
	 * @Length(max=64)
	 */
	@Column("nick_name")
	private java.lang.String nickName;
	/**
	 * 真实姓名       db_column: real_name
	 *
	 * @Length(max=64)
	 */
	@Column("real_name")
	private java.lang.String realName;
	/**
	 * 禁用标识：0禁用；1可用       db_column: usable
	 */
	@Column("usable")
	private java.lang.Integer usable;
	/**
	 * 户用签名       db_column: user_signature
	 *
	 * @Length(max=500)
	 */
	@Column("user_signature")
	private java.lang.String userSignature;
	/**
	 * 户用等级       db_column: user_level
	 */
	@Column("user_level")
	private java.lang.Integer userLevel;
	/**
	 * 是否激活：0未激活；1已激活       db_column: enabled
	 */
	@Column("enabled")
	private java.lang.Integer enabled;
	/**
	 * 省       db_column: province
	 *
	 * @Length(max=20)
	 */
	@Column("province")
	private java.lang.String province;
	/**
	 * 市       db_column: city
	 *
	 * @Length(max=20)
	 */
	@Column("city")
	private java.lang.String city;
	/**
	 * 区       db_column: district
	 *
	 * @Length(max=20)
	 */
	@Column("district")
	private java.lang.String district;
	/**
	 * 性别  'm','f','n'   m：男   f：女  n：保密       db_column: gender
	 *
	 * @Length(max=2)
	 */
	@Column("gender")
	private java.lang.String gender;
	private Date lastLoginTime;
	/**
	 * 用户类型：0普通，1员工，2亲友
	 *
	 * @Length(max=2)
	 */
	@Column("employee_type")
	private int employeeType = 0;
	/**
	 * 用户类型：0普通，1员工，2亲友
	 *
	 * @Length(max=2)
	 */
	@Column("shop_link")
	private String shopLink;
	
	public String getShopLink() {
		return shopLink;
	}

	public void setShopLink(String shopLink) {
		this.shopLink = shopLink;
	}
	/**
	 * 隐藏信息：1手机，2姓名
	 *
	 * @Length(max=2)
	 */
	@Column("hide_info")
	private int hideInfo;
	
	public int getHideInfo() {
		return hideInfo;
	}
	public void setHideInfo(int hideInfo) {
		this.hideInfo = hideInfo;
	}
	
	//columns END
	private Long supplierId;

	private String password;

	private Integer role;

	private List<Resource> resources;

	private Integer shopCount;
	
	private String sectionName;//部门
	
	public UserFactory() {
	}

	public UserFactory(java.lang.Long id) {
		this.id = id;
	}


	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public void setEmail(java.lang.String value) {
		this.email = value;
	}

	public java.lang.String getEmail() {
		return this.email;
	}

	public void setPhone(java.lang.String value) {
		this.phone = value;
	}

	public java.lang.String getPhone() {
		return this.phone;
	}

	public void setType(java.lang.Integer value) {
		this.type = value;
	}

	public java.lang.Integer getType() {
		return this.type;
	}

	public void setAddress(java.lang.String value) {
		this.address = value;
	}

	public java.lang.String getAddress() {
		return this.address;
	}


	public void setLoginTime(java.util.Date value) {
		this.loginTime = value;
	}

	public java.util.Date getLoginTime() {
		return this.loginTime;
	}


	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}

	public java.util.Date getCreatTime() {
		return this.creatTime;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setAvatar(java.lang.String value) {
		this.avatar = value;
	}

	public java.lang.String getAvatar() {
		return this.avatar;
	}

	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setNickName(java.lang.String value) {
		this.nickName = value;
	}

	public java.lang.String getNickName() {
		return this.nickName;
	}

	public void setRealName(java.lang.String value) {
		this.realName = value;
	}

	public java.lang.String getRealName() {
		return this.realName;
	}

	public void setUsable(java.lang.Integer value) {
		this.usable = value;
	}

	public java.lang.Integer getUsable() {
		return this.usable;
	}

	public void setUserSignature(java.lang.String value) {
		this.userSignature = value;
	}

	public java.lang.String getUserSignature() {
		return this.userSignature;
	}

	public void setUserLevel(java.lang.Integer value) {
		this.userLevel = value;
	}

	public java.lang.Integer getUserLevel() {
		return this.userLevel;
	}

	public void setEnabled(java.lang.Integer value) {
		this.enabled = value;
	}

	public java.lang.Integer getEnabled() {
		return this.enabled;
	}

	public void setProvince(java.lang.String value) {
		this.province = value;
	}

	public java.lang.String getProvince() {
		return this.province;
	}

	public void setCity(java.lang.String value) {
		this.city = value;
	}

	public java.lang.String getCity() {
		return this.city;
	}

	public void setDistrict(java.lang.String value) {
		this.district = value;
	}

	public java.lang.String getDistrict() {
		return this.district;
	}

	public void setGender(java.lang.String value) {
		this.gender = value;
	}

	public java.lang.String getGender() {
		return this.gender;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(int employeeType) {
		this.employeeType = employeeType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}


	public Integer getShopCount() {
		return shopCount;
	}

	public void setShopCount(Integer shopCount) {
		this.shopCount = shopCount;
	}
	
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId())
				.append("Username", getUserName())
				.append("Email", getEmail())
				.append("Phone", getPhone())
				.append("Type", getType())
				.append("Address", getAddress())
				.append("LoginTime", getLoginTime())
				.append("CreatTime", getCreatTime())
				.append("Status", getStatus())
				.append("NickName", getNickName())
				.append("RealName", getRealName())
				.append("Usable", getUsable())
				.append("Enabled", getEnabled())
				.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof UserFactory == false) return false;
		if (this == obj) return true;
		UserFactory other = (UserFactory) obj;
		return new EqualsBuilder()
				.append(getId(), other.getId())
				.isEquals();
	}

	public boolean hasAuth(String authName) {
		Resource resource = new Resource(authName);
		return this.resources.contains(resource);
	}

	public String getAuthUri(String authName) {
		int index = this.resources.indexOf(new Resource(authName));
		if (index >= 0) {
			return this.resources.get(index).getUri();
		}
		return "";
	}
}

