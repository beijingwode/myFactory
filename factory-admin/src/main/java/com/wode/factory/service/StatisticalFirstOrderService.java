package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.StatisticalFirstOrder;

/**
 *
 */
public interface StatisticalFirstOrderService extends FactoryEntityService<StatisticalFirstOrder> {

	PageInfo<StatisticalFirstOrder> countSupplierByModel(Map<String, Object> params);
	PageInfo<StatisticalFirstOrder> countEnterpriseByModel(Map<String, Object> params);
	PageInfo<StatisticalFirstOrder> findList(Map<String, Object> params);
}
