package com.wode.tongji.mapper;

import java.util.List;
import java.util.Map;

import com.wode.tongji.model.App;

public interface AppMapper{

	/**
	 * 查询所有App数据
	 * @return
	 */
	List<App> findPageInfo(Map<String, Object> params);
	
	/**
	 * 新增/修改App数据
	 * @param app
	 * @return
	 */
	int insertSelective(App app);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 根据主键查询App
	 * @param id
	 * @return
	 */
	App selectByPrimaryKey(Long id);

	
	App selectByUrl(String url);

	
	/**
	 * 根据主键更新App数据
	 * @param app
	 * @return
	 */
	int updateByPrimaryKeySelective(App app);

}
