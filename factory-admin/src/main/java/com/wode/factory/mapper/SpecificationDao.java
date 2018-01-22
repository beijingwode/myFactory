package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.Specification;
import com.wode.factory.vo.SpecificationVo;




public interface SpecificationDao {
	/**
	 * 功能说明：查询规格信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 */
	public List<SpecificationVo> selectSpecification(Map map);
	public SpecificationVo selectById(Long id);
	/**
	 * 功能说明：删除规格信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id);
	/**
	 * 功能说明：修改规格信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param specification
	 * @return
	 */
	public Integer insertSpecification(Specification specification);
	/**
	 * 功能说明：修改规格信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param specification
	 * @return
	 */
	public Integer updateById(Specification specification);
	/**
	 * 功能说明：查询规格的全部类别
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @return
	 */
	public List<SpecificationVo> selectSpecificationCategory();

}