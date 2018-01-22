package com.wode.factory.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_group_buy_user")
public class GroupBuyUser extends BaseModel implements Serializable {

	private static final long serialVersionUID = -808671812006281276L;
	
	// columns START
	/**
	 * ID db_column: id
	 * 
	 */
	@PrimaryKey
	@Column(value = "id")
	@Id
	private java.lang.Long id;
	/**
	 * 店铺ID db_column: shop_id
	 * 
	 */
	@Column(value = "shop_id")
	private java.lang.Long shopId;
	/**
	 * 团购id db_column: group_id
	 * 
	 * @Length(max=255)
	 */
	@Column(value = "group_id")
	private java.lang.Long groupId;
	/**
	 * 用户id db_column: user_id
	 * 
	 * @Max(127)
	 */
	@Column(value = "user_id")
	private java.lang.Long userId;
	/**
	 * 创建时间 db_column: create_time
	 * 
	 * @Max(127)
	 */
	@Column(value = "create_time")
	private Date createTime;
	/**
	 * 是否是团长 db_column: is_ladder
	 * 
	 * @Max(127)
	 */
	@Column(value = "is_ladder")
	private Integer isLadder;
	/**
	 * 是否参团 db_column: is_add
	 * 
	 * @Max(127)
	 */
	@Column(value = "is_add")
	private Integer isAdd;
	/**
	 * 用户名称 db_column: user_name
	 * 
	 * @Max(127)
	 */
	@Column(value = "user_name")
	private String userName;
	
	/**
	 * 
	 */
	@Column(value = "status")
	private Integer status;
	
	// columns END
	
	//getter setter
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Long getShopId() {
		return shopId;
	}
	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}
	public java.lang.Long getGroupId() {
		return groupId;
	}
	public void setGroupId(java.lang.Long groupId) {
		this.groupId = groupId;
	}
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsLadder() {
		return isLadder;
	}
	public void setIsLadder(Integer isLadder) {
		this.isLadder = isLadder;
	}
	public Integer getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(Integer isAdd) {
		this.isAdd = isAdd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("ShopId",getShopId())
			.append("GroupId",getGroupId())
			.append("UserId",getUserId())
			.append("UserName",getUserName())
			.append("CreateTime",getCreateTime())
			.append("isAdd",getIsAdd())
			.append("isLadder",getIsLadder())
			.toString();
	}
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GroupBuyUser == false) return false;
		if(this == obj) return true;
		GroupBuyUser other = (GroupBuyUser)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
