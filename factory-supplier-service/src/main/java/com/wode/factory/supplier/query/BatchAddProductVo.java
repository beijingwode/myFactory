package com.wode.factory.supplier.query;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ApprProduct;


//public class BatchAddProductVo extends Product {
public class BatchAddProductVo extends ApprProduct {
	/**
	 * 
	 */
	private static final long serialVersionUID = 647701926020462183L;
	//商品规格
	public List<BatchAddProductSku> sku;
	//是否已经添加
	public Boolean isAdd=false;
	//是否能够添加
	public Boolean isCouldAdd=true;
	//商品分类
	public String productCategory;
	//运费模板
	public String shippingTemplate;
	//商品编号
	public String productNumber;
	//商品详情图app图片
	public List<String> appImages;
	//商品详情图pc图片
	public List<String> pcImages;
	
//	//成功件数
//	public Integer successNumber;
//	//总件数件数
//	public Integer totalNumber;
	public Boolean getIsCouldAdd() {
		return isCouldAdd;
	}

	public List<String> getAppImages() {
		return appImages;
	}

	public void setAppImages(List<String> appImages) {
		this.appImages = appImages;
	}

	public List<String> getPcImages() {
		return pcImages;
	}

	public void setPcImages(List<String> pcImages) {
		this.pcImages = pcImages;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getShippingTemplate() {
		return shippingTemplate;
	}

	public void setShippingTemplate(String shippingTemplate) {
		this.shippingTemplate = shippingTemplate;
	}

	public List<BatchAddProductSku> getSku() {
		return sku;
	}


	public void setSku(List<BatchAddProductSku> sku) {
		this.sku = sku;
	}


	public void setIsCouldAdd(Boolean isCouldAdd) {
		this.isCouldAdd = isCouldAdd;
	}
 

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
}
