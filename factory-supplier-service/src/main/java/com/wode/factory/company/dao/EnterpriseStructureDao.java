package com.wode.factory.company.dao;

import java.util.List;

import com.wode.factory.company.query.EnterpriseStructureVo;
import com.wode.factory.model.EnterpriseStructure;

public interface EnterpriseStructureDao extends BasePageDao<EnterpriseStructure>{
	
	/**批量修改
	 * @param list
	 * @return
	 */
	Integer batchUpdate(List<EnterpriseStructure> list);
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	Integer deleteBatchByPrimaryKey(List<EnterpriseStructure> list);
	
	/**
	 * 根据企业id和关系类型，查询
	 * @param map
	 * @return
	 */
	List<EnterpriseStructureVo> selectByEntIdAndType(Long enterpriseId,Integer type1,Integer type2);
}