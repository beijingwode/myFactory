package com.wode.factory.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2440719653568294975L;
	/**
	 * 
	 */
	private int quantity;// 购买数量
	private String partNumber;//商品SKUID
	private BigDecimal price;//价格
	private Long supplierId;//供应商ID
	private String supplierName;//供应商名称
	private Long productId;//商品ID
	private String productName;//商品名称
	private String imagePath;//商品图片
	private List<String> specificationList;//规格 数据格式：颜色_红色，尺码_XL
	private boolean buyFlag = true;//是否现在购买
	private BigDecimal freight;//运费
	private BigDecimal companyTicket = BigDecimal.ZERO;//使用企业券数量
	private BigDecimal benefitTicket = BigDecimal.ZERO;//使用优惠券数量
	private BigDecimal benefitSelf = BigDecimal.ZERO;//员工资格福利
	private BigDecimal benefitAmount = BigDecimal.ZERO;//使用优惠券抵扣金额
	private BigDecimal maxCompanyTicket;//最大使用企业券数量  Integer
	private BigDecimal realAmount = BigDecimal.ZERO;//使用企业券数量
	private Long promotionId;//活动Id
	private Long promotionProductId;//活动商品Id
	private Integer stock;//库存
	private Long time;//活动商品Id
	private Integer saleKbn;	//销售区分 0：普通/1:特省/2:换领,
	private String productCode;//条形码
	private String itemValues;//规格值
	private Long shopId;//店铺id
	private BigDecimal realPrice;//内购价
	private boolean isLadder;//是否内购价是阶梯价
	
	private String pageKey;//商品来自的页面
	private String fromType;//商品第一次添加到购物的登录设备，weixin,app,pc;
	
	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getPageKey() {
		return pageKey;
	}

	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Long getPromotionProductId() {
		return promotionProductId;
	}

	public void setPromotionProductId(Long promotionProductId) {
		this.promotionProductId = promotionProductId;
	}

	public BigDecimal getCompanyTicket() {
		return companyTicket;
	}

	public void setCompanyTicket(BigDecimal companyTicket) {
		this.companyTicket = companyTicket;
	}

	public BigDecimal getMaxCompanyTicket() {
		return maxCompanyTicket;
	}

	public void setMaxCompanyTicket(BigDecimal maxCompanyTicket) {
		this.maxCompanyTicket = maxCompanyTicket;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<String> getSpecificationList() {
		return specificationList;
	}

	public void setSpecificationList(List<String> specificationList) {
		this.specificationList = specificationList;
	}

	public boolean isBuyFlag() {
		return buyFlag;
	}

	public void setBuyFlag(boolean buyFlag) {
		this.buyFlag = buyFlag;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Integer getSaleKbn() {
		return saleKbn;
	}

	public void setSaleKbn(Integer saleKbn) {
		this.saleKbn = saleKbn;
	}


	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getItemValues() {
		return itemValues;
	}

	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}

	public BigDecimal getBenefitSelf() {
		return benefitSelf;
	}

	public void setBenefitSelf(BigDecimal benefitSelf) {
		this.benefitSelf = benefitSelf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((partNumber == null) ? 0 : partNumber.hashCode());
		return result;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public BigDecimal getBenefitAmount() {
		return benefitAmount;
	}

	public void setBenefitAmount(BigDecimal benefitAmount) {
		this.benefitAmount = benefitAmount;
	}

	public BigDecimal getBenefitTicket() {
		return benefitTicket;
	}

	public void setBenefitTicket(BigDecimal benefitTicket) {
		this.benefitTicket = benefitTicket;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		return true;
	}

	public BigDecimal getRealPrice() {
		if(null == realPrice){
			if(null != maxCompanyTicket && null != price){
				return price.subtract(maxCompanyTicket);
			}
            if(null != price){
				return price;
			}
		}
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public boolean getIsLadder() {
		return isLadder;
	}

	public void setIsLadder(boolean isLadder) {
		this.isLadder = isLadder;
	}
	
	
}
