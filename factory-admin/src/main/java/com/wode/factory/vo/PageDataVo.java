package com.wode.factory.vo;

public class PageDataVo {
	
	private Long pageTypeId;
	
	private String page;
	
	public Long getPageTypeId() {
		return pageTypeId;
	}

	public void setPageTypeId(Long pageTypeId) {
		this.pageTypeId = pageTypeId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	private int channelId;
	
}
