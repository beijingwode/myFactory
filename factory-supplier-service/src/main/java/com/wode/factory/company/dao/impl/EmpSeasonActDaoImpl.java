/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.dao.EmpSeasonActDao;
import com.wode.factory.company.query.GiveBenefitRecordVo;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.EmpSeasonAct;

@Repository("empSeasonActDao")
public class EmpSeasonActDaoImpl extends BasePageDaoImpl<EmpSeasonAct> implements EmpSeasonActDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EmpSeasonActMapper";
	}

	@Override
	public List<GiveBenefitRecordVo> selectOneByModel(
			GiveBenefitRecordVo giveBenefitRecordVo) {
		 List<GiveBenefitRecordVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectOneByModel", giveBenefitRecordVo, new RowBounds(giveBenefitRecordVo.getPageNumber(), giveBenefitRecordVo.getPageSize()));
		return list;
	}

	@Override
	public void saveOrUpdate(EmpSeasonAct entity) throws DataAccessException {
		if(entity.getId() == null) 
			save(entity);
		else 
			update(entity);
	}

	@Override
	public PageInfo<GiveBenefitRecordVo> selectPageInfo(
			GiveBenefitRecordVo giveBenefitRecordVo) {
		 List<GiveBenefitRecordVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectPageInfo", giveBenefitRecordVo, new RowBounds(giveBenefitRecordVo.getPageNumber(), giveBenefitRecordVo.getPageSize()));
		return new PageInfo<GiveBenefitRecordVo>(list);
	}

	@Override
	public int insertSelective(EmpSeasonAct empSeason) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(getIbatisMapperNamesapce()+".insertSelective", empSeason);
	}

	@Override
	public int updateByPrimaryKeySelective(EmpSeasonAct empSeason) {
		// TODO Auto-generated method stub
		return getSqlSession().update(getIbatisMapperNamesapce()+".updateByPrimaryKeySelective", empSeason);
	}

	@Override
	public Long getId(EmpSeasonAct model) {
		return model.getId();
	}
}
