package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.StatisticalManagerResultDao;
import com.wode.factory.model.StatisticalManagerResult;
import com.wode.factory.service.StatisticalManagerResultService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("statisticalManagerResultService")
public class StatisticalManagerResultServiceImpl extends FactoryEntityServiceImpl<StatisticalManagerResult>	implements StatisticalManagerResultService {
	@Autowired
	StatisticalManagerResultDao dao;
	@Autowired
	private StatisticalManagerResultDao statisticalManagerResultDao;

	@Override
	public StatisticalManagerResultDao getDao() {
		return dao;
	}

	@Override
	public Long getId(StatisticalManagerResult entity) {
		return entity.getId();
	}

	@Override
	public void setId(StatisticalManagerResult entity, Long id) {
		entity.setId(id);
	}

	@Override
	public PageInfo<StatisticalManagerResult> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<StatisticalManagerResult> ordersList = statisticalManagerResultDao.selectByModel(params);
		return new PageInfo<StatisticalManagerResult>(ordersList);
	}
}