package com.wode.factory.company.dao;

import java.math.BigDecimal;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.query.EmpTradeFlowVo;
import com.wode.factory.model.EmpBenefitFlow;

public interface EmpBenefitFlowDao extends BasePageDao<EmpBenefitFlow> {

	/**
	 * 根据订单id查询订单实付金额
	 * @param id
	 * @return
	 */
	public BigDecimal selectOrderRealPrice(Long id);
	/**
	 * 根据订单id查询订单订单总额
	 * @param id
	 * @return
	 */
	public BigDecimal selectOrderTotalProduct(Long id);
	
	public PageInfo<EmpTradeFlowVo> selectPageInfo(EmpTradeFlowVo empTradeFlowVo);
	/**
	 * 分页查询全部的员工交易流水信息
	 * @param empTradeFlowVo
	 * @return
	 */
	public PageInfo<EmpTradeFlowVo> selectPageInfoAll(EmpTradeFlowVo empTradeFlowVo);
}