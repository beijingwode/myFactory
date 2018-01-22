package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.EntBenefitFlowDao;
import com.wode.factory.mapper.FactoryBaseDao;
import com.wode.factory.model.EntBenefitFlow;
import com.wode.factory.service.EntBenefitFlowService;
import com.wode.factory.vo.EntBenefitFlowVo;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("entBenefitFlowService")
public class EntBenefitFlowServiceImpl extends FactoryEntityServiceImpl<EntBenefitFlow> implements EntBenefitFlowService {

	@Autowired
	EntBenefitFlowDao dao;

	@Override
	public PageInfo<EntBenefitFlowVo> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<EntBenefitFlowVo> ordersList = dao.findList(params);
		return new PageInfo(ordersList);
	}


	@Override
	public Long getId(EntBenefitFlow entity) {
		return entity.getId();
	}

	@Override
	public void setId(EntBenefitFlow entity, Long id) {
		entity.setId(id);
	}


	@Override
	public FactoryBaseDao<EntBenefitFlow> getDao() {
		return dao;
	}

}
