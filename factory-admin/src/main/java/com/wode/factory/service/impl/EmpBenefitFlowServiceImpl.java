package com.wode.factory.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.EmpBenefitFlowDao;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.service.EmpBenefitFlowService;
import com.wode.factory.vo.EmpBenefitFlowVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("empBenefitFlowService")
public class EmpBenefitFlowServiceImpl extends EntityServiceImpl<EmpBenefitFlow,Long> implements EmpBenefitFlowService {

	@Autowired
	EmpBenefitFlowDao empBenefitFlowDao;

	@Override
	public EmpBenefitFlowDao getDao() {
		return empBenefitFlowDao;
	}

	@Override
	public void insert(EmpBenefitFlow entity) {
		empBenefitFlowDao.insert(entity);
	}

	@Override
	public List<EmpBenefitFlow> selectCashByUserId(Long userId) {
		EmpBenefitFlow query = new EmpBenefitFlow();
		query.setEmpId(userId);
		query.setCash(BigDecimal.ZERO);
		return empBenefitFlowDao.selectByModel(query);
	}

	@Override
	public PageInfo<EmpBenefitFlowVo> findList(Map<String, Object> params) {

		PageHelper.startPage(params);
		List<EmpBenefitFlowVo> list = empBenefitFlowDao.selectByMap(params);
		return new PageInfo<EmpBenefitFlowVo>(list);
	}

}
