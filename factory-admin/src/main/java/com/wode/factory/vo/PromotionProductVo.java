package com.wode.factory.vo;

import java.math.BigDecimal;

import com.wode.factory.model.PromotionProduct;

public class PromotionProductVo extends PromotionProduct {
	
	private static final long serialVersionUID = 1L;

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	// 商品名称
    private String productName;
    
    private boolean updateAble;
    
	public boolean getUpdateAble() {
		return updateAble;
	}
	public void setUpdateAble(boolean updateAble) {
		this.updateAble = updateAble;
	}
	private Long promotionId;
    
    public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
	private String joinStartStart;
    
    private String joinStartEnd;
    
	public String getJoinStartStart() {
		return joinStartStart;
	}
	public void setJoinStartStart(String joinStartStart) {
		this.joinStartStart = joinStartStart;
	}
	public String getJoinStartEnd() {
		return joinStartEnd;
	}
	public void setJoinStartEnd(String joinStartEnd) {
		this.joinStartEnd = joinStartEnd;
	}
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	// 原价
    private BigDecimal originalPrice;
    // 原价（页面显示用）
    private String showOriginalPrice;
    // 活动价（页面显示用）
    private String showPrice;
    // 促销商品上传时间（页面显示用，格式化后的时间 yyyy年MM月dd日 HH:mm:ss）
    private String formatCreateDate;
    // 成功参加活动的商品数量
    private Long joinTotal;
    
	public String getFormatCreateDate() {
		return formatCreateDate;
	}
	public void setFormatCreateDate(String formatCreateDate) {
		this.formatCreateDate = formatCreateDate;
	}
	public String getShowOriginalPrice() {
		return showOriginalPrice;
	}
	public void setShowOriginalPrice(String showOriginalPrice) {
		this.showOriginalPrice = showOriginalPrice;
	}
	public String getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice;
	}
	public Long getJoinTotal() {
		return joinTotal;
	}
	public void setJoinTotal(Long joinTotal) {
		this.joinTotal = joinTotal;
	}
	
}
