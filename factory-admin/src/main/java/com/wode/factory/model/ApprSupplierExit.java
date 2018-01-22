package com.wode.factory.model;


import java.math.BigDecimal;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_appr_supplier_exit")
public class ApprSupplierExit extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4718600762931534303L;
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
     * 招商经理id       db_column: manager_id
     * 
     * 
     * 
     */ 
    @Column("manager_id")
    private java.lang.Long managerId;
    /**
     * 招商经理名       db_column: manager_name
     * 
     * 
     * 
     */ 
    @Column("manager_name")
    private java.lang.String managerName;
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 商家名称       db_column: supplier_name
     * 
     * 
     * 
     */ 
    @Column("supplier_name")
    private java.lang.String supplierName;
    /**
     * 企业类型 0:生产厂商/1:品牌商/2:代理商       db_column: property
     * 
     * 
     * 
     */ 
    @Column("property")
    private java.lang.String property;
    /**
     * 营业范围       db_column: bus_scope
     * 
     * 
     * 
     */ 
    @Column("bus_scope")
    private java.lang.String busScope;
    /**
     * 公司营业执照注册号       db_column: com_registernum
     * 
     * 
     * 
     */ 
    @Column("com_registernum")
    private java.lang.String comRegisternum;
    /**
     * 公司固定电话       db_column: com_tel
     * 
     * 
     * 
     */ 
    @Column("com_tel")
    private java.lang.String comTel;
    /**
     * 公司邮编       db_column: com_pc
     * 
     * 
     * 
     */ 
    @Column("com_pc")
    private java.lang.Long comPc;
    /**
     * 省       db_column: com_state
     * 
     * 
     * 
     */ 
    @Column("com_state")
    private java.lang.String comState;
    /**
     * 市       db_column: com_city
     * 
     * 
     * 
     */ 
    @Column("com_city")
    private java.lang.String comCity;
    /**
     * 区       db_column: com_add
     * 
     * 
     * 
     */ 
    @Column("com_add")
    private java.lang.String comAdd;
    /**
     * 详细地址       db_column: com_address
     * 
     * 
     * 
     */ 
    @Column("com_address")
    private java.lang.String comAddress;
    /**
     * 企业人数       db_column: people_number
     * 
     * 
     * 
     */ 
    @Column("people_number")
    private java.lang.Integer peopleNumber;
    /**
     * 入驻时间 creat_time       db_column: join_time
     * 
     * 
     * 
     */ 
    @Column("join_time")
    private java.util.Date joinTime;
    /**
     * 状态 0:未提交 1:审核驳回 2:待运营审核 3:待财务审核 4:待退出执行 5:已退出  -1取消       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    /**
     * 创建、编辑时间 招商经理编辑时保存，升序       db_column: edit_time
     * 
     * 
     * 
     */ 
    @Column("edit_time")
    private java.util.Date editTime;
    /**
     * 退出原因       db_column: exit_reason
     * 
     * 
     * 
     */ 
    @Column("exit_reason")
    private java.lang.String exitReason;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;
    /**
     * 更新用户 姓名       db_column: update_user
     * 
     * 
     * 
     */ 
    @Column("update_user")
    private java.lang.String updateUser;

    //columns END

    private Integer productCnt;				//在售商品数
    private Integer orderCnt; 				//订单总数
    private Integer unCloseOrderCnt;		//未结订单总数
    private Integer unClosebillCnt; 		//未结对账单数
    private BigDecimal cashBalance; 		//现金券余额
    private String checkOpinion;			//审核意见
    
    
    
    public String getCheckOpinion() {
		return checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ManagerId",getManagerId())
            .append("ManagerName",getManagerName())
            .append("SupplierId",getSupplierId())
            .append("SupplierName",getSupplierName())
            .append("Property",getProperty())
            .append("BusScope",getBusScope())
            .append("ComRegisternum",getComRegisternum())
            .append("ComTel",getComTel())
            .append("ComPc",getComPc())
            .append("ComState",getComState())
            .append("ComCity",getComCity())
            .append("ComAdd",getComAdd())
            .append("ComAddress",getComAddress())
            .append("PeopleNumber",getPeopleNumber())
            .append("JoinTime",getJoinTime())
            .append("Status",getStatus())
            .append("EditTime",getEditTime())
            .append("ExitReason",getExitReason())
            .append("UpdateTime",getUpdateTime())
            .append("UpdateUser",getUpdateUser())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getManagerId() {
		return managerId;
	}

	public void setManagerId(java.lang.Long managerId) {
		this.managerId = managerId;
	}

	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

	public java.lang.String getProperty() {
		return property;
	}

	public void setProperty(java.lang.String property) {
		this.property = property;
	}

	public java.lang.String getBusScope() {
		return busScope;
	}

	public void setBusScope(java.lang.String busScope) {
		this.busScope = busScope;
	}

	public java.lang.String getComRegisternum() {
		return comRegisternum;
	}

	public void setComRegisternum(java.lang.String comRegisternum) {
		this.comRegisternum = comRegisternum;
	}

	public java.lang.String getComTel() {
		return comTel;
	}

	public void setComTel(java.lang.String comTel) {
		this.comTel = comTel;
	}

	public java.lang.Long getComPc() {
		return comPc;
	}

	public void setComPc(java.lang.Long comPc) {
		this.comPc = comPc;
	}

	public java.lang.String getComState() {
		return comState;
	}

	public void setComState(java.lang.String comState) {
		this.comState = comState;
	}

	public java.lang.String getComCity() {
		return comCity;
	}

	public void setComCity(java.lang.String comCity) {
		this.comCity = comCity;
	}

	public java.lang.String getComAdd() {
		return comAdd;
	}

	public void setComAdd(java.lang.String comAdd) {
		this.comAdd = comAdd;
	}

	public java.lang.String getComAddress() {
		return comAddress;
	}

	public void setComAddress(java.lang.String comAddress) {
		this.comAddress = comAddress;
	}

	public java.lang.Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(java.lang.Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public java.util.Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(java.util.Date joinTime) {
		this.joinTime = joinTime;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.util.Date getEditTime() {
		return editTime;
	}

	public void setEditTime(java.util.Date editTime) {
		this.editTime = editTime;
	}

	public java.lang.String getExitReason() {
		return exitReason;
	}

	public void setExitReason(java.lang.String exitReason) {
		this.exitReason = exitReason;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(java.lang.String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(Integer productCnt) {
		this.productCnt = productCnt;
	}

	public Integer getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(Integer orderCnt) {
		this.orderCnt = orderCnt;
	}

	public Integer getUnCloseOrderCnt() {
		return unCloseOrderCnt;
	}

	public void setUnCloseOrderCnt(Integer unCloseOrderCnt) {
		this.unCloseOrderCnt = unCloseOrderCnt;
	}

	public Integer getUnClosebillCnt() {
		return unClosebillCnt;
	}

	public void setUnClosebillCnt(Integer unClosebillCnt) {
		this.unClosebillCnt = unClosebillCnt;
	}

	public BigDecimal getCashBalance() {
		return cashBalance;
	}

	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

