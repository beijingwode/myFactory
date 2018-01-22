package com.wode.factory.vo;

import java.io.Serializable;


public class ProductAttributeVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	private Integer ismust;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getIsmust() {
		return ismust;
	}
	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
	}
	
}
