package com.wode.factory.company.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.db.DBUtils;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EmpSeasonActDao;
import com.wode.factory.company.query.GiveBenefitRecordVo;
import com.wode.factory.company.service.EmpSeasonActService;
import com.wode.factory.model.EmpSeasonAct;
@Service("empSeasonActService")
public class EmpSeasonActServiceImpl extends BasePageServiceImpl<EmpSeasonAct> implements EmpSeasonActService{

	@Autowired
	EmpSeasonActDao empSeasonActDao;

	@Autowired
	DBUtils dbUtils;
	
	@Override
	public EmpSeasonAct getEmpSeasonAct(Long empId, int year, int season,Long entId) {

		EmpSeasonAct esa = new EmpSeasonAct();
		esa.setEmpId(empId); 		//员工ID
		esa.setCurYear(year+"");
		esa.setCurSeason(season+"");
		esa.setEnterpriseId(entId);
		
		List<EmpSeasonAct> rtn= this.selectByModel(esa);

		if(rtn==null || rtn.size() == 0) {
			//DB中不存在记录，则返回全零记录
			esa.setCreateDate(new Date());
			esa.setGiveCashSum(BigDecimal.ZERO);
			esa.setGiveTicketSum(BigDecimal.ZERO);
			return esa;
		} else {
			return rtn.get(0);
		}
	}

	@Override
	public int saveSeasonAct(EmpSeasonAct model, String updUser) {
		model.setUpdateDate(new Date());
		model.setUpdateUser(updUser);
		
		
		//DB保存
//		getBaseDao().saveOrUpdate(model);
		if(StringUtils.isEmpty(model.getId())) {
			//插入
			model.setId(dbUtils.CreateID());
			return this.empSeasonActDao.insertSelective(model);
		} else {
			//更新
			return this.empSeasonActDao.updateByPrimaryKeySelective(model);
		}
	}

//	@Override
//	public PageInfo<GiveBenefitRecordVo> selectPageInfo(
//			GiveBenefitRecordVo giveBenefitRecordVo) {
//		return empSeasonActDao.selectPageInfo(giveBenefitRecordVo);
//	}

	@Override
	public List<GiveBenefitRecordVo> selectOneByModel(
			GiveBenefitRecordVo giveBenefitRecordVo) {
		return empSeasonActDao.selectOneByModel(giveBenefitRecordVo);
	}

	@Override
	protected BasePageDao<EmpSeasonAct> getBaseDao() {
		return empSeasonActDao;
	}


	@Override
	public PageInfo<GiveBenefitRecordVo> selectPageInfo(
			GiveBenefitRecordVo giveBenefitRecordVo) {
		return empSeasonActDao.selectPageInfo(giveBenefitRecordVo);
	}

}
