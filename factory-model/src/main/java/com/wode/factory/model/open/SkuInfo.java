package com.wode.factory.model.open;

import com.wode.factory.model.OpenRequestBase;

public class SkuInfo extends OpenRequestBase{
	/**
	 * 规格集id
	 */
	private String sku_id;
	/**
	 * 商品名称
	 */
	private String p_title;
	/**
	 * 规格值集
	 */
	private String spec;
	/**
	 * 商品外部编码
	 */
	private String outer_id;
	
	/**
	 * 修改数量
	 * @return
	 */
	private Long quantity;
	
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public String getP_title() {
		return p_title;
	}
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getOuter_id() {
		return outer_id;
	}
	public void setOuter_id(String outer_id) {
		this.outer_id = outer_id;
	}
	
	
	
}
