package com.wode.factory.company.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.GiveBenefitRecordVo;
import com.wode.factory.model.EmpSeasonAct;

public interface EmpSeasonActDao extends BasePageDao<EmpSeasonAct> {
	
	public List<GiveBenefitRecordVo>selectOneByModel(GiveBenefitRecordVo giveBenefitRecordVo);
	PageInfo<GiveBenefitRecordVo> selectPageInfo(GiveBenefitRecordVo giveBenefitRecordVo);
	int insertSelective(EmpSeasonAct empSeason);
	int updateByPrimaryKeySelective(EmpSeasonAct empSeason);
}