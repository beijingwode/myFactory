package com.wode.factory.company.service;

import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.EmpTradeFlowVo;
import com.wode.factory.model.EmpBenefitFlow;


public interface EmpBenefitFlowService extends BasePageService<EmpBenefitFlow> {
	/**
	 * 分页查询员工福利发放记录
	 * @param giveBenefitRecordVo
	 * @return
	 */
	public PageInfo<EmpTradeFlowVo> selectPageInfo(EmpTradeFlowVo empTradeFlowVo);
	public PageInfo<EmpTradeFlowVo> selectEmpBenefitFlow(EmpTradeFlowVo empTradeFlowVo);
	/**
	 * 分页查询员工交易流水
	 * @param giveBenefitRecordVo
	 * @return
	 */
	public PageInfo<EmpTradeFlowVo> selectPageInfoAll(EmpTradeFlowVo empTradeFlowVo);

	/**
	 * 根据订单id查询订单实付金额
	 * @param id
	 * @return
	 */
	public BigDecimal selectOrderRealPrice(Long id);
	public BigDecimal selectOrderTotalProduct(Long id);
}
