/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.dao.EmpBenefitFlowDao;
import com.wode.factory.company.query.EmpTradeFlowVo;
import com.wode.factory.model.EmpBenefitFlow;
import com.wode.factory.model.UserBalance;

@Repository("empBenefitFlowDao")
public class EmpBenefitFlowDaoImpl extends BasePageDaoImpl<EmpBenefitFlow> implements EmpBenefitFlowDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EmpBenefitFlowMapper";
	}

	@Override
	public PageInfo<EmpTradeFlowVo> selectPageInfo(EmpTradeFlowVo empTradeFlowVo) {
		 List<EmpTradeFlowVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectPageInfo", empTradeFlowVo, new RowBounds(empTradeFlowVo.getPageNumber(), empTradeFlowVo.getPageSize()));
		 return new PageInfo<EmpTradeFlowVo>(list);
	}

	@Override
	public PageInfo<EmpTradeFlowVo> selectPageInfoAll(
			EmpTradeFlowVo empTradeFlowVo) {
		// TODO Auto-generated method stub
		 List<EmpTradeFlowVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectPageInfoAll", empTradeFlowVo, new RowBounds(empTradeFlowVo.getPageNumber(), empTradeFlowVo.getPageSize()));
		 return new PageInfo<EmpTradeFlowVo>(list);
	}

	@Override
	public BigDecimal selectOrderRealPrice(Long id) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectOrderRealPrice", id);
	}

	@Override
	public void saveOrUpdate(EmpBenefitFlow entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal selectOrderTotalProduct(Long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".selectOrderTotalProduct", id);
	}
	
	@Override
	public Long getId(EmpBenefitFlow model) {
		return model.getId();
	}

}
