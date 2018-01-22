package com.wode.factory.vo;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.entity.annotation.One;

import com.wode.factory.model.Product;
import com.wode.factory.model.ProductCategory;

public class ProductVo extends Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品属性List
	 */
	private List<ProductAttributeVo> attrList;
	
	/**
	 * 商品参数List
	 */
	private List<ProductParameterVo> paraList;
	
	
	@One(target = ProductCategory.class, field = "categoryId")
	private ProductCategory category;
	
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public List<ProductAttributeVo> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<ProductAttributeVo> attrList) {
		this.attrList = attrList;
	}
	public List<ProductParameterVo> getParaList() {
		return paraList;
	}
	public void setParaList(List<ProductParameterVo> paraList) {
		this.paraList = paraList;
	}
	
	
	
}
