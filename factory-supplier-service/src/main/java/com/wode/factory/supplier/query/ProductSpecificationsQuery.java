/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.query;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;

import com.wode.common.frame.base.BaseQuery;
import com.wode.common.stereotype.PrimaryKey;


public class ProductSpecificationsQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** 规格集ID */
	private java.lang.Long id;
	/** 商品编码 */
	private java.lang.String productCode;
	/** 产品ID */
	private java.lang.Long productId;
	/** 成本价 */
	private Long cost;
	/** 市场价 */
	private Long marketPrice;
	/** 销售价 */
	private Long price;
	/** 库存 */
	private java.lang.Long stock;
	//columns START
    /**
     * 销量       db_column: stock
     */
	private java.lang.Integer sellnum;
	
	private Integer maxFucoin;//最大可使用的福利币（联盟员工福利币）
	private String itemids;//决定sku的商品规格的id的集合，以“，”分割
	private String itemValues;//决定sku的商品规格的value的集合，以“/”分割
	
	private String itemnames;//决定sku的商品规格的name的集合，以“/”分割 
	
	private Integer isDelete;//是否删除 0：未删除   1：已删除
	private Integer warnnum;//预警值
	
	private Integer promotionStatus; // 是否正在参加某项活动（0或null：没有； 1：有）
	
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public Long getCost() {
		return this.cost;
	}
	
	public void setCost(Long value) {
		this.cost = value;
	}
	
	public Long getMarketPrice() {
		return this.marketPrice;
	}
	
	public void setMarketPrice(Long value) {
		this.marketPrice = value;
	}
	
	public Long getPrice() {
		return this.price;
	}
	
	public void setPrice(Long value) {
		this.price = value;
	}
	
	public java.lang.Long getStock() {
		return this.stock;
	}
	
	public void setStock(java.lang.Long value) {
		this.stock = value;
	}
	

	public java.lang.Integer getSellnum() {
		return sellnum;
	}

	public void setSellnum(java.lang.Integer sellnum) {
		this.sellnum = sellnum;
	}

	public Integer getMaxFucoin() {
		return maxFucoin;
	}

	public void setMaxFucoin(Integer maxFucoin) {
		this.maxFucoin = maxFucoin;
	}

	public String getItemids() {
		return itemids;
	}

	public void setItemids(String itemids) {
		this.itemids = itemids;
	}

	public String getItemValues() {
		return itemValues;
	}

	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}

	public String getItemnames() {
		return itemnames;
	}

	public void setItemnames(String itemnames) {
		this.itemnames = itemnames;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getWarnnum() {
		return warnnum;
	}

	public void setWarnnum(Integer warnnum) {
		this.warnnum = warnnum;
	}

	public Integer getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(Integer promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

