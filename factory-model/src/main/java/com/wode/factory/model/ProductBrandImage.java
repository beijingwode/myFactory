package com.wode.factory.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_brand_image")
public class ProductBrandImage extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1952791673597966346L;
	//columns START
    /**
     * 图片ID       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 排序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    /**
     * 路径       db_column: source
     * 
     * 
     * 
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
     * 商户       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 品牌id       db_column: brand_id
     * 
     * 
     * 
     */ 
    @Column("brand_id")
    private java.lang.Long brandId;
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

    
    
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Orders",getOrders())
            .append("Source",getSource())
            .append("CreateDate",getCreateDate())
            .append("UpdateDate",getUpdateDate())
            .append("SupplierId",getSupplierId())
            .append("BrandId",getBrandId())
            .append("Size",getSize())
            .append("Height",getHeight())
            .append("Width",getWidth())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.String getSource() {
		return source;
	}

	public void setSource(java.lang.String source) {
		this.source = source;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.Long getBrandId() {
		return brandId;
	}

	public void setBrandId(java.lang.Long brandId) {
		this.brandId = brandId;
	}

	public java.lang.Long getSize() {
		return size;
	}

	public void setSize(java.lang.Long size) {
		this.size = size;
	}

	public java.lang.Integer getHeight() {
		return height;
	}

	public void setHeight(java.lang.Integer height) {
		this.height = height;
	}

	public java.lang.Integer getWidth() {
		return width;
	}

	public void setWidth(java.lang.Integer width) {
		this.width = width;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
}
