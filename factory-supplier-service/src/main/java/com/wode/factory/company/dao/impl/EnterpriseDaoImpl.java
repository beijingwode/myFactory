/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.EnterpriseDao;
import com.wode.factory.company.query.EnterpriseVo;
import com.wode.factory.model.Enterprise;

@Repository("enterpriseDao")
public class EnterpriseDaoImpl extends BasePageDaoImpl<Enterprise> implements EnterpriseDao{
	public String getIbatisMapperNamesapce() {
		return "EnterpriseMapper";
	}

	/* 根据企业名称查询
	 * @see com.wode.factory.company.dao.EnterpriseDao#selectByPrimaryName(java.lang.String)
	 */
	@Override
	public List<Enterprise> selectByPrimaryName(String name) {
		return getSqlSession().selectList(getIbatisMapperNamesapce()+".selectByPrimaryName", name);
	}

	@Override
	public void saveOrUpdate(Enterprise entity) throws DataAccessException {

		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public EnterpriseVo selectById(Long id) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectById", id);
	}

	@Override
	public Integer updateByPrimaryKeySelective(Enterprise ent) {
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateByPrimaryKeySelective", ent);
	}

	@Override
	public Integer updateByPrimaryKey(Enterprise ent) {
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateByPrimaryKey", ent);
	}

	@Override
	public Integer insertSelective(EnterpriseVo entVo) {
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insertSelective", entVo);
	}

	@Override
	public Integer insert(Enterprise ent) {
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insert", ent);
	}
	


	@Override
	public Long getId(Enterprise model) {
		return model.getId();
	}

	@Override
	public Long getFirstShopId(Long id) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".getFirstShopId", id);
	}

	@Override
	public void updateEmpDefultAvatars(Map<String, Object> param) {
		getSqlSession().update(getIbatisMapperNamesapce()+".updateEmpDefultAvatars", param);
	}

	@Override
	public Enterprise findByEmailPostfix(String emailPostfix) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findByEmailPostfix", emailPostfix);
	}
}
