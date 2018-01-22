package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.StatisticalBenefitDao;
import com.wode.factory.model.StatisticalBenefit;
import com.wode.factory.model.StatisticalFirstOrder;
import com.wode.factory.service.StatisticalBenefitService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("statisticalBenefitService")
public class StatisticalBenefitServiceImpl extends FactoryEntityServiceImpl<StatisticalBenefit> implements StatisticalBenefitService {
	@Autowired
	StatisticalBenefitDao dao;
	//注入查询生日福利接口
	@Autowired
	private StatisticalBenefitDao statisticalBenefitDao;
	@Override
	public StatisticalBenefitDao getDao() {
		return dao;
	}


	@Override
	public Long getId(StatisticalBenefit entity) {
		return entity.getId();
	}

	@Override
	public void setId(StatisticalBenefit entity, Long id) {
		entity.setId(id);
	}
	
	/**
	 * 查询生日福利列表Service
	 * 
	 */
	@Override
	public PageInfo<StatisticalBenefit> findList(Map<String, Object> params) {
		// 
		PageHelper.startPage(params);
		
		if("1".equals(params.get("noEmpty"))) {
			List<StatisticalBenefit> orList = statisticalBenefitDao.selectByModel(params);
			return new PageInfo<StatisticalBenefit>(orList);
		} else {
			List<StatisticalBenefit> orList = statisticalBenefitDao.selectByModelEmpty(params);
			return new PageInfo<StatisticalBenefit>(orList);			
		}
	}
}
