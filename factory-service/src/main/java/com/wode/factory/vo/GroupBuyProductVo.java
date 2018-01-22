package com.wode.factory.vo;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ProductSpecificationValue;

public class GroupBuyProductVo {
	
	private Long productId;
	private Map<String,List<ProductSpecificationValue>> smap;//规格
	private Map<String,String> skuMap;//sku
	private Map<String,String> stockMap;//库存
	private Map<String,String> strMap;//默认选中规格策略
	private Map<String,Integer> skuPurchasedNumMap;//sku团已购数量
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Map<String, List<ProductSpecificationValue>> getSmap() {
		return smap;
	}
	public void setSmap(Map<String, List<ProductSpecificationValue>> smap) {
		this.smap = smap;
	}
	public Map<String, String> getSkuMap() {
		return skuMap;
	}
	public void setSkuMap(Map<String, String> skuMap) {
		this.skuMap = skuMap;
	}
	public Map<String, String> getStockMap() {
		return stockMap;
	}
	public void setStockMap(Map<String, String> stockMap) {
		this.stockMap = stockMap;
	}
	public Map<String, String> getStrMap() {
		return strMap;
	}
	public void setStrMap(Map<String, String> strMap) {
		this.strMap = strMap;
	}
	public Map<String, Integer> getSkuPurchasedNumMap() {
		return skuPurchasedNumMap;
	}
	public void setSkuPurchasedNumMap(Map<String, Integer> skuPurchasedNumMap) {
		this.skuPurchasedNumMap = skuPurchasedNumMap;
	}
	
	
}
