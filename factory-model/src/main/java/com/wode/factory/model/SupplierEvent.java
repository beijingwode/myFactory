package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_event")
public class SupplierEvent extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4485618520151276048L;
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
     * 所属企业       db_column: enterprise_id
     * 
     * 
     * 
     */ 
    @Column("enterprise_id")
    private java.lang.Long enterpriseId;
    /**
     * 企业名称（优先使用昵称）       db_column: enterprise_name
     * 
     * 
     * 
     */ 
    @Column("enterprise_name")
    private java.lang.String enterpriseName;
    /**
     * 企业类型 0：临时企业 1：已入住       db_column: enterprise_type
     * 
     * 
     * 
     */ 
    @Column("enterprise_type")
    private java.lang.Integer enterpriseType;
    /**
     * 活动名称       db_column: event_title
     * 
     * 
     * 
     */ 
    @Column("event_title")
    private java.lang.String eventTitle;
    /**
     * 预计开始时间       db_column: start_date
     * 
     * 
     * 
     */ 
    @Column("start_date")
    private java.util.Date startDate;
    /**
     * 背景图片       db_column: pc_page_bg
     * 
     * 
     * 
     */ 
    @Column("pc_page_bg")
    private java.lang.String pcPageBg;
    /**
     * 活动l主图       db_column: pc_page_banner
     * 
     * 
     * 
     */ 
    @Column("pc_page_banner")
    private java.lang.String pcPageBanner;
    /**
     * 微信签到页主图       db_column: wx_page_banner
     * 
     * 
     * 
     */ 
    @Column("wx_page_banner")
    private java.lang.String wxPageBanner;
    /**
     * 活动说明       db_column: event_note
     * 
     * 
     * 
     */ 
    @Column("event_note")
    private java.lang.String eventNote;
    /**
     * 状态       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 最大内购券 刮奖用       db_column: max_ticket
     * 
     * 
     * 
     */ 
    @Column("max_ticket")
    private java.math.BigDecimal maxTicket;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 创建者ID       db_column: create_user
     * 
     * 
     * 
     */ 
    @Column("create_user")
    private java.lang.Long createUser;
    /**
     * 创建者姓名       db_column: create_user_name
     * 
     * 
     * 
     */ 
    @Column("create_user_name")
    private java.lang.String createUserName;
    /**
     * 修改时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;
    /**
     * 修改者ID       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    
    private Integer signCnt;//签到总数
    

    //columns END

    public Integer getSignCnt() {
		return signCnt;
	}

	public void setSignCnt(Integer signCnt) {
		this.signCnt = signCnt;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("EnterpriseId",getEnterpriseId())
            .append("EnterpriseName",getEnterpriseName())
            .append("EnterpriseType",getEnterpriseType())
            .append("EventTitle",getEventTitle())
            .append("StartDate",getStartDate())
            .append("PcPageBg",getPcPageBg())
            .append("PcPageBanner",getPcPageBanner())
            .append("WxPageBanner",getWxPageBanner())
            .append("EventNote",getEventNote())
            .append("Status",getStatus())
            .append("MaxTicket",getMaxTicket())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .append("CreateUserName",getCreateUserName())
            .append("UpdateDate",getUpdateDate())
            .append("UpdateUser",getUpdateUser())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public java.lang.String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(java.lang.String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public java.lang.Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(java.lang.Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public java.lang.String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(java.lang.String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.lang.String getPcPageBg() {
		return pcPageBg;
	}

	public void setPcPageBg(java.lang.String pcPageBg) {
		this.pcPageBg = pcPageBg;
	}

	public java.lang.String getPcPageBanner() {
		return pcPageBanner;
	}

	public void setPcPageBanner(java.lang.String pcPageBanner) {
		this.pcPageBanner = pcPageBanner;
	}

	public java.lang.String getWxPageBanner() {
		return wxPageBanner;
	}

	public void setWxPageBanner(java.lang.String wxPageBanner) {
		this.wxPageBanner = wxPageBanner;
	}

	public java.lang.String getEventNote() {
		return eventNote;
	}

	public void setEventNote(java.lang.String eventNote) {
		this.eventNote = eventNote;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.math.BigDecimal getMaxTicket() {
		return maxTicket;
	}

	public void setMaxTicket(java.math.BigDecimal maxTicket) {
		this.maxTicket = maxTicket;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(java.lang.Long createUser) {
		this.createUser = createUser;
	}

	public java.lang.String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(java.lang.String createUserName) {
		this.createUserName = createUserName;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.Long updateUser) {
		this.updateUser = updateUser;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}