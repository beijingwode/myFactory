package com.wode.factory.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProductParameterVo implements Serializable{
	private Long parentId;
	
	private Long ProductId;
	
	public Integer getIsmust() {
		return ismust;
	}

	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
	}

	public ProductParameterVo(Long parentId, Long productId, String name,
			Integer ismust, List<String> parValueList, String parVal) {
		super();
		this.parentId = parentId;
		ProductId = productId;
		this.name = name;
		this.ismust = ismust;
		this.parValueList = parValueList;
		this.parVal = parVal;
	}

	private String name;
	private Integer ismust;
	private List<String> parValueList= new ArrayList<String>();
	
	public ProductParameterVo() {
	}

	public List<String> getParValueList() {
		return parValueList;
	}

	public void setParValueList(List<String> parValueList) {
		this.parValueList = parValueList;
	}

	public Long getProductId() {
		return ProductId;
	}

	public void setProductId(Long productId) {
		ProductId = productId;
	}

	public String getParVal() {
		return parVal;
	}

	public void setParVal(String parVal) {
		this.parVal = parVal;
	}

	private String parVal;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}