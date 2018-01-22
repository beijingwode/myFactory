package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.StatisticalFirstOrderDao;
import com.wode.factory.model.StatisticalFirstOrder;
import com.wode.factory.service.StatisticalFirstOrderService;

/**
 * Created by zoln on 2015/7/24.
 */
@Service("statisticalFirstOrderService")
public class StatisticalFirstOrderServiceImpl extends FactoryEntityServiceImpl<StatisticalFirstOrder> implements StatisticalFirstOrderService {
	@Autowired
	StatisticalFirstOrderDao dao;
	//注入销售额的Dao接口
	@Autowired
	private StatisticalFirstOrderDao statisticalFirstOrderDao;
	@Override
	public StatisticalFirstOrderDao getDao() {
		return dao;
	}


	@Override
	public Long getId(StatisticalFirstOrder entity) {
		return entity.getId();
	}

	@Override
	public void setId(StatisticalFirstOrder entity, Long id) {
		entity.setId(id);
	}

	@Override
	public PageInfo<StatisticalFirstOrder> countSupplierByModel(Map<String, Object> params) {
		// 设置分页条件
		PageHelper.startPage(params);
		//进行查询
		if("1".equals(params.get("noEmpty"))) {
			List<StatisticalFirstOrder> orList = statisticalFirstOrderDao.countSupplierByModel(params);
			return new PageInfo<StatisticalFirstOrder>(orList);
		} else {
			List<StatisticalFirstOrder> orList = statisticalFirstOrderDao.countSupplierEmpty(params);
			return new PageInfo<StatisticalFirstOrder>(orList);			
		}
	}


	@Override
	public PageInfo<StatisticalFirstOrder> countEnterpriseByModel(Map<String, Object> params) {
		// 设置分页条件
		PageHelper.startPage(params);
		//进行查询
		
		if("1".equals(params.get("noEmpty"))) {
			List<StatisticalFirstOrder> orList = statisticalFirstOrderDao.countEnterpriseByModel(params);
			return new PageInfo<StatisticalFirstOrder>(orList);
		} else {
			List<StatisticalFirstOrder> orList = statisticalFirstOrderDao.countEnterpriseEmpty(params);
			return new PageInfo<StatisticalFirstOrder>(orList);			
		}
	}


	@Override
	public PageInfo<StatisticalFirstOrder> findList(Map<String, Object> params) {
		// 设置分页条件
				PageHelper.startPage(params);
				//进行查询
				List<StatisticalFirstOrder> orList = statisticalFirstOrderDao.countEnterpriseByModel(params);
				return new PageInfo<StatisticalFirstOrder>(orList);
			}
}
