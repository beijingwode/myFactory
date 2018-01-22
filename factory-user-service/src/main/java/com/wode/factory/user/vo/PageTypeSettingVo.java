package com.wode.factory.user.vo;

import java.io.Serializable;
import java.util.List;

public class PageTypeSettingVo implements Serializable {
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 标题
	 */
	private java.lang.String title;
	/**
	 * 一对多关系
	 */
	private List<PageDataVo> pageDataList;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

 
	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}
 

	public List<PageDataVo> getPageDataList() {
		return pageDataList;
	}

	public void setPageDataList(List<PageDataVo> pageDataList) {
		this.pageDataList = pageDataList;
	}

}