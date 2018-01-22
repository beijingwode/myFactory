package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.StatisticalSaleDao;
import com.wode.factory.model.StatisticalFirstOrder;
import com.wode.factory.model.StatisticalManagerResult;
import com.wode.factory.model.StatisticalSale;
import com.wode.factory.service.StatisticalSaleService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("statisticalSaleService")
public class StatisticalSaleServiceImpl extends FactoryEntityServiceImpl<StatisticalSale> implements StatisticalSaleService {
	@Autowired
	StatisticalSaleDao dao;
	
	@Autowired
	private StatisticalSaleDao statisticalSaleDao;
	
	@Override
	public StatisticalSaleDao getDao() {
		return dao;
	}


	@Override
	public Long getId(StatisticalSale entity) {
		return entity.getId();
	}

	@Override
	public void setId(StatisticalSale entity, Long id) {
		entity.setId(id);
	}


	@Override
	public PageInfo<StatisticalSale> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		if("1".equals(params.get("noEmpty"))) {
			List<StatisticalSale> ordersList = statisticalSaleDao.selectByModel(params);
			return new PageInfo<StatisticalSale>(ordersList);
		} else {
			List<StatisticalSale> ordersList = statisticalSaleDao.selectByModelEmpty(params);
			return new PageInfo<StatisticalSale>(ordersList);		
		}
	}
}
