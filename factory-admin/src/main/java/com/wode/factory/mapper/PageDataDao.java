package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.PageData;


public interface PageDataDao {

	/**
	 * 功能说明：根据channelId(app或pc端)标示查询数据并分页
	 * 日期:	2015年6月18日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param ppId
	 * @param pages
	 * @param size,Integer pages,Integer size
	 * @return
	 */
	public List<PageData> selectByChannelId(Integer channelId);
	/**
	 * 功能说明：根据pageId批量(也可以单个删除)删除数据
	 * 日期:	2015年6月17日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pageId
	 * @return
	 */
	public Integer deleteByPageId(Integer id);
	/**
	 * 功能说明：插入数据
	 * 日期:	2015年6月17日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pageStting
	 * @return
	 */
	public Integer insertPageSetting(PageData pageStting);
	/**
	 * 功能说明：修改数据
	 * 日期:	2015年6月18日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pageStting
	 * @return
	 */
	public Integer updateById(PageData pageStting);
	/**
	 * 功能说明：根据id查询（级联查询）
	 * 日期:	2015年6月19日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public PageData selectById(Integer id);
	/**
	 * 功能说明：根据pageTypeId进行查询统计总数
	 * 日期:	2015年7月16日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pageTypeId
	 * @return
	 */
	public Integer selectCountByPageTypeId(Long pageTypeId);
	
	public Integer selectCountByLink(String link);
	/**
	 * 功能说明：根据pageTypeId查询数据
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param pageTypeId
	 * @return
	 */
	public List<PageData> selectByPageTypeId(Map<String,Object> map);
	
	/**
	 * 
	 * 功能说明：查询多条数据
	 * 日期:	2015年9月28日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	public List<PageData> getList(Map<String, Object> params);
}