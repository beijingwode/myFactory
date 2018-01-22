package com.wode.factory.model;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_group_buy_product")
public class GroupBuyProduct extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2969081796248420788L;
	@PrimaryKey
	@Column(value="id")
	@Id
	private java.lang.Long id;
	/**
	 * 团ID db_column: group_id
	 */
	@Column(value="group_id")
	private java.lang.Long groupId;
	/**
	 * 商品ID db_column: product_id
	 */
	@Column(value="product_id")
	private java.lang.Long productId;
	/**
	 * 规格ID db_column: skuId
	 */
	@Column(value="skuId")
	private java.lang.Long skuId;
	/**
	 * 商品全称 db_column: product_name
	 */
	@Column(value="product_name")
	private java.lang.String productName;
	/**
	 * 电商价 db_column: market_price
	 */
	@Column(value="market_price")
	private BigDecimal marketPrice;
	/**
	 * 商品主图 db_column: image
	 */
	@Column(value="image")
	private java.lang.String image;
	/**
	 * 内购券 db_column: maxFucoin
	 */
	@Column(value="maxFucoin")
	private BigDecimal maxFucoin;
	/**
	 * 内购价 db_column: internal_purchase_price
	 */
	@Column(value="internal_purchase_price")
	private BigDecimal internalPurchasePrice;
	/**
	 * 起售数量db_column: min_limit_num
	 */
	@Column(value="min_limit_num")
	private java.lang.Integer minLimitNum;
	/**
	 * 最低阶梯价 db_column: best_price
	 */
	@Column(value="best_price")
	private BigDecimal bestPrice;
	/**
	 * 创建时间 db_column: create_time
	 */
	@Column(value="create_time")
	private java.util.Date create_time;
	@Column(value="itemValues")
	private java.lang.String itemValues;
	@Column(value="exp3")
	private java.lang.String exp3;
	@Column(value="exp4")
	private java.lang.String exp4;
	
	private Integer purchasedNum;//已购数量
	public Integer getPurchasedNum() {
		return purchasedNum;
	}
	public void setPurchasedNum(Integer purchasedNum) {
		this.purchasedNum = purchasedNum;
	}
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.Long getGroupId() {
		return groupId;
	}
	public void setGroupId(java.lang.Long groupId) {
		this.groupId = groupId;
	}
	public java.lang.Long getProductId() {
		return productId;
	}
	public void setProductId(java.lang.Long productId) {
		this.productId = productId;
	}
	public java.lang.Long getSkuId() {
		return skuId;
	}
	public void setSkuId(java.lang.Long skuId) {
		this.skuId = skuId;
	}
	public java.lang.String getProductName() {
		return productName;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public java.lang.String getImage() {
		return image;
	}
	public void setImage(java.lang.String image) {
		this.image = image;
	}
	public BigDecimal getMaxFucoin() {
		return maxFucoin;
	}
	public void setMaxFucoin(BigDecimal maxFucoin) {
		this.maxFucoin = maxFucoin;
	}
	public BigDecimal getInternalPurchasePrice() {
		return internalPurchasePrice;
	}
	public void setInternalPurchasePrice(BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}
	public java.lang.Integer getMinLimitNum() {
		return minLimitNum;
	}
	public void setMinLimitNum(java.lang.Integer minLimitNum) {
		this.minLimitNum = minLimitNum;
	}
	public BigDecimal getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(BigDecimal bestPrice) {
		this.bestPrice = bestPrice;
	}
	public java.lang.String getExp3() {
		return exp3;
	}
	public void setExp3(java.lang.String exp3) {
		this.exp3 = exp3;
	}
	public java.lang.String getExp4() {
		return exp4;
	}
	public void setExp4(java.lang.String exp4) {
		this.exp4 = exp4;
	}
	
	public java.util.Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "GroupBuyProduct [id=" + id + ", groupId=" + groupId + ", productId=" + productId + ", skuId=" + skuId
				+ ", productName=" + productName + ", marketPrice=" + marketPrice + ", image=" + image + ", maxFucoin="
				+ maxFucoin + ", internalPurchasePrice=" + internalPurchasePrice + ", minLimitNum=" + minLimitNum
				+ ", bestPrice=" + bestPrice + ", create_time=" + create_time + ",exp3=" + exp3
				+ ", exp4=" + exp4 + ", purchasedNum=" + purchasedNum + "]";
	}
	public java.lang.String getItemValues() {
		return itemValues;
	}
	public void setItemValues(java.lang.String itemValues) {
		this.itemValues = itemValues;
	}
	
	
}
