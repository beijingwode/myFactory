package com.wode.factory.company.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wode.common.util.StringUtils;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EmpBenefitFlowDao;
import com.wode.factory.company.query.EmpTradeFlowVo;
import com.wode.factory.company.service.EmpBenefitFlowService;
import com.wode.factory.model.EmpBenefitFlow;
@Service("empBenefitFlowService")
public class EmpBenefitFlowServiceImpl extends BasePageServiceImpl<EmpBenefitFlow> implements EmpBenefitFlowService{

	@Autowired
	EmpBenefitFlowDao empBenefitFlowDao;

	@Override
	public PageInfo<EmpTradeFlowVo> selectPageInfo(EmpTradeFlowVo empTradeFlowVo) {
		PageInfo<EmpTradeFlowVo> page = null;
		if(StringUtils.isNullOrEmpty(empTradeFlowVo.getStartDate())||StringUtils.isNullOrEmpty(empTradeFlowVo.getEndDate())){
			page = empBenefitFlowDao.selectPageInfo(empTradeFlowVo);
		}else{
			String endDate = empTradeFlowVo.getEndDate();
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(empTradeFlowVo.getEndDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//日期加一天  因为数据库中时间都是年-月-日 时:分:秒 格式的 查询条件是 年-月-日 格式的。
			cal.add(Calendar.DAY_OF_MONTH, 1);
			empTradeFlowVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			page = empBenefitFlowDao.selectPageInfo(empTradeFlowVo);
			empTradeFlowVo.setEndDate(endDate);
		}
		return page;
	}

	@Override
	public PageInfo<EmpTradeFlowVo> selectEmpBenefitFlow(
			EmpTradeFlowVo empTradeFlowVo) {
		return this.empBenefitFlowDao.selectPageInfo(empTradeFlowVo);
	}

	@Override
	public PageInfo<EmpTradeFlowVo> selectPageInfoAll(
			EmpTradeFlowVo empTradeFlowVo) {
		return this.empBenefitFlowDao.selectPageInfoAll(empTradeFlowVo);
	}

	@Override
	public BigDecimal selectOrderRealPrice(Long id) {
		return this.empBenefitFlowDao.selectOrderRealPrice(id);
	}

	@Override
	protected BasePageDao<EmpBenefitFlow> getBaseDao() {
		return empBenefitFlowDao;
	}

	@Override
	public BigDecimal selectOrderTotalProduct(Long id) {
		// TODO Auto-generated method stub
		return this.empBenefitFlowDao.selectOrderTotalProduct(id);
	}
	 
}
