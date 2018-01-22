/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.company.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageInfo;
import com.wode.factory.company.dao.EntBenefitFlowDao;
import com.wode.factory.company.query.EntBenefitFlowVo;
import com.wode.factory.model.EntBenefitFlow;

@Repository("entBenefitFlowDao")
public class EntBenefitFlowDaoImpl extends BasePageDaoImpl<EntBenefitFlow> implements EntBenefitFlowDao{

	@Override
	public String getIbatisMapperNamesapce() {
		return "EntBenefitFlowMapper";
	}

	@Override
	public PageInfo<EntBenefitFlowVo> selectPageInfo(
			EntBenefitFlowVo entBenefitFlowVo) {
		 List<EntBenefitFlowVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".selectPageInfo", entBenefitFlowVo, new RowBounds(entBenefitFlowVo.getPageNumber(), entBenefitFlowVo.getPageSize()));
		return new PageInfo<EntBenefitFlowVo>(list);

	}
	@Override
	public void saveOrUpdate(EntBenefitFlow entity) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getId(EntBenefitFlow model) {
		return model.getId();
	}

	@Override
	public PageInfo<EntBenefitFlowVo> findCashPage(EntBenefitFlowVo entBenefitFlowVo) {

		 List<EntBenefitFlowVo> list = getSqlSession().selectList(getIbatisMapperNamesapce()+".findCashPage", entBenefitFlowVo, new RowBounds(entBenefitFlowVo.getPageNumber(), entBenefitFlowVo.getPageSize()));
		return new PageInfo<EntBenefitFlowVo>(list);
	}
}
