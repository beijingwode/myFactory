package com.wode.factory.supplier.query;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;

import com.alibaba.fastjson.JSONObject;
import com.wode.common.frame.base.BaseQuery;
import com.wode.common.util.StringUtils;

public class SkuLadderVo extends BaseQuery implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4510558199047183212L;
	//columns START
    /**
     * 商品id       db_column: pId
     * 
     * 
     * 
     */ 
    @Column("pId")
    private java.lang.Long pId;
    /**
     * plId       db_column: plId
     * 
     * 
     * 
     */ 
    @Column("plId")
    private java.lang.Long plId;
    /**
     * skuId       db_column: skuId
     * 
     * 
     * 
     */ 
    @Column("skuId")
    private java.lang.Long skuId;
    
    /**
     * 商家id       db_column: supplier_id
     * 
     * 
     * 
     */ 
    @Column("supplier_id")
    private java.lang.Long supplierId;
    
    /**
     * 分类id       db_column: category_id
     * 
     * 
     * 
     */ 
    @Column("categoryId")
    private java.lang.Long categoryId;
    /**
     * 分类名称       db_column: categoryName
     * 
     * 
     * 
     */ 
    @Column("categoryName")
    private java.lang.String categoryName;
    /**
     * 商品名称       db_column: productName
     * 
     * 
     * 
     */ 
    @Column("productName")
    private java.lang.String productName;
    /**
     * itemValues      db_column: itemValues
     * 
     * 
     * 
     */ 
    @Column("itemValues")
    private java.lang.String itemValues;   
    /**
     * 内购价       db_column: internal_purchase_price
     * 
     * 
     * 
     */ 
    @Column("internal_purchase_price")
    private BigDecimal internalPurchasePrice;   
    /**
     * 库存      db_column: quantity
     * 
     * 
     * 
     */ 
    @Column("quantity")
    private Long quantity;
    /**
     * 数量    db_column: num
     * 
     * 
     * 
     */ 
    @Column("num")
    private Long num;
    /**
     * 价格       db_column: price
     * 
     * 
     * 
     */ 
    @Column("price")
    private BigDecimal price;
    @Column("type")
    private Integer type;
    //columns END
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public java.lang.Long getpId() {
		return pId;
	}
	public void setpId(java.lang.Long pId) {
		this.pId = pId;
	}
	public java.lang.Long getPlId() {
		return plId;
	}
	public void setPlId(java.lang.Long plId) {
		this.plId = plId;
	}
	public java.lang.Long getSkuId() {
		return skuId;
	}
	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}
	public java.lang.Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(java.lang.Long supplierId) {
		this.supplierId = supplierId;
	}
	public java.lang.Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(java.lang.Long categoryId) {
		this.categoryId = categoryId;
	}
	public java.lang.String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(java.lang.String categoryName) {
		this.categoryName = categoryName;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public java.lang.String getItemValues() {
		if(!StringUtils.isEmpty(itemValues)) {
			if(itemValues.contains("{")) {
				JSONObject jo = JSONObject.parseObject(itemValues);
				
				String rtn="";
				for (String key : jo.keySet()) {
					rtn += jo.getString(key)+"/";
				}
				if(rtn.length()>0) {
					rtn=rtn.substring(0, rtn.length()-1);
				}
				
				return rtn;
			}
		}
		return itemValues;
	}
	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}
	public BigDecimal getInternalPurchasePrice() {
		return internalPurchasePrice;
	}
	public void setInternalPurchasePrice(BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}   

}
