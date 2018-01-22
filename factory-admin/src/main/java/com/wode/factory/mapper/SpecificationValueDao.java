package com.wode.factory.mapper;

import java.util.List;

import com.wode.factory.model.SpecificationValue;


public interface SpecificationValueDao {
	/**
	 * 功能说明：查询规格值信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 * 
	 * @param specificationId
	 * @return
	 */
	public List<SpecificationValue> selectBySpecificationId(Long specificationId);
	/**
	 * 功能说明：删除规格值信息
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id);
	/**
	 * 功能说明：根据规格id删除对应的规格值
	 * 日期:	2015年7月23日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param specificationId
	 * @return
	 */
	public Integer deleteBySpecificationId(Long specificationId);
	/**
	 * 功能说明：批量删除
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param specificationValue
	 * @return
	 */
	public Integer deleteBatchByPrimaryKey(List<SpecificationValue> specificationValue);
	/**
	 * 功能说明：批量添加
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param specificationValue
	 * @return
	 */
	public Integer insertBatchSpecification(List<SpecificationValue> specificationValue);
	/**
	 * 功能说明：批量更新
	 * 日期:	2015年7月29日
	 * 开发者:张晨旭
	 * 版本号:1.0
	 *
	 * @param specificationValue
	 * @return
	 */
	public Integer updateBatchSpecification(List<SpecificationValue> specificationValue);

}