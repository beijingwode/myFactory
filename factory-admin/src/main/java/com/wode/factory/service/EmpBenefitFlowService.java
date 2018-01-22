package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.vo.EmpBenefitFlowVo;

/**
 *
 */
public interface EmpBenefitFlowService extends EntityService<EmpBenefitFlow,Long> {
	void insert(EmpBenefitFlow entity);

	public List<EmpBenefitFlow> selectCashByUserId(Long userId);
	public PageInfo<EmpBenefitFlowVo> findList(Map<String, Object> params);
}
