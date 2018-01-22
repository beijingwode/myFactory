/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.EnterpriseStructureDao;
import com.wode.factory.company.query.EnterpriseStructureVo;
import com.wode.factory.model.Enterprise;
import com.wode.factory.model.EnterpriseStructure;

@Repository("enterpriseStructureDao")
public class EnterpriseStructureDaoImpl extends BasePageDaoImpl<EnterpriseStructure> implements EnterpriseStructureDao{
	public String getIbatisMapperNamesapce() {
		return "EnterpriseStructureMapper";
	}

	/* 批量修改
	 * @see com.wode.factory.company.dao.EnterpriseStructureDao#batchUpdate(java.util.List)
	 */
	@Override
	public Integer batchUpdate(List<EnterpriseStructure> list) {
		// TODO Auto-generated method stub
		return getSqlSession().update(getIbatisMapperNamesapce()+".batchUpdate", list);
	}

	/* 批量删除
	 * @see com.wode.factory.company.dao.EnterpriseStructureDao#deleteBatchByPrimaryKey(java.util.List)
	 */
	@Override
	public Integer deleteBatchByPrimaryKey(List<EnterpriseStructure> list) {
		// TODO Auto-generated method stub
		return getSqlSession().delete(getIbatisMapperNamesapce()+".deleteBatchByPrimaryKey", list);
	}

	/* 根据企业id和关系类型查询
	 * @see com.wode.factory.company.dao.EnterpriseStructureDao#selectByEntIdAndType(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<EnterpriseStructureVo> selectByEntIdAndType(Long enterpriseId,Integer type1,Integer type2) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("enterpriseId", enterpriseId);
		map.put("type1", type1);
		map.put("type2", type2);
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByEntIdAndType", map);
	}

	@Override
	public void saveOrUpdate(EnterpriseStructure entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getId(EnterpriseStructure model) {
		return model.getId();
	}
	
}
