package com.wode.factory.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.Parameter;
import com.wode.factory.vo.ParameterGroupVo;

public interface ParameterGroupService {

	public PageInfo<ParameterGroupVo> selectParameterGroup(Integer pages,
			Integer size,Long categoryId);

	public ParameterGroupVo selectById(Long id);

	public Integer deleteById(Long id);

	public Integer updateParameterGroupVo(ParameterGroupVo parVo);

	public Integer insertParameterGroupVo(ParameterGroupVo parVo);
	

	/**
	 * 
	 * 功能说明：根据参数组id查询参数值
	 * 日期:	2015年7月31日
	 * 开发者:宋艳垒
	 *
	 * @param id
	 * @return
	 */
	public List<Parameter> selectParamValue(Long id);


	/**
	 * 
	 * 功能说明：批量添加参数值
	 * 日期:	2015年7月31日
	 * 开发者:宋艳垒
	 *
	 * @param listParam
	 * @return
	 */
	public Integer paramBatchAdd(List<Parameter> listParam);

	/**
	 * 
	 * 功能说明：批量添加参数值
	 * 日期:	2015年7月31日
	 * 开发者:宋艳垒
	 *
	 * @param listParam
	 * @return
	 */
	public Integer paramBatchUpdate(List<Parameter> listParam);

	/**
	 * 
	 * 功能说明：批量删除参数值
	 * 日期:	2015年7月31日
	 * 开发者:宋艳垒
	 *
	 * @param ids
	 * @return
	 */
	public Integer paramBatchDel(List<String> ids);
	
}
