package com.wode.tongji.model;

import java.util.List;

public class UserGeoInfo {
	/**
	 * 全部城市信息(城市名称+城市对应的注册量)
	 */
	public List<CityInfo> cityInfo;
	/**
	 * 最大几个城市的信息
	 */
	public List<CityInfo> maxCityInfo;
	/**
	 * 最大注册量
	 */
	public Long maxRegistNumber;
	/**
	 * 最小注册量
	 */
	public Long minRegistNumber;

	public List<CityInfo> getMaxCityInfo() {
		return maxCityInfo;
	}
	public void setMaxCityInfo(List<CityInfo> maxCityInfo) {
		this.maxCityInfo = maxCityInfo;
	}
	public Long getMinRegistNumber() {
		return minRegistNumber;
	}
	public void setMinRegistNumber(Long minRegistNumber) {
		this.minRegistNumber = minRegistNumber;
	}
	public Long getMaxRegistNumber() {
		return maxRegistNumber;
	}
	public void setMaxRegistNumber(Long maxRegistNumber) {
		this.maxRegistNumber = maxRegistNumber;
	}
	public List<CityInfo> getCityInfo() {
		return cityInfo;
	}
	public void setCityInfo(List<CityInfo> cityInfo) {
		this.cityInfo = cityInfo;
	}
	
	
}
