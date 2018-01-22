package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Attribute;
import com.wode.factory.model.PageTypeSetting;
import com.wode.factory.vo.AttributeVo;
import com.wode.factory.vo.SpecificationVo;



public interface AttributeDao {
	/**
	 * 功能说明：
	 * 日期:	2015年6月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public List<PageTypeSetting> selectByChannelId(Integer id);
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
	public Integer update(Attribute pojo);

	/**
	 * 
	 * 功能说明：根据id删除
	 * 日期:	2015年6月18日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 */
	public Integer delete(Long id);


	/**
	 * 
	 * 功能说明：根据id查询
	 * 日期:	2015年6月23日
	 * 开发者:宋艳垒
	 *
	 * @param pageTypeId
	 * @return
	 */
	public AttributeVo getById(Long id);
	
	/**
	 * 
	 * 功能说明：查询属性列表
	 * 日期:	2015年7月24日
	 * 开发者:宋艳垒
	 *
	 * @param params
	 * @return
	 */
	public List<AttributeVo> findList(Map<String, Object> params);
	
	/**
	 * 
	 * 功能说明：添加
	 * 日期:	2015年7月28日
	 * 开发者:宋艳垒
	 *
	 * @param pojo
	 * @return
	 */
	public Integer insert(Attribute pojo);
	
	/**
	 * 
	 * 功能说明：查询属性中的类别
	 * 日期:	2015年7月30日
	 * 开发者:宋艳垒
	 *
	 * @return
	 */
	public List<SpecificationVo> selectAttrCategory();
}