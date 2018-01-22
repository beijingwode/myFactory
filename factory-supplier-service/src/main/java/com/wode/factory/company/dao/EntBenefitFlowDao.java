package com.wode.factory.company.dao;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.EntBenefitFlowVo;
import com.wode.factory.model.EntBenefitFlow;

public interface EntBenefitFlowDao extends BasePageDao<EntBenefitFlow> {
	PageInfo<EntBenefitFlowVo> selectPageInfo(EntBenefitFlowVo entBenefitFlowVo);
	PageInfo<EntBenefitFlowVo> findCashPage(EntBenefitFlowVo entBenefitFlowVo);
}