/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.model;


import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.wode.common.base.BaseModel;
import com.wode.common.stereotype.PrimaryKey;

@Table("t_product_specifications")
public class ProductSpecifications extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ProductSpecifications";
	
	public static final String ALIAS_ID = "规格集ID";
	 
	public static final String ALIAS_PRODUCT_CODE = "商品编码";
	
	public static final String ALIAS_PRODUCT_ID = "产品ID";
	
	public static final String ALIAS_COST = "成本价";
	
	public static final String ALIAS_MARKET_PRICE = "市场价";
	
	public static final String ALIAS_PRICE = "销售价";
	
	public static final String ALIAS_STOCK = "库存";
	
	//date formats
	
	//columns START
    /**
     * 规格集ID       db_column: id  
     * 
     * 
     * 
     */	
	@PrimaryKey
	@Id
	private java.lang.Long id;
    /**
     * 商品编码       db_column: product_code  
     * 
     * 
     * @Length(max=100)
     */	
	@Column("product_code")
	private java.lang.String productCode;
	

    /**
     * 产品ID       db_column: product_id  
     * 
     * 
     * 
     */
	@Column("product_id")
	private java.lang.Long productId;
    /**
     * 成本价       db_column: cost  
     * 
     * 
     * 
     */
	@Column("cost")
	private BigDecimal cost;
    /**
     * 市场价       db_column: market_price  
     * 
     * 
     * 
     */
	@Column("market_price")
	private BigDecimal marketPrice;
    /**
     * 销售价       db_column: price  
     * 
     * 
     * 
     */
	@Column("price")
	private BigDecimal price;
    /**
     * 库存       db_column: stock  
     * 
     * 
     * 
     */
	@Column("stock")
	private java.lang.Integer stock;
	@Column("sellnum")
    /**
     * 销量       db_column: stock
     */
	private java.lang.Integer sellnum;
	
	@Column("maxFucoin")
	private BigDecimal maxFucoin;//最大可使用的福利币（联盟员工福利币）
	@Column("itemids")
	private String itemids;//决定sku的商品规格的id的集合，以“，”分割
	@Column("itemValues")
	private String itemValues;//决定sku的商品规格的value的集合，以“/”分割
	
	@Column("itemnames")
	private String itemnames;//决定sku的商品规格的name的集合，以“/”分割 
	
	@Column("isDelete")
	private Integer isDelete;//是否删除 0：未删除   1：已删除
	@Column("warnnum")
	private Integer warnnum;//预警值
	@Column("min_limit_num")
	private Integer minLimitNum;//起售数量
	
	@Column("outer_id")
	private String outerId;//商品外部编码
	
	private Integer promotionStatus; // 是否正在参加某项活动（0或null：没有； 1：有）
	
	/**
	 * 内购价
	 */
	@Column("internal_purchase_price")
	private BigDecimal internalPurchasePrice;

	//columns END

	

	public Integer getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(Integer promotionStatus) {
		this.promotionStatus = promotionStatus;
	}

	public String getItemValues() {
		return itemValues;
	}

	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}
	/********************以下字段仅供显示***********************/
	private Integer quantity;//库存
	private Integer lockQuantity;//锁定数量
	private Integer warnQuantity;//预警值
	private String productName; //父类商品名称
	private String productImage;//父类商品图片
	private String mainImage;//sku主图
	private Integer pSaleKbn;	//商品销售区分 0：普通/1:特省 2换领 4特享 5试用,
	private BigDecimal empPrice;//员工专享价
	private BigDecimal benefitSubsidy;// 内购券补贴标准
	
	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getLockQuantity() {
		return lockQuantity;
	}

	public void setLockQuantity(Integer lockQuantity) {
		this.lockQuantity = lockQuantity;
	}

	public Integer getWarnQuantity() {
		return warnQuantity;
	}

	public void setWarnQuantity(Integer warnQuantity) {
		this.warnQuantity = warnQuantity;
	}

	public BigDecimal getMaxFucoin() {
		return maxFucoin;
	}

	public void setMaxFucoin(BigDecimal maxFucoin) {
		this.maxFucoin = maxFucoin;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	private List<ProductSpecificationsImage> productSpecificationsImagelist;
	private List<ProductSpecificationValue> productSpecificationValue;
	
	public List<ProductSpecificationValue> getProductSpecificationValue() {
		return productSpecificationValue;
	}

	public void setProductSpecificationValue(
			List<ProductSpecificationValue> productSpecificationValue) {
		this.productSpecificationValue = productSpecificationValue;
	}

	public List<ProductSpecificationsImage> getProductSpecificationsImagelist() {
		return productSpecificationsImagelist;
	}

	public void setProductSpecificationsImagelist(
			List<ProductSpecificationsImage> productSpecificationsImagelist) {
		this.productSpecificationsImagelist = productSpecificationsImagelist;
	}
	private String secondName;//规格值的第二个字段的名字
	
	

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public ProductSpecifications(){
	}

	public Integer getWarnnum() {
		return warnnum;
	}

	public void setWarnnum(Integer warnnum) {
		this.warnnum = warnnum;
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

	public String getItemids() {
		return itemids;
	}

	public void setItemids(String itemids) {
		this.itemids = itemids;
	}

	public ProductSpecifications(
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
	public void setProductCode(java.lang.String value) {
		this.productCode = value;
	}
	
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}
	
	public java.lang.Long getProductId() {
		return this.productId;
	}
	

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public java.lang.Integer getStock() {
		return stock;
	}

	public void setStock(java.lang.Integer stock) {
		this.stock = stock;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductCode",getProductCode())
			.append("ProductId",getProductId())
			.append("Cost",getCost())
			.append("MarketPrice",getMarketPrice())
			.append("Price",getPrice())
			.append("Stock",getStock())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ProductSpecifications == false) return false;
		if(this == obj) return true;
		ProductSpecifications other = (ProductSpecifications)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public java.lang.Integer getSellnum() {
		return sellnum;
	}

	public void setSellnum(java.lang.Integer sellnum) {
		this.sellnum = sellnum;
	}

	public Integer getpSaleKbn() {
		return pSaleKbn;
	}

	public void setpSaleKbn(Integer pSaleKbn) {
		this.pSaleKbn = pSaleKbn;
	}

	public BigDecimal getInternalPurchasePrice() {
		if(null == internalPurchasePrice){
			if(null != maxFucoin && null != price){
				return price.subtract(maxFucoin);
			}
            if(null != price){
				return price;
			}
		}
		return internalPurchasePrice;
	}

	public void setInternalPurchasePrice(BigDecimal internalPurchasePrice) {
		this.internalPurchasePrice = internalPurchasePrice;
	}

	public Integer getMinLimitNum() {
		return minLimitNum == null || 0 == minLimitNum ?1 : minLimitNum;
	}

	public void setMinLimitNum(Integer minLimitNum) {
		this.minLimitNum = minLimitNum;
	}

	public String getOuterId() {
		return null == outerId? productCode:outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public BigDecimal getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(BigDecimal empPrice) {
		this.empPrice = empPrice;
	}

	public BigDecimal getBenefitSubsidy() {
		return benefitSubsidy;
	}

	public void setBenefitSubsidy(BigDecimal benefitSubsidy) {
		this.benefitSubsidy = benefitSubsidy;
	}
	
}

