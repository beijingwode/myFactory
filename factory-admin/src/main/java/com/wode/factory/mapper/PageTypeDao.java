package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PageTypeSetting;

public interface PageTypeDao {
	/**
	 * 功能说明：
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @param page 
	 * @return
	 */
	public List<PageTypeSetting> selectByChannelId(PageTypeSetting data);
	/**
	 * 
	 * 功能说明：添加位置
	 * 日期:	2015年6月18日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @return
	 */
	public Integer add(PageTypeSetting pojo);

	/**
	 * 
	 * 功能说明：修改位置信息
	 * 日期:	2015年6月18日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 */
	public Integer update(PageTypeSetting pojo);

	/**
	 * 
	 * 功能说明：根据id删除
	 * 日期:	2015年6月18日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 */
	public Integer delete(Long pageTypeId);

	/**
	 * 
	 * 功能说明：分页查询位置
	 * 日期:	2015年6月18日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	public List<PageTypeSetting> findTypeList(Map<String, Object> params);

	/**
	 * 
	 * 功能说明：根据id查询
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 * @return
	 */
	public PageTypeSetting getById(Long pageTypeId);
}