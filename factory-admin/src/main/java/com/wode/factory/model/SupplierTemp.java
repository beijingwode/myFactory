package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_temp")
public class SupplierTemp extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2041087121645663848L;
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
     * 企业名称       db_column: com_name
     * 
     * 
     * 
     */ 
    @Column("com_name")
    private java.lang.String comName;
    /**
     * 品牌       db_column: brand_name
     * 
     * 
     * 
     */ 
    @Column("brand_name")
    private java.lang.String brandName;
    /**
     * 地址       db_column: addres
     * 
     * 
     * 
     */ 
    @Column("addres")
    private java.lang.String addres;
    /**
     * 联系人       db_column: contact
     * 
     * 
     * 
     */ 
    @Column("contact")
    private java.lang.String contact;
    /**
     * 手机号       db_column: phone
     * 
     * 
     * 
     */ 
    @Column("phone")
    private java.lang.String phone;
    /**
     * 创建时间 排序用       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 招商经理id (tongji)       db_column: manager_id
     * 
     * 
     * 
     */ 
    @Column("manager_id")
    private java.lang.Integer managerId;
    /**
     * 状态 0:洽谈中  2:已入住 3:已删除       db_column: status
     * 
     * 
     * 
     */ 
    @Column("status")
    private java.lang.Integer status;
    @Column("manager_name")
    private java.lang.String managerName;
    
    private Integer enCount;
    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ComName",getComName())
            .append("BrandName",getBrandName())
            .append("Addres",getAddres())
            .append("Contact",getContact())
            .append("Phone",getPhone())
            .append("CreateTime",getCreateTime())
            .append("ManagerId",getManagerId())
            .append("Status",getStatus())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getComName() {
		return comName;
	}
	public java.lang.String getManagerName() {
		return managerName;
	}

	public void setManagerName(java.lang.String managerName) {
		this.managerName = managerName;
	}
	public void setComName(java.lang.String comName) {
		this.comName = comName;
	}

	public java.lang.String getBrandName() {
		return brandName;
	}

	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}

	public java.lang.String getAddres() {
		return addres;
	}

	public void setAddres(java.lang.String addres) {
		this.addres = addres;
	}

	public java.lang.String getContact() {
		return contact;
	}

	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(java.lang.Integer managerId) {
		this.managerId = managerId;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getEnCount() {
		return enCount;
	}

	public void setEnCount(Integer enCount) {
		this.enCount = enCount;
	}

}

