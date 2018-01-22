package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.AttributeOption;
import com.wode.factory.model.Parameter;




public interface ParameterDao {

	/**
	 * 
	 * 功能说明：根据参数组id查询参数值
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	List<Parameter> findList(Long id);

	/**
	 * 
	 * 功能说明：删除多个属性项
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param ids
	 * @return
	 */
	Integer deleteList(List<String> ids);

	/**
	 * 
	 * 功能说明：批量更新参数值
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param listParam
	 * @return
	 */
	Integer batchUpdate(List<Parameter> listParam);

	/**
	 * 
	 * 功能说明：批量参数值
	 * 日期:	2015年7月29日
	 * 开发者:宋艳垒
	 *
	 * @param listParam
	 * @return
	 */
	Integer batchAdd(List<Parameter> listParam);

	/**
	 * 
	 * 功能说明：根据组id删除属性值
	 * 日期:	2015年7月31日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	Integer delByGroupId(Long id);
	
}