package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageData;

public interface PageDataService{
	
	public PageInfo<PageData> selectByChannelId(Integer channelId, Integer pages,Integer size);
	
	public Integer deleteByPageId(Integer id);
	
	public PageData selectById(Integer id);
	
	public Integer updatePageSetting(String imgUrl,PageData pd,Long oldPageTypeId);
	
	public Integer insertPageSetting(String pageTypeIdAndPid,String page, Integer orders,String title,String link,String imagePath);
	
	public PageInfo<PageData> selectByPageTypeId(Integer pageTypeId,Integer channelId,Integer pages,Integer size);

	/**
	 * 
	 * 功能说明：查询数据
	 * 日期:	2015年9月28日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @param page
	 * @return
	 */
	public PageInfo<PageData> selectList(Map<String, Object> params);

	public Integer selectCountByLink(String link);
}
