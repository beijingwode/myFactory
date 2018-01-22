package com.wode.factory.model;


import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_user_exchange_favorites")
public class UserExchangeFavorites extends BaseModel implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3611337454483555254L;
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
     * 用户id       db_column: user_id
     * 
     * 
     * 
     */ 
    @Column("user_id")
    private java.lang.Long userId;
    /**
     * 商品id       db_column: product_id
     * 
     * 
     * 
     */ 
    @Column("product_id")
    private java.lang.Long productId;
    /**
     * 商家id 选择商品时带入       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    /**
     * 商品名称       db_column: product_name
     * 
     * 
     * 
     */ 
    @Column("product_name")
    private java.lang.String productName;
    /**
     * 店铺名称 选择商品时带入       db_column: shop_name
     * 
     * 
     * 
     */ 
    @Column("shop_name")
    private java.lang.String shopName;
    /**
     * skuid 无实际意义，选择商品时带入       db_column: sku_id
     * 
     * 
     * 
     */ 
    @Column("sku_id")
    private java.lang.Long skuId;
    /**
     * 内购价 选择商品时带入       db_column: sale_price
     * 
     * 
     * 
     */ 
    @Column("sale_price")
    private java.math.BigDecimal salePrice;
    /**
     * 库存 选择商品时带入       db_column: stock
     * 
     * 
     * 
     */ 
    @Column("stock")
    private java.lang.Integer stock;
    /**
     * 主图 选择商品时带入       db_column: image_path
     * 
     * 
     * 
     */ 
    @Column("image_path")
    private java.lang.String imagePath;
    /**
     * 主图 选择商品时带入       db_column: itemValues
     * 
     * 
     * 
     */ 
    @Column("itemValues")
    private java.lang.String itemValues;
    /**
     * 填入实际 排序是第二顺序 降序       db_column: create_time
     * 
     * 
     * 
     */ 
    @Column("create_time")
    private java.util.Date createTime;
    /**
     * 排序 默认100 我的圈 500 用户可修改 第一顺序 升序       db_column: orders
     * 
     * 
     * 
     */ 
    @Column("orders")
    private java.lang.Integer orders;
    
    //columns END
    private ShippingTemplate shippingTemplate;
	private List<ProductSpecifications> productSpecificationslist;
	
    public List<ProductSpecifications> getProductSpecificationslist() {
		return productSpecificationslist;
	}

	public void setProductSpecificationslist(List<ProductSpecifications> productSpecificationslist) {
		this.productSpecificationslist = productSpecificationslist;
	}

	public ShippingTemplate getShippingTemplate() {
		return shippingTemplate;
	}

	public void setShippingTemplate(ShippingTemplate shippingTemplate) {
		this.shippingTemplate = shippingTemplate;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("UserId",getUserId())
            .append("ProductId",getProductId())
            .append("SupplierId",getSupplierId())
            .append("ProductName",getProductName())
            .append("ShopName",getShopName())
            .append("SkuId",getSkuId())
            .append("SalePrice",getSalePrice())
            .append("Stock",getStock())
            .append("ImagePath",getImagePath())
            .append("CreateTime",getCreateTime())
            .append("Orders",getOrders())
            .toString();
    }

    public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Long getProductId() {
		return productId;
	}

	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}

	public java.lang.Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getShopName() {
		return shopName;
	}

	public void setShopName(java.lang.String shopName) {
		this.shopName = shopName;
	}

	public java.lang.Long getSkuId() {
		return skuId;
	}

	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}

	public java.math.BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(java.math.BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public java.lang.Integer getStock() {
		return stock;
	}

	public void setStock(java.lang.Integer stock) {
		this.stock = stock;
	}

	public java.lang.String getImagePath() {
		return imagePath;
	}

	public void setImagePath(java.lang.String imagePath) {
		this.imagePath = imagePath;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getOrders() {
		return orders;
	}

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	public java.lang.String getItemValues() {
		return itemValues;
	}

	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}

	public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}

