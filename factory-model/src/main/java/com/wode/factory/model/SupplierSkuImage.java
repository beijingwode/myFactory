package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_sku_image")
public class SupplierSkuImage  implements java.io.Serializable{
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
     * image1       db_column: image1  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("image1")
	private java.lang.String image1;
    /**
     * image2       db_column: image2  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("image2")
	private java.lang.String image2;
    /**
     * image3       db_column: image3  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("image3")
	private java.lang.String image3;
    /**
     * image4       db_column: image4  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("image4")
	private java.lang.String image4;
    /**
     * image5       db_column: image5  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("image5")
	private java.lang.String image5;
    /**
     * supplierId       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("supplier_id")
	private java.lang.Long supplierId;
    /**
     * introduce       db_column: introduce  
     * 
     * 
     * @Length(max=255)
     */	
	@Column("introduce")
	private java.lang.String introduce;
    /**
     * createTime       db_column: create_time  
     * 
     * 
     * 
     */	
	@Column("create_time")
	private java.util.Date createTime;
    /**
     * 状态       db_column: status  
     * 
     * 
     * 
     */	
	@Column("status")
	private java.lang.Integer status;
	//columns END

	public SupplierSkuImage(){
	}

	public SupplierSkuImage(
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
		
	public void setImage1(java.lang.String value) {
		this.image1 = value;
	}
	
	public java.lang.String getImage1() {
		return this.image1;
	}
		
	public void setImage2(java.lang.String value) {
		this.image2 = value;
	}
	
	public java.lang.String getImage2() {
		return this.image2;
	}
		
	public void setImage3(java.lang.String value) {
		this.image3 = value;
	}
	
	public java.lang.String getImage3() {
		return this.image3;
	}
		
	public void setImage4(java.lang.String value) {
		this.image4 = value;
	}
	
	public java.lang.String getImage4() {
		return this.image4;
	}
		
	public void setImage5(java.lang.String value) {
		this.image5 = value;
	}
	
	public java.lang.String getImage5() {
		return this.image5;
	}
		
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	
	public java.lang.String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
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
			.append("Image1",getImage1())
			.append("Image2",getImage2())
			.append("Image3",getImage3())
			.append("Image4",getImage4())
			.append("Image5",getImage5())
			.append("SupplierId",getSupplierId())
			.append("Introduce",getIntroduce())
			.append("CreateTime",getCreateTime())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierSkuImage == false) return false;
		if(this == obj) return true;
		SupplierSkuImage other = (SupplierSkuImage)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

