package com.wode.factory.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_third_price")
public class ProductThirdPrice extends BaseModel implements java.io.Serializable{

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
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 第三方平台 jd:京东 tmall:天猫       db_column: third_type
     * 
     * 
     * 
     */ 
    @Column("third_type")
    private java.lang.String thirdType;
    /**
     * 电商价 比价时       db_column: price
     * 
     * 
     * 
     */ 
    @Column("price")
    private java.math.BigDecimal price;
    /**
     * 比价用网址       db_column: item_url
     * 
     * 
     * 
     */ 
    @Column("item_url")
    private java.lang.String itemUrl;
    /**
     * 价格确认日期       db_column: confrim_date
     * 
     * 
     * 
     */ 
    @Column("confrim_date")
    private java.util.Date confrimDate;
    /**
     * 最新价格       db_column: last_price
     * 
     * 
     * 
     */ 
    @Column("last_price")
    private java.math.BigDecimal lastPrice;
    /**
     * 更新时间       db_column: update_date
     * 
     * 
     * 
     */ 
    @Column("update_date")
    private java.util.Date updateDate;
    /**
     * 数据状态 1:正常/-1:url无法访问/-2:url无法解析       db_column: url_status
     * 
     * 
     * 
     */ 
    @Column("url_status")
    private java.lang.Integer urlStatus;
    
    /**
     * 商品规格集Id
     */
    @Column("itemValues")
    private java.lang.String itemValues;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ProductId",getProductId())
            .append("ThirdType",getThirdType())
            .append("Price",getPrice())
            .append("ItemUrl",getItemUrl())
            .append("ConfrimDate",getConfrimDate())
            .append("LastPrice",getLastPrice())
            .append("UpdateDate",getUpdateDate())
            .append("UrlStatus",getUrlStatus())
            .append("itemValues",getItemValues())
            .toString();
    }
    
    
    public java.lang.String getItemValues() {
		return itemValues;
	}


	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}


	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.String getThirdType() {
		return thirdType;
	}

	public void setThirdType(java.lang.String thirdType) {
		this.thirdType = thirdType;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.lang.String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(java.lang.String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public java.util.Date getConfrimDate() {
		return confrimDate;
	}

	public void setConfrimDate(java.util.Date confrimDate) {
		this.confrimDate = confrimDate;
	}

	public java.math.BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(java.math.BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.Integer getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(java.lang.Integer urlStatus) {
		this.urlStatus = urlStatus;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProductThirdPrice == false) return false;
		if(this == obj) return true;
		ProductThirdPrice other = (ProductThirdPrice)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
}

