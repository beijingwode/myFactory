package com.wode.factory.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Table("t_group_buy")
public class GroupBuy extends BaseModel implements Serializable{
	private static final long serialVersionUID = 385822472878941284L;
	
	// date formats

	// columns START
	/**
	 * ID db_column: id
	 * 
	 * 
	 * 
	 */
	@PrimaryKey
	@Column(value="id")
	@Id
	private java.lang.Long id;
	/**
	 * 店铺ID db_column: shop_id
	 * 
	 * 
	 * 
	 */
	@Column(value="shop_id")
	private java.lang.Long shopId;
	/**
	 * 参团人数 db_column: num
	 * 
	 * 
	 * @Length(max=255)
	 */
	@Column(value="num")
	private Integer num;
	/**
	 * 参团时间 db_column: days
	 * 
	 * 
	 * @Max(127)
	 */
	@Column(value="days")
	private Integer days;
	/**
	 * 收货人 db_column: user_name
	 * 
	 * @Max(127)
	 */
	@Column(value="user_name")
	private String userName;
	/**
	 * 收货人手机号 db_column: phone_num
	 * 
	 * @Max(127)
	 */
	@Column(value="phone_num")
	private String phoneNum;
	/**
	 * 收货地址 db_column: address
	 * 
	 * @Max(127)
	 */
	@Column(value="address")
	private String address;
	/**
	 * 创建时间 db_column: create_time
	 * 
	 * @Max(127)
	 */
	@Column(value="create_time")
	private Date createTime;
	/**
	 * 团长留言db_column: comment
	 * 
	 * @Max(127)
	 */
	@Column(value="comment")
	private String comment;
	/**
	 * 结束时间db_column: end_time
	 * 
	 * @Max(127)
	 */
	@Column(value="end_time")
	private Date endTime;
	/**
	 * 创建团购的用户id db_column: user_id
	 * 
	 * @Max(127)
	 */
	@Column(value="user_id")
	private java.lang.Long userId;
	/**
	 * 状态 db_column: status
	 * 
	 * @Max(127)
	 */
	@Column(value="status")
	private Integer status;
	/**
	 * 地址id db_column: aid
	 * 
	 * @Max(127)
	 */
	@Column(value="aid")
	private String aid;
	
	/**
	 * 群id db_column: im_group_idd
	 * 
	 * @Max(127)
	 */
	@Column(value="im_group_id")
	private String im_groupId;
	/**
	 * '店铺名称':shop_name,
	 */
	@Column(value="shop_name")
	private String shopName;
	/**
	 * 团长头像:user_avatar
	 */
	@Column(value="user_avatar")
	private String userAvatar;
	
	/**
	 * 参团人数 db_column: join_num
	 * 
	 * @Max(127)
	 */
	@Column(value="join_num")
	private Integer JoinNum;
	
	/**
	 * 团长昵称 db_column: commander_name
	 * 
	 * @Max(127)
	 */
	@Column(value="commander_name")
	private String commanderName;
	
	/**
	 * 订单状态 0:未生成 1:已生成 2:已发货 4:已收货 5:已分发 -1:已取消 db_column: order_status
	 * 
	 * @Max(127)
	 */
	@Column(value="order_status")
	private Integer orderStatus;
	/**
	 * 订单创建时间 db_column: order_create_time
	 * 
	 */
	@Column(value="order_create_time")
	private Date orderCreateTime;
	/**
	 * 订单发货时间 db_column: order_send_time
	 * 
	 */
	@Column(value="order_send_time")
	private Date orderSendTime;
	/**
	 * 物流类型 db_column: expressType
	 */
	@Column(value="expressType")
	private String expressType;
	/**
	 * 物流单号 db_column: expressNo
	 */
	@Column(value="expressNo")
	private String expressNo;
	/**
	 * 订单关闭理由 db_column: closeReason
	 */
	@Column(value="closeReason")
	private String closeReason;
	/**
	 * 订单取消时间 db_column: order_cancel_time
	 */
	@Column(value="order_cancel_time")
	private Date orderCancelTime;

