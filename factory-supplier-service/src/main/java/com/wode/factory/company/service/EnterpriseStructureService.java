package com.wode.factory.company.service;

import java.util.List;

import com.wode.factory.company.query.EnterpriseStructureVo;
import com.wode.factory.model.EnterpriseStructure;

public interface EnterpriseStructureService extends BasePageService<EnterpriseStructure>{
	
	/**
	 * 添加企业的组织结构
	 * @param ent
	 * @return
	 */
	public Integer addEnterpriseStructure(EnterpriseStructure ent);
	/**
	 * 修改企业的组织结构
	 * @param ent
	 * @return
	 */
	public Integer updateBatchEnterpriseStructure(List<EnterpriseStructureVo> entUpdate);
	/**
	 * 批量删除
	 * @param ent
	 * @return
	 */
	public Integer deleteBatchEnterpriseStructure(List<EnterpriseStructure> ent);
	/**
	 * 查询企业组织信息()
	 * @param enterpriseId  企业id
	 * @param type1  
	 * @param type2  type1 or type2 都有值，查询全部的，type1有值查询具体值。(1:表示是母公司。2:表示的是子公司。3:表示的是友盟关系公司)
	 * @return
	 */
	public List<EnterpriseStructureVo> selectEnterpriseInfo(Long enterpriseId,Integer type1,Integer type2);

}
