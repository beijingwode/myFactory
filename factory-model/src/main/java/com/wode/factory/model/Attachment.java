/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_attachment")
public class Attachment extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Attachment";
	
	public static final String ALIAS_ID = "附件ID";
	
	public static final String ALIAS_NAME = "附件名称";
	
	public static final String ALIAS_RELATED_ID = "附件关联id";
	
	public static final String ALIAS_SUPPLIER_ID = "附件所属供应商id";
	
	public static final String ALIAS_TYPE = "附件类型:图片类型、文档类型、txt类型";
	
	public static final String ALIAS_USER_ID = "附件上传人id";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_END_DATE = "附件截止有效期";
	
	public static final String ALIAS_RELATED_TYPE = "附件关联类型：";
	
	public static final String ALIAS_SIZE = "附件大小";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_END_DATE = DATE_TIME_FORMAT;
	
	//columns START
    /**
     * 附件ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 附件名称       db_column: name  
     * 
     * 
     * @NotBlank @Length(max=100)
     */
	@Column
	private java.lang.String name;
    /**
     * 附件关联id       db_column: relatedId  
     * 
     * 
     * @NotNull 
     */	
	@Column
	private java.lang.Long relatedId;
    /**
     * 附件所属供应商id       db_column: supplierId  
     * 
     * 
     * @NotNull 
     */	
	@Column
	private java.lang.Long supplierId;
    /**
     * 商家id       db_column: supplier_id  
     * 
     * 
     * 
     */	
	@Column("shop_id")
	private java.lang.Long shopId;
    /**
     * 附件类型:图片类型、文档类型、txt类型       db_column: type  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer type;
    /**
     * 附件上传人id       db_column: userId  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Long userId;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */	
	@Column
	private java.util.Date createDate;
	
    /**
     * 附件截止有效期       db_column: endDate  
     * 
     * 
     * 
     */
	@Column
	private java.util.Date endDate;
	
    /**
     * 附件关联类型：       db_column: relatedType  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Integer relatedType;
    /**
     * 附件大小       db_column: size  
     * 
     * 
     * 
     */	
	@Column
	private java.lang.Double size;
	
	@Column
	private java.lang.String url;
	//columns END

	public Attachment(){
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public Attachment(
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setRelatedId(java.lang.Long value) {
		this.relatedId = value;
	}
	
	public java.lang.Long getRelatedId() {
		return this.relatedId;
	}
	public void setSupplierId(java.lang.Long value) {
		this.supplierId = value;
	}
	
	public java.lang.Long getSupplierId() {
		return this.supplierId;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public String getCreateDateString() {
		return DateConvertUtils.format(getCreateDate(), FORMAT_CREATE_DATE);
	}
	public void setCreateDateString(String value) {
		setCreateDate(DateConvertUtils.parse(value, FORMAT_CREATE_DATE,java.util.Date.class));
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public String getEndDateString() {
		return DateConvertUtils.format(getEndDate(), FORMAT_END_DATE);
	}
	public void setEndDateString(String value) {
		setEndDate(DateConvertUtils.parse(value, FORMAT_END_DATE,java.util.Date.class));
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	public void setRelatedType(java.lang.Integer value) {
		this.relatedType = value;
	}
	
	public java.lang.Integer getRelatedType() {
		return this.relatedType;
	}
	public void setSize(java.lang.Double value) {
		this.size = value;
	}
	
	public java.lang.Double getSize() {
		return this.size;
	}

	public java.lang.Long getShopId() {
		return shopId;
	}

	public void setShopId(java.lang.Long shopId) {
		this.shopId = shopId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("RelatedId",getRelatedId())
			.append("SupplierId",getSupplierId())
			.append("ShopId",getShopId())
			.append("Type",getType())
			.append("UserId",getUserId())
			.append("CreateDate",getCreateDate())
			.append("EndDate",getEndDate())
			.append("RelatedType",getRelatedType())
			.append("Size",getSize())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Attachment == false) return false;
		if(this == obj) return true;
		Attachment other = (Attachment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

