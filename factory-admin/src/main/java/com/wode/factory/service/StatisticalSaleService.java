package com.wode.factory.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.StatisticalSale;

/**
 *
 */
public interface StatisticalSaleService extends FactoryEntityService<StatisticalSale> {

	PageInfo<StatisticalSale> findList(Map<String, Object> params);

}
