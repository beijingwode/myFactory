/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

@Table("t_product_specifications_image")
public class ProductSpecificationsImage extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductSpecificationsImage";
	
	public static final String ALIAS_ID = "图片ID";
	
	public static final String ALIAS_SPECIFICATIONS_ID = "商品规格集ID";
	
	public static final String ALIAS_ORDERS = "排序";
	
	public static final String ALIAS_SOURCE = "路径";
	
	public static final String ALIAS_CREATE_DATE = "创建时间";
	
	public static final String ALIAS_UPDATE_DATE = "更新时间";
	
	public static final String ALIAS_SUPPLY_ID = "商户";
	
	public static final String ALIAS_SIZE = "大小";
	
	public static final String ALIAS_HEIGHT = "height";
	
	public static final String ALIAS_WIDTH = "width";
	
	//date formats
	public static final String FORMAT_CREATE_DATE = DATE_TIME_FORMAT;
	public static final String FORMAT_UPDATE_DATE = DATE_TIME_FORMAT;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//columns START
    /**
     * 图片ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 商品规格集ID       db_column: specifications_id  
     * 
     * 
     * 
     */
	@Column("specifications_id")
	private java.lang.Long specificationsId;
    /**
     * 排序       db_column: orders  
     * 
     * 
     * @Max(127)
     */
	@Column("orders")
	private java.lang.Integer orders;
    /**
     * 路径       db_column: source  
     * 
     * 
     * @Length(max=500)
     */
	@Column("source")
	private java.lang.String source;
    /**
     * 创建时间       db_column: createDate  
     * 
     * 
     * 
     */
	@Column("createDate")
	private java.util.Date createDate;
    /**
     * 更新时间       db_column: updateDate  
     * 
     * 
     * 
     */
	@Column("updateDate")
	private java.util.Date updateDate;
    /**
     * 商户       db_column: supply_id  
     * 
     * 
     * 
     */
	@Column("supply_id")
	private java.lang.Long supplyId;
    /**
     * 大小       db_column: size  
     * 
     * 
     * 
     */
	@Column("size")
	private java.lang.Long size;
    /**
     * height       db_column: height  
     * 
     * 
     * 
     */
	@Column("height")
	private java.lang.Integer height;
    /**
     * width       db_column: width  
     * 
     * 
     * 
     */
	@Column("width")
	private java.lang.Integer width;
	//columns END

	public ProductSpecificationsImage(){
	}

	public ProductSpecificationsImage(
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
	public void setSpecificationsId(java.lang.Long value) {
		this.specificationsId = value;
	}
	
	public java.lang.Long getSpecificationsId() {
		return this.specificationsId;
	}
	public void setOrders(Integer value) {
		this.orders = value;
	}
	
	public Integer getOrders() {
		return this.orders;
	}
	public void setSource(java.lang.String value) {
		this.source = value;
	}
	
	public java.lang.String getSource() {
		return this.source;
	}
	public String getCreateDateString() {
		return DateConvertUtils.format(getCreateDate(), FORMAT_CREATE_DATE);
	}
	public void setCreateDateString(String value) {
		try {
			setCreateDate(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		setCreateDate(DateConvertUtils.parse(value, FORMAT_CREATE_DATE,java.util.Date.class));
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public String getUpdateDateString() {
		return DateConvertUtils.format(getUpdateDate(), FORMAT_UPDATE_DATE);
	}
	public void setUpdateDateString(String value) {
		try {
			setUpdateDate(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		setUpdateDate(DateConvertUtils.parse(value, FORMAT_UPDATE_DATE,java.util.Date.class));
	}
	
	public void setUpdateDate(java.util.Date value) {
		this.updateDate = value;
	}
	
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	public void setSupplyId(java.lang.Long value) {
		this.supplyId = value;
	}
	
	public java.lang.Long getSupplyId() {
		return this.supplyId;
	}
	public void setSize(java.lang.Long value) {
		this.size = value;
	}
	
	public java.lang.Long getSize() {
		return this.size;
	}
	public void setHeight(java.lang.Integer value) {
		this.height = value;
	}
	
	public java.lang.Integer getHeight() {
		return this.height;
	}
	public void setWidth(java.lang.Integer value) {
		this.width = value;
	}
	
	public java.lang.Integer getWidth() {
		return this.width;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SpecificationsId",getSpecificationsId())
			.append("Orders",getOrders())
			.append("Source",getSource())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.append("SupplyId",getSupplyId())
			.append("Size",getSize())
			.append("Height",getHeight())
			.append("Width",getWidth())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductSpecificationsImage == false) return false;
		if(this == obj) return true;
		ProductSpecificationsImage other = (ProductSpecificationsImage)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

