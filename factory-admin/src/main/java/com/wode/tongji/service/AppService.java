package com.wode.tongji.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.tongji.model.App;


public interface AppService{

	/**
	 * 分页查询App列表数据
	 * @param params
	 * @return
	 */
	public PageInfo<App> findAllApp(Map<String, Object> params);

	/**
	 * 保存App
	 * @param app
	 * @return
	 */
	public int saveOrUpdate(App app);

	/**
	 * 根据ID删除一条数据
	 * @param id
	 * @return
	 */
	public int deleteApp(Long id);

	/**
	 * 根据主键查询App
	 * @param id
	 * @return
	 */
	public App selectByPrimaryKey(Long id);
	
	public App selectByUrl(String url);
	
}
