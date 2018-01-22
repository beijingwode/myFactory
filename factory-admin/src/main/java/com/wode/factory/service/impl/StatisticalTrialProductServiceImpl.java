package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.StatisticalTrialProductDao;
import com.wode.factory.model.StatisticalSale;
import com.wode.factory.model.StatisticalTrialProduct;
import com.wode.factory.service.StatisticalTrialProductService;

import cn.org.rapid_framework.page.Page;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("statisticalTrialProductService")
public class StatisticalTrialProductServiceImpl extends FactoryEntityServiceImpl<StatisticalTrialProduct> implements StatisticalTrialProductService {
	@Autowired
	StatisticalTrialProductDao dao;
	//注入上商品使用Dao
	@Autowired
	private StatisticalTrialProductDao statisticalTrialProductDao;
	
	@Override
	public StatisticalTrialProductDao getDao() {
		return dao;
	}


	@Override
	public Long getId(StatisticalTrialProduct entity) {
		return entity.getId();
	}

	@Override
	public void setId(StatisticalTrialProduct entity, Long id) {
		entity.setId(id);
	}
	
	//查询商品试用的Service
	@Override
	public PageInfo<StatisticalTrialProduct> findList(Map<String, Object> params) {
		PageHelper.startPage(params);
		if("1".equals(params.get("noEmpty"))) {
			List<StatisticalTrialProduct> orList = statisticalTrialProductDao.selectByModel(params);
			return new PageInfo<StatisticalTrialProduct>(orList);
		} else {
			List<StatisticalTrialProduct> orList = statisticalTrialProductDao.selectByModelEmpty(params);
			return new PageInfo<StatisticalTrialProduct>(orList);
		}
	}
}
