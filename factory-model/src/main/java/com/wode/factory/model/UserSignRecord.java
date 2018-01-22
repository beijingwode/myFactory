package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_sign_record")
public class UserSignRecord extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -277369440748855286L;
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
     * 用户名称       db_column: name
     * 
     * 
     * 
     */ 
    @Column("name")
    private java.lang.String name;
    /**
     * 手机号       db_column: phone
     * 
     * 
     * 
     */ 
    @Column("phone")
    private java.lang.String phone;
    /**
     * 幸运数字       db_column: lucky_number
     * 
     * 
     * 
     */ 
    @Column("lucky_number")
    private java.lang.Integer luckyNumber;
    /**
     * 中奖编号       db_column: prize_id
     * 
     * 
     * 
     */ 
    @Column("prize_id")
    private java.lang.Long prizeId;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 活动id       db_column: acticity_id
     * 
     * 
     * 
     */ 
    @Column("acticity_id")
    private java.lang.Long acticityId;
    /**
     * 福利类型       db_column: type
     * 
     * 
     * 
     */ 
    @Column("type")
    private java.lang.Integer type;
    /**
     * 内购券       db_column: ticket
     * 
     * 
     * 
     */ 
    @Column("ticket")
    private java.math.BigDecimal ticket;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("Name",getName())
            .append("Phone",getPhone())
            .append("LuckyNumber",getLuckyNumber())
            .append("PrizeId",getPrizeId())
            .append("CreateDate",getCreateDate())
            .append("ActicityId",getActicityId())
            .append("Type",getType())
            .append("Ticket",getTicket())
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

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.Integer getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(java.lang.Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public java.lang.Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(java.lang.Long prizeId) {
		this.prizeId = prizeId;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getActicityId() {
		return acticityId;
	}

	public void setActicityId(java.lang.Long acticityId) {
		this.acticityId = acticityId;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.math.BigDecimal getTicket() {
		return ticket;
	}

	public void setTicket(java.math.BigDecimal ticket) {
		this.ticket = ticket;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

