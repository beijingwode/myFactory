package com.wode.factory.vo;

import java.io.Serializable;


public class ProductParameterVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long parentId;
	
	private String name;
	private String value;
	private Integer ismust;
	
	public ProductParameterVo() {
	}
	

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


	public String getValue() {
		return value;
	}


	public Integer getIsmust() {
		return ismust;
	}


	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
}