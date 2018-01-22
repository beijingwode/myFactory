package com.wode.factory.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_address")
public class SupplierAddress extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SupplierAddress";
	
	public static final String ALIAS_ID = "id";
	
	public static final String ALIAS_SUPPLIER_ID = "supplier_id";
	
	public static final String ALIAS_AID = "aid";
	
	public static final String ALIAS_PROVINCE_NAME = "provinceName";
	
	public static final String ALIAS_CITY_NAME = "cityName";
	
	public static final String ALIAS_AREA_NAME = "areaName";
	
	public static final String ALIAS_ADDRESS = "address";
	
	public static final String ALIAS_NAME = "name";
	
	public static final String ALIAS_PHONE = "phone";
	
	public static final String ALIAS_ORDER_NO = "order_no";
	
	public static final String ALIAS_COMPANYNAME = "公司名称";
	
	public static final String ALIAS_TEL = "座机号码";
	
	public static final String ALIAS_RETURNED = "退货默认    0否  1是";
	
	public static final String ALIAS_SEND = "发货默认   0否 1是";
	
	public static final String ALIAS_COMMENTS = "备注";
	
	//date formats
	
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
     * user_id       db_column: user_id  
     * 
     * 
     * 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * aid       db_column: aid  
     * 
     * 
     * 
     */	
	@Column("aid")
	private java.lang.String aid;
    /**
     * provinceName       db_column: provinceName  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("provinceName")
	private java.lang.String provinceName;
    /**
     * cityName       db_column: cityName  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("cityName")
	private java.lang.String cityName;
    /**
     * areaName       db_column: areaName  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("areaName")
	private java.lang.String areaName;
    /**
     * address       db_column: address  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("address")
	private java.lang.String address;
    /**
     * name       db_column: name  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("name")
	private java.lang.String name;
    /**
     * phone       db_column: phone  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("phone")
	private java.lang.String phone;
    /**
     * order_no       db_column: order_no  
     * 
     * 
     * 
     */	
	@Column("order_no")
	private java.lang.Integer orderNo;
    /**
     * 公司名称       db_column: companyname  
     * 
     * 
     * @Length(max=50)
     */	
	@Column("companyname")
	private java.lang.String companyname;
    /**
     * 座机号码       db_column: tel  
     * 
     * 
     * @Length(max=20)
     */	
	@Column("tel")
	private java.lang.String tel;
    /**
     * 退货默认    0否  1是       db_column: returned  
     * 
     * 
     * 
     */	
	@Column("returned")
	private java.lang.Integer returned;
    /**
     * 发货默认   0否 1是       db_column: send  
     * 
     * 
     * 
     */	
	@Column("send")
	private java.lang.Integer send;
    /**
     * 备注       db_column: comments  
     * 
     * 
     * @Length(max=65535)
     */	
	@Column("comments")
	private java.lang.String comments;
	
	/**
     * 邮编       db_column: postcode  
     * 
     * 
     * @Length(max=10)
     */	
	@Column("postcode")
	private java.lang.Integer postcode;
	//columns END

	public SupplierAddress(){
	}

	public SupplierAddress(
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
	
	public java.lang.String getAid() {
		return aid;
	}

	public void setAid(java.lang.String aid) {
		this.aid = aid;
	}

	public void setProvinceName(java.lang.String value) {
		this.provinceName = value;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setCityName(java.lang.String value) {
		this.cityName = value;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setAreaName(java.lang.String value) {
		this.areaName = value;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setOrderNo(java.lang.Integer value) {
		this.orderNo = value;
	}
	
	public java.lang.Integer getOrderNo() {
		return this.orderNo;
	}
	public void setCompanyname(java.lang.String value) {
		this.companyname = value;
	}
	
	public java.lang.String getCompanyname() {
		return this.companyname;
	}
	public void setTel(java.lang.String value) {
		this.tel = value;
	}
	
	public java.lang.String getTel() {
		return this.tel;
	}
	public void setReturned(java.lang.Integer value) {
		this.returned = value;
	}
	
	public java.lang.Integer getReturned() {
		return this.returned;
	}
	public void setSend(java.lang.Integer value) {
		this.send = value;
	}
	
	public java.lang.Integer getSend() {
		return this.send;
	}
	public void setComments(java.lang.String value) {
		this.comments = value;
	}
	
	public java.lang.String getComments() {
		return this.comments;
	}

	public java.lang.Integer getPostcode() {
		return postcode;
	}

	public void setPostcode(java.lang.Integer postcode) {
		this.postcode = postcode;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SupplierId",getSupplierId())
			.append("Aid",getAid())
			.append("ProvinceName",getProvinceName())
			.append("CityName",getCityName())
			.append("AreaName",getAreaName())
			.append("Address",getAddress())
			.append("Name",getName())
			.append("Phone",getPhone())
			.append("OrderNo",getOrderNo())
			.append("Companyname",getCompanyname())
			.append("Tel",getTel())
			.append("Returned",getReturned())
			.append("Send",getSend())
			.append("Comments",getComments())
			.append("Postcode",getPostcode())
			.toString();
	}
	
	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierAddress == false) return false;
		if(this == obj) return true;
		SupplierAddress other = (SupplierAddress)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}