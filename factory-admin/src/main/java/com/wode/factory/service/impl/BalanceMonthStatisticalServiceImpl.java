package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.BalanceMonthStatisticalDao;
import com.wode.factory.model.BalanceMonthStatistical;
import com.wode.factory.service.BalanceMonthStatisticalService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("balanceMonthStatisticalService")
public class BalanceMonthStatisticalServiceImpl extends FactoryEntityServiceImpl<BalanceMonthStatistical> implements BalanceMonthStatisticalService {

	@Autowired
	BalanceMonthStatisticalDao balanceMonthStatisticalMapper;
	
	@Override
	public Long getId(BalanceMonthStatistical entity) {
		return entity.getId();
	}

	@Override
	public void setId(BalanceMonthStatistical entity, Long id) {
		entity.setId(id);
	}
	
	@Override
	public BalanceMonthStatisticalDao getDao() {
		return balanceMonthStatisticalMapper;
	}
	
	@Override
	public PageInfo<BalanceMonthStatistical> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<BalanceMonthStatistical> ordersList = balanceMonthStatisticalMapper.findList(params);
		return new PageInfo<BalanceMonthStatistical>(ordersList);
	}

	
}
