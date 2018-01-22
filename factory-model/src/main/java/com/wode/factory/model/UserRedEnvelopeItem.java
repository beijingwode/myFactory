package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_red_envelope_item")
public class UserRedEnvelopeItem extends BaseModel implements java.io.Serializable{

    public java.lang.Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(java.lang.Long currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4643093996573191485L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */
    @Column("id")
    private java.lang.Long id;
    /**
     * 红包id       db_column: envelope_id
     * 
     * 
     * 
     */ 
    @Column("envelope_id")
    private java.lang.Long envelopeId;
    /**
     * 分配方式 0:固定值 1:随机分配       db_column: allot_type
     * 
     * 
     * 
     */ 
    @Column("allot_type")
    private java.lang.Integer allotType;
    /**
     * 使用货币id       db_column: currency_id
     * 
     * 
     * 
     */ 
    @Column("currency_id")
    private java.lang.Long currencyId;
    /**
     * 排序 1开始 拼 时1手气最佳       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    /**
     * 单笔金额       db_column: price
     * 
     * 
     * 
     */ 
    @Column("price")
    private java.math.BigDecimal price;
    /**
     * from 用户id       db_column: from_id
     * 
     * 
     * 
     */ 
    @Column("from_id")
    private java.lang.Long fromId;
    /**
     * form 用户昵称       db_column: from_nike
     * 
     * 
     * 
     */ 
    @Column("from_nike")
    private java.lang.String fromNike;
    /**
     * 领取者 id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 状态 0:未领取 2:已领取 3:自动取消       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 用户昵称       db_column: user_nike
     * 
     * 
     * 
     */ 
    @Column("user_nike")
    private java.lang.String userNike;
    /**
     * 头像地址       db_column: user_avatar
     * 
     * 
     * 
     */ 
    @Column("user_avatar")
    private java.lang.String userAvatar;
    /**
     * 流水号       db_column: flow_cd
     * 
     * 
     * 
     */ 
    @Column("flow_cd")
    private java.lang.String flowCd;

    //columns END

    
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("EnvelopeId",getEnvelopeId())
            .append("AllotType",getAllotType())
            .append("Orders",getOrders())
            .append("FromId",getFromId())
            .append("FromNike",getFromNike())
            .append("UserId",getUserId())
            .append("Status",getStatus())
            .append("UpdateTime",getUpdateTime())
            .append("UserNike",getUserNike())
            .append("UserAvatar",getUserAvatar())
            .append("FlowCd",getFlowCd())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(java.lang.Long envelopeId) {
		this.envelopeId = envelopeId;
	}

	public java.lang.Integer getAllotType() {
		return allotType;
	}

	public void setAllotType(java.lang.Integer allotType) {
		this.allotType = allotType;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.Long getFromId() {
		return fromId;
	}

	public void setFromId(java.lang.Long fromId) {
		this.fromId = fromId;
	}

	public java.lang.String getFromNike() {
		return fromNike;
	}

	public void setFromNike(java.lang.String fromNike) {
		this.fromNike = fromNike;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getUserNike() {
		return userNike;
	}

	public void setUserNike(java.lang.String userNike) {
		this.userNike = userNike;
	}

	public java.lang.String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(java.lang.String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public java.lang.String getFlowCd() {
		return flowCd;
	}

	public void setFlowCd(java.lang.String flowCd) {
		this.flowCd = flowCd;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
