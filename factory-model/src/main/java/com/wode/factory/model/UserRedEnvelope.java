package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_red_envelope")
public class UserRedEnvelope extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * 用户id 发红包用户       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
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
     * 使用货币id       db_column: currency_id
     * 
     * 
     * 
     */ 
    @Column("currency_id")
    private java.lang.Long currencyId;
    /**
     * 目标类型 0:个人 1:群       db_column: to_type
     * 
     * 
     * 
     */ 
    @Column("to_type")
    private java.lang.Integer toType;
    /**
     * 对方id       db_column: to_id
     * 
     * 
     * 
     */ 
    @Column("to_id")
    private java.lang.Long toId;
    /**
     * 数量       db_column: to_cnt
     * 
     * 
     * 
     */ 
    @Column("to_cnt")
    private java.lang.Integer toCnt;
    /**
     * 总金额       db_column: amount
     * 
     * 
     * 
     */ 
    @Column("amount")
    private java.math.BigDecimal amount;
    /**
     * 分配方式 0:固定值 1:随机分配       db_column: allot_type
     * 
     * 
     * 
     */ 
    @Column("allot_type")
    private java.lang.Integer allotType;
    /**
     * 单笔金额       db_column: price
     * 
     * 
     * 
     */ 
    @Column("price")
    private java.math.BigDecimal price;
    /**
     * 状态 1:已发送 3:部分领取 4:全部领取 6:全部取消       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 留言       db_column: message
     * 
     * 
     * 
     */ 
    @Column("message")
    private java.lang.String message;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 流水号       db_column: flow_cd
     * 
     * 
     * 
     */ 
    @Column("flow_cd")
    private java.lang.String flowCd;

    //columns END

    private List<UserRedEnvelopeItem> items;
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("UserNike",getUserNike())
            .append("UserAvatar",getUserAvatar())
            .append("CurrencyId",getCurrencyId())
            .append("ToType",getToType())
            .append("ToId",getToId())
            .append("ToCnt",getToCnt())
            .append("Amount",getAmount())
            .append("AllotType",getAllotType())
            .append("Price",getPrice())
            .append("Status",getStatus())
            .append("CreateTime",getCreateTime())
            .append("Message",getMessage())
            .append("UpdateTime",getUpdateTime())
            .append("FlowCd",getFlowCd())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
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

	public java.lang.Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(java.lang.Long currencyId) {
		this.currencyId = currencyId;
	}

	public java.lang.Integer getToType() {
		return toType;
	}

	public void setToType(java.lang.Integer toType) {
		this.toType = toType;
	}

	public java.lang.Long getToId() {
		return toId;
	}

	public void setToId(java.lang.Long toId) {
		this.toId = toId;
	}

	public java.lang.Integer getToCnt() {
		return toCnt;
	}

	public void setToCnt(java.lang.Integer toCnt) {
		this.toCnt = toCnt;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	public java.lang.Integer getAllotType() {
		return allotType;
	}

	public void setAllotType(java.lang.Integer allotType) {
		this.allotType = allotType;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getMessage() {
		return message;
	}

	public void setMessage(java.lang.String message) {
		this.message = message;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getFlowCd() {
		return flowCd;
	}

	public void setFlowCd(java.lang.String flowCd) {
		this.flowCd = flowCd;
	}

	public List<UserRedEnvelopeItem> getItems() {
		return items;
	}

	public void setItems(List<UserRedEnvelopeItem> items) {
		this.items = items;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
