package com.wode.factory.company.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.GiveBenefitRecordVo;
import com.wode.factory.model.EmpSeasonAct;


public interface EmpSeasonActService extends BasePageService<EmpSeasonAct> {
	
	/**
	 * 取得员工当前季度福利发放记录
	 * @param empId
	 * @param year
	 * @param season
	 * @param entId
	 * @return
	 */
	public EmpSeasonAct getEmpSeasonAct(Long empId, int year, int season,Long entId);
	
	/**
	 * 保存员工当前季度福利发放记录
	 * @param model
	 * @param updUser
	 * @return
	 */
	public int saveSeasonAct(EmpSeasonAct model, String updUser);
	
	/**
	 * 查询一条
	 * @param giveBenefitRecordVo
	 * @return
	 */
	public List<GiveBenefitRecordVo>selectOneByModel(GiveBenefitRecordVo giveBenefitRecordVo);
	

	/**
	 * 分页查询员工福利发放记录
	 * @param giveBenefitRecordVo
	 * @return
	 */
	public PageInfo<GiveBenefitRecordVo> selectPageInfo(GiveBenefitRecordVo giveBenefitRecordVo);
}
