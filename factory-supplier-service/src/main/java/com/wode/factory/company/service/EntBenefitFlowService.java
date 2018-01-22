package com.wode.factory.company.service;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.EntBenefitFlowVo;
import com.wode.factory.model.EntBenefitFlow;


public interface EntBenefitFlowService extends BasePageService<EntBenefitFlow> {

	/**
	 * 分页查询员工福利发放记录
	 * @param giveBenefitRecordVo
	 * @return
	 */
	public PageInfo<EntBenefitFlowVo> selectPageInfo(EntBenefitFlowVo query);
	public PageInfo<EntBenefitFlowVo> findCashPage(EntBenefitFlowVo query);
}
