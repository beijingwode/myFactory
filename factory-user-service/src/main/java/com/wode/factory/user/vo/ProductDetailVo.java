package com.wode.factory.user.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wode.factory.model.ProductCategory;
import com.wode.factory.model.ProductSpecificationValue;
import com.wode.factory.vo.ProductSpecificationsImgVo;
import com.wode.factory.vo.ProductVo;
import com.wode.factory.vo.SupplierShopVo;

public class ProductDetailVo extends ProductVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Map<String,List<ProductSpecificationValue>> smap;//规格
	
	private Map<String,List<ProductSpecificationsImgVo>> psiMap;//图片集合
	
	/**
	 * 商家信息
	 */
	private SupplierShopVo supplierShopVo;
	
	/**
	 * 商品相关分类
	 */
	private List<ProductCategory> pcList;
	
	private boolean isQuestioned =true;//是否回答过调查问卷
	
	public Map<String, List<ProductSpecificationValue>> getSmap() {
		return smap;
	}
	public void setSmap(Map<String, List<ProductSpecificationValue>> smap) {
		this.smap = smap;
	}
	
	public SupplierShopVo getSupplierShopVo() {
		return supplierShopVo;
	}
	public void setSupplierShopVo(SupplierShopVo supplierShopVo) {
		this.supplierShopVo = supplierShopVo;
	}
	public List<ProductCategory> getPcList() {
		return pcList;
	}
	public void setPcList(List<ProductCategory> pcList) {
		this.pcList = pcList;
	}
	public Map<String, List<ProductSpecificationsImgVo>> getPsiMap() {
		return psiMap;
	}
	public void setPsiMap(Map<String, List<ProductSpecificationsImgVo>> psiMap) {
		this.psiMap = psiMap;
	}
	public boolean getIsQuestioned() {
		return isQuestioned;
	}
	public void setIsQuestioned(boolean isQuestioned) {
		this.isQuestioned = isQuestioned;
	}
	
}
