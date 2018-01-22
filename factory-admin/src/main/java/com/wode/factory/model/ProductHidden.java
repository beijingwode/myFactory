package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_hidden")
public class ProductHidden extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8705460810191788938L;
	//columns START
    /**
     * 商品ID       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 商品名称       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 隐藏时间       db_column: hidden_date
     * 
     * 
     * 
     */ 
    @Column("hidden_date")
    private java.util.Date hiddenDate;
    /**
     * 内购价       db_column: internal_purchase_price
     * 
     * 
     * 
     */ 
    @Column("internal_purchase_price")
    private java.math.BigDecimal internalPurchasePrice;
    /**
     * 最新电商价       db_column: last_price
     * 
     * 
     * 
     */ 
    @Column("last_price")
    private java.math.BigDecimal lastPrice;
    /**
     * 更新时间       db_column: update_time
     * 
     * 
     * 
     */ 
    @Column("update_time")
    private java.util.Date updateTime;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("ProductName",getProductName())
            .append("HiddenDate",getHiddenDate())
            .append("InternalPurchasePrice",getInternalPurchasePrice())
            .append("LastPrice",getLastPrice())
            .append("UpdateTime",getUpdateTime())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.util.Date getHiddenDate() {
		return hiddenDate;
	}

	public void setHiddenDate(java.util.Date hiddenDate) {
		this.hiddenDate = hiddenDate;
	}

	public java.math.BigDecimal getInternalPurchasePrice() {
		return internalPurchasePrice;
	}

	public void setInternalPurchasePrice(java.math.BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}

	public java.math.BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(java.math.BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

