package com.wode.tongji.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_manager_business")
public class ManagerBusiness extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5588891822462677340L;
	//columns START
    /**
     * 招商经理id       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 招商经理姓名       db_column: name
     * 
     * 
     * 
     */ 
    @Column("name")
    private java.lang.String name;
    /**
     * 运营id       db_column: business_id
     * 
     * 
     * 
     */ 
    @Column("business_id")
    private java.lang.Long businessId;
    /**
     * 运营姓名       db_column: business_name
     * 
     * 
     * 
     */ 
    @Column("business_name")
    private java.lang.String businessName;
    /**
     * 运营邮箱       db_column: business_email
     * 
     * 
     * 
     */ 
    @Column("business_email")
    private java.lang.String businessEmail;
    /**
     * 运营电话       db_column: business_phone
     * 
     * 
     * 
     */ 
    @Column("business_phone")
    private java.lang.String businessPhone;
    /**
     * 运营手机       db_column: business_mobile
     * 
     * 
     * 
     */ 
    @Column("business_mobile")
    private java.lang.String businessMobile;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Name",getName())
            .append("BusinessId",getBusinessId())
            .append("BusinessName",getBusinessName())
            .append("BusinessEmail",getBusinessEmail())
            .append("BusinessPhone",getBusinessPhone())
            .append("BusinessMobile",getBusinessMobile())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(java.lang.Long businessId) {
		this.businessId = businessId;
	}

	public java.lang.String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(java.lang.String businessName) {
		this.businessName = businessName;
	}

	public java.lang.String getBusinessEmail() {
		return businessEmail;
	}

	public void setBusinessEmail(java.lang.String businessEmail) {
		this.businessEmail = businessEmail;
	}

	public java.lang.String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(java.lang.String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public java.lang.String getBusinessMobile() {
		return businessMobile;
	}

	public void setBusinessMobile(java.lang.String businessMobile) {
		this.businessMobile = businessMobile;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

