package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.ParameterGroup;
import com.wode.factory.vo.ParameterGroupVo;





public interface ParameterGroupDao {
	 /**
	 * 功能说明：查询参数
	 * 日期:	2015年7月30日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param map
	 * @return
	 */
	public List<ParameterGroupVo> selectParameterGroup(Map<String,Object> map);
	/**
	 * 功能说明：参数类别
	 * 日期:	2015年7月30日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public List<ParameterGroupVo> selectParameterGorpCategory();
	/**
	 * 功能说明：根据主键id查询
	 * 日期:	2015年7月30日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public ParameterGroupVo selectById(Long id);
	/**
	 * 功能说明：删除
	 * 日期:	2015年7月30日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id);
	/**
	 * 功能说明：添加
	 * 日期:	2015年7月30日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param parameterGroup
	 * @return
	 */
	public Integer insertParameterGorp(ParameterGroup parameterGroup);
	/**
	 * 功能说明：修改
	 * 日期:	2015年7月30日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param parameterGroup
	 * @return
	 */
	public Integer updateById(ParameterGroup parameterGroup);

}