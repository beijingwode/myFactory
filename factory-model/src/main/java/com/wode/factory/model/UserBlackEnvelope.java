package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_black_envelope")
public class UserBlackEnvelope extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2205973358741722021L;
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
     * 用户id 收黑包用户       db_column: user_id
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
     * 已获得总金额       db_column: gain_amount
     * 
     * 
     * 
     */ 
    @Column("gain_amount")
    private java.math.BigDecimal gainAmount;
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
    /**
     * 用户id 发黑包用户       db_column: from_user_id
     * 
     * 
     * 
     */ 
    @Column("from_user_id")
    private java.lang.Long fromUserId;
    /**
     * 发黑包用户昵称       db_column: from_user_nike
     * 
     * 
     * 
     */ 
    @Column("from_user_nike")
    private java.lang.String fromUserNike;
    /**
     * 发黑包用户头像       db_column: from_user_avatar
     * 
     * 
     * 
     */ 
    @Column("from_user_avatar")
    private java.lang.String fromUserAvatar;
    /**
     * 理由 1:余额不足 2:生日祝福 3:节日礼品 4:其他       db_column: reason_type
     * 
     * 
     * 
     */ 
    @Column("reason_type")
    private java.lang.Integer reasonType;
    /**
     * 关联id1       db_column: exp_key1
     * 
     * 
     * 
     */ 
    @Column("exp_key1")
    private java.lang.Long expKey1;
    /**
     * 关联id2       db_column: exp_key2
     * 
     * 
     * 
     */ 
    @Column("exp_key2")
    private java.lang.Long expKey2;
    /**
     * 关联文本1       db_column: exp_msg1
     * 
     * 
     * 
     */ 
    @Column("exp_msg1")
    private java.lang.String expMsg1;
    /**
     * 关联文本2       db_column: exp_msg2
     * 
     * 
     * 
     */ 
    @Column("exp_msg2")
    private java.lang.String expMsg2;
    /**
     * 关联文本3       db_column: exp_msg3
     * 
     * 
     * 
     */ 
    @Column("exp_msg3")
    private java.lang.String expMsg3;
    /**
     * 关联图片1       db_column: exp_img1
     * 
     * 
     * 
     */ 
    @Column("exp_img1")
    private java.lang.String expImg1;
    /**
     * 关联图片2       db_column: exp_img2
     * 
     * 
     * 
     */ 
    @Column("exp_img2")
    private java.lang.String expImg2;

    //columns END
    private List<UserBlackEnvelopeItem> items;

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
            .append("GainAmount",getGainAmount())
            .append("AllotType",getAllotType())
            .append("Price",getPrice())
            .append("Status",getStatus())
            .append("CreateTime",getCreateTime())
            .append("Message",getMessage())
            .append("UpdateTime",getUpdateTime())
            .append("FlowCd",getFlowCd())
            .append("FromUserId",getFromUserId())
            .append("FromUserNike",getFromUserNike())
            .append("FromUserAvatar",getFromUserAvatar())
            .append("ReasonType",getReasonType())
            .append("ExpKey1",getExpKey1())
            .append("ExpKey2",getExpKey2())
            .append("ExpMsg1",getExpMsg1())
            .append("ExpMsg2",getExpMsg2())
            .append("ExpMsg3",getExpMsg3())
            .append("ExpImg1",getExpImg1())
            .append("ExpImg2",getExpImg2())
            .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
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

	public java.math.BigDecimal getGainAmount() {
		return gainAmount;
	}

	public void setGainAmount(java.math.BigDecimal gainAmount) {
		this.gainAmount = gainAmount;
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

	public java.lang.Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(java.lang.Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public java.lang.String getFromUserNike() {
		return fromUserNike;
	}

	public void setFromUserNike(java.lang.String fromUserNike) {
		this.fromUserNike = fromUserNike;
	}

	public java.lang.String getFromUserAvatar() {
		return fromUserAvatar;
	}

	public void setFromUserAvatar(java.lang.String fromUserAvatar) {
		this.fromUserAvatar = fromUserAvatar;
	}

	public java.lang.Integer getReasonType() {
		return reasonType;
	}

	public void setReasonType(java.lang.Integer reasonType) {
		this.reasonType = reasonType;
	}

	public java.lang.Long getExpKey1() {
		return expKey1;
	}

	public void setExpKey1(java.lang.Long expKey1) {
		this.expKey1 = expKey1;
	}

	public java.lang.Long getExpKey2() {
		return expKey2;
	}

	public void setExpKey2(java.lang.Long expKey2) {
		this.expKey2 = expKey2;
	}

	public java.lang.String getExpMsg1() {
		return expMsg1;
	}

	public void setExpMsg1(java.lang.String expMsg1) {
		this.expMsg1 = expMsg1;
	}

	public java.lang.String getExpMsg2() {
		return expMsg2;
	}

	public void setExpMsg2(java.lang.String expMsg2) {
		this.expMsg2 = expMsg2;
	}

	public java.lang.String getExpMsg3() {
		return expMsg3;
	}

	public void setExpMsg3(java.lang.String expMsg3) {
		this.expMsg3 = expMsg3;
	}

	public java.lang.String getExpImg1() {
		return expImg1;
	}

	public void setExpImg1(java.lang.String expImg1) {
		this.expImg1 = expImg1;
	}

	public java.lang.String getExpImg2() {
		return expImg2;
	}

	public void setExpImg2(java.lang.String expImg2) {
		this.expImg2 = expImg2;
	}

	public List<UserBlackEnvelopeItem> getItems() {
		return items;
	}

	public void setItems(List<UserBlackEnvelopeItem> items) {
		this.items = items;
	}

}

