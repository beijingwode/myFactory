package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_prize_take")
public class UserPrizeTake extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5033108607057974407L;
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
     * 奖品id       db_column: prize_id
     * 
     * 
     * 
     */ 
    @Column("prize_id")
    private java.lang.Long prizeId;
    /**
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.String userId;
    /**
     * 收货人       db_column: name
     * 
     * 
     * 
     */ 
    @Column("name")
    private java.lang.String name;
    /**
     * 电话       db_column: mobile
     * 
     * 
     * 
     */ 
    @Column("mobile")
    private java.lang.String mobile;
    /**
     * 收货地址       db_column: address
     * 
     * 
     * 
     */ 
    @Column("address")
    private java.lang.String address;
    /**
     * 收到邮件的邮箱       db_column: email
     * 
     * 
     * 
     */ 
    @Column("email")
    private java.lang.String email;
    /**
     * 状态       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.String status;
    /**
     * 发货时间       db_column: send_date
     * 
     * 
     * 
     */ 
    @Column("send_date")
    private java.util.Date sendDate;
    /**
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;
    /**
     * 操作人       db_column: operation_name
     * 
     * 
     * 
     */ 
    @Column("operation_name")
    private java.lang.String operationName;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("PrizeId",getPrizeId())
            .append("UserId",getUserId())
            .append("Name",getName())
            .append("Mobile",getMobile())
            .append("Address",getAddress())
            .append("Email",getEmail())
            .append("Status",getStatus())
            .append("SendDate",getSendDate())
            .append("UpdateDate",getUpdateDate())
            .append("OperationName",getOperationName())
            .append("CreateDate",getCreateDate())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(java.lang.Long prizeId) {
		this.prizeId = prizeId;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.util.Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(java.util.Date sendDate) {
		this.sendDate = sendDate;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.String getOperationName() {
		return operationName;
	}

	public void setOperationName(java.lang.String operationName) {
		this.operationName = operationName;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

