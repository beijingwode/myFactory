package com.wode.factory.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_supplier_limit_ticket_sku")
public class SupplierLimitTicketSku extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1268925398888226465L;
	//columns START
    /**
     * 券子单项ID       db_column: id
     * 
     * 
     * 
     */ 
    @PrimaryKey
    @Column("id")
    private java.lang.Long id;
    /**
     * 券ID       db_column: limit_ticket_id
     * 
     * 
     * 
     */ 
    @Column("limit_ticket_id")
    private java.lang.Long limitTicketId;
    /**
     * 商品id       db_column: productId
     * 
     * 
     * 
     */ 
    @Column("productId")
    private java.lang.Long productId;
    /**
     * 商品名称       db_column: productName
     * 
     * 
     * 
     */ 
    @Column("productName")
    private java.lang.String productName;
    /**
     * 规格id       db_column: skuId
     * 
     * 
     * 
     */ 
    @Column("skuId")
    private java.lang.Long skuId;
    /**
     * 商品主图       db_column: image
     * 
     * 
     * 
     */ 
    @Column("image")
    private java.lang.String image;
    /**
     * 商品规格       db_column: itemValues
     * 
     * 
     * 
     */ 
    @Column("itemValues")
    private java.lang.String itemValues;
    /**
     * sku 数量       db_column: sku_num
     * 
     * 
     * 
     */ 
    @Column("sku_num")
    private java.lang.Integer skuNum;
    /**
     * 电商价       db_column: price
     * 
     * 
     * 
     */ 
    @Column("price")
    private java.math.BigDecimal price;
    /**
     * 内购价       db_column: sale_price
     * 
     * 
     * 
     */ 
    @Column("sale_price")
    private java.math.BigDecimal salePrice;
    /**
     * 内购券       db_column: ticket
     * 
     * 
     * 
     */ 
    @Column("ticket")
    private java.math.BigDecimal ticket;
    /**
     * 扩展1       db_column: exp1
     * 
     * 
     * 
     */ 
    @Column("exp1")
    private java.lang.String exp1;
    /**
     * 扩展2       db_column: exp2
     * 
     * 
     * 
     */ 
    @Column("exp2")
    private java.lang.String exp2;
    /**
     * 扩展3       db_column: exp3
     * 
     * 
     * 
     */ 
    @Column("exp3")
    private java.lang.String exp3;

    //columns END

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("LimitTicketId",getLimitTicketId())
            .append("ProductId",getProductId())
            .append("ProductName",getProductName())
            .append("SkuId",getSkuId())
            .append("Image",getImage())
            .append("ItemValues",getItemValues())
            .append("SkuNum",getSkuNum())
            .append("Price",getPrice())
            .append("SalePrice",getSalePrice())
            .append("Ticket",getTicket())
            .append("Exp1",getExp1())
            .append("Exp2",getExp2())
            .append("Exp3",getExp3())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getLimitTicketId() {
		return limitTicketId;
	}

	public void setLimitTicketId(java.lang.Long limitTicketId) {
		this.limitTicketId = limitTicketId;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.Long getSkuId() {
		return skuId;
	}

	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}

	public java.lang.String getImage() {
		return image;
	}

	public void setImage(java.lang.String image) {
		this.image = image;
	}

	public java.lang.String getItemValues() {
		return itemValues;
	}

	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}

	public java.lang.Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(java.lang.Integer skuNum) {
		this.skuNum = skuNum;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.math.BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(java.math.BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public java.math.BigDecimal getTicket() {
		return ticket;
	}

	public void setTicket(java.math.BigDecimal ticket) {
		this.ticket = ticket;
	}

	public java.lang.String getExp1() {
		return exp1;
	}

	public void setExp1(java.lang.String exp1) {
		this.exp1 = exp1;
	}

	public java.lang.String getExp2() {
		return exp2;
	}

	public void setExp2(java.lang.String exp2) {
		this.exp2 = exp2;
	}

	public java.lang.String getExp3() {
		return exp3;
	}

	public void setExp3(java.lang.String exp3) {
		this.exp3 = exp3;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}