    /**
     * 团总运费       db_column: total_shipping  
     * 
     * 
     * 
     */
	@Column("total_shipping")
	private BigDecimal totalShipping;

	/**
	 * 免邮商品总数 db_column: product_num
	 */
	@Column(value="product_num")
	private Integer productNum;

	/**
	 * 免邮商品总金额 db_column: product_amout
	 */
	@Column(value="product_amout")
	private BigDecimal productAmout;
	/**
	 * 团名称 db_column: group_name
	 */
	@Column(value="group_name")
	private String groupName;
	/**
	 * 是否限时限时 0不限时，1限时 db_column: limited_time
	 */
	@Column(value="limited_time")
	private Integer limitedTime;
	/**
	 * 总金额(每个商品内购价*数量) db_column: all_total_price
	 */
	@Column(value="all_total_price")
	private BigDecimal allTotalPrice;
	/**
	 * 实际金额（通过查询计算阶梯*数量） db_column: total_price
	 */
	@Column(value="total_price")
	private BigDecimal totalPrice;
	/**
	 * 合计运费 db_column: all_total_shipping
	 */
	@Column(value="all_total_shipping")
	private BigDecimal allTotalShipping;
	// columns END
	//getter setter
	public java.lang.Long getId() {
		return id;
	}
	public BigDecimal getAllTotalPrice() {
		return allTotalPrice;
	}
	public void setAllTotalPrice(BigDecimal allTotalPrice) {
		this.allTotalPrice = allTotalPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getAllTotalShipping() {
		return allTotalShipping;
	}
	public void setAllTotalShipping(BigDecimal allTotalShipping) {
		this.allTotalShipping = allTotalShipping;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getLimitedTime() {
		return limitedTime;
	}
	public void setLimitedTime(Integer limitedTime) {
		this.limitedTime = limitedTime;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public java.lang.Long getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getIm_groupId() {
		return im_groupId;
	}
	public void setIm_groupId(String im_groupId) {
		this.im_groupId = im_groupId;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getJoinNum() {
		return JoinNum;
	}
	public void setJoinNum(Integer joinNum) {
		JoinNum = joinNum;
	}
	public String getCommanderName() {
		return commanderName;
	}
	public void setCommanderName(String commanderName) {
		this.commanderName = commanderName;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("ShopId",getShopId())
			.append("Num",getNum())
			.append("Days",getDays())
			.append("UserName",getUserName())
			.append("Address",getAddress())
			.append("PhoneNum",getPhoneNum())
			.append("CreateTime",getCreateTime())
			.append("Comment",getComment())
			.append("Status",getStatus())
			.append("Aid",getAid())
			.append("Im_groupId",getIm_groupId())
			.append("user_avatar",getUserAvatar())
			.append("shop_name",getShopName())
			.append("join_num",getJoinNum())
			.append("commander_name",getCommanderName())
			.toString();
	}
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public Date getOrderSendTime() {
		return orderSendTime;
	}
	public void setOrderSendTime(Date orderSendTime) {
		this.orderSendTime = orderSendTime;
	}
	public String getExpressType() {
		return expressType;
	}
	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getCloseReason() {
		return closeReason;
	}
	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}
	public Date getOrderCancelTime() {
		return orderCancelTime;
	}
	public void setOrderCancelTime(Date orderCancelTime) {
		this.orderCancelTime = orderCancelTime;
	}
	public BigDecimal getTotalShipping() {
		return totalShipping;
	}
	public void setTotalShipping(BigDecimal totalShipping) {
		this.totalShipping = totalShipping;
	}
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	public BigDecimal getProductAmout() {
		return productAmout;
	}
	public void setProductAmout(BigDecimal productAmout) {
		this.productAmout = productAmout;
	}
	public boolean equals(Object obj) {
		if(obj instanceof GroupBuy == false) return false;
		if(this == obj) return true;
		GroupBuy other = (GroupBuy)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
}
