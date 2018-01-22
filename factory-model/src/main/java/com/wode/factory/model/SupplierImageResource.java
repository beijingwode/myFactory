package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_image_resource")
public class SupplierImageResource  implements java.io.Serializable{
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
     *  图片url       db_column: image  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("image")
	private java.lang.String image;
    /**
     * 年       db_column: yaer  
     * 
     * 
     * 
     */	
	@Column("yaer")
	private java.lang.Integer yaer;
    /**
     *  年+月       db_column: year_month  
     * 
     * 
     * 
     */	
	@Column("date")
	private java.util.Date date;
    /**
     * 创建时间       db_column: create_time  
     * 
     * 
     * 
     */	
	@Column("create_time")
	private java.util.Date createTime;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * 状态       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
	//columns END

	public SupplierImageResource(){
	}

	public SupplierImageResource(
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
		
	public void setImage(java.lang.String value) {
		this.image = value;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}

	public java.lang.Integer getYaer() {
		return yaer;
	}

	public void setYaer(java.lang.Integer yaer) {
		this.yaer = yaer;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
		
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Image",getImage())
			.append("Yaer",getYaer())
			.append("Date",getDate())
			.append("CreateTime",getCreateTime())
			.append("SupplierId",getSupplierId())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierImageResource == false) return false;
		if(this == obj) return true;
		SupplierImageResource other = (SupplierImageResource)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

