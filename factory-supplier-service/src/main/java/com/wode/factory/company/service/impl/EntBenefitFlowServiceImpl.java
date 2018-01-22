package com.wode.factory.company.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.dao.EntBenefitFlowDao;
import com.wode.factory.company.query.EntBenefitFlowVo;
import com.wode.factory.company.service.EntBenefitFlowService;
import com.wode.factory.model.EntBenefitFlow;

@Service("entBenefitFlowService")
public class EntBenefitFlowServiceImpl extends BasePageServiceImpl<EntBenefitFlow> implements EntBenefitFlowService{

	@Autowired
	EntBenefitFlowDao entBenefitFlowDao;

	@Override
	protected BasePageDao<EntBenefitFlow> getBaseDao() {
		return entBenefitFlowDao;
	}
	@Override
	public PageInfo<EntBenefitFlowVo> selectPageInfo(
			EntBenefitFlowVo entBenefitFlowVo) {
		PageInfo<EntBenefitFlowVo> page=null;
		if(StringUtils.isNullOrEmpty(entBenefitFlowVo.getStartDate())||StringUtils.isNullOrEmpty(entBenefitFlowVo.getEndDate())){
			page = entBenefitFlowDao.selectPageInfo(entBenefitFlowVo);
		}else{
			String endDate = entBenefitFlowVo.getEndDate();
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(entBenefitFlowVo.getEndDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//日期加一天  因为数据库中时间都是年-月-日 时:分:秒 格式的 查询条件是 年-月-日 格式的。
			cal.add(Calendar.DAY_OF_MONTH, 1);
			entBenefitFlowVo.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			page = entBenefitFlowDao.selectPageInfo(entBenefitFlowVo);
			entBenefitFlowVo.setEndDate(endDate);
		}
		return page;
	}
	
	@Override
	public PageInfo<EntBenefitFlowVo> findCashPage(EntBenefitFlowVo entBenefitFlowVo) {
		PageInfo<EntBenefitFlowVo> page=null;

		page = entBenefitFlowDao.findCashPage(entBenefitFlowVo);
		
		return page;
	}

	 
}
