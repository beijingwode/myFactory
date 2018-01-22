package com.wode.factory.model;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_limit_ticket")
public class SupplierLimitTicket extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6072450558702904078L;
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
     * 商家id       db_column: supplierId
     * 
     * 
     * 
     */ 
    @Column("supplierId")
    private java.lang.Long supplierId;
    /**
     * 商家名称       db_column: companyName
     * 
     * 
     * 
     */ 
    @Column("companyName")
    private java.lang.String companyName;
    /**
     * 券是否一次使用 ：1:是，2：否       db_column: onece_flag
     * 
     * 
     * 
     */ 
    @Column("onece_flag")
    private java.lang.Integer oneceFlag;
    /**
     * 券类型 ：1:内购抵扣券，2：体验券，3:现金券       db_column: ticket_type
     * 
     * 
     * 
     */ 
    @Column("ticket_type")
    private java.lang.Integer ticketType;
    /**
     * 限制信息：1：通用，2：商品 pid_商品id，3：品类 cate_品类id       db_column: limit_key
     * 
     * 
     * 
     */ 
    @Column("limit_key")
    private java.lang.String limitKey;
    /**
     * 内购券总额       db_column: ticket
     * 
     * 
     * 
     */ 
    @Column("ticket")
    private java.math.BigDecimal ticket;
    /**
     * 现金券总额       db_column: cash
     * 
     * 
     * 
     */ 
    @Column("cash")
    private java.math.BigDecimal cash;
    /**
     * 使用期限开始       db_column: limit_start
     * 
     * 
     * 
     */ 
    @Column("limit_start")
    private java.util.Date limitStart;
    /**
     * 使用期限结束       db_column: limit_end
     * 
     * 
     * 
     */ 
    @Column("limit_end")
    private java.util.Date limitEnd;
    /**
     * 券使用说明       db_column: ticket_note
     * 
     * 
     * 
     */ 
    @Column("ticket_note")
    private java.lang.String ticketNote;
    /**
     * 跳转路径       db_column: next_action
     * 
     * 
     * 
     */ 
    @Column("next_action")
    private java.lang.String nextAction;
    /**
     * 扩展1       db_column: exp1
     * 
     * 
     * 
     */ 
    @Column("exp1")
    private java.lang.String exp1;
    /**
     * 扩展2       db_column: exp2
     * 
     * 
     * 
     */ 
    @Column("exp2")
    private java.lang.String exp2;
    /**
     * 扩展3       db_column: exp3
     * 
     * 
     * 
     */ 
    @Column("exp3")
    private java.lang.String exp3;
    /**
     * 券数量       db_column: ticket_num
     * 
     * 
     * 
     */ 
    @Column("ticket_num")
    private java.lang.Integer ticketNum;
    /**
     * 领取数量       db_column: receive_num
     * 
     * 
     * 
     */ 
    @Column("receive_num")
    private java.lang.Integer receiveNum;
    /**
     * 领取数量       db_column: used_num
     * 
     * 
     * 
     */ 
    @Column("used_num")
    private java.lang.Integer usedNum;
    /**
     * 状态 0:未使用/1:部分使用 /2:已用完 /3:已过期/       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 创建时间       db_column: create_date
     * 
     * 
     * 
     */ 
    @Column("create_date")
    private java.util.Date createDate;
    /**
     * 创建者       db_column: create_user
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
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;
    /**
     * 更新者       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.Long updateUser;
    /**
     * 是否用于注册 1:可以 2:不能注册       db_column: registe_flg
     * 
     * 
     * 
     */ 
    @Column("registe_flg")
    private String registeFlg;
    /**
     * 注册时，是否发放平台奖励 500内购券 1:发放 0不发放       db_column: registe_normal_przie
     * 
     * 
     * 
     */ 
    @Column("registe_normal_przie")
    private String registeNormalPrzie;
    /**
     * 其他途径注册时 是否自动发放 1:自动发放       db_column: registe_auto_plus
     * 
     * 
     * 
     */ 
    @Column("registe_auto_plus")
    private String registeAutoPlus;
    /**
     * 下一步操作 一般为注册，也可以是领取成功       db_column: registe_action
     * 
     * 
     * 
     */ 
    @Column("registe_action")
    private String registeAction;
    
    
    /**
     * 微信带参数二维码 关注用临时       db_column: wx_temp_qr_url
     * 
     * 
     * 
     */ 
    @Column("wx_temp_qr_url")
    private String wxTempQrUrl;
    /**
     * 微信临时二维码有效期限       db_column: wx_temp_limit_end
     * 
     * 
     * 
     */ 
    @Column("wx_temp_limit_end")
    private Date wxTempLimitEnd;
    
    /**
     * 限制类型 ：1:内购抵扣券，2：体验券，3:现金券
     */
    @Column("limit_type")
    private Integer limitType;
    //columns END
    
    private Integer flag;//业务字段
    private List<SupplierLimitTicketSku> supplierLimitTicketSkuList;
    private Long userLimitTicketId;
	public List<SupplierLimitTicketSku> getSupplierLimitTicketSkuList() {
		return supplierLimitTicketSkuList;
	}

	public void setSupplierLimitTicketSkuList(List<SupplierLimitTicketSku> supplierLimitTicketSkuList) {
		this.supplierLimitTicketSkuList = supplierLimitTicketSkuList;
	}
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("SupplierId",getSupplierId())
            .append("CompanyName",getCompanyName())
            .append("OneceFlag",getOneceFlag())
            .append("TicketType",getTicketType())
            .append("LimitKey",getLimitKey())
            .append("Ticket",getTicket())
            .append("Cash",getCash())
            .append("LimitStart",getLimitStart())
            .append("LimitEnd",getLimitEnd())
            .append("TicketNote",getTicketNote())
            .append("NextAction",getNextAction())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
            .append("Exp3",getExp3())
            .append("TicketNum",getTicketNum())
            .append("ReceiveNum",getReceiveNum())
            .append("UsedNum",getUsedNum())
            .append("Status",getStatus())
            .append("CreateDate",getCreateDate())
            .append("CreateUser",getCreateUser())
            .append("CreateUserName",getCreateUserName())
            .append("UpdateDate",getUpdateDate())
            .append("UpdateUser",getUpdateUser())
            .toString();
    }
    

	public String getRegisteFlg() {
		return registeFlg;
	}


	public void setRegisteFlg(String registeFlg) {
		this.registeFlg = registeFlg;
	}


	public String getRegisteNormalPrzie() {
		return registeNormalPrzie;
	}


	public void setRegisteNormalPrzie(String registeNormalPrzie) {
		this.registeNormalPrzie = registeNormalPrzie;
	}


	public String getRegisteAutoPlus() {
		return registeAutoPlus;
	}


	public void setRegisteAutoPlus(String registeAutoPlus) {
		this.registeAutoPlus = registeAutoPlus;
	}



	public String getRegisteAction() {
		return registeAction;
	}


	public void setRegisteAction(String registeAction) {
		this.registeAction = registeAction;
	}


	public String getWxTempQrUrl() {
		return wxTempQrUrl;
	}

	public void setWxTempQrUrl(String wxTempQrUrl) {
		this.wxTempQrUrl = wxTempQrUrl;
	}

	public Date getWxTempLimitEnd() {
		return wxTempLimitEnd;
	}

	public void setWxTempLimitEnd(Date wxTempLimitEnd) {
		this.wxTempLimitEnd = wxTempLimitEnd;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}

	public java.lang.Integer getOneceFlag() {
		return oneceFlag;
	}

	public void setOneceFlag(java.lang.Integer oneceFlag) {
		this.oneceFlag = oneceFlag;
	}

	public java.lang.Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(java.lang.Integer ticketType) {
		this.ticketType = ticketType;
	}

	public java.lang.String getLimitKey() {
		return limitKey;
	}

	public void setLimitKey(java.lang.String limitKey) {
		this.limitKey = limitKey;
	}

	public java.math.BigDecimal getTicket() {
		return ticket;
	}

	public void setTicket(java.math.BigDecimal ticket) {
		this.ticket = ticket;
	}

	public java.math.BigDecimal getCash() {
		return cash;
	}

	public void setCash(java.math.BigDecimal cash) {
		this.cash = cash;
	}

	public java.util.Date getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(java.util.Date limitStart) {
		this.limitStart = limitStart;
	}

	public java.util.Date getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(java.util.Date limitEnd) {
		this.limitEnd = limitEnd;
	}

	public java.lang.String getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(java.lang.String ticketNote) {
		this.ticketNote = ticketNote;
	}

	public java.lang.String getNextAction() {
		return nextAction;
	}

	public void setNextAction(java.lang.String nextAction) {
		this.nextAction = nextAction;
	}

	public java.lang.String getExp1() {
		return exp1;
	}

	public void setExp1(java.lang.String exp1) {
		this.exp1 = exp1;
	}

	public java.lang.String getExp2() {
		return exp2;
	}

	public void setExp2(java.lang.String exp2) {
		this.exp2 = exp2;
	}

	public java.lang.String getExp3() {
		return exp3;
	}

	public void setExp3(java.lang.String exp3) {
		this.exp3 = exp3;
	}

	public java.lang.Integer getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(java.lang.Integer ticketNum) {
		this.ticketNum = ticketNum;
	}

	public java.lang.Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(java.lang.Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

	public java.lang.Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(java.lang.Integer usedNum) {
		this.usedNum = usedNum;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
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
	
	public Integer getLimitType() {
		return limitType;
	}

	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}


	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getUserLimitTicketId() {
		return userLimitTicketId;
	}

	public void setUserLimitTicketId(Long userLimitTicketId) {
		this.userLimitTicketId = userLimitTicketId;
	}

}

