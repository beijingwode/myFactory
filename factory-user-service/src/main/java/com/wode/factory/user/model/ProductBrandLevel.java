package com.wode.factory.user.model;

public class ProductBrandLevel {

	private Integer level;
	private String name;
	
	public ProductBrandLevel(Integer level,String name){
		this.level = level;
		this.name = name;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
