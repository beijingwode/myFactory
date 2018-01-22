package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.EntBenefitFlow;
import com.wode.factory.vo.EntBenefitFlowVo;

/**
 *
 */
public interface EntBenefitFlowService extends FactoryEntityService<EntBenefitFlow> {

	/**
	 * @param query 查询条件存储对象, 目前紧支持时间参数, startDate(开始时间) endDate(结束时间)
	 * @return
	 */
	public PageInfo<EntBenefitFlowVo> findList(Map<String, Object> params);
}
