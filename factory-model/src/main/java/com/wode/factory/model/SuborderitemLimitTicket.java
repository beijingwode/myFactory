package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_suborderitem_limit_ticket")
public class SuborderitemLimitTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 693754836691192646L;
	//columns START
    /**
     * id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 订单id       db_column: orderId
     * 
     * 
     * 
     */ 
    @Column("orderId")
    private java.lang.Long orderId;
    /**
     * 子单id       db_column: subOrderId
     * 
     * 
     * 
     */ 
    @Column("subOrderId")
    private java.lang.String subOrderId;
    /**
     * 子弹项id       db_column: subOrderItemId
     * 
     * 
     * 
     */ 
    @Column("subOrderItemId")
    private java.lang.Long subOrderItemId;
    /**
     * 券id       db_column: user_limit_ticket_id
     * 
     * 
     * 
     */ 
    @Column("user_limit_ticket_id")
    private java.lang.Long userLimitTicketId;
    /**
     * 券类型       db_column: ticket_type
     * 
     * 
     * 
     */ 
    @Column("ticket_type")
    private java.lang.Integer ticketType;
    /**
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * skuId       db_column: sku_id
     * 
     * 
     * 
     */ 
    @Column("sku_id")
    private java.lang.Long skuId;
    /**
     * sku 数量       db_column: sku_num
     * 
     * 
     * 
     */ 
    @Column("sku_num")
    private java.lang.Integer skuNum;
    /**
     * 状态：1.抵扣成功，2.取消抵扣       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 券的限制信息：1：通用，2：商品 pid_商品id，3：品类 cate_品类id,       db_column: limit_key
     * 
     * 
     * 
     */ 
    @Column("limit_key")
    private java.lang.String limitKey;
    /**
     * 订单类型 0:普通订单 1 4:团订单 5:换领单       db_column: order_type
     * 
     * 
     * 
     */ 
    @Column("order_type")
    private java.lang.String orderType;
    /**
     * 现金优惠金额       db_column: benefit_cash
     * 
     * 
     * 
     */ 
    @Column("benefit_cash")
    private java.math.BigDecimal benefitCash;
    /**
     * 内购券优惠金额       db_column: benefit_ticket
     * 
     * 
     * 
     */ 
    @Column("benefit_ticket")
    private java.math.BigDecimal benefitTicket;
    /**
     * 创建时间       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("OrderId",getOrderId())
            .append("SubOrderId",getSubOrderId())
            .append("SubOrderItemId",getSubOrderItemId())
            .append("UserLimitTicketId",getUserLimitTicketId())
            .append("TicketType",getTicketType())
            .append("Status",getStatus())
            .append("LimitKey",getLimitKey())
            .append("OrderType",getOrderType())
            .append("BenefitCash",getBenefitCash())
            .append("BenefitTicket",getBenefitTicket())
            .append("CreateTime",getCreateTime())
            .append("UpdateTime",getUpdateTime())
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

	public java.lang.Long getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.Long orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(java.lang.String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public java.lang.Long getSubOrderItemId() {
		return subOrderItemId;
	}

	public void setSubOrderItemId(java.lang.Long subOrderItemId) {
		this.subOrderItemId = subOrderItemId;
	}

	public java.lang.Long getUserLimitTicketId() {
		return userLimitTicketId;
	}

	public void setUserLimitTicketId(java.lang.Long userLimitTicketId) {
		this.userLimitTicketId = userLimitTicketId;
	}

	public java.lang.Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(java.lang.Integer ticketType) {
		this.ticketType = ticketType;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getLimitKey() {
		return limitKey;
	}

	public void setLimitKey(java.lang.String limitKey) {
		this.limitKey = limitKey;
	}

	public java.lang.String getOrderType() {
		return orderType;
	}

	public void setOrderType(java.lang.String orderType) {
		this.orderType = orderType;
	}

	public java.math.BigDecimal getBenefitCash() {
		return benefitCash;
	}

	public void setBenefitCash(java.math.BigDecimal benefitCash) {
		this.benefitCash = benefitCash;
	}

	public java.math.BigDecimal getBenefitTicket() {
		return benefitTicket;
	}

	public void setBenefitTicket(java.math.BigDecimal benefitTicket) {
		this.benefitTicket = benefitTicket;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.Long getSkuId() {
		return skuId;
	}

	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}

	public java.lang.Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(java.lang.Integer skuNum) {
		this.skuNum = skuNum;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}