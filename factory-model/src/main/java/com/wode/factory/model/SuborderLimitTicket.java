package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_suborder_limit_ticket")
public class SuborderLimitTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6604580366737763600L;
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
     * 状态：1.抵扣成功，2.取消抵扣       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 订单类型 0:普通订单 1 4:团订单 5:换领单       db_column: order_type
     * 
     * 
     * 
     */ 
    @Column("order_type")
    private java.lang.String orderType;
    /**
     * 现金优惠总额       db_column: total_benefit_cash
     * 
     * 
     * 
     */ 
    @Column("total_benefit_cash")
    private java.math.BigDecimal totalBenefitCash;
    /**
     * 内购券优惠总额       db_column: total_benefit_ticket
     * 
     * 
     * 
     */ 
    @Column("total_benefit_ticket")
    private java.math.BigDecimal totalBenefitTicket;
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
            .append("Status",getStatus())
            .append("OrderType",getOrderType())
            .append("TotalBenefitCash",getTotalBenefitCash())
            .append("TotalBenefitTicket",getTotalBenefitTicket())
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

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getOrderType() {
		return orderType;
	}

	public void setOrderType(java.lang.String orderType) {
		this.orderType = orderType;
	}

	public java.math.BigDecimal getTotalBenefitCash() {
		return totalBenefitCash;
	}

	public void setTotalBenefitCash(java.math.BigDecimal totalBenefitCash) {
		this.totalBenefitCash = totalBenefitCash;
	}

	public java.math.BigDecimal getTotalBenefitTicket() {
		return totalBenefitTicket;
	}

	public void setTotalBenefitTicket(java.math.BigDecimal totalBenefitTicket) {
		this.totalBenefitTicket = totalBenefitTicket;
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

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

