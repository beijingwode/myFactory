package com.wode.tongji.model;

public class CityInfo {
	/**
	 * 城市名
	 */
	public String name;
	/**
	 * 城市对应的注册量
	 */
	public Long value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
}
