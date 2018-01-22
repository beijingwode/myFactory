//Powered By if, Since 2014 - 2020

package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.PageTypeSetting;


/**
 * 
 * <pre>
 * 功能说明: 
 * 日期:	2015年6月17日
 * 开发者:	宋艳垒
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年6月17日
 * </pre>
 */
public interface PageTypeService{
	
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
	 * 功能说明：分页查询位置数据
	 * 日期:	2015年6月18日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	public PageInfo<PageTypeSetting> findTypeList(Map<String, Object> params);

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

	/**
	 * 
	 * 功能说明：根据条件查询
	 * 日期:	2015年9月7日
	 * 开发者:宋艳垒
	 *
	 * @param data
	 * @return
	 */
	public List<PageTypeSetting> selectPageType(PageTypeSetting data);


}
