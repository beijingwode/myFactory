package com.wode.factory.model;


import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_invoice_apply")
public class InvoiceApply extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5370084955700093379L;
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
     * 抬头       db_column: userId
     * 
     * 
     * 
     */ 
    @Column("userId")
    private java.lang.Long userId;
    /**
     * 发票抬头       db_column: title
     * 
     * 
     * 
     */ 
    @Column("title")
    private java.lang.String title;
    /**
     * 订单id       db_column: suborderid
     * 
     * 
     * 
     */ 
    @Column("suborderid")
    private java.lang.String suborderid;
    /**
     * 地址       db_column: address
     * 
     * 
     * 
     */ 
    @Column("address")
    private java.lang.String address;
    
    /**
     * 创建时间
     */
    @Column("createtime")
    private Date createtime;
    /**
     * 备注
     */
    @Column("note")
    private String note;
    /**
     * 发票类型0个人1单位
     */
    @Column("type")
    private Integer type;
    
    private IssuedInvoice issuedInvoice;
    
    private Integer billType;//发票类型
    private String taxpayerNumber;//纳税人识别号
    private String registerAddress;//注册地址
    private String registerPhone;//注册电话
    private String openingBan;//开户行
    private String openingBanNumber;//开户行账号
    
    
    
    //columns END

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}

	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getOpeningBan() {
		return openingBan;
	}

	public void setOpeningBan(String openingBan) {
		this.openingBan = openingBan;
	}

	public String getOpeningBanNumber() {
		return openingBanNumber;
	}

	public void setOpeningBanNumber(String openingBanNumber) {
		this.openingBanNumber = openingBanNumber;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("Title",getTitle())
            .append("Suborderid",getSuborderid())
            .append("Address",getAddress())
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

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getSuborderid() {
		return suborderid;
	}

	public void setSuborderid(java.lang.String suborderid) {
		this.suborderid = suborderid;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public IssuedInvoice getIssuedInvoice() {
		return issuedInvoice;
	}

	public void setIssuedInvoice(IssuedInvoice issuedInvoice) {
		this.issuedInvoice = issuedInvoice;
	}

}

