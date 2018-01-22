/**
 * 
 */
package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Attribute;
import com.wode.factory.model.AttributeOption;
import com.wode.factory.vo.AttributeVo;


/**
 * 
 * <pre>
 * 功能说明: 商品属性
 * 日期:	2015年1月23日
 * 开发者:	王向阳
 * 
 * 历史记录
 *    修改内容：
 *    修改人员：
 *    修改日期： 2015年1月23日
 * </pre>
 */
public interface AttributeService{
	

	/**
	 * 
	 * 功能说明：查询属性列表
	 * 日期:	2015年7月24日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	PageInfo<AttributeVo> findList(Map<String, Object> params);

	/**
	 * 
	 * 功能说明：根据id查询
	 * 日期:	2015年7月27日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	AttributeVo getById(Long id);

	/**
	 * 
	 * 功能说明：删除属性
	 * 日期:	2015年7月27日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 
	 * 功能说明：添加
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @return
	 */
	Integer add(Attribute pojo);

	/**
	 * 
	 * 功能说明：更新
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @return
	 */
	Integer update(Attribute pojo);
	
	/**
	 * 
	 * 功能说明：根据属性id查询属性项
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	List<AttributeOption> selectAttrValue(Long id);


	/**
	 * 
	 * 功能说明：批量更新属性项
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param listPojo
	 * @return
	 */
	Integer optionUpdate(List<AttributeOption> listPojo);

	/**
	 * 
	 * 功能说明：批量添加属性项
	 * 日期:	2015年7月29日
	 * 开发者:宋艳垒
	 *
	 * @param listOption
	 * @return
	 */
	Integer optionAdd(List<AttributeOption> listOption);

	/**
	 * 
	 * 功能说明：删除属性项
	 * 日期:	2015年7月31日
	 * 开发者:宋艳垒
	 *
	 * @param ids
	 * @return
	 */
	Integer optionDelete(List<String> ids);
	
}
