package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_duration")
public class SupplierDuration  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	
	
	
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
     * supplierId       db_column: supplierId  
     * 
     * 
     * 
     */	
	@Column("supplierId")
	private java.lang.Long supplierId;
	
    /**
     * 对账单生成类别key       db_column: saleDurationKey  
     * 
     * 
     * 
     */	
	@Column("saleDurationKey")
	private java.lang.String saleDurationKey;
    /**
     * 首次账单生成日       db_column: startTime  
     * 
     * 
     * 
     */	
	@Column("startTime")
	private java.util.Date startTime;
    /**
     * 创建时间       db_column: createTime  
     * 
     * 
     * 
     */	
	@Column("createTime")
	private java.util.Date createTime;
    /**
     * createUserid       db_column: createUserid  
     * 
     * 
     * 
     */	
	@Column("createUserid")
	private java.lang.Long createUserid;
    /**
     * createUserid       db_column: createUserid  
     * 
     * 
     * 
     */	
    @Column("finance_code")
    private java.lang.String financeCode;
    /**
     * 收款方式 0:银行账号/1:支付宝对公/2:微信对公       db_column: account_type
     * 
     * 
     * 
     */ 
    @Column("account_type")
    private java.lang.String accountType;
    /**
     * 支付宝账号       db_column: alipay_account
     * 
     * 
     * 
     */ 
    @Column("alipay_account")
    private java.lang.String alipayAccount;
    /**
     * 微信账号       db_column: wechat_account
     * 
     * 
     * 
     */ 
    @Column("wechat_account")
    private java.lang.String wechatAccount;
    /**
     * 电话       db_column: phone
     * 
     * 
     * 
     */ 
    @Column("phone")
    private java.lang.String phone;
    /**
     * 联系人       db_column: contacts
     * 
     * 
     * 
     */ 
    @Column("contacts")
    private java.lang.String contacts;
    /**
     * 佣金计算保留几位小数     db_column: scale
     * 
     * 
     * 
     */ 
    @Column("scale")
    private java.lang.Integer scale;
    /**
     * 联系人       db_column: round_mode
     * 
     * 
     * 
     */ 
    @Column("round_mode")
    private java.lang.Integer roundMode;
    
    private Integer billType;//类型0普通发票1专用发票
    private String billTypeValue;//类型值
    private String taxpayerNumber;//纳税人识别号
    private String addressNumber;//地址、电话
    private String openingBanNumber;//开户行及账号
    
    
    
	//columns END


	public String getBillTypeValue() {
		return billTypeValue;
	}

	public void setBillTypeValue(String billTypeValue) {
		this.billTypeValue = billTypeValue;
	}

	public String getOpeningBanNumber() {
		return openingBanNumber;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public void setOpeningBanNumber(String openingBanNumber) {
		this.openingBanNumber = openingBanNumber;
	}

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


	public SupplierDuration(){
	}

	public SupplierDuration(
		java.lang.Long id
	){
		this.id = id;
	}

		
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
		
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
		
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
	public void setCreateUserid(java.lang.Long value) {
		this.createUserid = value;
	}
	
	public java.lang.Long getCreateUserid() {
		return this.createUserid;
	}

	public java.lang.String getSaleDurationKey() {
		return saleDurationKey;
	}

	public void setSaleDurationKey(java.lang.String saleDurationKey) {
		this.saleDurationKey = saleDurationKey;
	}

	public String getFinanceCode() {
		return financeCode;
	}

	public void setFinanceCode(String financeCode) {
		this.financeCode = financeCode;
	}

	public java.lang.String getAccountType() {
		return accountType;
	}

	public void setAccountType(java.lang.String accountType) {
		this.accountType = accountType;
	}

	public java.lang.String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(java.lang.String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public java.lang.String getWechatAccount() {
		return wechatAccount;
	}

	public void setWechatAccount(java.lang.String wechatAccount) {
		this.wechatAccount = wechatAccount;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getContacts() {
		return contacts;
	}

	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}

	public java.lang.Integer getScale() {
		if(scale == null) return 2;
		return scale;
	}

	public void setScale(java.lang.Integer scale) {
		this.scale = scale;
	}

	public java.lang.Integer getRoundMode() {
		if(roundMode == null) return 6;
		return roundMode;
	}

	public void setRoundMode(java.lang.Integer roundMode) {
		this.roundMode = roundMode;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("Id",getId())
                .append("SupplierId",getSupplierId())
                .append("SaleDurationKey",getSaleDurationKey())
                .append("StartTime",getStartTime())
                .append("CreateTime",getCreateTime())
                .append("CreateUserid",getCreateUserid())
                .append("FinanceCode",getFinanceCode())
                .append("AccountType",getAccountType())
                .append("AlipayAccount",getAlipayAccount())
                .append("WechatAccount",getWechatAccount())
                .append("Phone",getPhone())
                .append("Contacts",getContacts())
                .toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierDuration == false) return false;
		if(this == obj) return true;
		SupplierDuration other = (SupplierDuration)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

