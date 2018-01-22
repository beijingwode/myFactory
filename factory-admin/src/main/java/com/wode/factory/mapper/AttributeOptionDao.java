package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.Attribute;
import com.wode.factory.model.AttributeOption;




public interface AttributeOptionDao {

	/**
	 * 
	 * 功能说明：根据属性id查询属性值
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	List<AttributeOption> findList(Long id);

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
	 * 功能说明：批量更新属性项
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param listPojo
	 * @return
	 */
	Integer batchUpdate(List<AttributeOption> listPojo);

	/**
	 * 
	 * 功能说明：批量添加属性项
	 * 日期:	2015年7月29日
	 * 开发者:宋艳垒
	 *
	 * @param listOption
	 * @return
	 */
	Integer batchAdd(List<AttributeOption> listOption);
	
}