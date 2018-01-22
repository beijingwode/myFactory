package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.StatisticalExchangeProductDao;
import com.wode.factory.model.StatisticalExchangeProduct;
import com.wode.factory.model.StatisticalFirstOrder;
import com.wode.factory.model.StatisticalManagerResult;
import com.wode.factory.service.StatisticalExchangeProductService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("statisticalExchangeProductService")
public class StatisticalExchangeProductServiceImpl extends FactoryEntityServiceImpl<StatisticalExchangeProduct> implements StatisticalExchangeProductService {
	@Autowired
	StatisticalExchangeProductDao dao;
	
	@Autowired
	private StatisticalExchangeProductDao statisticalExchangeProductDao;
	
	@Override
	public StatisticalExchangeProductDao getDao() {
		return dao;
	}


	@Override
	public Long getId(StatisticalExchangeProduct entity) {
		return entity.getId();
	}

	@Override
	public void setId(StatisticalExchangeProduct entity, Long id) {
		entity.setId(id);
	}
	
	//查询列表
	@Override
	public PageInfo<StatisticalExchangeProduct> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		
		if("1".equals(params.get("noEmpty"))) {
			List<StatisticalExchangeProduct> ordersList = statisticalExchangeProductDao.selectByModel(params);
			return new PageInfo<StatisticalExchangeProduct>(ordersList);
		} else {
			List<StatisticalExchangeProduct> ordersList = statisticalExchangeProductDao.selectByModelEmpty(params);
			return new PageInfo<StatisticalExchangeProduct>(ordersList);	
		}
	}
}
